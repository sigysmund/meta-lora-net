SUMMARY = "LoRa Gateway project"
SECTION = "libs/network"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=a2bdef95625509f821ba00460e3ae0eb"

SRC_URI = "https://github.com/Lora-net/lora_gateway/archive/v${PV}.tar.gz"

SRC_URI[md5sum] = "5d07f8471e1a67920787e3879afe0cb6"
SRC_URI[sha256sum] = "1a0447d5e8183d08e6dce5f739f6872b9c57824b98f4078830d5ee21b15782c1"

S = "${WORKDIR}/lora_gateway-${PV}"

CFLAGS_append = "-I ${S}/libloragw/inc -I ${S}/libloragw -I ${S}/util_pkt_logger/inc "

do_install() {
	install -d ${D}${bindir}
	install -d ${D}${docdir}
	install -d ${D}${libdir}
	install -d ${D}${includedir}/libloragw
	
	install -D -m 0644 ${B}/libloragw/inc/* ${D}${includedir}/libloragw
	
	install -D -m 0644 ${B}/libloragw/libloragw.a ${D}${libdir}/libloragw/libloragw.a
	install -D -m 0644 ${S}/libloragw/library.cfg ${D}${libdir}/libloragw/library.cfg
	
	install ${B}/libloragw/test_* ${D}${bindir}
	install ${B}/util_*/util_* ${D}${bindir}
	
	install -D -m 0644 ${S}/readme.md ${D}${docdir}/libloragw/changelog.md
	install -D -m 0644 ${S}/libloragw/readme.md ${D}${docdir}/libloragw/README.md
	install -D -m 0644 ${S}/util_lbt_test/readme.md ${D}${docdir}/libloragw/util_lbt_test.md
	install -D -m 0644 ${S}/util_pkt_logger/readme.md ${D}${docdir}/libloragw/util_pkt_logger.md
	install -D -m 0644 ${S}/util_spectral_scan/readme.md ${D}${docdir}/libloragw/util_spectral_scan.md
	install -D -m 0644 ${S}/util_spi_stress/readme.md ${D}${docdir}/libloragw/util_spi_stress.md
	install -D -m 0644 ${S}/util_tx_continuous/readme.md ${D}${docdir}/libloragw/util_tx_continuous.md
	install -D -m 0644 ${S}/util_tx_test/readme.md ${D}${docdir}/libloragw/util_tx_test.md
	
	install -D -m 0644 ${S}/util_pkt_logger/global_conf.json ${D}${docdir}/libloragw/global_conf.json
	install -D -m 0644 ${S}/util_pkt_logger/local_conf.json ${D}${docdir}/libloragw/local_conf.json
}

PACKAGE_DEBUG_SPLIT_STYLE = "debug-without-src"

PACKAGES = "${PN}-dbg ${PN} ${PN}-doc ${PN}-dev ${PN}-staticdev"

# Avoid QA Issue: No GNU_HASH in the elf binary
INSANE_SKIP_${PN} = "ldflags"

FILES_${PN}-dbg = " \
	${bindir}/.debug \
	${libdir}/libloragw/.debug \
"
FILES_${PN} = " \
	${bindir}/* \
"
FILES_${PN}-dev = " \
	${includedir}/libloragw/* \
"
FILES_${PN}-staticdev = " \
	${libdir}/libloragw/*.a \
	${libdir}/libloragw/*.cfg \
"
FILES_${PN}-doc = " \
	${docdir} \
"
