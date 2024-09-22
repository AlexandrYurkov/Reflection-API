package ru.yurkov_aleksandr;

import ru.yurkov_aleksandr.annotations.*;


import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;



public class PerformanceTest {
    private final Map<Method, Integer> suite = new HashMap<>();
    private final List<Method> disabled = new ArrayList<>();
    private Method beforeSuite = null;
    private Method afterSuite = null;
    private int count = 0;
    private int failedCount = 0;
    /*
    Почему поля не приватные? | Лучше использовать интерфейс Лист
    Исправлено
    31-34 непонятно, а почему сразу не запросить ссылку на нужную аннотацию?
    Исправлено.
     */
    public PerformanceTest(Method[] methods) {
        for (Method m : methods) {
            if (m.isAnnotationPresent(Disabled.class))
                this.disabled.add(m);
            else if (m.isAnnotationPresent(BeforeSuite.class))
                this.beforeSuite = m;
            else if (m.isAnnotationPresent(AfterSuite.class))
                this.afterSuite = m;
            else if (m.isAnnotationPresent(Test.class)) {
                Test tmp = m.getAnnotation(Test.class);
                    this.suite.put(m, tmp.priority());
            }
        }
    }

    public Method getBeforeSuite() {
        return beforeSuite;
    }

    public Method getAfterSuite() {
        return afterSuite;
    }

    public List<Method> getSuite() {
        return sortPriority();
    }
    public List<Method> getDisabled(){
        return this.disabled;
    }

    public void addCount() {
        this.count += 1;
    }

    public void addFailedCount(){
        this.failedCount += 1;
    }

    private static <K, V extends Comparable<? super V>> Comparator<Map.Entry<K, V>> comparingByValue() {
        return (Comparator<Map.Entry<K, V>> & Serializable)
                (c1, c2) -> c2.getValue().compareTo(c1.getValue());
    }

    private List<Method> sortPriority(){
        return suite.entrySet()
                .stream()
                .sorted(comparingByValue())
                .map(Map.Entry::getKey)
                .toList();

    }

    public String result() throws TestCaseException {
        String result = "Пропущенно тестов: " +
                disabled.size() +
                "\n" +
                "Выполнено тестов: " +
                count +
                "\n" +
                "Провалено тестов: " +
                failedCount;
        return result;
    }


}
