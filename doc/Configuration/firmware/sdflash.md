Flashing eMMC from SD-card
==========================

**Flashing of internal eMMC storage can be done using one of two approaches:**
1. Automatic
2. Manual


Automatic flashing procedure
----------------------------

*Environment requirements:*
1. u-boot should be built with `sdflash` command enabled;
2. *.wic file which represents system image (kernel/device tree blob and root file system) available;
3. Serial console connection with the device established successfully.

*Preparation steps:*
1. Power off the device
2. Format SD-card to FAT16/32
3. Rename `*.wic` file to `flash-image.bin`
4. Put `flash-image.bin` into first partition of SD-card


**Flashing**:
`WARNING: this procedure will replace the contents of internal eMMC storage with the contents of flash-image.bin entirely without any possibility of recovering original data.`

1. Put SD-card into the slot in the device
2. Push and hold the button on the front panel and apply the power to the device (don't release the button)
3. Keep holding the button pressed for at least 10 seconds untill 5 leds (labelled PF1 .. PF5) begin to
   flash one by one and then will lit completely green. You can now release the button.
4. Leds PF1 .. PF5 change their color from green to yellow which means that the flash procedure
   initiated.
5. If flash procedure completed without errors, leds PF1 .. PF5 will be lit with green light. Restart is required.


Manual flashing procedure
-------------------------

If for some reason automatic flash procedure was not performed, it may be done using manual method.
*Preparation step and environment requirements are the same as for automatic flashing procedure* while flashing
procedure is a little bit different.

**I. If the device is booted to the u-boot console**
1. Insert SD-card and type the following command:
    `mmc rescan`
2. Then, type the following command, but `don't hit enter`:
    `sdflash`
3. Press and hold the button on the front panel and after that hit `enter` to execute previously typed command
   (keep the button pressed for at least 10 seconds untill 5 leds (labelled PF1 .. PF5) begin to flash one by one
   and then will lit completely green. You can now release the button.
4. Leds PF1 .. PF5 change their color from green to yellow which means that the flash procedure
   initiated.
5. If flash procedure completed without errors, leds PF1 .. PF5 will be lit with green light. Restart is required.

**II. If the device has not been booted to the u-boot console yet (cold start)**
1. Insert SD-card and power on the device
2. Interrupt the boot process by immediately press any key in the console window
3. Type the following command, but `don't hit enter`:
    `sdflash`
3. Press and hold the button on the front panel and after that hit `enter` to execute previously typed command
   (keep the button pressed for at least 10 seconds untill 5 leds (labelled PF1 .. PF5) begin to flash one by one
   and then will lit completely green. You can now release the button.
4. Leds PF1 .. PF5 change their color from green to yellow which means that the flash procedure
   initiated.
5. If flash procedure completed without errors, leds PF1 .. PF5 will be lit with green light. Restart is required.

**NOTE:**

	On any error leds PF1 .. PF5 will be lit red. Flashing procedure will be interrupted. Restart is required.
