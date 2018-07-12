package ru.nickmiller.magpie.ui.channels

import org.koin.android.architecture.ext.viewModel
import ru.nickmiller.magpie.R
import ru.nickmiller.magpie.databinding.FragmentChannelsBinding
import ru.nickmiller.magpie.ui.base.BaseFragment


class ChannelsFragment : BaseFragment<FragmentChannelsBinding, ChannelsViewModel>() {
    override fun contentLayout(): Int = R.layout.fragment_channels

    override fun getTitleRes(): Int = R.string.title_channels

    override val viewModel by viewModel<ChannelsViewModel>()
}