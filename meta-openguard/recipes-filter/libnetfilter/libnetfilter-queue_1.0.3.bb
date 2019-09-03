SUMMARY = "Netfilter packet queue access library"
DESCRIPTION = "libnetfilter_queue is a userspace library providing an API to packets that have been queued by the kernel packet filter. It is is part of a system that deprecates the old ip_queue / libipq mechanism."
HOMEPAGE = "http://www.netfilter.org/projects/libnetfilter_queue/index.html"
BUGTRACKER = "https://bugzilla.netfilter.org/"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=8ca43cbc842c2336e835926c2166c28b"

DEPENDS = "libnfnetlink libmnl"

SRC_URI = "http://www.netfilter.org/projects/libnetfilter_queue/files/libnetfilter_queue-1.0.3.tar.bz2;name=tar"
SRC_URI[tar.sha1sum] = "3d182e3211b633d0a0f8a2b12ef80dc2621f53cb"
SRC_URI[tar.sha256sum] = "9859266b349d74c5b1fdd59177d3427b3724cd72a97c49cc2fffe3b55da8e774"

S = "${WORKDIR}/libnetfilter_queue-${PV}"

inherit autotools pkgconfig
