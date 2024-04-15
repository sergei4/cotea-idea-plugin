package pro.mobicode.mobile.ide.util.create_cotea

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.DataKey
import com.intellij.openapi.actionSystem.LangDataKeys
import com.intellij.psi.PsiDirectory
import com.intellij.psi.impl.file.PsiDirectoryFactory
import pro.mobicode.mobile.ide.util.dialogs.CoTeaCreateClassesDialog
import pro.mobicode.mobile.ide.util.settings.CoTeaConfig
import pro.mobicode.mobile.ide.util.settings.CoTeaSettings

class CreateCoTeaStructureOnlyAction : AnAction() {

    override fun actionPerformed(action: AnActionEvent) {
        val project = action.project ?: return
        val rootDir = action.targetDir ?: return

        CoTeaCreateClassesDialog(project).run {
            if (showAndGet()) {
                CreateCoTeaModuleFactory(project).createCoTeaStructure(
                    targetDir = rootDir,
                    classPrefix = coTeaClassPrefix
                )
                CoTeaSettings.getInstance().config = CoTeaConfig("com.apple.cotea")
            }
        }
    }

    override fun update(action: AnActionEvent) {
        val project = action.project ?: return
        val view = action.getData(LangDataKeys.IDE_VIEW) ?: return

        val psiDirs = view.directories
        if (psiDirs.size != 1) {
            action.presentation.isEnabled = false
            return
        }
        val directory = psiDirs.first()
        val factory = PsiDirectoryFactory.getInstance(project)
        action.presentation.isEnabled = factory.isPackage(directory)
    }

    private val AnActionEvent.targetDir: PsiDirectory?
        get() = getData(DataKey.create("psi.pasteTargetElement"))

}