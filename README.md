# openguard

The openGuard repository that will go public.



Build System Setup
------------------

openGuard is built on a system running Debian 9 (“stretch”). To prepare your
system for openGuard builds, run the following command as _root_ user (i.e.
copy and paste them literally into a _root_ shell).

### Install required software packages

``` shell
apt-get install --no-install-recommends \
    build-essential ca-certificates chrpath diffstat file gawk git \
    locales python python3 texinfo wget
```


Getting Started
---------------

The following commands must be run from the user account used for
development (i.e. not the _root_ account).

### Create an SSH key pair

If you already have an SSH key pair, you can skip this section.

Otherwise, generate a 2048 bit RSA key pair:

``` shell
ssh-keygen -t rsa -b 2048
```

The `ssh-keygen` command prompts for a file in which to save the key.
Press enter to accept the default (`~/.ssh/id_rsa`; the `~/.ssh`
directory will be created if it does not yet exist). Furthermore, you
will be asked to enter a passphrase that protects the private key.
Anyone who can access the private key file and acquire knowledge of the
passphrase can impersonate you, so choose a secure passphrase.

Make sure an SSH authentication agent is running in your session. How to
achieve this is beyond the scope of this introduction; if in doubt, ask
your system administrator for help. Alternatively, the command

``` shell
eval $(ssh-agent -s)
```

starts an SSH authentication agent that is effective only in the
terminal window where you typed the command.

Add your SSH key to the authentication agent:

``` shell
ssh-add
```

You will be asked for the passphrase again.

### Store the SSH public key into the repository server

Open the SSH keys page
([https://pxccs-gitlab.dev.de.innominate.com/profile/keys](https://pxccs-gitlab.dev.de.innominate.com/profile/keys),
or click on the avatar in the top-right corner, then on “Settings”, then
on “SSH Keys”). Paste your public key (the content of the file
`~/.ssh/id_rsa.pub`) into the “Key” field and click the “Add key”
button.

**Warning: Make sure you paste your public key (`~/.ssh/id_rsa.pub`),
not your private key (`~/.ssh/id_rsa`)! The private key should never
leave the system on which it was created.**

You can now access git repositories on the openGuard repository server with
the `git` command without having to enter your passphrase during every
interaction.

### Configure git

Replace `Your Name` and `your.name@phoenixcontact.com` with your actual
full name and e-mail address, respectively.

``` shell
git config --global user.name "Your Name"

git config --global user.email "your.name@phoenixcontact.com"
```

### Clone the openGuard git repository

Since the openGuard build infrastructure comprises multiple directories
containing local copies of git repositories, as well as a build
directory, it is advisable to use a dedicated workspace directory in
which to create them. Doing so prevents cluttering the home directory.

In this document, it is assumed that `~/workspace` is the workspace
directory, but this is not a technical requirement. Any name can be
used.

``` shell
mkdir ~/workspace

cd ~/workspace

git clone --recursive git@pxccs-gitlab.dev.de.innominate.com:openguard/openguard.git
```

When you access the repository server with the `git` command for the
first time, you will see a message similar to this:

```
The authenticity of host 'pxccs-gitlab.dev.de.innominate.com' can't be established.
ECDSA key fingerprint is SHA256:WDttGc/7BWS/DPAFqOdahJlMwgE1uKnJOoApRo11X2I.
Are you sure you want to continue connecting (yes/no)?
```

This is not an error, but an information that your SSH client has not
encountered the server “pxccs-gitlab.dev.de.innominate.com” before. Please enter
`yes` to continue.

### Set up the workspace

The openGuard repository contains a script that performs the necessary setup
steps:

``` shell
openguard/openguard-setup
```

This script creates a build directory (`~/workspace/build`) and
populates it with a number of required files. If you want to use a
different build directory, you can specify its path as an argument to
the `openguard-setup` script.

The script does not overwrite files that already exist. Therefore, any
modification you make to a file created by the script is retained when
you run `openguard-setup` again. If you want to recreate the original version
of a file, move the existing file out of the way before running the
script.

(The next paragraph contains advanced information for git experts. If
you do not understand what it means, you can ignore it.)

The setup script also creates git hooks (`post-checkout`, `post-merge`,
and `post-rewrite`) that update all submodules when you perform a
checkout, merge, rebase, or amend operation in your copy of the openGuard
repository. (Currently the `poky` repository is the only submodule.) If
this is not desired, you might want to disable the hooks.

### Build the openGuard base distribution

Follow the instructions that the setup script displayed:

``` shell
cd ~/workspace/build

. ./openguard-buildenv.sh
```

You may edit the `conf/bblayers.conf` and `conf/local.conf` files, but
this is not required to build the openGuard base distribution.

The openGuard base distribution can be built with this command:

``` shell
bitbake openguard-image
```

Building the base distribution for the first time takes between about
half an hour and several hours, depending on the build system.
Subsequent builds are faster, since only changed parts are rebuilt.

By default (i.e. if you did not change the `MACHINE` variable in
`conf/local.conf`), the base distribution is built for an _x86_ target
system that can be run in the QEMU emulator. This command runs the
emulator with the correct arguments to launch the openGuard image just built:

``` shell
runqemu openguard-image nographic
```


openGuard on the openGuard device
-----------------------------

To run openGuard on the openGuard device follow the instructions in the “[openGuard on the mguard3 Board](doc/Hardware/mguard3.md)”
document.
