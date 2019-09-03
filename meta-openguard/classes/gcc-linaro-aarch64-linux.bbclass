## This class includes the gcc-linaro compiler set
## to recipe build workspace
## So you can use it as an alternative compiler

LINARO_PV_X64 = "5.2-2015.11-2"
HOST_ARCH_X64 = "x86_64"
LTC_ARCH_X64 = "aarch64-linux-gnu"
python () {
    ## TODO auto determine needed host type
    d.setVar("HOST_ARCH_X64", "x86_64")
}

SRC_URI += "https://releases.linaro.org/components/toolchain/binaries/${LINARO_PV_X64}/${LTC_ARCH_X64}/gcc-linaro-${LINARO_PV_X64}-${HOST_ARCH_X64}_${LTC_ARCH_X64}.tar.xz;name=linarox64;subdir=linarox64"
SRC_URI[linarox64.md5sum] = "7d2a8af2f14f0d10831562f18335bf89"
SRC_URI[linarox64.sha256sum] = "b318a1837a54146b0120a13852386576e38355513b4e2cd5e2125f9c26913777"

RDEPENDS_${PN} += "bash"

LINAROX64_PREFIX = "${WORKDIR}/linarox64/gcc-linaro-${LINARO_PV_X64}-${HOST_ARCH_X64}_${LTC_ARCH_X64}/bin/${LTC_ARCH_X64}-"