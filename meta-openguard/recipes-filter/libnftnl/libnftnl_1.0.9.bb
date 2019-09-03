SUMMARY = "Library for low-level interaction with nftables Netlink's API over libmnl"
DESCRIPTION = "libnftnl is a userspace library providing a low-level netlink programming interface (API) to the in-kernel nf_tables subsystem. The library libnftnl has been previously known as libnftables. This library is currently used by nftables."
HOMEPAGE = "http://www.netfilter.org/projects/libnftnl/index.html"
BUGTRACKER = "https://bugzilla.netfilter.org/"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=79808397c3355f163c012616125c9e26"

DEPENDS = "libmnl"

SRC_URI = "http://www.netfilter.org/projects/libnftnl/files/libnftnl-${PV}.tar.bz2;name=tar"
SRC_URI[tar.md5sum] = "6c4f392faab5745933553b4354be5d8d"
SRC_URI[tar.sha256sum] = "fec1d824aee301e59a11aeaae2a2d429cb99ead81e6bafab791a4dd6569b3635"

inherit autotools pkgconfig
