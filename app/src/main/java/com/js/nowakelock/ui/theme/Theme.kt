package com.js.nowakelock.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.js.nowakelock.data.repository.preferences.UserPreferencesRepository

// 应用扩展颜色
data class ExtendedColors(
    val accentColor: Color,
    val accentColor2: Color,
    val success: Color,
    val warning: Color,
    val info: Color,
    val divider: Color,
    val caption: Color
)

// 为浅色模式定义扩展颜色
val LightExtendedColors = ExtendedColors(
    accentColor = AccentLight,
    accentColor2 = AccentLight2,
    success = AllowedGreen,
    warning = WarningYellow,
    info = Tertiary40,
    divider = Color(0xFFC7C5D0),
    caption = Color(0xFF5F5F6B)
)

// 为深色模式定义扩展颜色
val DarkExtendedColors = ExtendedColors(
    accentColor = Primary80,
    accentColor2 = Color(0xFFD4BCFF),
    success = Color(0xFF7DDA95),
    warning = Color(0xFFFFB959),
    info = Tertiary80,
    divider = Color(0xFF46464F),
    caption = Color(0xFFA9A9B4)
)

// 创建自定义CompositionLocal以提供扩展颜色
val LocalExtendedColors = staticCompositionLocalOf {
    LightExtendedColors
}

// 添加扩展颜色访问器
val MaterialTheme.extendedColors: ExtendedColors
    @Composable
    get() = LocalExtendedColors.current

// ============================================================
// 完整 Material 3 色彩方案
// ============================================================

private val DarkColorScheme = darkColorScheme(
    primary = Primary80,
    onPrimary = Color(0xFF1A2578),
    primaryContainer = Color(0xFF323F92),
    onPrimaryContainer = Color(0xFFDFE0FF),

    secondary = Secondary80,
    onSecondary = Color(0xFF2F2F42),
    secondaryContainer = Color(0xFF454559),
    onSecondaryContainer = Color(0xFFE1E0F9),

    tertiary = Tertiary80,
    onTertiary = Color(0xFF003738),
    tertiaryContainer = Color(0xFF1E4F50),
    onTertiaryContainer = Color(0xFF9CF1F3),

    background = Color(0xFF121319),
    onBackground = Color(0xFFE4E1E9),
    surface = Color(0xFF121319),
    onSurface = Color(0xFFE4E1E9),
    surfaceVariant = Color(0xFF46464F),
    onSurfaceVariant = Color(0xFFC7C5D0),

    surfaceContainerLowest = Color(0xFF0D0E13),
    surfaceContainerLow = Color(0xFF1A1B21),
    surfaceContainer = Color(0xFF1E1F25),
    surfaceContainerHigh = Color(0xFF282A30),
    surfaceContainerHighest = Color(0xFF33353B),

    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005),
    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD6),

    outline = Color(0xFF90909A),
    outlineVariant = Color(0xFF46464F),
    inverseSurface = Color(0xFFE4E1E9),
    inverseOnSurface = Color(0xFF2F2F3A),
    inversePrimary = Primary40,
    scrim = Color(0xFF000000)
)

private val LightColorScheme = lightColorScheme(
    primary = Primary40,
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFDFE0FF),
    onPrimaryContainer = Color(0xFF000E61),

    secondary = Secondary40,
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFE1E0F9),
    onSecondaryContainer = Color(0xFF191A2C),

    tertiary = Tertiary40,
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFF9CF1F3),
    onTertiaryContainer = Color(0xFF002021),

    background = Background,
    onBackground = OnSurface,
    surface = Surface,
    onSurface = OnSurface,
    surfaceVariant = SurfaceVariant,
    onSurfaceVariant = OnSurfaceVariant,

    surfaceContainerLowest = Color(0xFFFFFFFF),
    surfaceContainerLow = SurfaceContainer,
    surfaceContainer = Color(0xFFEFEDF7),
    surfaceContainerHigh = Color(0xFFE9E7F1),
    surfaceContainerHighest = Color(0xFFE3E1EC),

    error = Color(0xFFBA1A1A),
    onError = Color(0xFFFFFFFF),
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFF410002),

    outline = Color(0xFF777680),
    outlineVariant = Color(0xFFC7C5D0),
    inverseSurface = Color(0xFF2F2F3A),
    inverseOnSurface = Color(0xFFF1EFF7),
    inversePrimary = Primary80,
    scrim = Color(0xFF000000)
)

// 全局圆角形状系统
private val AppShapes = Shapes(
    extraSmall = RoundedCornerShape(8.dp),
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(20.dp),
    extraLarge = RoundedCornerShape(28.dp)
)

@Composable
fun NoWakeLockTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+; disabled by default to
    // keep the app's own visual identity consistent across devices
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    // choose the color scheme
    val extendedColors = if (darkTheme) DarkExtendedColors else LightExtendedColors

    // tapBar only when android version < 15
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.VANILLA_ICE_CREAM) {
        val view = LocalView.current
        if (!view.isInEditMode) {
            SideEffect {
                val window = (view.context as Activity).window
                window.statusBarColor = colorScheme.background.toArgb()
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars =
                    !darkTheme
            }
        }
    }

    androidx.compose.runtime.CompositionLocalProvider(
        LocalExtendedColors provides extendedColors
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            shapes = AppShapes,
            content = content
        )
    }
}

/**
 * NoWakeLock theme that applies Material 3 styling
 * based on user preferences
 */
@Composable
fun NoWakeLockTheme(
    themeMode: UserPreferencesRepository.ThemeMode = UserPreferencesRepository.ThemeMode.SYSTEM,
    content: @Composable () -> Unit
) {
    val darkTheme = when (themeMode) {
        UserPreferencesRepository.ThemeMode.LIGHT -> false
        UserPreferencesRepository.ThemeMode.DARK -> true
        UserPreferencesRepository.ThemeMode.SYSTEM -> isSystemInDarkTheme()
    }

    // 调用现有的 NoWakeLockTheme 实现
    NoWakeLockTheme(
        darkTheme = darkTheme,
        dynamicColor = false,
        content = content
    )
}
