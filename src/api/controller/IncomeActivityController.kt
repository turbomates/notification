package com.turbomates.api.controller

import com.google.inject.*
import com.turbomates.corebot.Bot
import com.turbomates.corebot.incomeactivity.Handler
import com.turbomates.corebot.incomeactivity.IncomeActivity

class IncomeActivityController @Inject constructor(
    private val bot: Bot
) {

    suspend fun accept(activity: IncomeActivity) {
        Handler.handle(activity, bot)
    }
}