"""
  summary: python-ptest for testing logins, useradds, ... with pexpect to
           verify the PAM module
           This tests are for the debug mode plattform, which is not hardened
  author:  PxCCs - HDR
  date: 2018-03-13
  version: 0.1
"""

import unittest
import subprocess

import pexpect_functions

class TestSystemUsers(unittest.TestCase):

    def __init__(self, method_name = 'runTest'):
        return super().__init__(method_name)

    def test_login_root_without_password(self):
        self.assertTrue(pexpect_functions.test_login("root"))

if __name__ == '__main__':
    unittest.main(verbosity=0)
