package ru.yurkov_aleksandr;

import ru.yurkov_aleksandr.annotations.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;

public class TestRunner {

    private static void checkSuite(Method[] methods) throws TestCaseException {
        int beforeCount = 0;
        int afterCount = 0;
        for (Method m : methods) {
            if (m.isAnnotationPresent(BeforeSuite.class)) {
                if (m.isAnnotationPresent(Test.class)) {
                    throw new TestCaseException(" На одном методеде установлены @BeforeSuite и @Test", "BeforeSuite");
                }
                beforeCount++;
            }

            if (m.isAnnotationPresent(AfterSuite.class)) {
                if (m.isAnnotationPresent(Test.class))
                    throw new TestCaseException(" На одном методеде установлены @AfterSuite и @Test", "AfterSuite");
                afterCount++;
            }
            if (m.isAnnotationPresent(Test.class)){
                Annotation[] tmp = m.getAnnotations();
                for(Annotation a : tmp)
                    if(a instanceof Test)
                        if(((Test) a).priority() <= 0 || ((Test) a).priority() >= 11)
                            throw new TestCaseException(" Не верно указан приоритет!", m.getName());
            }
        }
        if (beforeCount > 1 || afterCount > 1)
            throw new TestCaseException(" init or destroy more than one", "BeforeSuite / AfterSuite");
    }

    /*
        А чего бы в чекСьюте сразу ексепшн не бросать, смысл сюда вытягивать булеан?
        exception вынес в метод checkSuite
        Если тестовый метод провалится, то остановятся все тесты
        исправлено: Добавлен try при выполненнии тестов.
        А где кол-во проваленных тестов?
        Добавлено обработка и ввывод проваленных тестов.
     */

    public static void run(Class<?> testSiuteClass) throws TestCaseException {
        Method[] methods = testSiuteClass.getMethods();
        checkSuite(methods);
        PerformanceTest run = new PerformanceTest(methods);
        try {
            for(Method m : run.getDisabled()){
                System.out.println("Следующие тесты будут пропущены\n" + m.getName() + " : @Disabled");
            }
            if(null != run.getBeforeSuite()){
                if(run.getBeforeSuite().isAnnotationPresent(Disabled.class))
                    System.out.println("BeforeSuite : @Disabled");
                else
                    run.getBeforeSuite().invoke(null);
            }
            for(Method m : run.getSuite()){
                try {
                    m.invoke(null);
                    run.addCount();
                }catch (Exception e){
                    run.addFailedCount();
                    System.out.println(m.getName() + " : Провален тест");
                }

            }
            if(null != run.getAfterSuite())
                if(run.getAfterSuite().isAnnotationPresent(Disabled.class))
                    System.out.println("AfterSuite : @Disabled");
                else
                    run.getAfterSuite().invoke(null);
            System.out.println(run.result());

        } catch (IllegalAccessException | InvocationTargetException e) {
            System.out.println(run.result());
            throw new TestCaseException("Тут все пошлно не по плану", "Error");
        }
    }
}

