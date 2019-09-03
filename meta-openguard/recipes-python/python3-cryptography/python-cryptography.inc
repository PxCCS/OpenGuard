SUMMARY = "Provides cryptographic recipes and primitives to python developers"
HOMEPAGE = "https://cryptography.io/"
LICENSE = "(Apache-2.0 | BSD-3-Clause) & PSF"
LIC_FILES_CHKSUM = "file://LICENSE;md5=d5ede125ed08d4fd14ec44d334bdb388"

DEPENDS += " \
    python3-cffi \
    python3-cffi-native \
    python3-asn1crypto \
    python3-six \
"

SRC_URI[md5sum] = "9f39d4d8f2b05f295fa0ff52475ec4f2"
SRC_URI[sha256sum] = "e4d967371c5b6b2e67855066471d844c5d52d210c36c28d49a8507b96e2c5291"

RDEPENDS_${PN} += " \
    python3-cffi \
    python3-idna \
    python3-asn1crypto \
    python3-pycparser \
    python3-setuptools \
    python3-six \
"

RDEPENDS_${PN}_class-target += " \
    python3-cffi \
    python3-idna \
    python3-numbers \
    python3-asn1crypto \
    python3-pycparser \
    python3-six \
    python3-setuptools \
    python3-threading \
"

RDEPENDS_${PN}-ptest += " \
    ${PN} \
    python3-cryptography-vectors \
    python3-iso8601 \
    python3-pretend \
    python3-pytest \
"

inherit ptest

do_install_ptest() {
    install -d ${D}${PTEST_PATH}/tests
    cp -rf ${S}/tests/* ${D}${PTEST_PATH}/tests/
    install -d ${D}${PTEST_PATH}/tests/hazmat
    cp -rf ${S}/tests/hazmat/* ${D}${PTEST_PATH}/tests/hazmat/
}

FILES_${PN}-dbg += " \
    ${libdir}/python32.7/site-packages/${SRCNAME}/hazmat/bindings/.debug \
"

BBCLASSEXTEND = "native nativesdk"
