package ru.nickmiller.magpie.ui.base

import android.app.Activity
import android.arch.lifecycle.AndroidViewModel
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.nickmiller.magpie.ui.MainActivity


abstract class BaseFragment<B : ViewDataBinding, M : AndroidViewModel> : Fragment() {
    @LayoutRes abstract fun contentLayout(): Int
    abstract fun getTitleRes(): Int
    abstract val viewModel: M
    lateinit var binding: B


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, contentLayout(), container, false)
        return binding.root
    }

    protected fun setTitle() = try {
        (activity as MainActivity).setTitle(getString(getTitleRes()))
    } catch (e: ClassCastException) {

    }
}