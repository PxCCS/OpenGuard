General information
===================

According to current configuration, there are two configuration
files exist in the /etc/ssh folder:
- `sshd_config`
- `sshd_config_readonly`

The first one is used when the system operates normally and root
file system is not mounted as r/o, the second in turn is used when
the system is r/o and this is the default behavior when the image
is created if other behavior has not been otherwise defined.
    Switching between configuration files can be done by modifying
`/etc/default/ssh` by changing the following line:

```
SSHD_OPTS='-f /etc/ssh/sshd_config_readonly'
```

to

```
SSHD_OPTS='-f /etc/ssh/sshd_config'
```

To enable SSH access using public key, the following steps need
to be done:

```
After board is booted and a user is logged in:
1. mount -o,remount rw /
    N.B. - if the file system is mounted r/o
2. useradd -m -s /bin/sh -p test demo
    N.B. - user name: demo, password: test
3. mkdir -p /home/demo/.ssh
4. touch /home/demo/.ssh/authorized_keys
5. chown demo:demo -R /home/demo
6. echo %key% > /home/demo/.ssh/authorized_keys

Generate host keys:
ssh-keygen -f /etc/ssh/ssh_host_rsa_key -t rsa -P ''
ssh-keygen -f /etc/ssh/ssh_host_dsa_key -t dsa -P ''
ssh-keygen -f /etc/ssh/ssh_host_ecdsa_key -t ecdsa -P ''
ssh-keygen -f /etc/ssh/ssh_host_ed25519_key -t ed25519 -P '' 
```
**N.B. - '' <- single quotes**


Default configuration
=====================

You can either use the following suggested configuration
to be able to connect to the device over SSH, or write your own.
Replace `/etc/ssh/sshd_config` contents with the following:

```
Port 22
Protocol 2
HostKey /etc/ssh/ssh_host_rsa_key
HostKey /etc/ssh/ssh_host_dsa_key
HostKey /etc/ssh/ssh_host_ecdsa_key
HostKey /etc/ssh/ssh_host_ed25519_key
UsePrivilegeSeparation yes
KeyRegenerationInterval 3600
ServerKeyBits 1024
SyslogFacility AUTH
LogLevel INFO
LoginGraceTime 120
PermitRootLogin no
StrictModes yes
RSAAuthentication yes
PubkeyAuthentication yes
IgnoreRhosts yes
RhostsRSAAuthentication no
HostbasedAuthentication no
PermitEmptyPasswords no
ChallengeResponseAuthentication no
PasswordAuthentication no
X11Forwarding yes
X11DisplayOffset 10
PrintMotd no
PrintLastLog yes
TCPKeepAlive yes
AcceptEnv LANG LC_*
UsePAM yes
```
