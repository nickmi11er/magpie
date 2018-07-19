package ru.nickmiller.magpie.ui.article

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import org.koin.android.architecture.ext.viewModel
import ru.nickmiller.magpie.R
import ru.nickmiller.magpie.databinding.ActivityArticleBinding
import ru.nickmiller.magpie.model.Article
import ru.nickmiller.magpie.ui.article.layout.ArticleLayout
import ru.nickmiller.magpie.ui.article.layout.FixAppBarLayoutBehavior
import ru.nickmiller.magpie.ui.base.BaseActivity


class ArticleActivity : BaseActivity<ActivityArticleBinding, ArticleViewModel>() {
    override fun contentLayout(): Int = R.layout.activity_article
    lateinit var rootView: NestedScrollView
    lateinit var title: TextView
    lateinit var appbar: AppBarLayout
    lateinit var toolbar: Toolbar
    lateinit var article: Article

    override val viewModel by viewModel<ArticleViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        toolbar.setNavigationIcon(R.drawable.ic_back_arrow)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { onBackPressed() }
        article = intent.getParcelableExtra("article_extra")
        title.text = article.acTitle
        val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT)
        val artLayout = ArticleLayout(this, article)
        rootView.addView(artLayout, params)

    }

    private fun initViews() {
        rootView = binding.rootView
        toolbar = binding.appbar.findViewById(R.id.toolbar)
        appbar = binding.coordinator.findViewById(R.id.appbar)
        (appbar.layoutParams as CoordinatorLayout.LayoutParams).behavior = FixAppBarLayoutBehavior(this, null)
        title = binding.coordinator.findViewById(R.id.toolbar_title)
        //title.setTextSize(TypedValue.COMPLEX_UNIT_SP, resources.getDimension(R.dimen.toolbar_title_medium))
        val params = title.layoutParams as LinearLayout.LayoutParams
        params.gravity = Gravity.START
        title.layoutParams = params
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_article_menu, menu)
        val fav = menu?.getItem(1)
        val icon =
                if (article.favorite) R.drawable.ic_bookmark_fill
                else R.drawable.ic_bookmark
        fav?.setIcon(icon)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.favorite_btn -> {
                if (article.favorite) {
                    viewModel.deleteFromBookmarks(article)
                    item.setIcon(R.drawable.ic_bookmark)
                } else {
                    viewModel.addToBookmarks(article)
                    item.setIcon(R.drawable.ic_bookmark_fill)
                }
            }
            R.id.share_btn -> {
                val shIntent = Intent(Intent.ACTION_SEND)
                shIntent.type = "text/plain"
                shIntent.putExtra(Intent.EXTRA_TEXT, article.link)
                startActivity(Intent.createChooser(shIntent, "Share"))
            }
        }

        return true
    }
}