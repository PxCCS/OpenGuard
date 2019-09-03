# Files directory
FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

# Sources
SRC_URI_append = " \
    file://dnsmasq_dnss.conf \
    file://dnsmasq-dnslocal.service \
    file://hosts.dnsmasq-dnss \
"

do_install_append() {
    install -m 0644 ${WORKDIR}/dnsmasq-dnslocal.service ${D}${systemd_system_unitdir}/
    install -m 0644 ${WORKDIR}/dnsmasq_dnss.conf ${D}${sysconfdir}/
    install -m 0644 ${WORKDIR}/hosts.dnsmasq-dnss ${D}${sysconfdir}/
}

SYSTEMD_SERVICE_${PN} += "dnsmasq-dnslocal.service"
SYSTEMD_AUTO_ENABLE_${PN} = "enable"

include ${MACHINE}.inc
