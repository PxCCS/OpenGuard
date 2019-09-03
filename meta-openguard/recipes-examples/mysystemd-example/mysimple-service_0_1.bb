DESCRIPTION = "Hello world program which demonstrates how a service should be configured within yocto to be started during system boot, how a service can be started as user, how a command will be started if the service was reloaded and how a service can be restarted automatically."

SRC_URI = "file://mysimple-service.c \
	   file://myreload-service.c \
	   file://mysimple-service.service"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${THISDIR}/../../../COPYING.GPL-2;md5=b234ee4d69f5fce4486a80fdaf4a4263"

S = "${WORKDIR}"

# use the systemd bbclass
inherit systemd

# add the service file
SYSTEMD_SERVICE_${PN} = "mysimple-service.service"

#This can be set if the service should not run automatically
#SYSTEMD_AUTO_ENABLE_${PN} = "disable"

do_compile() {
	${CC} ${CFLAGS} ${LDFLAGS} mysimple-service.c -o mysimple-service
	${CC} ${CFLAGS} ${LDFLAGS} myreload-service.c -o myreload-service
}

do_install() {
	install -d ${D}${bindir}
	install -m 0755 mysimple-service ${D}${bindir}
	install -m 0755 myreload-service ${D}${bindir}

	# install systemd unit files
	install -d ${D}${systemd_unitdir}/system
	install -m 0644 ${WORKDIR}/mysimple-service.service ${D}${systemd_unitdir}/system
	sed -i -e 's,@BASE_BINDIR@,${base_bindir},g' \
	       -e 's,@BINDIR@,${bindir},g' \
	       ${D}${systemd_unitdir}/system/mysimple-service.service

}
