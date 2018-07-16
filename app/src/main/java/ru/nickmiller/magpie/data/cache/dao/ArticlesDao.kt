package ru.nickmiller.magpie.data.cache.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import io.reactivex.Flowable
import ru.nickmiller.magpie.data.entity.article.ArticleEntity


@Dao
interface ArticlesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticle(art: ArticleEntity)

    @Delete
    fun deleteArticle(art: ArticleEntity)

    @Query("SELECT * FROM articleentity")
    fun getAllArticles(): LiveData<List<ArticleEntity>>

    @Query("SELECT * FROM articleentity WHERE cached")
    fun getCachedArticles(): LiveData<List<ArticleEntity>>

//    @Query("DELETE FROM articleentity WHERE cached AND NOT favorite AND cachedTime > :arg0")
//    fun clearCacheByDate(cTime: Long)

//    @Query("UPDATE articleentity SET cached = 1 WHERE link = :arg0")
//    fun cacheExistArt(link: String)

    @Query("SELECT * FROM articleentity WHERE favorite")
    fun getFavoriteArticles(): LiveData<List<ArticleEntity>>


//    @Query("SELECT * FROM articleentity WHERE link = :arg0")
//    fun getArtByLink(link: String): ArticleEntity?

//    @Query("SELECT * FROM articleentity WHERE acLink = :arg0")
//    fun getFeedArticles(url: String): Flowable<List<ArticleEntity>>

}