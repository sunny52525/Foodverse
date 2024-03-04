import android.os.Build

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

class AndroidPlatformType() : PlatformType {
    override val type: SupportedPlatforms = SupportedPlatforms.ANDROID
}

actual fun getPlatform(): Platform = AndroidPlatform()

actual fun getPlatformType(): PlatformType = AndroidPlatformType()