SUMMARY = "Netfilter Tables userspace utillites"
DESCRIPTION = "nftables replaces the popular {ip,ip6,arp,eb}tables. This software provides a new in-kernel packet classification framework that is based on a network-specific Virtual Machine (VM) and a new nft userspace command line tool. nftables reuses the existing Netfilter subsystems such as the existing hook infrastructure, the connection tracking system, NAT, userspace queueing and logging subsystem."
HOMEPAGE = "http://www.netfilter.org/projects/nftables/index.html"
BUGTRACKER = "https://bugzilla.netfilter.org/"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d1a78fdd879a263a5e0b42d1fc565e79"

DEPENDS = "libmnl libnftnl readline gmp bison-native"
RDEPENDS_${PN} += "kernel-module-nf-tables"

SRC_URI = "http://www.netfilter.org/projects/nftables/files/${BP}.tar.bz2;name=tar"
SRC_URI[tar.md5sum] = "7468ed2effac96d54f128b44abbbec73"
SRC_URI[tar.sha256sum] = "8aead66cce70d68c70e4be917813abcbcf62811ee6de4c7761d0e34391772fc4"

inherit autotools pkgconfig
