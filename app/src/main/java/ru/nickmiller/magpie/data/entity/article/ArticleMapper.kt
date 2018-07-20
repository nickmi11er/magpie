package ru.nickmiller.magpie.data.entity.article

import com.rometools.rome.feed.synd.SyndEnclosure
import com.rometools.rome.feed.synd.SyndEntryImpl
import ru.nickmiller.magpie.model.Article
import ru.nickmiller.magpie.model.Enclosure
import ru.nickmiller.magpie.utils.HtmlHelper


fun SyndEnclosure.syndMap(): EnclosureEntity = EnclosureEntity(url, type, length)

fun List<SyndEnclosure>.syndMap(): List<EnclosureEntity> = map { it.syndMap() }

fun EnclosureEntity.transform() = Enclosure(link, type, length)

fun List<EnclosureEntity>.transform(): MutableList<Enclosure> = map { it.transform() }.toMutableList()

fun Enclosure.encMap(): EnclosureEntity = EnclosureEntity(url, type, length)

fun List<Enclosure>.encMap(): List<EnclosureEntity> = map { it.encMap() }


class ArticleMapper {

    fun transform(artEntity: ArticleEntity): Article =
            with(artEntity) {
                return Article(link = link, description = description, favorite = favorite,
                        cached = cached, cachedTime = cachedTime, imageUrl = imageUrl,
                        acLink = acLink, acTitle = acTitle, author = author, pubDate = pubDate,
                        title = title, acIconUrl = acIconUrl, channelId = channelId, pureDescription = pureDescription,
                        enclosures = enclosures.transform())
            }

    fun transform(artEntityCollection: List<ArticleEntity>): MutableList<Article> =
            mutableListOf<Article>().apply {
                artEntityCollection.forEach { add(transform(it)) }
            }


    fun map(art: Article): ArticleEntity = with(art) {
        ArticleEntity(link!!, categories, description, favorite, cached, cachedTime,
                imageUrl, acTitle, author, acLink, pubDate, title, acIconUrl, channelId, pureDescription, enclosures.encMap())
    }


    fun map(arts: List<Article>): List<ArticleEntity> {
        val ents = mutableListOf<ArticleEntity>()
        arts.forEach { ents.add(map(it)) }
        return ents.filter { it.pubDate != null }
    }

    private val IMAGE_MATCHER_GROUP = 2

    fun transformArticleEntity(acTitle: String?, acLink: String?, iconUrl: String?, channelId: String, syndArt: SyndEntryImpl): ArticleEntity {
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
                    pubDate = publishedDate, title = title, pureDescription = description?.let { HtmlHelper.correct(it) },
                    enclosures = enclosures.syndMap())
        }
    }
}