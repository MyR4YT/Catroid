package com.myradev.lunarcode.content.actions

import com.myradev.lunarcode.content.Sprite
import com.myradev.lunarcode.content.Script
import com.myradev.lunarcode.modloader.ModManager

class LuaBrickAction(val brickName: String) : Action {
    override fun execute(sprite: Sprite?, script: Script?) {
        ModManager.executeBrick(brickName, sprite)
    }
}
