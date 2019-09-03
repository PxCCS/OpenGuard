SRC_URI = "file://ptest-autostart.service"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${THISDIR}/../../../COPYING.GPL-2;md5=b234ee4d69f5fce4486a80fdaf4a4263"

RDEPENDS_${PN} = "ptest-runner"

inherit systemd
REQUIRED_DISTRO_FEATURES = "systemd"

SYSTEMD_SERVICE_${PN} = "ptest-autostart.service"

S = "${WORKDIR}"

FILES_${PN} = "${systemd_system_unitdir}"

do_install() {
	install -d ${D}${systemd_system_unitdir}
	install -m 0644 ${WORKDIR}/ptest-autostart.service ${D}${systemd_system_unitdir}
}
