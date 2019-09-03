FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

SRC_URI += "file://sshd_config \
            file://Banner \
            file://sshd@.service \
            file://init_files"

FILES_${PN}-sshd += "${sysconfdir}/ssh/sshd_files"

do_install_append() {
    if [ "${@bb.utils.filter('DISTRO_FEATURES', 'pam', d)}" ]; then
        sed -i -e 's:#UsePAM no:UsePAM yes:' ${D}${sysconfdir}/ssh/sshd_config_readonly
        sed -i -e 's:ChallengeResponseAuthentication no:ChallengeResponseAuthentication yes:' ${D}${sysconfdir}/ssh/sshd_config_readonly 
    fi

    ## for none read-only configuration adjust the authorized_keys-filepath
    sed -i -e "s:AuthorizedKeysFile /var/run/ssh/authorized_keys:AuthorizedKeysFile ${sysconfdir}/ssh/authorized_keys:" ${D}${sysconfdir}/ssh/sshd_config
    sed -i -e "s:RevokedKeys /var/run/ssh/revoked_keys:RevokedKeys ${sysconfdir}/ssh/revoked_keys:" ${D}${sysconfdir}/ssh/sshd_config
    
    if [ $(grep "PermitRootLogin no" "${D}${sysconfdir}/ssh/sshd_config" | wc -l) -ne 1 ]; then
        bbwarn "Root access is permitted - Consider deactivating this\n \
by changing 'PermitRootLogin' to 'no' in sshd_config\n \
if you have configured at least one additional user in system"
    fi

    ## Install the banner file
    install -m 644 ${WORKDIR}/Banner ${D}/${sysconfdir}/ssh/Banner

    ## Install the additional create files task
    install -m 0755 ${WORKDIR}/init_files ${D}${sysconfdir}/ssh/sshd_files
}
