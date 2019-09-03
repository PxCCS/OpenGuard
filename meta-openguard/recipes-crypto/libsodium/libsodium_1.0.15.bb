SUMMARY = "The Sodium crypto library"
HOMEPAGE = "http://libsodium.org/"
LICENSE = "ISC"
LIC_FILES_CHKSUM = "file://LICENSE;md5=7f5ecba1fa793fc1f3c8f32d6cb5a37b"

SRC_URI = "https://download.libsodium.org/libsodium/releases/${BPN}-${PV}.tar.gz"
SRC_URI[md5sum] = "070373e73a0b10bd96f412e1732ebc42"
SRC_URI[sha256sum] = "fb6a9e879a2f674592e4328c5d9f79f082405ee4bb05cb6e679b90afe9e178f4"

inherit autotools

BBCLASSEXTEND = "native nativesdk"