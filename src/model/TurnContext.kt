package com.turbomates.model

import com.turbomates.model.bot.Activity
import com.turbomates.model.bot.Member

class TurnContext (val initialActivity: Activity) {
    private var activities = mutableSetOf<Activity>()

    fun message(text: String, from: Member) {
        val activity = Activity.message(text, initialActivity.conversation, from)
        activities.add(activity)
        //@todo raise event
    }

    //@todo remove
    fun last(): Activity? {
        if (activities.isNotEmpty()) {
            return activities.last();
        }
        return null
    }
}