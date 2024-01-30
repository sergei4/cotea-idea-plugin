package icons

import com.intellij.openapi.util.IconLoader

object MobicodeIcons {

    private fun load(path: String) = IconLoader.getIcon(path, javaClass)

    @JvmField
    val mobileModules = load("/icons/cotea_modules_group.svg")

    @JvmField
    val mobileModulesCoTeaScreen = load("/icons/cotea_modules_cotea_screen.svg")

    @JvmField
    val mobileModulesCoTeaStructureOnly = load("/icons/cotea_modules_cotea_structure_only.svg")


}