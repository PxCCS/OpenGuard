require recipes-bsp/openguard-arm-trusted-preproduction/openguard-arm-trusted-preproduction.inc


SRC_URI = " \
    git://git@github.com/MarvellEmbeddedProcessors/atf-marvell.git;branch=${SRCBRANCH_atf};protocol=https;name=atf;destsuffix=atf \
    git://git@github.com/MarvellEmbeddedProcessors/mv-ddr-marvell.git;branch=${SRCBRANCH_mv-ddr};protocol=https;name=mv-ddr;destsuffix=mv-ddr \
    git://git@github.com/MarvellEmbeddedProcessors/binaries-marvell.git;branch=${SRCBRANCH_bin-marvell};protocol=https;name=bin-marvell;destsuffix=bin-marvell \
    git://git@github.com/MarvellEmbeddedProcessors/A3700-utils-marvell.git;branch=${SRCBRANCH_A3700-utils};protocol=https;name=A3700-utils;destsuffix=A3700-utils \
    file://Use-BUILD_CC-to-compile-native-parts.patch \
"

SRC_URI += "${@oe.utils.ifelse(d.getVar('MACHINE') == 'mguard3', 'file://0001-enable-wdt-as-reboot-fallback.patch', '')}"

inherit gcc-linaro-arm-linux gcc-linaro-aarch64-linux

SRCREV_atf = "34247e027e234634d65287d1cfdf0c3d6eb98cae"
SRCBRANCH_atf = "atf-v1.3-armada-17.10"

SRCREV_mv-ddr = "656440a9690f3d07be9e3d2c39d7cf56fd96eb7b"
SRCBRANCH_mv-ddr = "mv_ddr-armada-17.10"

SRCREV_bin-marvell = "0dabe23b956420b0928d337d635f0cd5646c33d0"
SRCBRANCH_bin-marvell = "binaries-marvell-armada-17.10"

SRCREV_A3700-utils = "34ce2160a1521dda9c7c68e06fcde83242dee94a"
SRCBRANCH_A3700-utils = "A3700_utils-armada-17.10"

PARALLEL_MAKE = ""

# Export path to a70xx binary file
do_compile_prepend_armada70xx() {
    export SCP_BL2="${WORKDIR}/bin-marvell/mrvl_scp_bl2_7040.img"
}

# Export path to a80xx binary file
do_compile_prepend_armada80xx() {
    export SCP_BL2="${WORKDIR}/bin-marvell/mrvl_scp_bl2_8040.img"
}
