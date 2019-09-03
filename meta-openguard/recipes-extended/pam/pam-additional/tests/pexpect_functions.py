#!/usr/bin/env python3
""" summary: common functions around the pexpect-lib
    date: 2018-03-13
    author: PxCCs - HDR
    version: 0.2
"""
import pexpect

################################################################################
def test_login(username):
    """
    Test logins with username, is epxected to work without password
     - note: - this is based on the output on the shell, if it varies on different
               systems, this function must be maintained
     - also when the language is another then it must also be maintained
    """
    #set the expected token after a successful login
    token_after_login = '[#\$]'
    try:
        child = pexpect.spawn('/bin/login')
        child.expect('login:', timeout=10)
        child.sendline(username)
        child.expect(token_after_login, timeout=10)
        return True
    except:
        return False
