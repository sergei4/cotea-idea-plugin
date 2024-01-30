package pro.mobicode.mobile.ide.util.utils

import com.intellij.openapi.util.text.StringUtil
import com.intellij.util.PlatformUtils
import java.util.regex.Pattern

object PluginUtils {

    private val VALID_PACKAGE = Pattern.compile("^([a-z]++([_]?[a-z0-9]+)*)++$")

    @Suppress("UnstableApiUsage")
    fun isAndroidStudio() = StringUtil.equals(PlatformUtils.getPlatformPrefix(), "AndroidStudio")

    fun isValidPackageName(name: String): Boolean = VALID_PACKAGE.matcher(name).matches()

}