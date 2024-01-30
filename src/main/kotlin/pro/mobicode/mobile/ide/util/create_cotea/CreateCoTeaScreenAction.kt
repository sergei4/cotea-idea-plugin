package pro.mobicode.mobile.ide.util.create_cotea

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.DataKey
import com.intellij.openapi.actionSystem.LangDataKeys
import com.intellij.psi.PsiDirectory
import com.intellij.psi.impl.file.PsiDirectoryFactory
import pro.mobicode.mobile.ide.util.dialogs.CoTeaCreateScreenDialog

class CreateCoTeaScreenAction : AnAction() {

    override fun actionPerformed(action: AnActionEvent) {
        val project = action.project ?: return
        val rootDir = action.targetDir ?: return

        CoTeaCreateScreenDialog(project).run {
            if (showAndGet()) {
                CreateCoTeaModuleFactory(project).createScreen(
                    targetDir = rootDir,
                    screenName = coTeaScreenName
                )
            }
        }

    }

    override fun update(action: AnActionEvent) {
        super.update(action)
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
        get() = dataContext.getData(DataKey.create("psi.pasteTargetElement"))

}