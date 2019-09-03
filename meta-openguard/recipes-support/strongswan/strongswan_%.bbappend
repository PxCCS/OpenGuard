# Files directory
FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

# Source
SRC_URI_append = " \
    file://ipsec.conf \
    file://sysctl.conf \
"

do_install_append() {
    install -m 0644 ${WORKDIR}/ipsec.conf ${D}${sysconfdir}/
    install -m 0644 ${WORKDIR}/sysctl.conf ${D}${sysconfdir}/
}

PACKAGECONFIG ??= "charon curl gmp openssl stroke sqlite3 kernel-libipsec \
        ${@bb.utils.filter('DISTRO_FEATURES', 'ldap', d)} \
"
PACKAGECONFIG[kernel-libipsec] = "--enable-kernel-libipsec,--disable-kernel-libipsec,,${PN}-plugin-kernel-libipsec"

RDEPENDS_${PN} += "\
    ${PN}-plugin-kernel-libipsec \
"
