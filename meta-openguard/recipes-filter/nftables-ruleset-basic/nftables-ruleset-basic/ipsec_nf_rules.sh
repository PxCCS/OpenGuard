#!/bin/sh
TMP=/tmp/nft_tmp
NFT=$(which nft)

case "${PLUTO_VERB}" in
    up-client)
cat << 'EOF' > ${TMP}
#! /usr/sbin/nft -f
table inet filter {
    chain forward {
	type filter hook forward priority 0; policy accept;
    }
}
EOF
    if [ -f ${TMP} ]; then
	${NFT} -f ${TMP}
	rm ${TMP}
    fi
        ;;
    down-client)
cat << 'EOF' > ${TMP}
#! /usr/sbin/nft -f
table inet filter {
    chain forward {
	type filter hook forward priority 0; policy drop;
    }
}
EOF
    if [ -f ${TMP} ]; then
	${NFT} -f ${TMP}
	rm ${TMP}
    fi
        ;;
esac
