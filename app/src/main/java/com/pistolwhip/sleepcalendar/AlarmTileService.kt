package com.pistolwhip.sleepcalendar

import android.content.Intent
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService

class AlarmTileService : TileService() {
    override fun onStartListening() { super.onStartListening(); updateTile() }
    override fun onClick() {
        super.onClick()
        val prefs = getSharedPreferences(MainActivity.PREFS, MODE_PRIVATE)
        val enabled = !prefs.getBoolean(MainActivity.ENABLED, false)
        prefs.edit().putBoolean(MainActivity.ENABLED, enabled).apply()
        updateTile()
        startActivityAndCollapse(Intent(this, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
    }
    private fun updateTile() {
        val enabled = getSharedPreferences(MainActivity.PREFS, MODE_PRIVATE).getBoolean(MainActivity.ENABLED, false)
        qsTile?.apply {
            state = if (enabled) Tile.STATE_ACTIVE else Tile.STATE_INACTIVE
            label = "Sleep Calendar Alarm"
            subtitle = if (enabled) "Enabled" else "Disabled"
            updateTile()
        }
    }
}
