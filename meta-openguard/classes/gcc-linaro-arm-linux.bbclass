## This class includes the gcc-linaro compiler set
## to recipe build workspace
## So you can use it as an alternative compiler

LINARO_PV = "5.5.0-2017.10"
HOST_ARCH = "x86_64"
LTC_ARCH = "arm-linux-gnueabi"
python () {
    ## TODO auto determine needed host type
    d.setVar("HOST_ARCH", "x86_64")
}

SRC_URI += "https://releases.linaro.org/components/toolchain/binaries/latest-5/${LTC_ARCH}/gcc-linaro-${LINARO_PV}-${HOST_ARCH}_${LTC_ARCH}.tar.xz;name=linaro;subdir=linaro"
SRC_URI[linaro.md5sum] = "1227e9b54d19d60598fff569931e0dc1"
SRC_URI[linaro.sha256sum] = "8a72f24dddef2d11330c35ad652e0879ac80003c38be36eb3f9a11b7745809e2"

RDEPENDS_${PN} += "bash"

LINARO_PREFIX = "${WORKDIR}/linaro/gcc-linaro-${LINARO_PV}-${HOST_ARCH}_${LTC_ARCH}/bin/${LTC_ARCH}-"