class JVMPlatform: Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
}


class JVMPlatformName() : PlatformType {
    override val type: SupportedPlatforms = SupportedPlatforms.DESKTOP
}
actual fun getPlatformType(): PlatformType = JVMPlatformName()
actual fun getPlatform(): Platform = JVMPlatform()
