package ru.nickmiller.magpie.data.entity.feed

import com.rometools.rome.feed.synd.SyndEntryImpl
import com.rometools.rome.feed.synd.SyndFeed
import ru.nickmiller.magpie.data.entity.article.ArticleEntity
import ru.nickmiller.magpie.data.entity.article.ArticleMapper
import ru.nickmiller.magpie.model.Feed


class FeedMapper(val artMapper: ArticleMapper) {

    fun transform(feedEntity: FeedEntity): Feed =
            with(feedEntity) {
                return Feed(title = title, articles = artMapper.transform(articles), description = description,
                        copyright = copyright, encoding = encoding, language = language, imgUrl = imgUrl,
                        author = author, link = link, acIconUrl = acIconUrl)
            }


    fun transform(feedEntityCollection: List<FeedEntity>): List<Feed> {
        val res = mutableListOf<Feed>()
        feedEntityCollection.forEach { res.add(transform(it)) }
        return res
    }

    fun transformFeedEntity(syndFeed: SyndFeed, iconUrl: String?): FeedEntity =
            with(syndFeed) {
                val articles: MutableList<ArticleEntity> = mutableListOf()
                syndFeed.entries.forEach {
                    articles.add(artMapper.transformArticleEntity(title, link, iconUrl, it as SyndEntryImpl))
                }
                return FeedEntity(link, articles, description, copyright, null,
                        encoding, language, image?.url, author, title, iconUrl)
            }

    fun syndToFeed(syndFeed: SyndFeed, iconUrl: String?) =
        transform(transformFeedEntity(syndFeed, iconUrl))

}