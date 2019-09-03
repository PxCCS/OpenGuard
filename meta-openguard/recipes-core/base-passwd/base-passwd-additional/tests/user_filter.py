class UserFilter():

    OFFSET_FIRST_COLUMN = 0

    OFFSET_USER_ID = 2
    OFFSET_GROUP_ID = 2

    STATIC_USER_LOWER_BOUNDARY = 0
    STATIC_USER_UPPER_BOUNDARY = 99
    STATIC_USER_NOBODY_ID = 65534

    STATIC_GROUP_LOWER_BOUNDARY = 0
    STATIC_GROUP_UPPER_BOUNDARY = 99
    STATIC_GROUP_NOGROUP_ID = 65534

    def filter_ids(self, users, lowerboundary, upperboundary, specials):
        return filter(lambda u: u[self.OFFSET_USER_ID] >= lowerboundary and u[self.OFFSET_USER_ID] <= upperboundary or u[self.OFFSET_USER_ID] in specials, users)

    def filter_users(self, users):
        return self.filter_ids(users, self.STATIC_USER_LOWER_BOUNDARY, self.STATIC_USER_UPPER_BOUNDARY, [self.STATIC_USER_NOBODY_ID])

    def filter_groups(self, groups):
        return self.filter_ids(groups, self.STATIC_GROUP_LOWER_BOUNDARY, self.STATIC_GROUP_UPPER_BOUNDARY, [self.STATIC_GROUP_NOGROUP_ID])

    def read_first_column(self, file_name):
        with open(file_name) as open_file:
            file_lines = open_file.readlines()
        open_file.close
        first_columns = []
        for line in file_lines:
            first_columns.append(line.split(':')[self.OFFSET_FIRST_COLUMN])

        return set(first_columns)
