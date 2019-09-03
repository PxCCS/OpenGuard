
PYUNIT_TESTFILES ?= "${WORKDIR}/tests/*.py"
PYUNIT_EXTRAFILES ?= ""
PYUNIT_TESTBASEDIR ?= "${WORKDIR}"
PYUNIT_SOURCEDIR ?= ""
PYUNIT_SHELL_SETUP ?= ""
PYUNIT_SHELL_SHUTDOWN ?= ""

RDEPENDS_${PN} += "python3"

inherit ptest

do_install_runner() {
    ## create Testrunner class
    mkdir -p "${B}"
    cat << EOF > "${B}/PTestRunner.py"
import unittest
import time

class PTestCompatResult(unittest._TextTestResult):
    import unittest

    def LazyPrint(self, msg, retries=3):
        im_flush=True
        if retries < 1:
            im_flush=False
        if len(msg) > 5000:
            msg = msg[0:5000] + "..."
        try:
            print(msg, flush=im_flush)
        except BlockingIOError:
            rtries = retries - 1
            time.sleep(1)
            self.LazyPrint(msg, retries=rtries)
        except UnicodeEncodeError:
            self.LazyPrint(msg.encode('utf-8'))
        except UnicodeDecodeError:
            self.LazyPrint(msg.decode('utf-8'))

    def get_formatted_error(self, err):
        import traceback
        return ''.join(traceback.format_exception(err[0], err[1], err[2]))

    def get_formatted_casename(self, test):
        x = test.id()
        if x.startswith("__main__."):
            x = x[len("__main__."):]
        return x

    def addSuccess(self, test):
        self.LazyPrint("PASS: %s" % self.get_formatted_casename(test))

    def addError(self, test, err):
        self.addFailure(test, err)

    def addSkip(self, test, reason):
        self.LazyPrint("#######################")
        self.LazyPrint(str(reason))
        self.LazyPrint("#######################")
        self.LazyPrint("SKIP: %s" % self.get_formatted_casename(test))

    def addFailure(self, test, err):
        import traceback
        self.LazyPrint("#######################")
        self.LazyPrint(self.get_formatted_error(err))
        self.LazyPrint("#######################")
        self.LazyPrint("FAIL: %s" % self.get_formatted_casename(test))

class PTestCompatTestRunner(unittest.TextTestRunner):
    def _makeResult(self):
        return PTestCompatResult(self.stream, self.descriptions, self.verbosity)
EOF
}

addtask do_install_runner after do_compile before do_install

python do_install_ptest_base_prepend() {
    import glob
    import os
    import re
    _files = []
    os.chdir(d.getVar("WORKDIR", True))
    for item in d.getVar("PYUNIT_TESTFILES", True).split(" "):
        _files += glob.glob(item)
    _files = list(set(_files))
    for _f in _files:
        ## Patch runner and import
        f = open(_f, "r")
        content = f.read()
        f.close()
        match = re.search(r"^from\s+PTestRunner\s+import\s+PTestCompatTestRunner$", content, re.MULTILINE)
        if not match:
            content = "from PTestRunner import PTestCompatTestRunner\n" + content
        ## add shebang
        if not content.startswith("#!/usr/bin/env python3\n"):
            content = "#!/usr/bin/env python3\n" + content
        match = re.search(r"unittest\.main\((?P<options>.*)\)", content, re.MULTILINE)
        if not match:
            bb.warn(content)
            bb.warn("%s: No unittest entry point found" % _f)
        else:
            options = [x.strip() for x in match.group("options").split(",")]
            o_testrunner = [x for x in options if x.startswith("testRunner")]
            o_exit = [x for x in options if x.startswith("exit")]
            options = [x for x in options if x not in o_testrunner]
            options = [x for x in options if x not in o_exit]
            options.append("testRunner = PTestCompatTestRunner()")
            options.append("exit = False")
            newmatch = "unittest.main(" + ",".join(options) +  ")"
            content = content.replace(match.group(0), newmatch)
        f = open(_f, "w")
        f.write(content)
        f.close()
        os.chmod(_f, 0o744)
}

python do_install_ptest_base() {
    import glob
    import os
    _testFiles = []
    os.chdir(d.getVar("WORKDIR", True))
    for item in d.getVar("PYUNIT_TESTFILES", True).split(" "):
        _testFiles += glob.glob(item)
    _testFiles = ["%s/%s" % (d.getVar("PTEST_PATH", True),  os.path.basename(x)) for x in list(set(_testFiles))]
    ## create run-ptest
    f = open("%s/%s/run-ptest" % (d.getVar("D", True), d.getVar("PTEST_PATH", True)), "w")
    f.write("#!/bin/sh\n")
    f.write(d.getVar("PYUNIT_SHELL_SETUP", True) + "\n")

    _extraFiles = d.getVar("PYUNIT_EXTRAFILES", True).split(" ")
    if "setup.py" in _extraFiles:
        f.write("coverage run --source " + d.getVar("PYUNIT_SOURCEDIR") + " setup.py test --test-runner 'PTestRunner:PTestCompatTestRunner'\n")
        f.write("coverage xml\n")
        f.write("echo \"[coverage-file-begin] `readlink -f coverage.xml`\"\n")
        f.write("cat coverage.xml | sed 's/.*/[coverage-file-content] &/'\n")
        f.write("echo \"[coverage-file-end] `readlink -f coverage.xml`\"")
    else:
        f.write("\n".join(_testFiles)+"\n")

    f.write(d.getVar("PYUNIT_SHELL_SHUTDOWN", True) + "\n")
    f.close();
    os.chmod("%s/%s/run-ptest" % (d.getVar("D", True), d.getVar("PTEST_PATH", True)), 0o744)
}

python do_install_ptest_base_append() {
    import shutil
    import glob
    import os
    _testFiles = []
    os.chdir(d.getVar("WORKDIR", True))
    for item in d.getVar("PYUNIT_TESTFILES", True).split(" "):
        _testFiles += glob.glob(item)
    for item in d.getVar("PYUNIT_EXTRAFILES", True).split(" "):
        _testFiles += glob.glob(item)

    for _f in _testFiles:
        basep = os.path.join(d.getVar("WORKDIR", True), _f)
        common_prefix = os.path.commonprefix([d.getVar("PYUNIT_TESTBASEDIR", True), basep])
        relTarget = os.path.relpath(basep, common_prefix)
        targetp = os.path.join(d.getVar("D", True), d.getVar("PTEST_PATH", True)[1:], relTarget)
        if not os.path.exists(os.path.dirname(targetp)):
            os.makedirs(os.path.dirname(targetp))
        shutil.copy2(basep, targetp)
    
    ## install testrunner
    basep = os.path.join(d.getVar("B", True), "PTestRunner.py")
    targetp = os.path.join(d.getVar("D", True), d.getVar("PTEST_PATH", True)[1:], "PTestRunner.py")
    shutil.copy2(basep, targetp)
}
