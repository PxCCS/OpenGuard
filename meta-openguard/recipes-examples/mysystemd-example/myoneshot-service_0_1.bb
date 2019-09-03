DESCRIPTION = "Hello world program which demonstrates how a service can be started manually and how a oneshoot service can be configured. The file writes a message in the syslog. It also tries to read the number of bytes currently available to be read from the kernel log buffer. CapabilityBoundingSet= can be used to remove this right to read this number from the service. In the service file it is explained how to run and supervise services with minimal capabilities and permissions."

SRC_URI = "file://myoneshot-startapp.c \
	   file://myoneshot-stopapp.c  \
	   file://myoneshot-service.service"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${THISDIR}/../../../COPYING.GPL-2;md5=b234ee4d69f5fce4486a80fdaf4a4263"

S = "${WORKDIR}"

# use the systemd bbclass
inherit systemd

# add the service file
SYSTEMD_SERVICE_${PN} = "myoneshot-service.service"

#Service is not enabled, service can be started with systemctl start myoneshot-service.service
SYSTEMD_AUTO_ENABLE_${PN} = "disable"

do_compile() {
	${CC} ${CFLAGS} ${LDFLAGS}  myoneshot-startapp.c -o myoneshot-startapp
	${CC} ${CFLAGS} ${LDFLAGS}  myoneshot-stopapp.c -o myoneshot-stopapp
}

do_install() {
	install -d ${D}${bindir}
	install -m 0755 myoneshot-startapp ${D}${bindir}
	install -m 0755 myoneshot-stopapp ${D}${bindir}

	# install systemd unit files
	install -d ${D}${systemd_unitdir}/system
	install -m 0644 ${WORKDIR}/myoneshot-service.service ${D}${systemd_unitdir}/system
	sed -i -e 's,@BINDIR@,${bindir},g' \
	       ${D}${systemd_unitdir}/system/myoneshot-service.service

}
