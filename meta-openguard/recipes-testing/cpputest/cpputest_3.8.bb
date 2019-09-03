HOMEPAGE =  "http://cpputest.github.io/"
SUMMARY = "Unit testing and mocking framework for C/C++"
BUGTRACKER = "https://github.com/cpputest/cpputest/issues"
DESCRIPTION = "CppUTest is a C /C++ based unit xUnit test framework for unit testing and for test-driving your code. It is written in C++ but is used in C and C++ projects and frequently used in embedded systems but it works for any C/C++ project."

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://COPYING;md5=ce5d5f1fe02bcd1343ced64a06fd4177"

SRC_URI = "https://github.com/cpputest/cpputest/releases/download/v3.8/cpputest-3.8.tar.gz"

SRC_URI[md5sum] = "e8fdbbb5dd37d32d65919f240f984905"
SRC_URI[sha256sum] = "c81dccc5a1bfc7fc6511590c0a61def5f78e3fb19cb8e1f889d8d3395a476456"

S = "${WORKDIR}/${BP}"

FILES_${PN}-staticdev = "${datadir}/${PN}/**"

do_compile() {
    oe_runmake
}

do_install_append() {
    rm -rf ${D}/*
    install -d ${D}${datadir}/${PN}/build/
    install -d ${D}${datadir}/${PN}/lib/
    install -d ${D}${datadir}/${PN}/include/
    cp -R ${S}/include/* ${D}${datadir}/${PN}/include/
    cp -R ${S}/build/* ${D}${datadir}/${PN}/build/
    cp -R ${B}/lib/* ${D}${datadir}/${PN}/lib/
}

inherit autotools

