##############################################################################
# Sources:
#
# https://github.com/MarvellEmbeddedProcessors/meta-marvell/blob/jethro/classes/marvell-kernel-config-fragments.bbclass
# https://git.yoctoproject.org/clean/cgit.cgi/meta-amd/tree/common/classes/cml1-config.bbclass?h=daisy
#
# License:
#
# Permission is hereby granted, free of charge, to any person obtaining a copy
# of this software and associated documentation files (the "Software"), to deal
# in the Software without restriction, including without limitation the rights
# to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
# copies of the Software, and to permit persons to whom the Software is
# furnished to do so, subject to the following conditions:
#
# The above copyright notice and this permission notice shall be
# included in all copies or substantial portions of the Software.
#
# THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
# EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
# MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
# IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
# CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
# TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
# SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
##############################################################################

# Marvell Kernel config fragments extension
#
# This allow to configuration fragments to be used inside the SRC_URI
# variable. The fragments must end with the .cfg file extension to be
# processed by this class.
#
# This class is based on cml1-config.bbclass from Mentor Graphics.
#
# Copyright 2016 (C) O.S. Systems Software LTDA.

# For merge_config.sh
DEPENDS += "kern-tools-native"

######
### Utilities
#####

merge_fragments () {
    list_fragments | ${merge_fragment_pipeline} >"${B}/fragments"
    merge_config.sh -m "$1" $(cat "${B}/fragments")
}

list_fragments () {
    cat <<END
    ${@"\n".join(src_config_fragments(d))}
END
}

def src_config_fragments(d):
    sources = src_patches(d, True)
    return [s for s in sources if s.endswith('.cfg')]

######

merge_fragment_pipeline = "cat"

do_configure_prepend() {
    mkdir -p ${B}
    test -f '${WORKDIR}/defconfig' && cp '${WORKDIR}/defconfig' '${B}/.config'
    merge_fragments ${B}/.config
}
