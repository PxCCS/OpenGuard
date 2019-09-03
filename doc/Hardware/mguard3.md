openGuard on the mGuard3 Board
=============================

This document describes how openGuard can be built for and run on the mGuard3 board.


Building openGuard for the mGuard3 Board
---------------------------------------

Build openGuard for machine `mguard3`. You can either
change the `MACHINE` variable in the `conf/local.conf` file or override
it on the command line by setting the `MACHINE` environment variable:

``` shell
cd ~/workspace/build

. ./openguard-buildenv.sh
```

then you're ready to execute the following command:
```
MACHINE=mguard3 bitbake openguard-image
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
dd if=~/workspace/build/mguard3_tmp/deploy/images/mguard3/openguard-image-mguard3.wic \
   of=/dev/sdd bs=64M conv=fsync
```

Depending on how your build system is set up, you may have to run this
command as _root_ user.


Booting the mGuard3 Board
-----------------------------

Insert the microSD card into the mGuard3 board, connect the boardʼs
micro USB interface (used as the serial console) with your PC, and plug
in the boardʼs power supply.

Start a serial communication program (also known as a terminal
emulator), e.g. `minicom`, on your PC. Configure it so that it can
communicate with the mGuard3 board: 115200 baud, 8N1, neither
hardware nor software flow control. The device name to use depends on
your PC; on a typical Linux system, it is `/dev/ttyUSB0`.

You should see boot loader messages in the terminal emulator, e.g.

```
WTMI-armada-17.10.5-34ce216
WTMI: system early-init
SVC REV: 4, CPU VDD voltage: 1.132V

Fill memory before self refresh...done

Fill memory before self refresh...done

Now in Self-refresh Mode
Restore termination values to original values
Exited self-refresh ...


Self refresh Pass.
DDR self test mode test done!!

Self refresh Pass.
DDR self test mode test done!!

QS GATING
=============
Calibration done: cycle = 0x00 tap =0x5E
CH0_PHY_RL_Control_CS0_B0[0xC0001180]: 0x0000005E
CH0_PHY_RL_Control_CS0_B1[0xC0001184]: 0x0000005E


QS GATING
=============
Calibration done: cycle = 0x00 tap =0x5F
CH0_PHY_RL_Control_CS1_B0[0xC00011A4]: 0x0000005F
CH0_PHY_RL_Control_CS1_B1[0xC00011A8]: 0x0000005F

DLL TUNING
==============
   DLL 0xc0001050[21:16]: [c,33,1f]
   DLL 0xc0001050[29:24]: [16,3a,28]
   DLL 0xc0001054[21:16]: [5,29,17]
   DLL 0xc0001054[29:24]: [e,32,20]
   DLL 0xc0001074[21:16]: [0,3f,1f]
   DLL 0xc0001074NOTICE:  Booting Trusted Firmware
NOTICE:  BL1: v1.3(release):armada-17.10.8:34247e02
NOTICE:  BL1: Built : 10:15:43, Oct 18 2NOTICE:  BL2: v1.3(release):armada-17.10.8:34247e02
NOTICE:  BL2: Built : 10:15:45, Oct 18 201NOTICE:  BL31: v1.3(release):armada-17.10.8:34247e02
NOTICE:  BL31:Apply SD-Card Voltage


U-Boot 2017.03-armada-17.10.3-mvebu_mguard3-88f3720-2017.03 (Oct 18 2018 - 10:15:34 +0000)

Model: mguard3 platform
       CPU    @ 1000 [MHz]
       L2     @ 800 [MHz]
       TClock @ 200 [MHz]
       DDR    @ 800 [MHz]
DRAM:  1 GiB
Initialized watchdog trigger
U-Boot DT blob at : 000000003f7140d8
Comphy-0: SGMII1        3.125 Gbps
Comphy-1: UNCONNECTED   1.25 Gbps 
Comphy-2: UNCONNECTED   1.25 Gbps 
MMC:   sdhci@d0000: 0, sdhci@d8000: 1
Enforce default environment
00:A0:45:38:22:0100:A0:45:38:22:0000:A0:45:38:22:0300:A0:45:38:22:02Net:   eth0: neta@30000 [PRIME], eth1: neta@40000
Hit any key to stop autoboot:  0
```

Stop the automatic boot process by hitting any key in the terminal
emulator. If you missed the timeout, press the reset button on the board
to boot it again.

You should now see the boot loader prompt:

```
u-boot>>
```

Set up the boot loader by entering these exact commands into the
terminal emulator (or copy and paste them):

```
setenv image_name Image-mguard3.bin

setenv fdt_name armada-3720-mguard3.dtb

setenv bootcmd 'mmc dev 0; fatload mmc 0:1 $kernel_addr $image_name; fatload mmc 0:1 $fdt_addr $fdt_name; setenv bootargs $console root=/dev/mmcblk0p2 rw rootwait; booti $kernel_addr - $fdt_addr'

run bootcmd
```

The mGuard3 board now boots the openGuard image.
