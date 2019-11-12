package com.turbomates.model.bot

import com.google.inject.Inject
import com.google.inject.name.Named
import com.turbomates.model.TurnContext

class EchoBot @Inject constructor(@Named("botMicrosoftAppId") private val botID: String) {

    fun onMembersAdded(turnContext: TurnContext) {
        turnContext.initialActivity.membersAdded!!.forEach {
            if (it.id != turnContext.initialActivity.recipient?.id) {
                turnContext.message("Пойдем-ка покурим-ка!", Member(botID, ""))
            }
        }
    }

    fun onMessage(turnContext: TurnContext) {
        turnContext.message("Ага, ${turnContext.initialActivity.text}, не до тебя щас!", Member(botID, ""))
    }
}