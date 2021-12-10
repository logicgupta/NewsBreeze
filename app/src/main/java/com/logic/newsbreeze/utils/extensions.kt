package com.logic.newsbreeze

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


fun Context.showToast(text: CharSequence) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun Context.showToast(@StringRes resId: Int) {
    Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()
}

fun Context.showLongToast(text: CharSequence) {
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}

fun <T> Activity.launchActivity(it: Class<T>, extras: Bundle = Bundle()) {
    val intent = Intent(this, it)
    intent.putExtras(extras)
    startActivity(intent)
}

fun <T> Activity.launchActivityForResult(
    it: Class<T>,
    requestCode: Int,
    extras: Bundle = Bundle()
) {
    val intent = Intent(this, it)
    intent.putExtras(extras)
    startActivityForResult(intent, requestCode)
}

fun <T> Fragment.launchActivityForResult(
    it: Class<T>,
    requestCode: Int,
    extras: Bundle = Bundle()
) {
    val intent = Intent(this.context, it)
    intent.putExtras(extras)
    startActivityForResult(intent, requestCode)
}

fun <T> Fragment.launchActivityForResult(
    it: Class<T>,
    activityResult: ActivityResultLauncher<Intent>,
    extras: Bundle = Bundle()
) {
    val intent = Intent(this.context, it)
    intent.putExtras(extras)
    activityResult.launch(intent)
}

fun <T> Activity.launchActivityForResult(
    it: Class<T>,
    activityResult: ActivityResultLauncher<Intent>,
    extras: Bundle = Bundle()
) {
    val intent = Intent(this, it)
    intent.putExtras(extras)
    activityResult.launch(intent)
}

 fun monthFromDate(date: Date): String {
    val formatterMonth = SimpleDateFormat("dd-MM-yy", Locale.getDefault())
    return formatterMonth.format(date.time)
}
fun ImageView.loadImage(url: String?) =
    Glide.with(context)
        .load(url ?: "")
        .placeholder(R.drawable.img_lg)
        .into(this)


