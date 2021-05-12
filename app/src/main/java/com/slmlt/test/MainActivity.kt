package com.slmlt.test

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.view.WindowInsets
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.slmlt.test.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { _, insets ->
            binding.textLatestBottomImeInset.text =
                    "Info: Latest bottom system window IME insets ${insets.bottomImeInsets}"
            insets
        }
        binding.textSymbols.setOnClickListener {
            copyTextToClipboard("copied text")
            binding.textInput.showKeyboard(InputType.TYPE_CLASS_TEXT)
        }
        binding.textDigits.setOnClickListener {
            copyTextToClipboard("123")
            binding.textInput.showKeyboard(InputType.TYPE_CLASS_NUMBER)
        }
    }

    private fun copyTextToClipboard(text: String) {
        val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboardManager.setPrimaryClip(ClipData.newPlainText("label", text))
    }

    private fun EditText.showKeyboard(inputType: Int) {
        this.inputType = inputType
        requestFocus()
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }
}

private val WindowInsetsCompat.bottomImeInsets: Int
    get() = toWindowInsets()?.getInsets(WindowInsets.Type.ime())?.bottom ?: 0
