FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

SRC_URI += "file://do-not-save-shell-history.cfg"
SRC_URI += "file://add-convenience.cfg"
SRC_URI += "file://ash-optimize-speed.cfg"
SRC_URI += "file://disable-flock.cfg"
SRC_URI += "file://disable-fs.cfg"
SRC_URI += "file://disable-net.cfg"
SRC_URI += "file://disable-user.cfg"
SRC_URI += "file://user-management.cfg"
SRC_URI += "file://less.cfg"
