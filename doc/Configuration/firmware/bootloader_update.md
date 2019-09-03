Updating the U-Boot
===================

Assume you have pre-compiled binary that should be called like this: `u-boot-trusted-mguard3-1.3+gitAUTOINC+34247e027e-r0.bin`,
where `34247e027e-r0` may change from build to build.

Then, you need to prepare an SD-card: one partition formatted to fat32. Put mentioned file to the formatted SD-card.
As soon as you enter u-boot command prompt, you can test that u-boot does "see" the first partition of SD card by issuing a command:

`N.B. - the output may differ depending on what OS has been used to format SD-card` 

```
u-boot>> fatls mmc 0:1
            .Spotlight-V100/
            .fseventsd/
   832460   u-boot-trusted-mguard3-1.3+gitAUTOINC+34247e027e-r0.bin

1 file(s), 2 dir(s)
```

Now you can execute command `bubt` which will perform SPI flash updade:

```
u-boot>> bubt u-boot-trusted-mguard3-1.3+gitAUTOINC+34247e027e-r0.bin spi mmc
Burning U-BOOT image "u-boot-trusted-mguard3-1.3+gitAUTOINC+34247e027e-r0.bin" from "mmc" to "spi" at offset "0x0"
Image checksum...OK!
SF: Detected w25q64dw with page size 256 Bytes, erase size 4 KiB, total 8 MiB
Erasing 835584 bytes (204 blocks) at offset 0 ...Done!
Writing 832460 bytes from 0x10000000 to offset 0 ...Done!
```

Then you may unplug and plug again the power cord, or just type `reset`, hit `enter` button and the device
will be rebooted to a freshly updated bootloader.

