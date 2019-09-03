import unittest

class Test_Example2(unittest.TestCase):

    def test_A(self):
        self.assertEqual(1, 1)

    def test_B(self):
        self.fail("This test will fail")

if __name__ == '__main__':
    unittest.main(verbosity=0)
