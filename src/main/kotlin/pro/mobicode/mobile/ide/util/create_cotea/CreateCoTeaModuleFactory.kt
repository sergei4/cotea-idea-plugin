package pro.mobicode.mobile.ide.util.create_cotea

import android.databinding.tool.ext.toCamelCase
import com.intellij.openapi.application.WriteAction
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiFileFactory
import org.jetbrains.kotlin.idea.KotlinLanguage
import org.jetbrains.kotlin.idea.gradleTooling.capitalize
import pro.mobicode.mobile.ide.util.settings.CoTeaSettings

class CreateCoTeaModuleFactory(private val project: Project) {

    private val psiFactory = PsiFileFactory.getInstance(project)

    fun createCoTeaStructure(targetDir: PsiDirectory, classPrefix: String) {
        WriteAction.runAndWait<Throwable> {
            doCreateCoTeaStructure(targetDir, classPrefix.capitalize())
        }
    }

    fun createScreen(targetDir: PsiDirectory, screenName: String, classPrefix: String = "") {
        WriteAction.runAndWait<Throwable> {
            val screenNameN = screenName.replace(" ", "_")
            val classPrefixN: String = classPrefix.takeIf { it.isNotEmpty() } ?: classPrefixByScreenName(screenNameN)

            val screenDir = targetDir.createSubdirectory(screenNameN.lowercase())
            val teaDir = screenDir.createSubdirectory("tea")
            val coTeaConfig = doCreateCoTeaStructure(teaDir, classPrefixN)

            createViewModelClass(screenDir, classPrefixN, coTeaConfig)
        }
    }

    private fun doCreateCoTeaStructure(targetDir: PsiDirectory, classPrefix: String): CoTeaClassConfig {
        val config = createCoTeaConfig(targetDir, classPrefix)
        createMessageInterface(config)
        createStateClass(config)
        createSideEffectClass(config)
        createCommandInterface(config)
        createCommandHandlerClass(config)
        createStateUpdaterClass(config)
        createFactoryClass(config)
        createAnalyticClass(config)
        return config
    }

    private fun getPackageName(directory: PsiDirectory): String {
        val path: String = directory.virtualFile.path
        val packageName = when {
            path.contains("java/") -> path.substringAfter("java/").replace("/", ".")
            path.contains("kotlin/") -> path.substringAfter("kotlin/").replace("/", ".")
            else -> error("Path doesn't represent java or kotlin package: $path")
        }
        return packageName
    }

    private fun createCoTeaConfig(directory: PsiDirectory, classPrefix: String): CoTeaClassConfig {
        val config = service<CoTeaSettings>().config
        val packageName = getPackageName(directory)

        val analyticClassName = "${classPrefix}Analytic"
        val messageClassName = "${classPrefix}Message"
        val stateClassName = "${classPrefix}State"
        val sideEffectClassName = "${classPrefix}SideEffect"
        val commandClassName = "${classPrefix}Command"
        val commandHandlerClassName = "${classPrefix}CommandHandler"
        val stateUpdaterClassName = "${classPrefix}StateUpdater"
        val factoryClassName = "${classPrefix}StoreFactory"

        return CoTeaClassConfig(
            directory,
            config.coTeaLibPackage,
            packageName,
            messageClassName,
            stateClassName,
            sideEffectClassName,
            commandClassName,
            commandHandlerClassName,
            stateUpdaterClassName,
            factoryClassName,
            analyticClassName
        )
    }

    private fun classPrefixByScreenName(screenName: String): String {
        return screenName.toCamelCase()
    }

    private fun createCommandInterface(config: CoTeaClassConfig) {
        val templateKt = CreateCoTeaModuleTemplates.commandKt
            .replace(LIB_BASE_PACKAGE, config.libBasePackageName)
            .replace(PACKAGE, config.packageName)
            .replace(COMMAND_CLASS, config.commandClassName)
        val file = psiFactory.createKotlinFile(config.commandClassName, templateKt)
        config.moduleDir.add(file)
    }

    private fun createMessageInterface(config: CoTeaClassConfig) {
        val templateKt = CreateCoTeaModuleTemplates.messageKt
            .replace(LIB_BASE_PACKAGE, config.libBasePackageName)
            .replace(PACKAGE, config.packageName)
            .replace(MESSAGE_CLASS, config.messageClassName)
        val file = psiFactory.createKotlinFile(config.messageClassName, templateKt)
        config.moduleDir.add(file)
    }

    private fun createStateClass(config: CoTeaClassConfig) {
        val templateKt = CreateCoTeaModuleTemplates.stateKt
            .replace(LIB_BASE_PACKAGE, config.libBasePackageName)
            .replace(PACKAGE, config.packageName)
            .replace(STATE_CLASS, config.stateClassName)
        val file = psiFactory.createKotlinFile(config.stateClassName, templateKt)
        config.moduleDir.add(file)
    }

    private fun createSideEffectClass(config: CoTeaClassConfig) {
        val templateKt = CreateCoTeaModuleTemplates.sideEffectKt
            .replace(LIB_BASE_PACKAGE, config.libBasePackageName)
            .replace(PACKAGE, config.packageName)
            .replace(SIDE_EFFECT_CLASS, config.sideEffectClassName)
        val file = psiFactory.createKotlinFile(config.sideEffectClassName, templateKt)
        config.moduleDir.add(file)
    }

