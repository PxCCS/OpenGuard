Setting up forwarding
=====================

This document describes the way how to enable firewall forwarding on openGuard device

**Important**
Since the device contains IPSec which requires forwarding to be enabled and IPSec configuration
automatically modifies forwarding on IPSec tunnel up/down, one needs to either disable mentioned
IPSec functionality, or consider it in firewall rules configuration. If one desides to disable
automatic firewall forwarding in IPSec, the following should be done before setting custom 
firewall rules:

head to `/etc/ipsec.conf` and find the following line: 

```
leftupdown=/etc/ipsec_nf_rules.sh
```

The line above is responsible for automatic forwarding enable/disable on IPSec tunnel up/down so it
may be safely commented out. IPSec service must be restarted afterwards in order to apply
new changes by issuing the following command under superuser rights:

```
ipsec restart
```


Configuring IPv4 forwarding in the kernel
=========================================

By-default, *netfilter* is set to block packet forwarding, so we need to enable it. But first, we
must check, whether IP forwarding is enabled in the kernel itself. There are few ways to do this.
First of all, IP forwaring may be set *permanent* by modifying `/etc/sysctl.conf` file. Make sure
it contains *at least* the following:

```
net.ipv4.ip_forward=1
```

Once these lines added into the mentioned file, issue a command under root rights, which will apply
updated configuration immediately:

```
root@openguard:~# sysctl -p
net.ipv4.ip_forward = 1
```

Another option is to enable IP forwarding *temporarly*. One just needs to perform the following:

```
echo "1" > /proc/sys/net/ipv4/ip_forward
```

Afterwards, the following command should give *1* as a result of successful operation:

```
root@openguard:~# cat /proc/sys/net/ipv4/ip_forward
1
```

**N.B. 1:**
Restart is not required in both cases (especially in second case, since changes done in procfs remain
in memory until reboot/power off procedure).

**N.B. 2:**
Also, keep in mind, that IPv6 forwarding works a little bit different comared to IPv4. Please, refer
to the following document - `https://www.kernel.org/doc/Documentation/networking/ip-sysctl.txt`


Configuring netfilter
=====================

After all previous steps are completed, finally we need to set-up netfilter itself.

```
root@openguard:~# nft list ruleset
    ... (skip)

	chain forward {
		type filter hook forward priority 0; policy drop;
	}

    ... (skip)
```

The rest of output is skipped for your convenience, so we look into *chain forward* which is by-default set
to drop forwarded traffic. In order to enable forwarding in netfilter, we need to do the following:

```
nft delete chain inet filter forward
nft add chain inet filter forward { type filter hook forward priority 0 \; }
```

Or, by creating a *shell script* with the following contents:

```
#! /usr/sbin/nft -f
table inet filter {
    chain forward {
	type filter hook forward priority 0; policy accept;
    }
}
```

