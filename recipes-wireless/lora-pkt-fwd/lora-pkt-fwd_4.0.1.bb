SUMMARY = "LoRa network packet forwarder project"
SECTION = "libs/network"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=22af7693d7b76ef0fc76161c4be76c45"

SRC_URI = "https://github.com/Lora-net/packet_forwarder/archive/v${PV}.tar.gz"

SRC_URI[md5sum] = "a1f942e0cc7b02d604b11c8cb5f2a029"
SRC_URI[sha256sum] = "e68fadf6f1d2e5e7b601e504d5efb48b0a8f374c2c29c0476ab2fe9db68d33ae"

DEPENDS += "lora-gateway" 

S = "${WORKDIR}/packet_forwarder-${PV}"

CFLAGS_append = "-I ${includedir}/libloragw -I ${S}/lora_pkt_fwd/inc -I ${S}/util_tx_test/inc "

do_configure_prepend() {
	export LGW_PATH="${STAGING_LIBDIR}/libloragw"
}

do_compile_prepend() {
	export LGW_PATH="${STAGING_LIBDIR}/libloragw"
}

do_install() {
	install -d ${D}${bindir}
	install -d ${D}${docdir}/lora-pkt-fwd/conf
	
	install ${B}/lora_pkt_fwd/lora_pkt_fwd ${D}${bindir}
	install ${B}/util_*/util_* ${D}${bindir}
	
	install -D -m 0644 ${S}/PROTOCOL.TXT ${D}${docdir}/lora-pkt-fwd/PROTOCOL.TXT
	install -D -m 0644 ${S}/lora_pkt_fwd/readme.md ${D}${docdir}/lora-pkt-fwd/readme.md
	install -D -m 0644 ${S}/lora_pkt_fwd/global_conf.json ${D}${docdir}/lora-pkt-fwd/global_conf.json
	install -D -m 0644 ${S}/lora_pkt_fwd/local_conf.json ${D}${docdir}/lora-pkt-fwd/local_conf.json
	install -D -m 0755 ${S}/lora_pkt_fwd/update_gwid.sh ${D}${docdir}/lora-pkt-fwd
	install -D -m 0644 ${S}/lora_pkt_fwd/cfg/*.json.* ${D}${docdir}/lora-pkt-fwd/conf
	
	rm -f ${D}${bindir}/util_tx_test
	rm -f ${D}${bindir}/.debug/util_tx_test
}

PACKAGE_DEBUG_SPLIT_STYLE = "debug-without-src"

PACKAGES = "${PN}-dbg ${PN} ${PN}-doc"

# Avoid QA Issue: No GNU_HASH in the elf binary
INSANE_SKIP_${PN} = "ldflags"

FILES_${PN}-dbg = " \
	${bindir}/.debug \
"
FILES_${PN} = " \
	${bindir}/* \
"
FILES_${PN}-doc = " \
	${docdir} \
"
