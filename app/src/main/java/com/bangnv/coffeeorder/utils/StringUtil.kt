package com.bangnv.coffeeorder.utils

import android.util.Patterns
import java.text.Normalizer
import java.util.Locale
import java.util.regex.Pattern

object StringUtil {

    @JvmStatic
    fun isValidEmail(target: CharSequence?): Boolean {
        return if (target == null) false else Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    @JvmStatic
    fun isEmpty(input: String?): Boolean {
        return input.isNullOrEmpty() || "" == input.trim { it <= ' ' }
    }

    @JvmStatic
    fun getDoubleNumber(number: Int): String {
        return if (number < 10) {
            "0$number"
        } else "" + number
    }

    // Replace Vietnamese to English (đ -> d,..), lowercase
    @JvmStatic
    fun normalizeEnglishText(input: String?): String {
        if (input.isNullOrEmpty()) return ""
        val normalizedStr = Normalizer.normalize(input, Normalizer.Form.NFD)
        val pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+")
        val withoutDiacritics = pattern.matcher(normalizedStr).replaceAll("")
        return withoutDiacritics.toLowerCase(Locale.getDefault()).replace("đ", "d")
    }
}