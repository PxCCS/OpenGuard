FILESEXTRAPATHS_prepend := "${THISDIR}/files/cb-88f3720-ddr3-espressobin:"

SRC_URI += " file://10-br0.netdev \
             file://10-br0.network \
             file://10-eth0.network \
             file://10-lan.network \
             file://10-wan.network "

PACKAGECONFIG_append_pn-systemd = " resolved networkd"
FILES_${PN} += "/etc/resolv.conf"

do_install_append() {
    install -d ${D}${sysconfdir}/systemd/network
    install -m 0644 ${WORKDIR}/10-br0.netdev ${D}${sysconfdir}/systemd/network
    install -m 0644 ${WORKDIR}/10-br0.network ${D}${sysconfdir}/systemd/network
    install -m 0644 ${WORKDIR}/10-eth0.network ${D}${sysconfdir}/systemd/network
    install -m 0644 ${WORKDIR}/10-lan.network ${D}${sysconfdir}/systemd/network
    install -m 0644 ${WORKDIR}/10-wan.network ${D}${sysconfdir}/systemd/network
}
