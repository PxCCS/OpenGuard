SUMMARY = "Netfilter connection tracking helper library"
DESCRIPTION = "libnetfilter_cthelper is the userspace library that provides the programming interface to the user-space helper infrastructure available since Linux kernel 3.6. With this library, you register, configure, enable and disable user-space helpers. This library is used by conntrack-tools."
HOMEPAGE = "http://www.netfilter.org/projects/libnetfilter_cthelper/index.html"
BUGTRACKER = "https://bugzilla.netfilter.org/"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=8ca43cbc842c2336e835926c2166c28b"

DEPENDS = "libmnl"

SRC_URI = "http://www.netfilter.org/projects/libnetfilter_cthelper/files/libnetfilter_cthelper-${PV}.tar.bz2;name=tar"
SRC_URI[tar.md5sum] = "b2efab1a3a198a5add448960ba011acd"
SRC_URI[tar.sha256sum] = "07618e71c4d9a6b6b3dc1986540486ee310a9838ba754926c7d14a17d8fccf3d"

S = "${WORKDIR}/libnetfilter_cthelper-${PV}"

inherit autotools pkgconfig
