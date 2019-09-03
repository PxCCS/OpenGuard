LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${THISDIR}/../../../COPYING.GPL-2;md5=b234ee4d69f5fce4486a80fdaf4a4263"

SRC_URI += "file://tests/file_functions.py \
            file://tests/pexpect_functions.py \
            file://tests/test_file_existence.py \
            file://tests/test_python_pam.py \
            file://tests/test_system_users.py \
           "

## Set which files contain unittest code
## You can seperate them by spaces
## Also you can use wildcards
PYUNIT_TESTFILES = "tests/test_*.py"

## Specify which files are needed by testcode
## e.g. additional libraries and mockups
## You can seperate them by spaces
## Also you can use wildcards
PYUNIT_EXTRAFILES = "tests/file_functions.py tests/pexpect_functions.py"

PYUNIT_TESTBASEDIR = "${WORKDIR}/tests"

RDEPENDS_${PN}-ptest += " python3-core python3-paramiko python3-dbus python3-pam python3-pexpect python3-ptyprocess"

inherit pyunittest
