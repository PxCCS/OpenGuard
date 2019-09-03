SUMMARY = "openGuard base system default image."

require recipes-core/images/core-image-minimal.bb

DEPENDS_append_openguard += "${@oe.utils.ifelse(d.getVar('MACHINE') == 'mguard3', ' arm-trusted-firmware virtual/bootloader openguard-arm-trusted-preproduction openguard-u-boot-preproduction ', '')}"

IMAGE_INSTALL_append = " \
  kernel-modules \
  conntrack-tools \
  nftables-ruleset-basic \
  nginx \
  openssh-sftp-server \
  openssh-sshd \
  os-release \
  python3 \
  dnsmasq \
  ncurses-terminfo-base \
  net-snmp \
  net-snmp-server \
  net-snmp-libs \
  net-snmp-mibs \
  chrony \
  chronyc \
  strongswan \
  kernel-modules \
  open62541 \
  open62541-examples \
  open62541-tests \
  "

NO_RECOMMENDATIONS_pn-openguard-image = "1"

IMAGE_FEATURES_append = " read-only-rootfs"

# guard against dependency cycle
IMAGE_BB_INC_TESTING = "${@oe.utils.ifelse(d.getVar('PN', True) not in ['openguard-image-testing'], '1', '0')}"

do_image_complete[depends] += "${@oe.utils.ifelse(d.getVar('IMAGE_BB_INC_TESTING', True) == '1', 'openguard-image-testing:do_image_complete', '')}"
