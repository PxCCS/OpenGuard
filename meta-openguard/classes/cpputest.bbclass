## This class does produce
## a ptest-package using CPPUTest-binaries
## If your tests are in a subfolder of WORKDIR
## Set CPPUTEST_INPUT_DIR before inherit this class

CPPUTEST_INPUT_DIR ?= "."

EXTRA_OEMAKE += "CPPUTEST_HOME=${STAGING_DATADIR}/cpputest COMPONENT_NAME=${PN}"
DEPENDS = "cpputest"

inherit ptest

do_compile_append() {
	cd ${WORKDIR}/${CPPUTEST_INPUT_DIR}/
		oe_runmake all_no_tests
}

## package_qa complains but file is proper
## so ignore this here
INSANE_SKIP_${PN}-ptest = "ldflags"

do_install_ptest() {
	mkdir -p ${D}${PTEST_PATH}/lib
	install ${WORKDIR}/${CPPUTEST_INPUT_DIR}/${PN}_tests ${D}${PTEST_PATH}
	## Inline create ptest-runner-script
	## to autoinclude the package name
	cat << EOF > ${D}${PTEST_PATH}/run-ptest
#!/bin/sh
for tcase in \$(./${PN}_tests -ln); do
	trpl=\$(echo \$tcase | sed "s/\./, /g" )
	tres=\$(./${PN}_tests "TEST(\$trpl)")
	if [ \$(echo "\$tres" | tail -n 2 | grep -c "Errors (") -gt 0 ]; then
		echo "FAIL: \$tcase"
		echo "#### OUTPUT BEGIN"
		echo "\$tres"
		echo "#### OUTPUT END"
	elif [ \$(echo "\$tres" | tail -n 2 | grep -c "1 ignored") -gt 0 ]; then
		echo "SKIP: \$tcase"
        else
		echo "PASS: \$tcase"
	fi
done
EOF
	chmod +x ${D}${PTEST_PATH}/run-ptest
}
