package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

enum class AppThemeMode(val title: String, val subtitle: String) {
  SYSTEM("System Default", "Follows device system settings"),
  OLED_BLACK("Pure Black (OLED)", "True #000000 black for maximum battery & eye comfort"),
  DARK("Dark Slate", "Deep midnight slate aesthetic"),
  LIGHT("Light Mode", "Crisp, bright high-contrast theme")
}

val LocalAppThemeMode = staticCompositionLocalOf { AppThemeMode.SYSTEM }

val OledDarkColorScheme = darkColorScheme(
  primary = BrandPrimary,
  onPrimary = Color.Black,
  primaryContainer = Color(0xFF0369A1),
  onPrimaryContainer = Color(0xFFE0F2FE),
  secondary = BrandSecondary,
  onSecondary = Color.Black,
  secondaryContainer = Color(0xFF3730A3),
  onSecondaryContainer = Color(0xFFEEF2FF),
  tertiary = BrandAccentGold,
  onTertiary = Color.Black,
  background = OledBackground,
  onBackground = OledTextPrimary,
  surface = OledSurface,
  onSurface = OledTextPrimary,
  surfaceVariant = OledSurfaceVariant,
  onSurfaceVariant = OledTextSecondary,
  outline = OledCardBorder,
  error = BrandRose,
  onError = Color.White
)

val DarkColorScheme = darkColorScheme(
  primary = BrandPrimary,
  onPrimary = Color(0xFF0F172A),
  primaryContainer = Color(0xFF0284C7),
  onPrimaryContainer = Color(0xFFE0F2FE),
  secondary = BrandSecondary,
  onSecondary = Color(0xFF0F172A),
  secondaryContainer = Color(0xFF4338CA),
  onSecondaryContainer = Color(0xFFEEF2FF),
  tertiary = BrandAccentGold,
  onTertiary = Color(0xFF0F172A),
  background = DarkBackground,
  onBackground = DarkTextPrimary,
  surface = DarkSurface,
  onSurface = DarkTextPrimary,
  surfaceVariant = DarkSurfaceVariant,
  onSurfaceVariant = DarkTextSecondary,
  outline = DarkBorder,
  error = BrandRose,
  onError = Color.White
)

val LightColorScheme = lightColorScheme(
  primary = BrandPrimaryDark,
  onPrimary = Color.White,
  primaryContainer = Color(0xFFBAE6FD),
  onPrimaryContainer = Color(0xFF0369A1),
  secondary = Color(0xFF4F46E5),
  onSecondary = Color.White,
  secondaryContainer = Color(0xFFE0E7FF),
  onSecondaryContainer = Color(0xFF3730A3),
  tertiary = Color(0xFFD97706),
  onTertiary = Color.White,
  background = LightBackground,
  onBackground = LightTextPrimary,
  surface = LightSurface,
  onSurface = LightTextPrimary,
  surfaceVariant = LightSurfaceVariant,
  onSurfaceVariant = LightTextSecondary,
  outline = LightBorder,
  error = BrandRose,
  onError = Color.White
)

@Composable
fun JEEPrepTheme(
  themeMode: AppThemeMode = AppThemeMode.SYSTEM,
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  val systemInDark = isSystemInDarkTheme()
  val effectiveDark = when (themeMode) {
    AppThemeMode.SYSTEM -> systemInDark
    AppThemeMode.OLED_BLACK -> true
    AppThemeMode.DARK -> true
    AppThemeMode.LIGHT -> false
  }

  val colorScheme = when {
    themeMode == AppThemeMode.OLED_BLACK -> OledDarkColorScheme
    dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
      val context = LocalContext.current
      if (effectiveDark) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
    }
    themeMode == AppThemeMode.DARK -> DarkColorScheme
    themeMode == AppThemeMode.LIGHT -> LightColorScheme
    systemInDark -> DarkColorScheme
    else -> LightColorScheme
  }

  CompositionLocalProvider(LocalAppThemeMode provides themeMode) {
    MaterialTheme(
      colorScheme = colorScheme,
      typography = Typography,
      content = content
    )
  }
}

@Composable
fun MyApplicationTheme(
  themeMode: AppThemeMode = AppThemeMode.SYSTEM,
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  JEEPrepTheme(themeMode = themeMode, dynamicColor = dynamicColor, content = content)
}
