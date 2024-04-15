package pro.mobicode.mobile.ide.util.settings


import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.components.service
import com.intellij.util.xmlb.XmlSerializerUtil

@State(
    name = "pro.mobicode.mobile.ide.util.create_cotea.settings",
    storages = [Storage("cotea_plugin_settings.xml")]
)
class CoTeaSettings : PersistentStateComponent<CoTeaSettings> {

    var config = CoTeaConfig()

    override fun getState(): CoTeaSettings {
        return this
    }

    override fun loadState(state: CoTeaSettings) {
        XmlSerializerUtil.copyBean(state, this)
    }

    companion object {
        fun getInstance(): CoTeaSettings {
            return service<CoTeaSettings>()
        }
    }

}