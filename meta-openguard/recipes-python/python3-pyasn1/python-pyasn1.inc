SUMMARY = "Python library implementing ASN.1 types."
HOMEPAGE = "http://pyasn1.sourceforge.net/"
LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://LICENSE.rst;md5=280606d9c18f200e03e0c247ac61475a"

SRC_URI[md5sum] = "cb5883ddc6a9f405600429f37cf0759c"
SRC_URI[sha256sum] = "d258b0a71994f7770599835249cece1caef3c70def868c4915e6e5ca49b67d15"

RDEPENDS_${PN}_class-target += " \
    python3-codecs \
    python3-logging \
    python3-math \
    python3-shell \
"

BBCLASSEXTEND = "native nativesdk"
