import unittest
import os, subprocess

class TestBasicFilterRules(unittest.TestCase):

    localhostv4 = "127.0.0.1"
    localhostv6 = "::1"

    def __init__(self, method_name = 'runTest'):
        return super().__init__(method_name)

    def test_ping_localhost_ipv4(self):
        with open(os.devnull, 'wb') as devnull:
            self.assertEqual(subprocess.call(['ping', '-c', '1', self.localhostv4], stdout=devnull, stderr=subprocess.STDOUT), 0, self.localhostv4 + 'is not up') 

    def test_ping_localhost_ipv6(self):
        with open(os.devnull, 'wb') as devnull:
            self.assertEqual(subprocess.call(['ping', '-6', '-c', '1', self.localhostv6], stdout=devnull, stderr=subprocess.STDOUT), 0, self.localhostv6 + 'is not up')

    def test_nft_chains_policy(self):
        proc = subprocess.Popen(['nft', 'list', 'chains'], stdout=subprocess.PIPE)
        self.assertEqual(proc.wait(), 0)

        output = proc.communicate()[0].decode("utf-8").splitlines()
        hasinput = 0
        hasforward = 0
        hasoutput = 0
        for line in output:
            if "type filter" in line and "hook input" in line and "policy drop" in line:
                hasinput = 1
            if "type filter" in line and "hook forward" in line and "policy drop" in line:
                hasforward = 1
            if "type filter" in line and "hook output" in line and "policy accept" in line:
                hasoutput = 1

        self.assertTrue(hasinput == 1 and hasforward == 1 and hasoutput == 1)

    def test_nft_conntrack_rules(self):
        proc = subprocess.Popen(['nft', 'list', 'chain', 'inet', 'filter', 'input'], stdout=subprocess.PIPE)
        self.assertEqual(proc.wait(), 0, "Cannot read expected chain 'input' in table 'filter'.")

        output = proc.communicate()[0].decode("utf-8").splitlines()
        okconntrack = 0
        for line in output:
            if "ct state" in line and "established" in line and "accept" in line:
                okconntrack = okconntrack + 1
            if "ct state" in line and "related" in line and "accept" in line:
                okconntrack = okconntrack + 1
            if "ct state" in line and "invalid" in line and "drop" in line:
                okconntrack = okconntrack + 1

        self.assertEqual(okconntrack, 3)

    def test_nft_icmpv6(self):
        proc = subprocess.Popen(['nft', 'list', 'chain', 'inet', 'filter', 'input'], stdout=subprocess.PIPE)
        self.assertEqual(proc.wait(), 0, "Cannot read expected chain 'input' in table 'filter'.")

        output = proc.communicate()[0].decode("utf-8").splitlines()
        okicmpv6 = 0
        for line in output:
            if "icmpv6 type {" in line and "destination-unreachable" in line and "packet-too-big" in line and "time-exceeded" in line and "parameter-problem" in line and "echo-request" in line and "echo-reply" in line and "} accept" in line:
                okicmpv6 = 1

        self.assertEqual(okicmpv6, 1)

    def test_nft_slaac(self):
        proc = subprocess.Popen(['nft', 'list', 'chain', 'inet', 'filter', 'input'], stdout=subprocess.PIPE)
        self.assertEqual(proc.wait(), 0, "Cannot read expected chain 'input' in table 'filter'.")

        output = proc.communicate()[0].decode("utf-8").splitlines()
        okslaac = 0
        for line in output:
            if "icmpv6 type {" in line and "nd-router-solicit" in line and "nd-router-advert" in line and "nd-neighbor-solicit" in line and "nd-neighbor-advert" in line and "} accept" in line:
                okslaac = 1

        self.assertEqual(okslaac, 1)

if __name__ == '__main__':
    unittest.main(verbosity=0)
