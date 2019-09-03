import unittest
from additional_lib import AdditionalLib

class Test_Example(unittest.TestCase):

    __lib = None
    def __init__(self, methodName = 'runTest'):
        self.__lib = AdditionalLib()
        return super().__init__(methodName)

    def test_A(self):
        if self.__lib.GetLocalVar() != "Set":
            self.skipTest("Precondition not met")
        self.assertEqual(self.__lib.GetVar(), 1)

    def test_B(self):
        self.fail("This test will fail")

if __name__ == '__main__':
    unittest.main(verbosity=0)
