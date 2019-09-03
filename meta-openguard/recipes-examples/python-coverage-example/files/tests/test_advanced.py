# -*- coding: utf-8 -*-
import unittest

from .context import coverage_example


class AdvancedTestSuite(unittest.TestCase):

    """Advanced test cases."""

    def test_thoughts(self):
        self.assertIsNone(coverage_example.hmm())


if __name__ == '__main__':
    unittest.main(verbosity=0)
