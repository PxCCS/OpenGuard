SUMMARY = "This is an example of a python module of which the coverage shall be measured"
DESCRIPTION = ""

SRC_URI = "file://setup.py \
           file://LICENSE \
           file://README.rst \
           file://coverage_example/ \
           file://tests/"


LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${THISDIR}/../../../COPYING.GPL-2;md5=b234ee4d69f5fce4486a80fdaf4a4263"

## Set which files contain unittest code
## You can seperate them by spaces
## Also you can use wildcards
PYUNIT_TESTFILES = "tests/test_*.py"

## Specify which files are needed by testcode
## e.g. additional libraries and mockups
## You can seperate them by spaces
## Also you can use wildcards
PYUNIT_EXTRAFILES = "setup.py LICENSE README.rst coverage_example/* tests/*"

## PYUNIT_TESTBASEDIR = "${WORKDIR}/tests"

PYUNIT_SOURCEDIR = "coverage_example"

inherit pyunittest
