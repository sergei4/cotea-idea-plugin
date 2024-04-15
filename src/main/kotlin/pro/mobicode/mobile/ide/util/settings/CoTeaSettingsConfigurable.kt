package pro.mobicode.mobile.ide.util.settings

import com.intellij.openapi.options.Configurable
import com.intellij.ui.dsl.builder.Cell
import com.intellij.ui.dsl.builder.panel
import com.intellij.ui.dsl.builder.text
import javax.swing.JComponent
import javax.swing.JTextField

class CoTeaSettingsConfigurable : Configurable {

    private val settings = CoTeaSettings.getInstance()

    private lateinit var coTeaLibBasePackageTextField : Cell<JTextField>

    override fun createComponent(): JComponent {
        return panel {
            row("&CoTea library package:") {
                coTeaLibBasePackageTextField = textField().text(settings.config.coTeaLibPackage)
            }
        }
    }

    override fun isModified(): Boolean {
        return settings.config.coTeaLibPackage != coTeaLibBasePackageTextField.component.text
    }

    override fun apply() {
        settings.config.coTeaLibPackage = coTeaLibBasePackageTextField.component.text
    }

    override fun getDisplayName(): String {
        return "CoTea Settings"
    }
}