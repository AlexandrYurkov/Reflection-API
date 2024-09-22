package ru.yurkov_aleksandr;


import ru.yurkov_aleksandr.annotations.*;


public class TestSuite {

    @BeforeSuite
    public static void init (){
        System.out.println("BeforeSuite");
    }

//    @BeforeSuite
//    public static void init_two (){
//        System.out.println("BeforeSuite_two");
//    }

    @Test(priority = 10)
    public static void test_1(){
        System.out.println("Test 1");
    }

    @Test(priority = 9)
    public static void test_2(){
        System.out.println("Test 2");
    }
    @Disabled
    @Test(priority = 9)
    public static void test_3(){
        System.out.println("Test 3");
    }


    @Test(priority = 10)
    public static void test_4(){
        System.out.println("Test 4");
    }

    @Test(priority = 8)
    public static void test_5(){
        System.out.println("Test 5");
    }

    @Test(priority = 7)
    public static void test_6(){
        System.out.println("Test 6");
    }

    @Test(priority = 6)
    public static void test_7(){
        System.out.println("Test 7");
    }

    @Test(priority = 5)
    public static void test_8(){
        System.out.println("Test 8");
    }

    @Test(priority = 4)
    public static void test_9(){
        System.out.println("Test 9");
    }

    @Test(priority = 3)
    public static void test_10(){
        System.out.println("Test 10");
    }

    @Test(priority = 2)
    public static void test_11(){
        System.out.println("Test 11");
    }

    @Test(priority = 1)
    public static void test_12(){
        System.out.println("Test 12");
    }

    @Test(priority = 10)
    public static void test_16() throws Exception {
        System.out.println("Error");
        throw new Exception("Error");
    }

//    @Test(priority = 0)
//    public static void test_13(){
//        System.out.println("Test 13");
//    }

//    @Test(priority = -3)
//    public static void negative_test_14(){
//        System.out.println("Test 14");
//    }

//    @Disabled
//    @Test(priority = 15)
//    public static void test_15(){
//        System.out.println("Test 15");
//    }

    @AfterSuite
    public static void destroy(){
        System.out.println("AfterSuite");
    }

}
