# Files directory
FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

# Sources
SRC_URI_append = " \
    file://snmpd.conf \
    file://snmptrapd.conf \
"

do_install_prepend() {
    if [ -f "${WORKDIR}/snmpd.service" ]; then
	sed -i '/Type=notify/a\Restart=always' ${WORKDIR}/snmpd.service
    fi
    if [ -f "${WORKDIR}/snmptrapd.service" ]; then
	sed -i '/Type=notify/a\Restart=always' ${WORKDIR}/snmptrapd.service
    fi
}

perl_scripts = " checkbandwidth \
		fixproc \
		ipf-mod.pl \
		net-snmp-cert \
		snmp-bridge-mib \
		snmpcheck \
		snmpconf \
		tkmib \
		traptoemail \
"

do_install_append() {
    install -d 0644 ${D}${sysconfdir}/snmp
    install -m 0644 ${WORKDIR}/snmpd.conf ${D}${sysconfdir}/snmp/
    install -m 0644 ${WORKDIR}/snmptrapd.conf ${D}${sysconfdir}/snmp/


   # Delete perl scripts
    if [ "${HAS_PERL}" = "0" ]; then
	for i in ${perl_scripts}; do
	    if [ -f ${D}${bindir}/${i} ]; then
		rm  ${D}${bindir}/${i}
	    fi;
	done
    fi
}
