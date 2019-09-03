DESCRIPTION = "Python pexpect module"
LICENSE = "ISC"
LIC_FILES_CHKSUM = "file://PKG-INFO;md5=3a4554bb4dd6427f3c06434a401da3f4"

SRC_URI[md5sum] = "e9b07f0765df8245ac72201d757baaef"

PYPI_PACKAGE = "pexpect"

inherit pypi setuptools3

RDEPENDS_${PN} = "python3-ptyprocess"
