FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

KBRANCH = "master"

require recipes-kernel/linux/linux-yocto.inc

KBRANCH_qemux86    = "master"
KBRANCH_qemux86-64 = "master"

SRCREV_machine            = "84df9525b0c27f3ebc2ebb1864fa62a97fdedb7d"
SRCREV_machine_qemux86    = "84df9525b0c27f3ebc2ebb1864fa62a97fdedb7d"
SRCREV_machine_qemux86-64 = "84df9525b0c27f3ebc2ebb1864fa62a97fdedb7d"
SRCREV_meta               = "35bdc5267c949a7dab11eb5aa7d1aeb85a39cadf"

SRC_URI = "git://git.kernel.org/pub/scm/linux/kernel/git/torvalds/linux;name=machine;branch=${KBRANCH}; \
           ${OPENGUARD_GIT_BASE_URL}/upstream/yocto-kernel-cache;protocol=ssh;type=kmeta;name=meta;branch=seos/yocto-4.19;destsuffix=${KMETA}"

SRC_URI_append = " \
    file://disable-bluetooth.cfg \
    file://disable-drivers.cfg \
    file://disable-features.cfg \
    file://disable-filesystem-options.cfg \
    file://disable-network-drivers.cfg \
    file://disable-networking-options.cfg \
    file://disable-wireless.cfg \
    file://optimize-for-size.cfg \
    file://security.cfg \
    "

LIC_FILES_CHKSUM = "file://COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"
LINUX_VERSION = "4.19"

DEPENDS += "${@bb.utils.contains('ARCH', 'x86', 'elfutils-native', '', d)}"
DEPENDS += "openssl-native util-linux-native"

PV = "${LINUX_VERSION}+git${SRCPV}"

KMETA = "kernel-meta"
KCONF_BSP_AUDIT_LEVEL = "2"

COMPATIBLE_MACHINE = "qemux86|qemux86-64"

KERNEL_FEATURES_append_qemuall = " cfg/virtio.scc"
KERNEL_FEATURES_append_qemux86 = " cfg/paravirt_kvm.scc"
KERNEL_FEATURES_append_qemux86-64 = " cfg/paravirt_kvm.scc"
KERNEL_FEATURES_append = " ${@bb.utils.contains("TUNE_FEATURES", "mx32", " cfg/x32.scc", "" ,d)}"

# Kernel features are enabled by Yocto scc files. See
# https://www.yoctoproject.org/docs/current/kernel-dev/kernel-dev.html
# for how this works. The files are found in the kernel meta repository,
# which is mirrored as upstream/yocto-kernel-cache on the SEOS GitLab
# server.

KERNEL_FEATURES_append_qemuall = " \
    features/scsi/disk.scc \
    "

KERNEL_FEATURES_append = " \
    features/netfilter/netfilter.scc \
    features/nf_tables/nf_tables.scc \
    "


##############################################################################
# FIXME: The setting below should be migrated to a separate BSP.
##############################################################################

inherit ${@oe.utils.ifelse(d.getVar('MACHINE', True) == 'mguard3', 'marvell-kernel-config-fragments', '')}

