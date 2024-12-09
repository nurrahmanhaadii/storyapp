package id.hadi.dicoding.storyapp.ui.custom

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.res.ResourcesCompat
import id.hadi.dicoding.storyapp.R

/**
 * Created by nurrahmanhaadii on 18,March,2024
 */
open class BaseCustomEditText  : AppCompatEditText{

    private var clearDrawable: Drawable? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        clearDrawable = compoundDrawablesRelative[2]
        if (clearDrawable == null) {
            clearDrawable = ResourcesCompat.getDrawable(resources, R.drawable.ic_close, context.theme)
        }
        clearDrawable?.setBounds(0, 0, clearDrawable!!.intrinsicWidth, clearDrawable!!.intrinsicHeight)
        setClearIconVisible(false)
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {
                setClearIconVisible(s.isNotEmpty())
            }
        })
    }

    private fun setClearIconVisible(visible: Boolean) {
        setCompoundDrawablesRelative(
            compoundDrawablesRelative[0],
            compoundDrawablesRelative[1],
            if (visible) clearDrawable else null,
            compoundDrawablesRelative[3]
        )
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_UP && clearDrawable != null) {
            val bounds = Rect()
            getGlobalVisibleRect(bounds)
            if (event.rawX >= bounds.right - clearDrawable!!.bounds.width()) {
                setText("")
                return true
            }
        }
        return super.onTouchEvent(event)
    }

}