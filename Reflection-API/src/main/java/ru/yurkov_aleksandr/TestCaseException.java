package ru.yurkov_aleksandr;

/*
    Кажется тут лучше просто от рантаймЕксепшна отнаследоваться
    Исправлеоно
 */
public class TestCaseException extends  RuntimeException{
    private String nameCase;


    public TestCaseException(String message,String nameCase) {
        super(message);
        this.nameCase = nameCase;
    }

    public String getNameCase() {
        return nameCase;
    }
}
