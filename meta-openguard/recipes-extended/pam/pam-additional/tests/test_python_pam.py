"""summary: python-ptest for logins with password via the PAM module
           using python-pam as library
  author:  PxCCs - HDR
  date: 2018-03-13
  version: 0.1
"""

import unittest
import pam

class TestPythonPam(unittest.TestCase):

    PAM_MODULE = pam.pam()

    def __init__(self, method_name = 'runTest'):
        return super().__init__(method_name)

    #note there is a problem with python-pam and the root-user
    #only service='system-auth' works, but not login
    #p.authenticate('root', '', service='system-auth')
    def test_authenticate_root_sshd(self):
        self.assertTrue(self.PAM_MODULE.authenticate('root', '', service='sshd'))

if __name__ == '__main__':
    unittest.main(verbosity=0)
