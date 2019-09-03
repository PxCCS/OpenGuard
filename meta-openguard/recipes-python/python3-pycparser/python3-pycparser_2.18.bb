inherit pypi setuptools3
require python-pycparser.inc

RDEPENDS_${PN}_class-target += "\
    python3-netclient \
    " 
