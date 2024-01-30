package pro.mobicode.mobile.ide.util.utils

import java.io.File

fun File.deleteIfExist() = if(exists()) deleteRecursively() else false