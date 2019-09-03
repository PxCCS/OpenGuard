SUMMARY = "This is an example of using cpputest"
## If you want to include cpputest-example in your build
## include the file seos-image-testing.bbappend with the line
## IMAGE_INSTALL_append = " cpputest-example-ptest"

SRC_URI = "file://cpputest-example.c \
		   file://cpputest-example.h \
		   file://tests/AllTests.cpp \
		   file://tests/firstcpputest.cpp \
		   file://tests/Makefile"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${THISDIR}/../../../COPYING.GPL-2;md5=b234ee4d69f5fce4486a80fdaf4a4263"

S = "${WORKDIR}"

## If your tests are in a subfolder of WORKDIR
## Set CPPUTEST_INPUT_DIR before inherit this class
CPPUTEST_INPUT_DIR = "tests"

## inherit the class functionality of cpputest and ptest
inherit cpputest
