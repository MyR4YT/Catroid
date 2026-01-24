package org.catrobat.catroid.modloader

import org.catrobat.catroid.content.bricks.Brick
import org.catrobat.catroid.content.bricks.BrickBaseType
import org.catrobat.catroid.content.Sprite
import org.catrobat.catroid.content.actions.ScriptSequenceAction
import android.content.Context
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.EditText
import android.text.TextWatcher
import android.text.Editable
import org.catrobat.catroid.R

class LuaDynamicBrick : BrickBaseType {
    private var luaFunctionName: String = ""
    private var brickName: String = ""
    private var inputValues: MutableMap<String, String> = mutableMapOf()

    constructor() : super()
    constructor(luaFunctionName: String, brickName: String) : super() {
        this.luaFunctionName = luaFunctionName
        this.brickName = brickName
    }

    override fun getViewResource(): Int = R.layout.brick_lua_dynamic

    override fun getView(context: Context): View {
        val view = super.getView(context)
        view.findViewById<TextView>(R.id.brick_lua_title).text = brickName
        val container = view.findViewById<LinearLayout>(R.id.brick_lua_inputs_container)
        container.removeAllViews()

        LuaBrickRegistry.getByName(brickName)?.parameters?.forEach { param ->
            val et = EditText(context).apply {
                hint = param
                setText(inputValues[param] ?: "0")
                addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) { inputValues[param] = s.toString() }
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                })
            }
            container.addView(et)
        }
        return view
    }

    override fun addActionToSequence(sprite: Sprite, sequence: ScriptSequenceAction) {
        // ADICIONA A LOGICA REAL DE EXECUCAO
        sequence.addAction(LuaDynamicAction(luaFunctionName, inputValues))
    }
}
