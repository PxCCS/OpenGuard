# Provides the Marvell Armada 37xx common settings

require conf/machine/include/arm/arch-arm64.inc

MACHINE_ENDIANNESS ?= "le"

DEFAULTTUNE_armada37xx ?= "${@bb.utils.contains("MACHINE_ENDIANNESS", "le", "aarch64", "aarch64_be", d)}"

MACHINEOVERRIDES =. "armada37xx:"

IMAGE_FSTYPES += "tar.bz2"

SERIAL_CONSOLES = "115200;ttyMV0"

KERNEL_IMAGETYPE = "Image"

MACHINE_FEATURES = "ext2 nfs pci usbgadget usbhost vfat"

