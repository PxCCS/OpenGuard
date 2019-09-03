Setup ipsec VPN with Strongswan, which will be configured with PreShared Key Authentication.
============================================================================================

Set the following kernel parameters:
--------------------------------------
Set the following kernel parameters in `/etc/sysctl.conf`:
```
net.ipv4.ip_forward=1
net.ipv6.conf.all.forwardong=1
```

Details of our 2 sides:
-----------------------
Side A(named `Server`):
```
$LAN_NAME: inet addr: 10.10.1.1 Mask: 255.255.255.0
$WAN_NAME: inet addr: 192.168.18.19 Mask: 255.255.255.224
```

Side B(named `Client`):
```
$LAN_NAME: inet addr: 10.12.1.1 Mask: 255.255.255.0
$WAN_NAME: inet addr: 192.168.18.24 Mask: 255.255.255.224
```

Configure `Server`:
------------------
Setup VPN Gateway in Side A ( `Server` ), first to setup the `/etc/ipsec.secrets` file
the same way as it's suggested by official documentation with respect to chosen authorization
algorithm (this document uses PSK key). The documentation can be found at:

`https://wiki.strongswan.org/projects/strongswan/wiki/IpsecSecrets`


Setup VPN configuration in `/etc/ipsec.conf`:
```
config setup
        charondebug="all"
        uniqueids=yes
        strictcrlpolicy=no

conn %default
    ikelifetime=60m
    keylife=20m
    rekeymargin=3m
    keyingtries=1
    authby=secret
    keyexchange=ikev2
    leftupdown=/etc/ipsec_nf_rules.sh
    mobike=no

conn net-net
    left=%any
    leftsubnet=10.10.1.0/24
    right=%any
    rightsubnet=10.12.1.0/24
    auto=add
```

Configure `Client`:
-------------------
Setup VPN Gateway in Side B ( `Client` ), first to setup the `/etc/ipsec.secrets` file
the same way as it was described in `Server` configuration section.


Setup VPN configuration in `/etc/ipsec.conf`:
```
config setup
        charondebug="all"
        uniqueids=yes
        strictcrlpolicy=no

conn %default
    ikelifetime=60m
    keylife=20m
    rekeymargin=3m
    keyingtries=1
    authby=secret
    keyexchange=ikev2
    leftupdown=/etc/ipsec_nf_rules.sh
    mobike=no

conn net-net
    left=%any
    leftsubnet=10.12.1.0/24
    right=192.168.18.19
    rightsubnet=10.10.1.0/24
    auto=start
```

Configure firewall
===================
For ipsec to work, you need to open ports for $WAN_NAME interface:
* TCP 50
* TCP 51
* UDP 500
* UDP 4500

To enable automatic firewall (netfilter) configuration on ipsec tunnel up/down, a file
named `/etc/ipsec_nf_rules.sh` exists which enables/disables forwarding during IPSec
operation.

Parameter that executes `/etc/ipsec_nf_rules.sh` in `/etc/ipsec.conf` is:
```
leftupdown=/etc/ipsec_nf_rules.sh
```

Start the VPN
=============
Start the VPN on both ends:
```
$ ipsec restart
```
Get the status of the tunnel, in this case we are logged onto our Side A ( `Server` ):
```
$ ipsec status
Security Associations (1 up, 0 connecting):
net-net[2]: ESTABLISHED 14 minutes ago, 192.168.18.19[192.168.18.19]...192.168.18.24[192.168.18.24]
net-net{1}:  INSTALLED, TUNNEL, reqid 1, ESP in UDP SPIs: c8c868ee_i c9d58dbd_o
net-net{1}:   10.10.1.0/24 === 10.12.1.0/24
```
