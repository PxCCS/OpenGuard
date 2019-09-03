SUMMARY = "openGuard testing image."

inherit core-image
include openguard-image.bb

# Test infrastructure packages
IMAGE_INSTALL += "ptest-runner"
IMAGE_INSTALL += "ptest-autostart"

# Test packages
IMAGE_INSTALL += "base-passwd-additional-ptest"
IMAGE_INSTALL += "nftables-ruleset-basic-ptest"
IMAGE_INSTALL += "pam-additional-ptest"
IMAGE_INSTALL += "python3-coverage"

## Conditional installs
IMAGE_INSTALL += "${@bb.utils.contains('IMAGE_FEATURES', 'read-only-rootfs', 'volatile-binds', '', d)}"
