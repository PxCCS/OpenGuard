#include "CppUTest/TestHarness.h"
#include "cpputest-example.h"

/* Define a testgroup */
TEST_GROUP(FirstTestGroup)
{
};

/* Define the first test of the first test group
   this test will always fail
   This test will be ignored */
IGNORE_TEST(FirstTestGroup, FirstTest)
{
    FAIL("fail me!");
}

/* Define the second test of the first test group
   Examples of tests that will always fail */
TEST(FirstTestGroup, SecondTest)
{
    STRCMP_EQUAL("hello", "world");
    LONGS_EQUAL(1, 2);
}

/* Define the third test of the first test group
   this test should not fail. */
TEST(FirstTestGroup, ThirdExample)
{
    STRCMP_EQUAL("hello", "hello");
}

/* Define the third test of the first test group
   this test should not fail. This test is an example of how to test a function of your code (test_func) */
TEST(FirstTestGroup, FourthExample)
{
    int x = test_func();
    CHECK_EQUAL(1, x);
}
