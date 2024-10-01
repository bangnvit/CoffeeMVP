package com.bangnv.coffeeorder.constant

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.ClipData
import android.content.ClipboardManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import com.bangnv.coffeeorder.constant.Constant
import com.bangnv.coffeeorder.R
//import com.bangnv.coffeeorder.activity.FoodDetailActivity
//import com.bangnv.coffeeorder.activity.admin.AdminMainActivity
//import com.bangnv.coffeeorder.activity.MainActivity
//import com.bangnv.coffeeorder.activity.admin.AdminOrderDetailActivity
//import com.bangnv.coffeeorder.database.AppApi
//import com.bangnv.coffeeorder.listener.IGetDateListener
//import com.bangnv.coffeeorder.model.Food
//import com.bangnv.coffeeorder.model.baseresponse.RetrofitClients
//import com.bangnv.coffeeorder.prefs.DataStoreManager.Companion.user
import com.bangnv.coffeeorder.utils.StringUtil.getDoubleNumber
import com.bangnv.coffeeorder.utils.StringUtil.isEmpty
import com.google.android.material.tabs.TabLayout
import java.text.DecimalFormat
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
import java.util.Calendar
import kotlin.math.roundToInt

object GlobalFunction {

    fun openActivity(context: Context, clz: Class<*>?) {
        val intent = Intent(context, clz)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    @JvmStatic
    fun openActivity(context: Context, clz: Class<*>?, bundle: Bundle?) {
        val intent = Intent(context, clz)
        intent.putExtras(bundle!!)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

//    @JvmStatic
//    fun gotoMainActivity(context: Context) {
//        if (user!!.type == Constant.TYPE_USER_ADMIN) {
//            openActivity(context, AdminMainActivity::class.java)
//        } else  if (user!!.type == Constant.TYPE_USER_DRIVER){
////            startActivity(context, DriverMainActivity::class.java)
//            Toast.makeText(context, "Global function gotoMainActivity: Cần tạo DriverMainActivity", Toast.LENGTH_SHORT ).show()
//        } else {
//            openActivity(context, MainActivity::class.java)
//        }
//    }
//
//    fun goToFoodDetail(context: Context, food: Food) {
//        val bundle = Bundle()
//        bundle.putSerializable(Constant.KEY_INTENT_FOOD_OBJECT, food)
//        openActivity(context, FoodDetailActivity::class.java, bundle)
//    }

//    @SuppressLint("SetTextI18n")
//    @BindingAdapter("formattedPrice")
//    @JvmStatic
//    fun setFormattedPrice(textView: TextView, price: Double) {
//        val decimalFormat = DecimalFormat("#,###")
//        textView.text = "${decimalFormat.format(price.roundToInt())}.000 VNĐ"
//    }

    @JvmStatic
    fun hideSoftKeyboard(activity: Activity) {
        try {
            val inputMethodManager =
                activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, 0)
        } catch (ex: NullPointerException) {
            ex.printStackTrace()
        }
    }

    @JvmStatic
    fun showSoftKeyboard(activity: Activity) {
        val inputMethodManager =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    fun showMessageError(activity: Activity?) {
        Toast.makeText(activity, Constant.GENERIC_ERROR, Toast.LENGTH_SHORT).show()
    }

    @JvmStatic
    fun onClickOpenGmail(context: Context) {
        val emailIntent = Intent(
            Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", Constant.GMAIL, null
            )
        )
        context.startActivity(Intent.createChooser(emailIntent, "Send Email"))
    }

    @JvmStatic
    fun onClickOpenSkype(context: Context) {
        try {
            val skypeUri = Uri.parse("skype:" + Constant.SKYPE_ID + "?chat")
            context.packageManager.getPackageInfo("com.skype.raider", 0)
            val skypeIntent = Intent(Intent.ACTION_VIEW, skypeUri)
            skypeIntent.component = ComponentName("com.skype.raider", "com.skype.raider.Main")
            context.startActivity(skypeIntent)
        } catch (e: Exception) {
            openSkypeWebview(context)
        }
    }

    private fun openSkypeWebview(context: Context) {
        try {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("skype:" + Constant.SKYPE_ID + "?chat")
                )
            )
        } catch (exception: Exception) {
            val skypePackageName = "com.skype.raider"
            try {
                context.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=$skypePackageName")
                    )
                )
            } catch (anfe: ActivityNotFoundException) {
                context.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=$skypePackageName")
                    )
                )
            }
        }
    }

    @JvmStatic
    fun onClickOpenFacebook(context: Context) {
        var intent: Intent
        try {
            var urlFacebook: String = Constant.PAGE_FACEBOOK
            val packageManager = context.packageManager
            val versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode
            if (versionCode >= 3002850) { //newer versions of fb app
                urlFacebook = "fb://facewebmodal/f?href=" + Constant.LINK_FACEBOOK
            }
            intent = Intent(Intent.ACTION_VIEW, Uri.parse(urlFacebook))
        } catch (e: Exception) {
            intent = Intent(Intent.ACTION_VIEW, Uri.parse(Constant.LINK_FACEBOOK))
        }
        context.startActivity(intent)
    }

    @JvmStatic
    fun onClickOpenYoutubeChannel(context: Context) {
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(Constant.LINK_YOUTUBE)))
    }

    @JvmStatic
    fun onClickOpenZalo(context: Context) {
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(Constant.ZALO_LINK)))
    }

    @JvmStatic
    fun onClickOpenDial(context: Context) {
        context.startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:${Constant.PHONE_NUMBER}")))
    }

    @JvmStatic
    fun callPhoneNumber(activity: Activity) {
        try {
            if (Build.VERSION.SDK_INT > 22) {
                if (ActivityCompat.checkSelfPermission(
                        activity,
                        Manifest.permission.CALL_PHONE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(
                        activity,
                        arrayOf(Manifest.permission.CALL_PHONE),
                        101
                    )
                    return
                }
                val callIntent = Intent(Intent.ACTION_CALL)
                callIntent.data = Uri.parse("tel:" + Constant.PHONE_NUMBER)
                activity.startActivity(callIntent)
            } else {
                val callIntent = Intent(Intent.ACTION_CALL)
                callIntent.data = Uri.parse("tel:" + Constant.PHONE_NUMBER)
                activity.startActivity(callIntent)
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    @JvmStatic
    fun showToastMessage(context: Context?, message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }


    fun setPasswordVisibility(
        isPasswordVisible: Boolean,
        editText: EditText,
        imageView: ImageView
    ) {
        val newVisibility = !isPasswordVisible
        if (newVisibility) { // Show password
            editText.transformationMethod = null
            imageView.setImageResource(R.drawable.ic_hide)
        } else { // Hide password
            editText.transformationMethod = PasswordTransformationMethod.getInstance()
            imageView.setImageResource(R.drawable.ic_show)
        }
        // Nove cursor to end of EditText
        editText.setSelection(editText.text.length)
    }

    fun EditText.setBackgroundOnEditTextFocusChange(layout: View) {
        this.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                layout.setBackgroundResource(R.drawable.bg_edittext_active)
            } else {
                layout.setBackgroundResource(R.drawable.bg_edittext_inactive)
            }
        }
    }

    fun EditText.addIconClearVisibilityOnTextChangedListener(clearButton: ImageView) {
        this.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                clearButton.visibility = if (s.isNullOrEmpty()) View.GONE else View.VISIBLE
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    fun EditText.addIconClearVisibilityOnFocusListener(clearButton: ImageView) {
        this.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                val editText = v as EditText
                val text = editText.text?.toString()
                if (!text.isNullOrEmpty()) {
                    clearButton.visibility = View.VISIBLE
                } else {
                    clearButton.visibility = View.GONE
                }
            } else {
                clearButton.visibility = View.GONE
            }
        }
    }

    fun EditText.setOnActionDoneListener(vararg actions: () -> Unit) {
        this.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                actions.forEach { it.invoke() }
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    fun EditText.setOnActionSearchListener(vararg actions: () -> Unit) {
        this.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                actions.forEach { it.invoke() }
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    fun setupLayoutEditTextWithIconClearListeners(
        layoutEditText: View,
        editText: EditText,
        imgClear: ImageView
    ) {
        // Change background when check focus
        editText.setBackgroundOnEditTextFocusChange(layoutEditText)
        // set visibility icon clear
        editText.addIconClearVisibilityOnTextChangedListener(imgClear)
//        editText.addIconClearVisibilityOnFocusListener(imgClear)
        // Set text ""
        imgClear.setOnClickListener {
            editText.setText("")
        }
    }

    fun setupLayoutEditTextWithIconClearNoClearTextListeners(
        layoutEditText: View,
        editText: EditText,
        imgClear: ImageView
    ) {
        // Change background when check focus
        editText.setBackgroundOnEditTextFocusChange(layoutEditText)
        // set visibility icon clear
        editText.addIconClearVisibilityOnTextChangedListener(imgClear)
    }

    fun setupLayoutPasswordListeners(
        layoutEditText: View,
        edtPassword: EditText,
        activity: Activity
    ) {
        // Change background when check focus
        edtPassword.setBackgroundOnEditTextFocusChange(layoutEditText)

        // Action Done: auto Check
        edtPassword.setOnActionDoneListener(
            { hideSoftKeyboard(activity) },
            { edtPassword.clearFocus() }
        )
    }

    fun TabLayout.addMyTabs(vararg texts: String, selectedTab: String) {
        texts.forEachIndexed { _, text ->
            val tab = this.newTab().setText(text)
            this.addTab(tab, text == selectedTab)
        }
    }

    fun View.setOnClickCopyTextToClipboard(textView: TextView, context: Context) {
        this.setOnClickListener {
            val textToCopy = textView.text.toString()
            val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("text", textToCopy)
            clipboardManager.setPrimaryClip(clipData)
            Toast.makeText(context, "Đã sao chép vào Clipboard", Toast.LENGTH_SHORT).show()
        }
    }

    fun formatNumberWithPeriods(number: Int): String {
        val formatter = java.text.DecimalFormat("#,###")
        return formatter.format(number.toLong())
    }

    fun customizeBottomSheetDialog(viewDialog: Dialog) {
        viewDialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        viewDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        viewDialog.window?.attributes?.windowAnimations = R.style.DiaLogAnimation
        viewDialog.window?.setGravity(Gravity.BOTTOM)
    }

//     Not Good: No fragments management
//    fun replaceFragment(activity: AppCompatActivity, fragment: Fragment) {
//        activity.supportFragmentManager.beginTransaction()
//            .replace(R.id.fragmentContainer, fragment)
//            .addToBackStack(null) // Add to backstack if needed
//            .commit()
//    }

    // Đang code chay thế này chứ không dùng lib navigation (navigation đơn giản hơn)
//    fun replaceFragment(activity: AppCompatActivity, fragment: Fragment) {
//        val fragmentTag = fragment::class.java.simpleName
//        val fragmentManager = activity.supportFragmentManager
//        val fragmentTransaction = fragmentManager.beginTransaction()
//
//        val existingFragment = fragmentManager.findFragmentByTag(fragmentTag)
//        if (existingFragment != null) {
//            fragmentTransaction.show(existingFragment)
//        } else {
//            fragmentTransaction.add(R.id.fragmentContainer, fragment, fragmentTag)
//        }
//        val fragments = fragmentManager.fragments
//        for (f in fragments) {
//            if (f != existingFragment) { fragmentTransaction.hide(f) }
//        }
//        fragmentTransaction.addToBackStack(null)
//        fragmentTransaction.commit()
//    }

//    fun connectToServerOnRender(){
//        val appApi: AppApi = RetrofitClients.getInstance().create(AppApi::class.java)
//        appApi.getSuccessServer().enqueue(object : Callback<Unit> {
//            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
//                // Không cần xử lý gì trong phần này
//            }
//
//            override fun onFailure(call: Call<Unit>, t: Throwable) {
//                // Không cần xử lý gì trong phần này
//            }
//        })
//    }

}