FILES_${PN}-base += " \
    ${bindir}/passwd.shadow \
    ${sbindir}/useradd.shadow \
    ${sbindir}/userdel.shadow \
    ${sbindir}/usermod.shadow \
    ${sysconfdir}/pam.d/passwd \
    ${sysconfdir}/pam.d/useradd \
    ${sysconfdir}/pam.d/userdel \
    ${sysconfdir}/pam.d/usermod \
    ${sysconfdir}/pam.d/groupadd \
    ${sysconfdir}/pam.d/groupdel \
    ${sysconfdir}/pam.d/groupmod \
    "

ALTERNATIVE_${PN}-base_append = " useradd userdel usermod groupadd groupdel groupmod"
ALTERNATIVE_LINK_NAME[useradd] = "${sbindir}/useradd"
ALTERNATIVE_LINK_NAME[userdel] = "${sbindir}/userdel"
ALTERNATIVE_LINK_NAME[usermod] = "${sbindir}/usermod"
ALTERNATIVE_LINK_NAME[groupadd] = "${sbindir}/groupadd"
ALTERNATIVE_LINK_NAME[groupdel] = "${sbindir}/groupdel"
ALTERNATIVE_LINK_NAME[groupmod] = "${sbindir}/groupmod"
