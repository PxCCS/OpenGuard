""" Simple python-ptest for checking the existance of files regarding the
    PAM module;config files
    date: 2018-03-13
    author: PxCCs - HDR
    version: 0.2
"""
import unittest
import file_functions

class TestFileExistence(unittest.TestCase):

    #list of config files in /etc/pam.d to check for their existance, these are not
    # all possible files, because it can differ depending on what feature is
    # integrated in SEOS, but the list contains the minimal list of config-files
    CONFIG_FILE_LIST = ['/etc/pam.d/common-auth', '/etc/pam.d/common-account',
                        '/etc/pam.d/common-password', '/etc/pam.d/common-session',
                        '/etc/pam.d/common-session-noninteractive', '/etc/pam.d/other',
                        '/etc/pam.d/sshd']
    
    #list of libraries of pam which are essntial for SEOS
    LIB_FILE_LIST = ['/lib/security/pam_cracklib.so', '/lib/security/pam_unix.so',
                     '/lib/security/pam_deny.so']

    def __init__(self, method_name = 'runTest'):
        return super().__init__(method_name)

    def test_config_files_existence(self):
        self.assertTrue(file_functions.check_files_exist(self.CONFIG_FILE_LIST))

    def test_libraries_existence(self):
        self.assertTrue(file_functions.check_files_exist(self.LIB_FILE_LIST))

if __name__ == '__main__':
    unittest.main(verbosity=0)
