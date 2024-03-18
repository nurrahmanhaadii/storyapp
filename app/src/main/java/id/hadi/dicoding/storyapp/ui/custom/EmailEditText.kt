package id.hadi.dicoding.storyapp.ui.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View

/**
 * Created by nurrahmanhaadii on 18,March,2024
 */
class EmailEditText : BaseCustomEditText {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onTextChanged(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
        if (text == null) return
        error = if (!isEmailValid(text.toString())) {
            "Invalid email address"
        } else null
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        hint = "Email"
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }

    fun isEmailValid(text: String) = android.util.Patterns.EMAIL_ADDRESS.matcher(text).matches()
}