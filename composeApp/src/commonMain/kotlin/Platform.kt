interface Platform {
    val name: String
}

interface PlatformType {
    val type: SupportedPlatforms
}

enum class SupportedPlatforms {
    IOS,
    ANDROID,
    DESKTOP,
    WEB
}

expect fun getPlatform(): Platform
expect fun getPlatformType(): PlatformType
