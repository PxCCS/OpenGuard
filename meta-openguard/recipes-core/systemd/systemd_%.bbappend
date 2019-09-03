PACKAGECONFIG = " \
    ${@bb.utils.filter('DISTRO_FEATURES', 'efi ldconfig pam selinux usrmerge', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'wifi', 'rfkill', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'xkbcommon', '', d)} \
    randomseed \
    seccomp \
    "

ALTERNATIVE_${PN}_remove = "resolv-conf"

# Remove the user systemd-bus-proxy of the systemd_%.bb, because the
# user is not used anymore. Since systemd v230 the system-bus-proxyd was
# removed and the regarding user isn't needed anymore.
USERADD_PACKAGES_remove = "${PN}-extra-utils"
USERADD_PARAM_${PN}-extra-utils_remove = "--system -d / -M --shell /bin/nologin systemd-bus-proxy;"


##############################################################################
# FIXME: Machine-dependent parts of a recipe should be appended in BSP layer,
#        not here.
##############################################################################

include ${MACHINE}.inc
