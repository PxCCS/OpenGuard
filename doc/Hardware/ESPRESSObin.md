openGuard on the ESPRESSObin Board
=============================

This document describes how openGuard can be built for and run on the Marvell
ESPRESSObin board.


Building openGuard for the ESPRESSObin Board
---------------------------------------

Build openGuard for machine `cb-88f3720-ddr3-espressobin`. You can either
change the `MACHINE` variable in the `conf/local.conf` file or override
it on the command line by setting the `MACHINE` environment variable:

``` shell
cd ~/workspace/build

. ./openguard-buildenv.sh

MACHINE=cb-88f3720-ddr3-espressobin bitbake openguard-image
```


Preparing an SD Card
--------------------

Insert a microSD card into your build machine. Use the `lsblk` command to
identify the device name corresponding to the card. The output of the
command may look like this:

```
NAME            MAJ:MIN RM   SIZE RO TYPE  MOUNTPOINT
sda               8:0    0 238.5G  0 disk
├─sda1            8:1    0   487M  0 part  /boot/efi
├─sda2            8:2    0    24G  0 part  /
└─sda3            8:3    0   194G  0 part  /home
sdd               8:48   1  14.9G  0 disk
├─sdd1            8:49   1    26M  0 part
└─sdd2            8:50   1 169.7M  0 part
```

In this example, the device name is `sdd`; yours may be different. Write
the openGuard image to the microSD card with the following command; replace
`sdd` with the device name of the card on your system.

**Warning: Make sure you use the correct device name, or you will
corrupt the disk of your build system!**

``` shell
dd if=~/workspace/build/cb-88f3720-ddr3-espressobin_tmp/deploy/images/cb-88f3720-ddr3-espressobin/openguard-image-cb-88f3720-ddr3-espressobin.wic \
   of=/dev/sdd bs=64M conv=fsync
```

Depending on how your build system is set up, you may have to run this
command as _root_ user.


Booting the ESPRESSObin Board
-----------------------------

Insert the microSD card into the ESPRESSObin board, connect the boardʼs
micro USB interface (used as the serial console) with your PC, and plug
in the boardʼs power supply.

Start a serial communication program (also known as a terminal
emulator), e.g. `minicom`, on your PC. Configure it so that it can
communicate with the ESPRESSObin board: 115200 baud, 8N1, neither
hardware nor software flow control. The device name to use depends on
your PC; on a typical Linux system, it is `/dev/ttyUSB0`.

You should see boot loader messages in the terminal emulator, e.g.

```
Booting Trusted Firmware
BL1: v1.2(release):armada-17.02.0:
BL1: Built : 09:41:56, Jun  2 2NOTICE:  BL2: v1.2(release):armada-17.02.0:
NOTICE:  BL2: Built : 09:41:57, Jun  2 20NOTICE:  BL31: v1.2(release):armada-17.02.0:
NOTICE:  BL31:

U-Boot 2015.01-armada-17.02.0-g8128e91 (Jun 02 2017 - 09:41:51)

I2C:   ready
DRAM:  1 GiB
Board: DB-88F3720-ESPRESSOBin
       CPU    @ 1000 [MHz]
       L2     @ 800 [MHz]
       TClock @ 200 [MHz]
       DDR    @ 800 [MHz]
Comphy-0: PEX0          2.5 Gbps
Comphy-1: USB3          5 Gbps
Comphy-2: SATA0         5 Gbps
Now running in RAM - U-Boot at: 3ff2b000
U-Boot DT blob at : 000000003fa18168
MMC:   XENON-SDHCI: 0
SF: Detected W25Q32DW with page size 256 Bytes, erase size 4 KiB, total 4 MiB
PCIE-0: Link down
SCSI:  SATA link 0 timeout.
AHCI 0001.0300 32 slots 1 ports 6 Gbps 0x1 impl SATA mode
flags: ncq led only pmp fbss pio slum part sxs
Net:   neta0
Hit any key to stop autoboot:  3
```

Stop the automatic boot process by hitting any key in the terminal
emulator. If you missed the timeout, press the reset button on the board
to boot it again.

You should now see the boot loader prompt:

```
Marvell>>
```

Set up the boot loader by entering these exact commands into the
terminal emulator (or copy and paste them):

```
setenv image_name Image-cb-88f3720-ddr3-espressobin.bin

setenv fdt_name Image-armada-3720-espressobin.dtb

setenv bootcmd 'mmc dev 0; fatload mmc 0:1 $kernel_addr $image_name; fatload mmc 0:1 $fdt_addr $fdt_name; setenv bootargs $console root=/dev/mmcblk0p2 rw rootwait; booti $kernel_addr - $fdt_addr'

save (or saveenv, that depends on the u-boot version)

run bootcmd
```

The ESPRESSObin board now boots the openGuard image.

The boot loader needs to be set up only once, i.e. you do not need to
stop the automatic boot process in the future. When you build a new
image, just write it to the micro SD card and boot it directly.
