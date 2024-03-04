import platform.UIKit.UIDevice

class IOSPlatform : Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

class PlatformName() : PlatformType {
    override val type: SupportedPlatforms = SupportedPlatforms.IOS
}
actual fun getPlatformType(): PlatformType = PlatformName()
actual fun getPlatform(): Platform = IOSPlatform()

