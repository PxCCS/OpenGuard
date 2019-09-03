Store firmware image from TFTP in flash memory
==================================
Setting up a TFTP server
----------------------------------
Install the following packages on your host PC:

        $: apt-get install openbsd-tftpd tftpd tftp

Create directory `'/srv/tftp/'`:

        $: cd /srv
        user@host: mkdir tftp

Test the server by creating a simple `test` file in `'/tftpboot/'`:

        $: cd /srv/tftp/
        $: echo "this is a test" > test
        $: cat test
        this is a test

Get the `IP address` of your host PC via 'ifconfig', then from another system:

        $: tftp <host_pc_ip_address>
        tftp> get test
        tftp> quit
        $: cat test
        this is a test

After confirming that the TFTP server is working correctly, copy your `openguard-image.wic` file to `/tftpboot/'.
This example expects the paths to be as follows:

        /tftp/openguard-image.wic

U-Boot TFTP
-------------------------------

Power up the `mguard3` and `interrupt U-Boot's` default boot selection:

        The default boot selection will start in   3 seconds

Set up `ip address` and `netmask` for mguard3:

        u-boot> setenv ipaddr <mguard3_ip_address>
        u-boot> setenv netmask <netmask>

Save your host PC's IP address to the `serverip` environment variable:

        u-boot> setenv serverip <host_pc_ip_address>

Download `openguard-image.wic` file:

        u-boot> tftp openguard-image.wic

Select the `internal mmc` device  and copy the downloaded file to the mmc flash:

        u-boot> mmc dev 1
        u-boot> mmc write ${loadaddr} 0x0 0x<count_of_blocks>

where is `count_of_blocks` is ( size of openguard-image.wic in `hex` / 200 )
converted into hexadecimal value.
>Example:
>
>        done
>        Bytes transferred = 262651904 (fa7c000 hex)
>
>
>Calculation `count_of_blocks`:
>
>        count_of_blocks = fa7c000 / 200 = 7d3e0
>
>Command:
>
>        u-boot> mmc write ${loadaddr} 0x0 0x7D3E0
>

Then run:

        u-boot> reset
