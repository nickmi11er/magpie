package ru.nickmiller.magpie.ui.channels

import android.os.Bundle
import android.view.View
import org.koin.android.architecture.ext.viewModel
import ru.nickmiller.magpie.R
import ru.nickmiller.magpie.databinding.FragmentChannelsBinding
import ru.nickmiller.magpie.ui.MainActivity
import ru.nickmiller.magpie.ui.base.BaseFragment


class ChannelsFragment : BaseFragment<FragmentChannelsBinding, ChannelsViewModel>() {
    override fun contentLayout(): Int = R.layout.fragment_channels

    override fun getTitleRes(): Int = R.string.title_channels

    override val viewModel by viewModel<ChannelsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).stopAppBarScrolling()
        binding.viewModel = viewModel
    }
}