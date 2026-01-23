package com.myradev.lunarcode.content.bricks

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.myradev.lunarcode.R
import com.myradev.lunarcode.content.actions.LuaBrickAction

class LuaBrick(val brickName: String, val category: String) : Brick {
    override fun getAction(): LuaBrickAction = LuaBrickAction(brickName)

    override fun getView(context: Context, inflater: LayoutInflater, parent: View?): View {
        val view = inflater.inflate(R.layout.brick_move_n_steps, null)
        val textView = view.findViewById<TextView>(R.id.brick_move_n_steps_text_view)
        textView.text = brickName
        return view
    }

    override fun clone(): Brick = LuaBrick(brickName, category)
}
