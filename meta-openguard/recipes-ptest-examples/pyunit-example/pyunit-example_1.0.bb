SUMMARY = "This is an example of using ptest with python code"
## If you want to include pyunit-example in your build
## include the file seos-image-testing.bbappend with the line
## IMAGE_INSTALL_append = " pyunit-example-ptest"

SRC_URI = "file://additional_lib.py \
           file://test_example.py \
           file://test_example2.py \
           file://__init__.py \
           file://data/"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${THISDIR}/../../../COPYING.GPL-2;md5=b234ee4d69f5fce4486a80fdaf4a4263"

## Set which files contain unittest code
## You can seperate them by spaces
## Also you can use wildcards
PYUNIT_TESTFILES = "test_*.py"

## Specify which files are needed by testcode
## e.g. additional libraries and mockups
## You can seperate them by spaces
## Also you can use wildcards
PYUNIT_EXTRAFILES = "additional_lib.py data/*"

inherit pyunittest
