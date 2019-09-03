require recipes-bsp/u-boot/u-boot.inc

DESCRIPTION = "U-Boot for Marvell Embedded Processors"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://README;md5=8ff0837b355cf37b6b956f316d010d0c"

PROVIDES += "u-boot"

DEPENDS += "bc-native dtc-native"

SRC_URI = " \
    git://git@github.com/MarvellEmbeddedProcessors/u-boot-marvell;branch=${SRCBRANCH};protocol=https \
    file://${UBOOT_MACHINE};subdir=git/configs \
"

SRCBRANCH = "u-boot-2018.03-armada-18.09"
SRCREV = "7297be2ac99ab7ef02518f88c6c025e7c681bd6d"

## Name a file which contains the boot-command-string
## This will be autoincluded into configuration
UBOOT_ENV_BOOTCMD_FILE ?= "bootcmd"
## Name a file which contains a line-seperated list of environment-variables
## to be injected into u-boot-code
## The patch target specified by UBOOT_ENV_SETTINGS_PATCH_TARGET
## must contain a string called '/* MAGIC_ENVIRONMENT_MARKER */'
UBOOT_ENV_SETTINGS_FILE ?= "env_settings"
UBOOT_ENV_SETTINGS_PATCH_TARGET ?= "include/configs/mvebu_armada-common.h"

S = "${WORKDIR}/git"

include ${MACHINE}-${PN}.inc

def lazy_readlink(file, timeout=90):
    import time
    import os
    target = None
    while timeout > 0:
        if os.path.isfile(file):
            return
        time.sleep(1)
        timeout -= 1
    raise Exception("File not found")

python do_create_environment() {
    content_in = ""
    content_out = ""
    if not os.path.isfile(os.path.join(d.getVar("WORKDIR"), d.getVar("UBOOT_ENV_SETTINGS_FILE"))):
        return
    with open(os.path.join(d.getVar("WORKDIR"),  d.getVar("UBOOT_ENV_SETTINGS_FILE"))) as i:
        content_in = i.read()
    dont_expand = False
    needle = ""
    for char in content_in:
        if char == '%':
            if dont_expand:
                if needle:
                    content_out += d.getVar(needle, True)
                    needle = ""
                dont_expand = False
            else:
                dont_expand = True
            continue
        elif dont_expand:
            if char == ' ':
                continue
            else:
                needle += char
        else:
            if char == '"':
                content_out += "\""
            else:
                content_out += char
    content_out = content_out.replace('"', '\\"')
    content_out = content_out.replace('\n', '\\0')
    content_out = "\"" + content_out + "\0\""
    lines = ""
    with open(os.path.join(d.getVar("S"), d.getVar("UBOOT_ENV_SETTINGS_PATCH_TARGET"))) as i:
        lines = i.read().replace("/* MAGIC_ENVIRONMENT_MARKER */", "#define CONFIG_EXTRA_ENV_SETTINGS {}\n".format(content_out))
    with open(os.path.join(d.getVar("S"), d.getVar("UBOOT_ENV_SETTINGS_PATCH_TARGET")), "w") as o:
        o.write(lines)
}

python do_create_bootcmd() {
    content_in = ""
    content_out = ""
    if not os.path.isfile(os.path.join(d.getVar("WORKDIR"), d.getVar("UBOOT_ENV_BOOTCMD_FILE"))):
        return
    with open(os.path.join(d.getVar("WORKDIR"), d.getVar("UBOOT_ENV_BOOTCMD_FILE"))) as i:
        content_in = i.read()
    dont_expand = False
    needle = ""
    for char in content_in:
        if char == '%':
            if dont_expand:
                if needle:
                    content_out += d.getVar(needle, True)
                    needle = ""
                dont_expand = False
            else:
                dont_expand = True
            continue
        elif dont_expand:
            if char == ' ':
                continue
            else:
                needle += char
        else:
            if char == '"':
                content_out += "\""
            else:
                content_out += char
    content_out = content_out.replace('"', '\\"')
    content_out = content_out.replace('\n', '')
    content_out = "\"" + content_out + "\""
    lines = ""
    with open(os.path.join(d.getVar("S"), "configs", d.getVar("UBOOT_MACHINE"))) as i:
        lines = i.read() + "\n"
    lines += "CONFIG_BOOTCOMMAND={}\n".format(content_out)
    with open(os.path.join(d.getVar("S"), "configs", d.getVar("UBOOT_MACHINE")), "w") as o:
        o.write(lines)
}

do_compile[prefuncs] += "do_create_bootcmd"
do_compile[prefuncs] += "do_create_environment"

EXTRA_OEMAKE += "DEVICE_TREE=${UBOOT_DEVICE_TREE}"

COMPATIBLE_MACHINE = "(armada37xx|armada70xx|armada80xx)"

do_install_append() {
    ## make a backup copy of the complete patched sources
    ## this could be used in combination with testing
    mkdir -p ${DEPLOY_DIR_IMAGE}
    cd ${S}
    GZIP=-1 tar -zcf ${DEPLOY_DIR_IMAGE}/u-boot-sources.tar.gz *
    cd -
}
