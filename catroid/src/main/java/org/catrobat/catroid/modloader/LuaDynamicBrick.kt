package org.catrobat.catroid.modloader

import org.catrobat.catroid.content.bricks.Brick
import org.catrobat.catroid.content.bricks.BrickBaseType
import org.catrobat.catroid.content.Sprite
import org.catrobat.catroid.content.actions.ScriptSequenceAction
import android.content.Context
import android.view.View
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.EditText
import android.text.TextWatcher
import android.text.Editable
import org.catrobat.catroid.R
import org.catrobat.catroid.formulaeditor.Formula

class LuaDynamicBrick : BrickBaseType {

    private var luaFunctionName: String = ""
    private var brickName: String = ""
    // Armazena os valores (Formulas) dos inputs. Chave = nome do parametro
    private var inputValues: MutableMap<String, String> = mutableMapOf()

    constructor() : super()

    constructor(luaFunctionName: String, brickName: String) : super() {
        this.luaFunctionName = luaFunctionName
        this.brickName = brickName
    }

    override fun getViewResource(): Int {
        return R.layout.brick_lua_dynamic
    }

    override fun getView(context: Context): View {
        val view = super.getView(context)
        
        val titleView = view.findViewById<TextView>(R.id.brick_lua_title)
        titleView.text = brickName

        val container = view.findViewById<LinearLayout>(R.id.brick_lua_inputs_container)
        container.removeAllViews()

        // Buscar definição para saber os parametros
        val def = LuaBrickRegistry.getByName(brickName)
        
        def?.parameters?.forEach { paramName ->
            // Criar layout simples para input: Label + Edit
            val inputLayout = LinearLayout(context)
            inputLayout.orientation = LinearLayout.HORIZONTAL
            
            val label = TextView(context)
            label.text = paramName + ": "
            inputLayout.addView(label)

            val input = EditText(context)
            input.width = 200 // largura fixa por enquanto
            input.setText(inputValues[paramName] ?: "0")
            
            input.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    inputValues[paramName] = s.toString()
                }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })

            inputLayout.addView(input)
            container.addView(inputLayout)
        }

        return view
    }

    override fun copyBrick(): Brick {
        val copy = LuaDynamicBrick(luaFunctionName, brickName)
        copy.inputValues.putAll(this.inputValues)
        return copy
    }

    override fun addActionToSequence(sprite: Sprite, sequence: ScriptSequenceAction) {
        // Passa os valores coletados para o executor
        LuaExecutor.executeFunction(luaFunctionName, inputValues)
    }
}
