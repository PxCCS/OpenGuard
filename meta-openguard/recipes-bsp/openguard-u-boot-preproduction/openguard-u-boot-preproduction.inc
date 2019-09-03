
FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://bootcmd \
            file://env_settings \
            file://0001-MGUARD3-add-bsp-for-mguard3.patch \
            file://0002-Fix-build-with-gcc-8.patch \
            file://0003-PCF8575-8-Bit-support.patch \
            file://0004-add-u-boot-cmd-sdflash.patch \
            file://0005-enable-kconfig-option-for-sdflash.patch \
            file://0006-enable-makefile-build-sdflash-cmd.patch \
            file://0007-MGUARD3-Enable-second-DRAM-bank-in-device-tree.patch \
            file://0008-fix-dram-banks-alloc.patch \
            file://0009-production-sdflash-cmd.patch \
            "

COMPATIBLE_MACHINE += "|(mguard3)"
