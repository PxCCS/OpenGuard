import unittest
import pwd
from user_filter import UserFilter

class TestSystemUsers(unittest.TestCase):

    OFFSET_USER_NAME = 0
    OFFSET_SHELL = 6
    EXPECTED_USERS_FILE = 'passwd.expect'

    USERS_WITH_LOGIN_TO_TEST = ['root', 'polkitd']
    SUFFIXES_PREVENTING_LOGIN = ['false', 'nologin']

    SHADOW_FILE = '/etc/shadow'
    PASSWD_FILE = '/etc/passwd'

    static_user_filter = None

    def __init__(self, method_name = 'runTest'):
        self.static_user_filter = UserFilter()
        return super().__init__(method_name)

    def test_system_users(self):
        users = pwd.getpwall()
        static_users = self.static_user_filter.filter_users(users)
        formatted_static_users=[]
        for u in static_users:
            formatted_static_users.append("%s:%d" % (u[self.OFFSET_USER_NAME], u[self.static_user_filter.OFFSET_USER_ID]))

        with open(self.EXPECTED_USERS_FILE) as expected_user_file:
            expected_static_users = [x for x in expected_user_file.read().split("\n") if x]

        self.assertListEqual(formatted_static_users, expected_static_users, 'System does not have the expected users!')

    def test_login(self):
        users = pwd.getpwall()
        static_users = self.static_user_filter.filter_users(users)

        for user in static_users:
            if user[self.OFFSET_USER_NAME] in self.USERS_WITH_LOGIN_TO_TEST:
                for suffix in self.SUFFIXES_PREVENTING_LOGIN:
                    self.assertFalse(user[self.OFFSET_SHELL].endswith(suffix), 'User ' + user[self.OFFSET_USER_NAME] + ' can not login.')

    def test_shadow_file(self):
        user_names_from_passwd_file = self.static_user_filter.read_first_column(self.PASSWD_FILE)
        user_names_from_shadow_file = self.static_user_filter.read_first_column(self.SHADOW_FILE)

        self.assertEqual(user_names_from_shadow_file, user_names_from_passwd_file, 'shadow file does not contain the expected users!')

if __name__ == '__main__':
    unittest.main(verbosity=0)
