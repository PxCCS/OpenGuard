SUMMARY = "Simple powerful teting with python"
HOMEPAGE = "http://pytest.org"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=c39b24965f4aef64222cb35de9d47cc4"

SRC_URI[md5sum] = "6768537cbb150022387c565051904a1c"
SRC_URI[sha256sum] = "9ddcb879c8cc859d2540204b5399011f842e5e8823674bf429f70ada281b3cc6"

SRC_URI_append = " file://0001-setup.py-remove-the-setup_requires-for-setuptools-scm.patch \
                   file://pytest_version_fix.patch "

RDEPENDS_${PN}_class-target += " \
    python3-attrs \
    python3-debugger \
    python3-doctest \
    python3-json \
    python3-pluggy \
    python3-py \
    python3-setuptools \
    python3-six \
"

FILESEXTRAPATHS_prepend := "${THISDIR}/python-pytest:"

BBCLASSEXTEND = "native nativesdk"