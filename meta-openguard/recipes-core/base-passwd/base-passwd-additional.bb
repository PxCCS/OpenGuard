LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${THISDIR}/../../../COPYING.GPL-2;md5=b234ee4d69f5fce4486a80fdaf4a4263"

SRC_URI += "file://tests/test_system_users.py \
            file://tests/test_system_groups.py \
            file://tests/user_filter.py \
            file://tests/__init__.py \
            file://tests/passwd.expect \
            file://tests/group.expect \
           "

## Set which files contain unittest code
## You can seperate them by spaces
## Also you can use wildcards
PYUNIT_TESTFILES = "tests/test_*.py"

## Specify which files are needed by testcode
## e.g. additional libraries and mockups
## You can seperate them by spaces
## Also you can use wildcards
PYUNIT_EXTRAFILES = "tests/user_filter.py tests/passwd.expect tests/group.expect"

PYUNIT_TESTBASEDIR = "${WORKDIR}/tests"

inherit pyunittest
