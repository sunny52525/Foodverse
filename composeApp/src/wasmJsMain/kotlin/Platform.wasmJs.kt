class WasmPlatform: Platform {
    override val name: String = "Web with Kotlin/Wasm"
}

class PlatformName() : PlatformType {
    override val type: SupportedPlatforms = SupportedPlatforms.WEB
}
actual fun getPlatformType(): PlatformType = PlatformName()
actual fun getPlatform(): Platform = WasmPlatform()