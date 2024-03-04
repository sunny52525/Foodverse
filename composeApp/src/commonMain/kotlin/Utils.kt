import androidx.compose.runtime.Composable

object Constants {
    val aToZ =
        listOf(
            "A to C",
            "D to F",
            "G to I",
            "J to L",
            "M to O",
            "P to R",
            "S to U",
            "V to X",
            "Y to Z"
        )
    val aToZValue = arrayOf(
        aToZ[0] to listOf("A", "B", "C"),
        aToZ[1] to listOf("D", "E", "F"),
        aToZ[2] to listOf("G", "H", "I"),
        aToZ[3] to listOf("J", "K", "L"),
        aToZ[4] to listOf("M", "N", "O"),
        aToZ[5] to listOf("P", "Q", "R"),
        aToZ[6] to listOf("S", "T", "U"),
        aToZ[7] to listOf("V", "W", "X"),
        aToZ[8] to listOf("Y", "Z")
    )

}

@Composable
fun isMobile(): Boolean {
    val platformType = getPlatformType()
    return platformType.type == SupportedPlatforms.ANDROID || platformType.type == SupportedPlatforms.IOS
}
