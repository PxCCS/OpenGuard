#
# summary: This is an additional recipe to add special plugins to the original 
#           poky-recipe, to get further pam libs like cracklib
#
# date:2018-03-13
# author:HDR
#


FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

#check whether debug-tweaks or empty-root-password are activated
SRC_URI += "${@bb.utils.contains_any("IMAGE_FEATURES", [ 'debug-tweaks', 'empty-root-password' ], "", "file://0001-pam-remove-nullok-parameter.patch;patchdir=${WORKDIR}", d)} \
            ${@bb.utils.contains_any("IMAGE_FEATURES", [ 'debug-tweaks', 'empty-root-password' ], "", "file://0002-password-hardening.patch;patchdir=${WORKDIR}", d)} \
"

RDEPENDS_${PN}-runtime = "${PN}-${libpam_suffix} \
    ${MLPREFIX}pam-plugin-deny-${libpam_suffix} \
    ${MLPREFIX}pam-plugin-permit-${libpam_suffix} \
    ${MLPREFIX}pam-plugin-warn-${libpam_suffix} \
    ${MLPREFIX}pam-plugin-unix-${libpam_suffix} \
    ${MLPREFIX}pam-plugin-cracklib-${libpam_suffix} \
    "
