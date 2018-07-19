package ru.nickmiller.magpie.ui.feed

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import ru.nickmiller.magpie.R
import ru.nickmiller.magpie.databinding.ArticleItemBinding
import ru.nickmiller.magpie.model.Article


class ArticlesAdapter : RecyclerView.Adapter<ArticlesAdapter.ArticleViewHolder>() {
    var articles = mutableListOf<Article>()
    var articleClickListener: ((Action, Article) -> Unit)? = null

    fun addArticle(item: Article) {
        if (item.pubDate == null) return
        if (articles.contains(item)) {
            val doubledArticle = articles.find { it.link == item.link }
            val dInd = articles.indexOf(doubledArticle)
            if (doubledArticle?.favorite != item.favorite) {
                articles.remove(doubledArticle)
                notifyItemRemoved(dInd)
                addArticle(item)
            } else return
        }
        if (articles.contains(item) || item.pubDate == null) return
        var left = 0
        var right = articles.size
        var mid: Int

        while (left < right) {
            mid = (left + right) / 2
            val res = if (articles[mid].pubDate?.time!! < item.pubDate?.time!!) 1 else 0
            if (res == 1) right = mid
            else left = mid + 1
        }
        articles.add(left, item)
        notifyItemInserted(left)
    }

    fun addArticles(articles: List<Article>) = articles.forEach { addArticle(it) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ArticleViewHolder(DataBindingUtil.inflate(inflater, R.layout.article_item, parent, false))
    }

    override fun getItemCount(): Int = articles.size

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(articles[position])
        holder.binding.bookmarkArt.setOnClickListener {
            articleClickListener?.invoke(Action.BOOKMARK, articles[position])
        }
        holder.binding.root.setOnClickListener {
            articleClickListener?.invoke(Action.SHOW_ARTICLE, articles[position])
        }
    }

    class ArticleViewHolder(val binding: ArticleItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            binding.article = article
            binding.executePendingBindings()
        }
    }

    enum class Action {
        BOOKMARK,
        SHOW_ARTICLE
    }
}