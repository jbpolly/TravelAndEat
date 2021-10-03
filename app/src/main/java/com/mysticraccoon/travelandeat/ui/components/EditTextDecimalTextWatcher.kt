package com.mysticraccoon.travelandeat.ui.components

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.mysticraccoon.travelandeat.core.utils.formatWithSeparators
import com.mysticraccoon.travelandeat.core.utils.toDoubleOrNullFromUS
import kotlin.math.pow

class EditTextDecimalTextWatcher (private val editText: EditText):
    TextWatcher {
    private var selfChange = false
    private val floatDigits : Int = 2

    override fun afterTextChanged(s: Editable?) {
        if (selfChange) return
        selfChange = true
        format(s)
        selfChange = false
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

    private fun format(p0: Editable?) {
        if (p0.isNullOrEmpty()) return
        val strFormat = p0.toString().replace("""[,.\\s]""".toRegex(), "")
        var sValue = strFormat.toDoubleOrNullFromUS()
        if(sValue != null && sValue!= Double.NEGATIVE_INFINITY && sValue!= Double.POSITIVE_INFINITY) {
            sValue /= (10.0.pow(floatDigits))
            val strText = sValue.formatWithSeparators()
            editText.setText(strText)
        }
        else {
            val strText = 0.00.formatWithSeparators()
            editText.setText(strText)
        }
        editText.setSelection(editText.text.length)
    }

}