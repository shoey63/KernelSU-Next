package com.rifsxd.ksunext.ui.util

import android.os.SystemClock

object AppLockManager {
    var activeActivities = 0
    var lastBackgroundTime = 0L
    var isUnlocked = false

    fun onActivityStart() {
        activeActivities++
    }

    fun onActivityStop() {
        activeActivities--
        if (activeActivities == 0) {
            lastBackgroundTime = SystemClock.elapsedRealtime()
            isUnlocked = false
        }
    }

    fun shouldPrompt(timeout: Long): Boolean {
        if (!isUnlocked) {
            if (lastBackgroundTime == 0L) return true
            val timeInBackground = SystemClock.elapsedRealtime() - lastBackgroundTime
            if (timeInBackground < timeout) {
                isUnlocked = true
                return false
            }
            return true
        }
        return false
    }

    fun unlock() {
        isUnlocked = true
        lastBackgroundTime = 0L
    }
}
