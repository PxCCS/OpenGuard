def clean_string(inputs, chars=["\n", "\r"]):
    for i in chars:
        inputs = inputs.replace(i, "")
    return inputs.strip()

def layers(d):
    import os
    layerdirs = [ os.path.abspath(os.path.join(p, '..')) for p in d.getVar("BBPATH").split(':') if os.path.exists(os.path.join(p, '..', '.git')) ]
    return layerdirs
            
def full_version(d):
    import subprocess
    try:
        distro = "0.1.0"
        batcmd = "cd {} && git describe --tags --first-parent --abbrev=0 --always".format(layers(d)[0])
        result = subprocess.run(batcmd, check=False, shell=True, stderr=subprocess.STDOUT, stdout=subprocess.PIPE, universal_newlines=True)
        if result.returncode == 0:
            tag = result.stdout.strip()
            distro = tag.split('/')[-1]
    except Exception:
        distro = "0.1.0"
    return distro

def distro_release_version(d):
    version = full_version(d)
    return version.split(sep='-', maxsplit=1)[0]

def distro_iteration(d):
    version = full_version(d)
    distro = version.split(sep='-', maxsplit=1)
    if len(distro) > 1:
        return "-{}".format(distro[1])
    return ''

def distro_localcommits(d):
    import subprocess
    result = ""
    try:
        batcmd="cd {} && git log --oneline $(git describe --tags --abbrev=0)..@".format(layers(d)[0])
        result = subprocess.check_output(batcmd, shell=True)
        result = [x for x in result.decode("utf-8").split("\n") if x]
        if any(result):
            result = [clean_string(x) for x in result[0].split(" ") if x]
            result = result[0]
        else:
            result = ""
        return result
    except:
        return ""

def filter_distro_lc(line):
    ## this function shall filter local changes
    chunks = line.split(" ")
    if len(chunks) > 1:
        return chunks[1]
    return None

def get_distro_localchanges(d):
    import subprocess
    try:
        batcmd="cd {} && git status --porcelain -u".format(layers(d)[0])
        result = subprocess.check_output(batcmd, shell=True)
        result = [clean_string(x) for x in result.decode("utf-8").split("\n") if x]
        result = [filter_distro_lc(x) for x in result]
        return [x for x in result if x]
    except:
        return []

def distro_localchanges(d):
    result = get_distro_localchanges(d)
    if any(result):
        if d.getVar("LOCAL_CHANGES_AS_ERROR", True):
            bb.error("Local changes for {} found".format(result))
        else:
            bb.warn("Local changes for {} found".format(result))
        return "LC-{}".format(len(result))
    return ""

def distro_localchanges_hash(d):
    import hashlib
    import os    
    m = hashlib.sha1()
    for item in [os.path.join(layers(d)[0], x) for x in get_distro_localchanges(d)]:
        try:
            with open(item) as f:
                m.update(f.read().encode("utf-8"))
        except:
            pass
    return m.hexdigest()[0:7]

def get_layer_hash(layerdir):
    import subprocess
    import re
    try:
        batcmd="cd {} && git rev-parse HEAD".format(layerdir)
        result = subprocess.check_output(batcmd, shell=True)
        res = result.decode("utf-8").strip("\n").strip(" ")
        batcmd="cd {} && git branch --no-color".format(layerdir)
        result = subprocess.check_output(batcmd, shell=True)
        ## get the current branch
        result = [clean_string(x) for x in result.decode("utf-8").split("\n") if x.startswith("*")]
        if result:
            pattern = r"^\*\s+(\(HEAD detached at )*(?P<name>.*)(\))*"
            for m in re.finditer(pattern, result[0], flags=re.MULTILINE):
                res = m.group("name").rstrip(")").strip() + ":" + res
        else:
            res = "unknown branch:" + res
        return res
    except Exception:
        return "local build"

def distro_layer_hashes(d):
    import os
    dirs = { os.path.split(layerdir)[1]:layerdir for layerdir in layers(d) }
    res = [ "{}:{}".format(k, get_layer_hash(v)) for k, v in dirs.items() ]
    return " ".join(res)

def uboot_machine(d):
    return d.getVar("UBOOT_MACHINE")[::-1].replace("_defconfig"[::-1], "", 1)[::-1]

def distro_build_version(d):
    drv = d.getVar("DISTRO_RELEASE_VERSION")
    dit = d.getVar("DISTRO_ITERATION")
    dlcom = d.getVar("DISTRO_LOCALCOMMITS")
    dlc = d.getVar("DISTRO_LOCALCHANGES").strip()
    if dlc:
        dlc = "+{}".format(dlc)
    if not dit.endswith("+") and dlcom:
        dit += "+"
    return "{}{}{}{}".format(drv, dit, dlcom, dlc)
