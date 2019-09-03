FILESEXTRAPATHS_prepend := "${THISDIR}/files/mguard3:"

SRC_URI += " \
    file://10-eth0.network \
    file://10-eth1.network \
    file://10-lan.network \
    file://10-after-udev.conf \
    file://85-hwclock.rules \
    file://resolved.conf \
    "

PACKAGECONFIG_append_pn-systemd = " resolved networkd"
FILES_${PN} += "/etc/resolv.conf"

do_install_append() {
    install -d ${D}${sysconfdir}/systemd/network
    install -m 0644 ${WORKDIR}/10-eth0.network ${D}${sysconfdir}/systemd/network
    install -m 0644 ${WORKDIR}/10-eth1.network ${D}${sysconfdir}/systemd/network
    install -m 0644 ${WORKDIR}/10-lan.network ${D}${sysconfdir}/systemd/network

    install -d ${D}${sysconfdir}/systemd/system
    install -d ${D}${sysconfdir}/systemd/system/systemd-networkd.service.d
    install -m 0644 ${WORKDIR}/10-after-udev.conf ${D}${sysconfdir}/systemd/system/systemd-networkd.service.d

    mkdir -p ${D}${sysconfdir}/udev/rules.d/
    install -m 0644 ${WORKDIR}/85-hwclock.rules ${D}${sysconfdir}/udev/rules.d/

    install -m 0644 ${WORKDIR}/resolved.conf ${D}${sysconfdir}/systemd/
}
