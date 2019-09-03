# Split out conntrackd into a separate package

PACKAGE_BEFORE_PN = "${PN}-conntrackd"

CONFFILES_${PN}-conntrackd = "\
    ${sysconfdir}/conntrackd \
    ${sysconfdir}/init.d \
    "

FILES_${PN}-conntrackd = "\
    ${sbindir}/conntrackd \
    ${CONFFILES_${PN}-conntrackd} \
    "

RDEPENDS_${PN}-conntrackd = "${PN} (= ${PV}-${PR})"
