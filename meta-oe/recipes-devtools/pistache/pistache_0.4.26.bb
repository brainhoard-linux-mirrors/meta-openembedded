SUMMARY = "A high-performance REST toolkit written in C++"
HOMEPAGE = "https://pistacheio.github.io/pistache"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=fa818a259cbed7ce8bc2a22d35a464fc"

SRC_URI = "git://github.com/pistacheio/pistache.git;branch=master;protocol=https"
# SRCREV tagged 0.4.26
SRCREV = "ddffda861aa49012dcda28f1362d0339e718cd52"

S = "${WORKDIR}/git"

DEPENDS = "openssl brotli zlib zstd rapidjson"

inherit pkgconfig meson

PACKAGECONFIG ?= "openssl brotli deflate zstd examples"
PACKAGECONFIG[openssl]  = "-DPISTACHE_USE_SSL=true, -DPISTACHE_USE_SSL=false"
PACKAGECONFIG[brotli]   = "-DPISTACHE_USE_CONTENT_ENCODING_BROTLI=true, -DPISTACHE_USE_CONTENT_ENCODING_BROTLI=false"
PACKAGECONFIG[deflate]  = "-DPISTACHE_USE_CONTENT_ENCODING_DEFLATE=true, -DPISTACHE_USE_CONTENT_ENCODING_DEFLATE=false"
PACKAGECONFIG[zstd]     = "-DPISTACHE_USE_CONTENT_ENCODING_ZSTD=true, -DPISTACHE_USE_CONTENT_ENCODING_ZSTD=false"
PACKAGECONFIG[examples] = "-DPISTACHE_BUILD_EXAMPLES=true, -DPISTACHE_BUILD_EXAMPLES=false"
PACKAGECONFIG[test]     = "-DPISTACHE_BUILD_TESTS=true, -DPISTACHE_BUILD_TESTS=false"

do_install:append () {
    install -d ${D}${bindir}
    rm -rf ${B}/examples/*.p/
    install -m 0755 ${B}/examples/run*   ${D}${bindir}/
}

PACKAGES += "${PN}-examples"
FILES:${PN}:remove  = " ${bindir}/*"
RDEPENDS:${PN}-examples += "${PN}"
FILES:${PN}-examples = "${bindir}"

RDEPENDS:${PN} += "rapidjson"

COMPATIBLE_HOST:libc-musl = "null"

BBCLASSEXTEND = "native nativesdk"