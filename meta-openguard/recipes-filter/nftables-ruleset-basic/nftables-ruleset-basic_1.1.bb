SUMMARY = "Extended ruleset for nftables"
DESCRIPTION = "Extended ruleset for nftables firewall."

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${THISDIR}/../../../COPYING.GPL-2;md5=b234ee4d69f5fce4486a80fdaf4a4263"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://firewall.service"

SRC_URI += "file://basic.ruleset"
SRC_URI += "file://port.ruleset"
SRC_URI += "file://services.ruleset"
SRC_URI += "file://zones.ruleset"

SRC_URI += "file://ipsec_nf_rules.sh"


RDEPENDS_${PN} = " \
  nftables \
  kernel-module-nf-tables \
  kernel-module-nf-tables-set \
  kernel-module-nft-ct \
  kernel-module-nft-reject-inet \
  "

inherit systemd

SYSTEMD_SERVICE_${PN} = "firewall.service"

do_install() {
	install -d ${D}${systemd_system_unitdir}
	install -d ${D}${sysconfdir}/firewall/rules

	install -m 0644 ${WORKDIR}/*.service ${D}${systemd_system_unitdir}
	install -m 0644 ${WORKDIR}/*.ruleset ${D}${sysconfdir}/firewall/rules
	install -m 0755 ${WORKDIR}/ipsec_nf_rules.sh ${D}${sysconfdir}/
}

FILES_${PN} = "${sysconfdir}/* ${bindir}/* ${systemd_system_unitdir}/*"


inherit pyunittest

RDEPENDS_${PN}-ptest = " \
  python3-core \
  "

SRC_URI += "file://tests/test_basic_filter_rules.py"

PYUNIT_TESTFILES = "tests/test_*.py"

PYUNIT_TESTBASEDIR = "${WORKDIR}/tests"
