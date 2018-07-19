package ru.nickmiller.magpie.data.entity.article

import com.rometools.rome.feed.synd.SyndEntryImpl
import ru.magpie.magpie.data.net.NetHelper
import ru.nickmiller.magpie.model.Article
import ru.nickmiller.magpie.utils.HtmlHelper
import ru.nickmiller.magpie.utils.mainLog


class ArticleMapper {

    fun transform(artEntity: ArticleEntity): Article =
            with(artEntity) {
                return Article(link = link, description = description, favorite = favorite,
                        cached = cached, cachedTime = cachedTime, imageUrl = imageUrl,
                        acLink = acLink, acTitle = acTitle, author = author, pubDate = pubDate,
                        title = title, acIconUrl = acIconUrl, channelId = channelId, pureDescription = pureDescription)
            }

    fun transform(artEntityCollection: List<ArticleEntity>): MutableList<Article> {
        val articles = mutableListOf<Article>()
        artEntityCollection.forEach {
            articles.add(transform(it))
        }
        return articles
    }

    fun map(art: Article): ArticleEntity = with(art) {
        ArticleEntity(link!!, categories, description, favorite, cached, cachedTime,
                imageUrl, acTitle, author, acLink, pubDate, title, acIconUrl, channelId, pureDescription)
    }


    fun map(arts: List<Article>): List<ArticleEntity> {
        val ents = mutableListOf<ArticleEntity>()
        arts.forEach { ents.add(map(it)) }
        return ents.filter { it.pubDate != null }
    }

    private val IMAGE_MATCHER_GROUP = 2

    fun transformArticleEntity(acTitle: String?, acLink: String?, iconUrl:String?, channelId: String, syndArt: SyndEntryImpl): ArticleEntity {
        val artEntity = transformArticleEntity(syndArt)
        artEntity.acTitle = acTitle
        artEntity.acLink = acLink
        artEntity.acIconUrl = iconUrl
        artEntity.channelId = channelId
        return artEntity
    }

    fun transformArticleEntity(syndArt: SyndEntryImpl): ArticleEntity {
        with(syndArt) {
            val description = try {
                if (description != null) description.value else throw NullPointerException()
            } catch (e: NullPointerException) {
                contents.first().value
            }
            val imageUrl = HtmlHelper.findImg(description, IMAGE_MATCHER_GROUP)
            return ArticleEntity(link = link, description = description, imageUrl = imageUrl, author = author,
                    pubDate = publishedDate, title = title, pureDescription = description?.let { HtmlHelper.correct(it) } )
        }
    }
}