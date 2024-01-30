package pro.mobicode.mobile.ide.util.create_cotea

object CreateCoTeaModuleTemplates {

    private fun load(path: String): String = checkNotNull(CreateCoTeaModuleTemplates::class.java.getResource(path)).readText()

    val commandKt = load("/templates/cotea/Command.kt.txt")
    val messageKt = load("/templates/cotea/Message.kt.txt")
    val stateKt = load("/templates/cotea/State.kt.txt")
    val sideEffectKt = load("/templates/cotea/SideEffect.kt.txt")
    val commandHandlerKt = load("/templates/cotea/CommandHandler.kt.txt")
    val stateUpdaterKt = load("/templates/cotea/StateUpdater.kt.txt")
    val factoryKt = load("/templates/cotea/Factory.kt.txt")
    val viewModelKt = load("/templates/cotea/ViewModel.kt.txt")
    val analyticKt = load("/templates/cotea/Analytic.kt.txt")

}