    private fun createCommandHandlerClass(config: CoTeaClassConfig) {
        val templateKt = CreateCoTeaModuleTemplates.commandHandlerKt
            .replace(LIB_BASE_PACKAGE, config.libBasePackageName)
            .replace(PACKAGE, config.packageName)
            .replace(COMMAND_HANDLER_CLASS, config.commandHandlerClassName)
            .replace(MESSAGE_CLASS, config.messageClassName)
            .replace(COMMAND_CLASS, config.commandClassName)
        val file = psiFactory.createKotlinFile(config.commandHandlerClassName, templateKt)
        config.moduleDir.add(file)
    }

    private fun createStateUpdaterClass(config: CoTeaClassConfig) {
        val templateKt = CreateCoTeaModuleTemplates.stateUpdaterKt
            .replace(LIB_BASE_PACKAGE, config.libBasePackageName)
            .replace(PACKAGE, config.packageName)
            .replace(STATE_UPDATER_CLASS, config.stateUpdaterClassName)
            .replace(MESSAGE_CLASS, config.messageClassName)
            .replace(STATE_CLASS, config.stateClassName)
            .replace(SIDE_EFFECT_CLASS, config.sideEffectClassName)
            .replace(COMMAND_CLASS, config.commandClassName)
        val file = psiFactory.createKotlinFile(config.stateUpdaterClassName, templateKt)
        config.moduleDir.add(file)
    }

    private fun createFactoryClass(config: CoTeaClassConfig) {
        val templateKt = CreateCoTeaModuleTemplates.factoryKt
            .replace(LIB_BASE_PACKAGE, config.libBasePackageName)
            .replace(PACKAGE, config.packageName)
            .replace(FACTORY_CLASS, config.factoryClassName)
            .replace(MESSAGE_CLASS, config.messageClassName)
            .replace(STATE_CLASS, config.stateClassName)
            .replace(SIDE_EFFECT_CLASS, config.sideEffectClassName)
            .replace(COMMAND_CLASS, config.commandClassName)
            .replace(STATE_UPDATER_CLASS, config.stateUpdaterClassName)
            .replace(COMMAND_HANDLER_CLASS, config.commandHandlerClassName)
            .replace(ANALYTIC_CLASS, config.analyticClassName)
        val file = psiFactory.createKotlinFile(config.factoryClassName, templateKt)
        config.moduleDir.add(file)
    }

    private fun createViewModelClass(screenDir: PsiDirectory, classPrefix: String, config: CoTeaClassConfig) {
        val viewModelClassName = "${classPrefix}ViewModel"
        val templateKt = CreateCoTeaModuleTemplates.viewModelKt
            .replace(LIB_BASE_PACKAGE, config.libBasePackageName)
            .replace(PACKAGE, getPackageName(screenDir))
            .replace(PACKAGE_TEA, config.packageName)
            .replace(VIEW_MODEL_CLASS, viewModelClassName)
            .replace(MESSAGE_CLASS, config.messageClassName)
            .replace(STATE_CLASS, config.stateClassName)
            .replace(SIDE_EFFECT_CLASS, config.sideEffectClassName)
            .replace(COMMAND_CLASS, config.commandClassName)
            .replace(STATE_UPDATER_CLASS, config.stateUpdaterClassName)
            .replace(COMMAND_HANDLER_CLASS, config.commandHandlerClassName)
            .replace(FACTORY_CLASS, config.factoryClassName)
            .replace(ANALYTIC_CLASS, config.analyticClassName)
        val file = psiFactory.createKotlinFile(viewModelClassName, templateKt)
        screenDir.add(file)
    }

    private fun createAnalyticClass(config: CoTeaClassConfig) {
        val templateKt = CreateCoTeaModuleTemplates.analyticKt
            .replace(LIB_BASE_PACKAGE, config.libBasePackageName)
            .replace(PACKAGE, config.packageName)
            .replace(ANALYTIC_CLASS, config.analyticClassName)
            .replace(MESSAGE_CLASS, config.messageClassName)
            .replace(STATE_CLASS, config.stateClassName)
            .replace(SIDE_EFFECT_CLASS, config.sideEffectClassName)
            .replace(COMMAND_CLASS, config.commandClassName)
        val file = psiFactory.createKotlinFile(config.analyticClassName, templateKt)
        config.moduleDir.add(file)
    }

    private fun PsiFileFactory.createKotlinFile(className: String, source: String) =
        this.createFileFromText("${className}.kt", KotlinLanguage.INSTANCE, source)

    data class CoTeaClassConfig(
        val moduleDir: PsiDirectory,
        val libBasePackageName: String,
        val packageName: String,
        val messageClassName: String,
        val stateClassName: String,
        val sideEffectClassName: String,
        val commandClassName: String,
        val commandHandlerClassName: String,
        val stateUpdaterClassName: String,
        val factoryClassName: String,
        val analyticClassName: String
    )

    companion object {
        const val LIB_BASE_PACKAGE = "%cotea_base_package%"
        const val PACKAGE = "%package%"
        const val PACKAGE_TEA = "%tea_package%"
        const val MESSAGE_CLASS = "%messageClass%"
        const val STATE_CLASS = "%stateClass%"
        const val SIDE_EFFECT_CLASS = "%sideEffectClass%"
        const val COMMAND_CLASS = "%commandClass%"
        const val STATE_UPDATER_CLASS = "%stateUpdaterClass%"
        const val COMMAND_HANDLER_CLASS = "%commandHandlerClass%"
        const val FACTORY_CLASS = "%factoryClass%"
        const val VIEW_MODEL_CLASS = "%viewModelClass%"
        const val ANALYTIC_CLASS = "%analyticClass%"
    }
}