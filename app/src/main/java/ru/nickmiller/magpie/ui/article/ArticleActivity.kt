package ru.nickmiller.magpie.ui.article

import org.koin.android.architecture.ext.viewModel
import ru.nickmiller.magpie.R
import ru.nickmiller.magpie.databinding.ActivityArticleBinding
import ru.nickmiller.magpie.ui.base.BaseActivity


class ArticleActivity : BaseActivity<ActivityArticleBinding, ArticleViewModel>() {
    override fun contentLayout(): Int = R.layout.activity_article

    override val viewModel by viewModel<ArticleViewModel>()

}