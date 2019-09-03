# Files directory
FILESEXTRAPATHS_prepend := "${THISDIR}/files/cb-88f3720-ddr3-espressobin:"

# Sources
SRC_URI_append = " \
    file://dnsmasq.conf \
"

do_install_append() {
    install -m 0644 ${WORKDIR}/dnsmasq.conf ${D}${sysconfdir}/
}
