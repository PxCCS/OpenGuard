SUMMARY = "Netfilter connection tracking timeout library"
DESCRIPTION = "libnetfilter_cttimeout is the userspace library that provides the programming interface to the fine-grain connection tracking timeout infrastructure. With this library, you can create, update and delete timeout policies that can be attached to traffic flows. This library is used by conntrack-tools."
HOMEPAGE = "http://www.netfilter.org/projects/libnetfilter_cttimeout/index.html"
BUGTRACKER = "https://bugzilla.netfilter.org/"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=8ca43cbc842c2336e835926c2166c28b"

DEPENDS = "libmnl"

SRC_URI = "http://www.netfilter.org/projects/libnetfilter_cttimeout/files/libnetfilter_cttimeout-${PV}.tar.bz2;name=tar"
SRC_URI[tar.md5sum] = "7697437fc9ebb6f6b83df56a633db7f9"
SRC_URI[tar.sha256sum] = "aeab12754f557cba3ce2950a2029963d817490df7edb49880008b34d7ff8feba"

S = "${WORKDIR}/libnetfilter_cttimeout-${PV}"

inherit autotools pkgconfig
