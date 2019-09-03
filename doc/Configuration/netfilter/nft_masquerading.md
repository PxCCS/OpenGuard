Configuring Masquerading/NAT
============================

After we've configured packet forwarding (see `nft_forwarding.md`), we may proceed with masquerading, which
is a special case of SNAT, where the source address is automagically set to the address of the output interface.

```
root@openguard:~# nft add table nat
root@openguard:~# nft add chain nat prerouting { type nat hook prerouting priority 0 \; }
root@openguard:~# nft add chain nat postrouting { type nat hook postrouting priority 100 \; }
root@openguard:~# nft add rule nat postrouting oifname eth0 masquerade
```

where `eth0` - your WAN (external) interface
Now, netfilter table `nat` should look like this:

```
root@openguard:~# nft list ruleset
    ... (skip)
    
table ip nat {
	chain prerouting {
		type nat hook prerouting priority 0; policy accept;
	}

	chain postrouting {
		type nat hook postrouting priority 100; policy accept;
		oifname "eth0" masquerade
	}
}
```

**N.B.**
More information about configuring NAT/masquerading for netfilter can be found here:
`https://wiki.nftables.org/wiki-nftables/index.php/Performing_Network_Address_Translation_(NAT)`

