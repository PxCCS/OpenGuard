SUMMARY = "This is an example of using ptest"
DESCRIPTION = ""

SRC_URI = "file://run-ptest"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${THISDIR}/../../../COPYING.GPL-2;md5=b234ee4d69f5fce4486a80fdaf4a4263"

inherit ptest

do_install_ptest() {
  #make sure that the path exists
  mkdir -p -- ${D}${PTEST_PATH}
  #copy the run-ptest script
  install -m 0755 ${B}/../run-ptest ${D}${PTEST_PATH}
}
