DESCRIPTION = "ARM Trusted Firmware"

LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://license.md;md5=829bdeb34c1d9044f393d5a16c068371"

DEPENDS = " \
    virtual/bootloader \
    openssl-native \
    coreutils-native \
"

DEPENDS_append_armada37xx = "unzip-native"

PROVIDES += "arm-trusted-firmware"

inherit deploy

S = "${WORKDIR}/atf"

SRCREV_FORMAT = "atf"

PV .= "+git${SRCPV}"

# requires CROSS_COMPILE set by hand as there is no configure script
export CROSS_COMPILE="${LINAROX64_PREFIX}"

# Avoid messing up with build flags
CFLAGS[unexport] = "1"
LDFLAGS[unexport] = "1"
AS[unexport] = "1"
LD[unexport] = "1"

do_configure[noexec] = "1"

python() {
    atfplatform = d.getVar("ARM_TRUSTED_FIRMWARE_PLATFORM", False)
    if not atfplatform:
        raise bb.parse.SkipPackage("ARM_TRUSTED_FIRMWARE_PLATFORM must be " \
                                   "set in the %s machine configuration." \
                                   % d.getVar("MACHINE", True))
}

python() {
    d.setVar("BL33_IMAGE", "u-boot.bin")
}

ATF_DEBUG="0"

EXTRA_OEMAKE += " \
    BL33=${DEPLOY_DIR_IMAGE}/${BL33_IMAGE} \
    BOOTDEV=SPINOR \
    CLOCKSPRESET=CPU_1000_DDR_800 \
    DDR_TOPOLOGY=2 \
    DEBUG=${ATF_DEBUG} \
    LOG_LEVEL=20 \
    MV_DDR_PATH=${WORKDIR}/mv-ddr \
    PARTNUM=0 \
    PLAT=${ARM_TRUSTED_FIRMWARE_PLATFORM} \
    SECURE=0 \
    USE_COHERENT_MEM=0 \
    CROSS_CM3=${LINARO_PREFIX} \
"

# ARM 32-bit cross compiler, which is required by building WTMI image for CM3.
export CROSS_CM3 = "${LINARO_PREFIX}"

do_compile_prepend_armada37xx() {
    export WTP=${WORKDIR}/A3700-utils/
}

do_compile() {
    oe_runmake all fip
}
do_compile[nostamp] = "1"

do_install[noexec] = "1"

do_deploy() {
    BLOBPATH="release"
    if [ "${ATF_DEBUG}" -eq "1" ]; then
        BLOBPATH="debug"
    fi
    install -d ${DEPLOYDIR}
    install -m 0644 ${S}/build/${ARM_TRUSTED_FIRMWARE_PLATFORM}/${BLOBPATH}/flash-image.bin ${DEPLOYDIR}/u-boot-trusted-${MACHINE}-${PV}-${PR}.bin
    ln -sf u-boot-trusted-${MACHINE}-${PV}-${PR}.bin ${DEPLOYDIR}/u-boot-trusted.bin
    install -d ${DEPLOYDIR}/u-boot-trusted-${PV}-${PR}-uart-images
    for _f in $(find ${S}/build/${ARM_TRUSTED_FIRMWARE_PLATFORM}/${BLOBPATH}/uart-images/ -type f -name "*.bin"); do
        install -m 0644 ${_f} \
        ${DEPLOYDIR}/u-boot-trusted-${PV}-${PR}-uart-images/$(basename ${_f})
    done
    ## Explicitly allow execution for everybody
    install -m 0777 ${WORKDIR}/A3700-utils/wtptp/linux/WtpDownload_linux ${DEPLOYDIR}/u-boot-trusted-${PV}-${PR}-uart-images/
}
addtask deploy before do_build after do_compile

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(armada70xx|armada80xx|armada37xx)"
