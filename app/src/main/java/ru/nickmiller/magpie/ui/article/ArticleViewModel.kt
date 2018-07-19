package ru.nickmiller.magpie.ui.article

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import ru.nickmiller.magpie.data.repository.ArticlesRepository
import ru.nickmiller.magpie.model.Article


class ArticleViewModel(val app: Application, val repository: ArticlesRepository) : AndroidViewModel(app) {

    fun addToBookmarks(article: Article) = repository.bookmark(article)

    fun deleteFromBookmarks(article: Article) = repository.deleteBookmark(article)
}