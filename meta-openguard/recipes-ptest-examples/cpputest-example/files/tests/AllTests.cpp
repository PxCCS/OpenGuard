#include "CppUTest/CommandLineTestRunner.h"

/*  The main function of our test  files. It will run our test files */

int main(int argc, char** argv)
{
   return CommandLineTestRunner::RunAllTests(argc, argv);
}
