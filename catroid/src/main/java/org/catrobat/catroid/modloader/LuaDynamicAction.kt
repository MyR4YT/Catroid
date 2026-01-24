package org.catrobat.catroid.modloader

import org.catrobat.catroid.content.actions.ScriptSequenceAction
import org.catrobat.catroid.content.Sprite
import com.badlogicgames.gdx.scenes.scene2d.Action

class LuaDynamicAction(
    private val functionName: String,
    private val args: Map<String, String>
) : Action() {

    override fun act(delta: Float): Boolean {
        // Chama o executor Lua com os argumentos do bloco
        LuaExecutor.executeFunction(functionName, args)
        return true // Retorna true para indicar que a ação terminou
    }
}
