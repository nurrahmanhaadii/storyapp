package id.hadi.dicoding.storyapp.ui.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import id.hadi.dicoding.storyapp.R

/**
 * Created by nurrahmanhaadii on 18,March,2024
 */
class PasswordEditText : BaseCustomEditText {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    private val minLength = 8 // Minimum password length

    override fun onTextChanged(text: CharSequence?, start: Int, count: Int, before: Int) {
        super.onTextChanged(text, start, count, before)
        if (text == null) return
        val error = if (text.length < minLength) {
            "Password must be at least $minLength characters long"
        } else {
            null
        }
        setError(error)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        hint = "Password"
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }

    fun isValid(): Boolean = error == null
}