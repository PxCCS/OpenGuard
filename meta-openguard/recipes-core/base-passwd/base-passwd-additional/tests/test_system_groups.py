import unittest
import grp
from user_filter import UserFilter

class TestSystemGroups(unittest.TestCase):

    OFFSET_GROUP_NAME = 0
    EXPECTED_GROUPS_FILE = 'group.expect'
    
    GROUPS_SHADOW_FILE = '/etc/gshadow'
    GROUPS_FILE = '/etc/group'

    static_user_filter = None

    def __init__(self, method_name = 'runTest'):
        self.static_user_filter = UserFilter()
        return super().__init__(method_name)

    def test_system_groups(self):
        groups = grp.getgrall()
        static_groups = self.static_user_filter.filter_groups(groups)
        formatted_static_groups=[]
        for g in static_groups:
            formatted_static_groups.append("%s:%d" % (g[self.OFFSET_GROUP_NAME], g[self.static_user_filter.OFFSET_GROUP_ID]))

        with open(self.EXPECTED_GROUPS_FILE) as expected_group_file:
            expected_static_groups = [x for x in expected_group_file.read().split("\n") if x]

        self.assertListEqual(formatted_static_groups, expected_static_groups, 'System does not contain the expected groups!')

    def test_gshadow_file(self):
        group_names_from_groups_file = self.static_user_filter.read_first_column(self.GROUPS_FILE)
        group_names_from_gshadow_file = self.static_user_filter.read_first_column(self.GROUPS_SHADOW_FILE)

        self.assertEqual(group_names_from_gshadow_file, group_names_from_groups_file, 'gshadow file does not contain the expected groups!')

if __name__ == '__main__':
    unittest.main(verbosity=0)
