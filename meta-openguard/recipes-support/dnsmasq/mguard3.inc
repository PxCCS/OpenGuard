# Files directory
FILESEXTRAPATHS_prepend := "${THISDIR}/files/mguard3:"

# Sources
SRC_URI_append = " \
    file://dnsmasq.conf \
"

do_install_append() {
    install -m 0644 ${WORKDIR}/dnsmasq.conf ${D}${sysconfdir}/
}
