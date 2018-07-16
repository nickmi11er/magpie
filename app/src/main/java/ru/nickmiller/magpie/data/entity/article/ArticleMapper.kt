package ru.nickmiller.magpie.data.entity.article

import com.rometools.rome.feed.synd.SyndEntryImpl
import ru.nickmiller.magpie.model.Article
import ru.nickmiller.magpie.utils.HtmlHelper


class ArticleMapper {

    fun transform(artEntity: ArticleEntity): Article =
            with(artEntity) {
                return Article(link = link, description = description, favorite = favorite,
                        cached = cached, cachedTime = cachedTime, imageUrl = imageUrl,
                        acLink = acLink, acTitle = acTitle, author = author, pubDate = pubDate,
                        title = title, acIconUrl = acIconUrl)
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
                imageUrl, acTitle, author, acLink, pubDate, title, acIconUrl)
    }


    fun map(arts: List<Article>): List<ArticleEntity> {
        val ents = mutableListOf<ArticleEntity>()
        arts.forEach { ents.add(map(it)) }
        return ents
    }

    private val IMAGE_MATCHER_GROUP = 2

    fun transformArticleEntity(acTitle: String?, acLink: String?, iconUrl:String?,  syndArt: SyndEntryImpl): ArticleEntity {
        val artEntity = transformArticleEntity(syndArt)
        artEntity.acTitle = acTitle
        artEntity.acLink = acLink
        artEntity.acIconUrl = iconUrl
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
            return ArticleEntity(link = link, title = title, author = author, pubDate = publishedDate,
                    description = description, imageUrl = imageUrl)
        }
    }
}