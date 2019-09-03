# Files directory
FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

# Source
SRC_URI_append = " \
    file://chrony.conf \
"

do_install_append() {
    install -m 0644 ${WORKDIR}/chrony.conf ${D}${sysconfdir}/
    sed -i 's!ExecStart=.*!ExecStart=${sbindir}/chronyd -s!g' ${D}${systemd_unitdir}/system/chronyd.service
    sed -i '/Type=forking/a\Restart=always' ${D}${systemd_unitdir}/system/chronyd.service
}
