package ru.nickmiller.magpie.data.repository.datasource.articles

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.rometools.rome.io.SyndFeedInput
import com.rometools.rome.io.XmlReader
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import ru.magpie.magpie.data.net.NetHelper
import ru.nickmiller.magpie.data.cache.dao.ChannelsDao
import ru.nickmiller.magpie.data.entity.article.ArticleMapper
import ru.nickmiller.magpie.data.entity.feed.FeedMapper
import ru.nickmiller.magpie.model.Article
import ru.nickmiller.magpie.model.Feed
import ru.nickmiller.magpie.utils.FetchProgress
import ru.nickmiller.magpie.utils.Resource
import ru.nickmiller.magpie.utils.Status
import ru.nickmiller.magpie.utils.switchMap


class CloudArticlesStore(val netHelper: NetHelper, val mapper: FeedMapper, val artMapper: ArticleMapper, val dao: ChannelsDao) {
    val fetchStatus = MutableLiveData<FetchProgress>()

    fun getArticles(): LiveData<List<Article>> = dao.getAllSubs().switchMap { channels ->
        fetchStatus.postValue(FetchProgress.started(channels.size))
        MutableLiveData<List<Article>>().apply {
            Single.fromCallable {
                val res = mutableListOf<Resource<Feed>>()
                var completed = 0
                var errors = 0
                channels.forEach {
                    try {
                        val resp = netHelper.call(it.feedId.replaceFirst("feed/", "")).execute()
                        if (resp?.body() != null) {
                            res.add(Resource.success(mapper.syndToFeed(SyndFeedInput().build(XmlReader(resp.body()!!.byteStream())), it.iconUrl)))
                            completed++
                        } else {
                            res.add(Resource.error("Illegal response value", null))
                            errors++
                        }
                    } catch (e: Exception) {
                        res.add(Resource.error("Can not fetch feed ${it.feedId}", null))
                        errors++
                    }
                    fetchStatus.postValue(FetchProgress.progress(channels.size, completed, errors))
                }
                fetchStatus.postValue(FetchProgress.completed(channels.size, completed, errors))
                postValue(combineArticles(res))
            }
                    .subscribeOn(Schedulers.newThread())
                    .subscribe()
        }
    }


    private fun combineArticles(feeds: MutableList<Resource<Feed>>): List<Article>? =
            feeds.filter { it.status == Status.SUCCESS && it.data != null }
                    .flatMap { it.data!!.articles }
                    .distinct()
}