SRC_URI_append_mguard3 = " \
    file://acl.cfg \
    file://blk-loop.cfg \
    file://bpf-cgroup.cfg \
    file://ethernet-bridge.cfg \
    file://ikconfig.cfg \
    file://overlayfs.cfg \
    file://squashfs.cfg \
    file://defconfig \
    file://gpio-drivers.cfg \
    file://gpio-reset.cfg \
    file://i2c.cfg \
    file://leds.cfg \
    file://md.cfg \
    file://netfilter.cfg \
    file://printk-time.cfg \
    file://rtc-drivers.cfg \
    file://switch.cfg \
    file://tempsens-ad7414.cfg \
    file://tpm-drivers.cfg \
    file://watchdog.cfg \
    file://ipsec.cfg \
    file://linux-yocto-production-template.its \
    file://0001-SPI-add-ISSI-spi-nor-flash.patch \
    file://0002-PINCTRL-rework-enable-GPIO-reset.patch \
    file://0003-SPI-a3700-initialise-additional-cs-gpios-as-outputs.patch \
    file://0004-MGUARD3-add-flat-device-tree.patch \
    file://0005-DSA-Add-access-to-switch-registers-through-sysfs.patch \
    file://0006-mGuard-use-polling-for-SD-Card-CD.patch \
    file://0007-eth0-power-down-during-boot.patch \
    file://0008-net-dsa-mv88e6xxx-Share-main-switch-IRQ.patch \
    file://0009-net-dsa-mv88e6xxx-Fix-writing-to-a-PHY-page.patch \
    file://0010-net-dsa-mv88e6xxx-Add-SERDES-phydev_link_change-for-.patch \
    file://0011-net-dsa-mv88e6xxx-Make-sure-to-configure-ports-with-.patch \
    file://0012-net-mdio-remove-duplicated-include-from-mdio_bus.c.patch \
    file://0013-net-dsa-mv88e6xxx-Fix-88E6141-6341-2500mbps-SERDES-s.patch \
    file://0014-net-dsa-mv88e6xxx-Group-cmode-ops-together.patch \
    file://0015-net-dsa-mv88e6xxx-Differentiate-between-6390-and-639.patch \
    file://0016-net-dsa-mv88e6xxx-Default-ports-9-10-6390X-CMODE-to-.patch \
    file://0017-net-dsa-mv88e6xxx-Add-support-for-SERDES-on-ports-2-.patch \
    file://0018-net-dsa-mv88e6xxx-Fix-clearing-of-stats-counters.patch \
    file://0019-net-dsa-mv88e6xxx-Work-around-mv886e6161-SERDES-miss.patch \
    file://0020-net-dsa-mv88e6xxx-set-ethtool-regs-version.patch \
    file://0021-net-dsa-mv88e6xxx-Add-missing-watchdog-ops-for-6320-.patch \
    file://0022-bnxt_en-Adjust-default-RX-coalescing-ticks-to-10-us.patch \
    file://0023-net-dsa-mv88x6xxx-mv88e6390-errata.patch \
    file://0024-net-dsa-mv88e6xxx-Fix-serdes-irq-setup-going-recursi.patch \
    file://0025-net-dsa-mv88e6xxx-Fix-counting-of-ATU-violations.patch \
    file://0026-dsa-mv88e6xxx-Ensure-all-pending-interrupts-are-hand.patch \
    file://0027-net-mvneta-Add-support-for-2500Mbps-SGMII.patch \
    file://0028-net-mvneta-correct-typo.patch \
    file://0030-net-mvneta-remove-redundant-check-for-eee-tx_lpi_tim.patch \
    file://0031-net-mvneta-fix-operation-for-64K-PAGE_SIZE.patch \
    file://0033-MGUARD3-enable-2.5Gbps-link-speed-on-SERDES-interfac.patch \
    file://3-3-mmc-sdhci-xenon-Fix-timeout-checks.patch \
    "

##############################################################################
# FIXME: The setting below should be migrated to a separate BSP.
#        See https://seos.de.innominate.com/seos/seos/issues/7
##############################################################################

## Armada37xx specific settings
COMPATIBLE_MACHINE += "|armada37xx"
SRC_URI += "${@oe.utils.ifelse(d.getVar('MACHINE', True) == 'cb-88f3720-ddr3-espressobin', 'file://defconfig', '')}"
SRC_URI += "${@oe.utils.ifelse(d.getVar('MACHINE', True) == 'cb-88f3720-ddr3-espressobin' and 'bigendian' in d.getVar('TUNE_FEATURES', True).split(' '), 'file://big-endian.cfg',  '')}"
SRC_URI_append_armada37xx = " file://mmc.cfg"
SRC_URI_append_armada37xx = " file://enable-marvell-net.cfg"

inherit ${@oe.utils.ifelse(d.getVar('MACHINE', True) == 'cb-88f3720-ddr3-espressobin', 'marvell-kernel-config-fragments', '')}
