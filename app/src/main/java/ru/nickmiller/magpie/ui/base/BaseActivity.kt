package ru.nickmiller.magpie.ui.base

import android.arch.lifecycle.AndroidViewModel
import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.InputMethodManager
import kotlin.reflect.KClass


abstract class BaseActivity<B : ViewDataBinding, M : AndroidViewModel> : AppCompatActivity() {
    @LayoutRes abstract fun contentLayout(): Int
    abstract val viewModel: M
    lateinit var binding: B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, contentLayout())
    }

    protected fun replaceFragment(@IdRes containerId: Int, frClass: KClass<out Fragment>): Boolean {
        val fragment = frClass.java.newInstance()
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        }
        val trans = supportFragmentManager.beginTransaction().replace(containerId, fragment)
        trans.commit()
        return true
    }

    protected fun hideKeyboard() {
        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(currentFocus.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}