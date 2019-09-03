SUMMARY = "SSH2 protocol library"
DESCRIPTION = "This is a library for making SSH2 connections (client or server). \
Emphasis is on using SSH2 as an alternative to SSL for making secure connections between python scripts. \
All major ciphers and hash methods are supported. SFTP client and server mode are both supported too."
HOMEPAGE = "https://github.com/paramiko/paramiko"
LICENSE = "LGPLv2.1+"
LIC_FILES_CHKSUM = "file://LICENSE;md5=fd0120fc2e9f841c73ac707a30389af5"
BUGTRACKER = "https://github.com/paramiko/paramiko/issues"

PV = "2.4.1"

SRC_URI[md5sum] = "f9fa1204f706767b6c179effa7c0fb9e"
SRC_URI[sha256sum] = "33e36775a6c71790ba7692a73f948b329cf9295a72b0102144b031114bd2a4f3"

RDEPENDS_${PN} += "python3 \
                   python3-bcrypt \
                   python3-cryptography \
                   python3-asn1crypto \
                   python3-pyasn1"
DEPENDS += "python3 \
            python3-bcrypt \
            python3-cryptography \
            python3-asn1crypto \
            python3-pyasn1"

PYPI_PACKAGE = "paramiko"

inherit setuptools3
inherit pypi

FILES_${PN} += "${datadir}/paramiko"

BBCLASSEXTEND = "native nativesdk"
