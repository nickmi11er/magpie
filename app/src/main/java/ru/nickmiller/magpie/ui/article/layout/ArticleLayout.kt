package ru.nickmiller.magpie.ui.article.layout

import android.content.Context
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import ru.nickmiller.magpie.R
import ru.nickmiller.magpie.model.Article
import ru.nickmiller.magpie.utils.HtmlHelper.Companion.IMG_URL_PATTERN
import java.text.SimpleDateFormat
import java.util.*


class ArticleLayout : LinearLayout {
    private val sdf = SimpleDateFormat("MMM d, yyyy hh:mm aaa", Locale.US)
    private val IMAGE_MATCHER_GROUP = 2
    private val textTileSize: Float = resources.getDimension(R.dimen.text_title_size)

    private lateinit var imgClick: (pos: Int) -> Unit
    private var imgCount = 0

    constructor(context: Context, art: Article) : super(context) {
        initLayout(context, art)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    private fun initLayout(context: Context, art: Article) {
        setBackgroundColor(ContextCompat.getColor(context, R.color.backgroundWhiteColor))
        orientation = LinearLayout.VERTICAL
        gravity = Gravity.CENTER_HORIZONTAL
        val linearParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)


        // Header
        val headerView = inflate(context, R.layout.layout_article_header, null)
        val articleTitle = headerView.findViewById<TextView>(R.id.article_title)
        val articleDate = headerView.findViewById<TextView>(R.id.article_dt)
        articleTitle.text = art.title
        articleDate.text = sdf.format(art.pubDate)
        addView(headerView)


        // Body
        val elements = separateDescriptionContent(art.description)

        if (elements.isEmpty()) {
            addView(TextArticleElement(context, art.description!!).generate(), linearParams)
        } else {
            elements.forEach { addView(it.generate(), linearParams) }
        }


        // Footer
        val footerView = inflate(context, R.layout.layout_article_footer, null)
        addView(footerView)
    }


    private fun separateDescriptionContent(desc: String?): MutableList<ArticleElement> {
        val els = mutableListOf<ArticleElement>()
        val matcher = IMG_URL_PATTERN.matcher(desc)
        var descCopy = desc

        // try to find image
        while (matcher.find()) {
            // if image has been found get url by matcher group (2)
            val image = matcher.group(IMAGE_MATCHER_GROUP)
            val iTag = matcher.group(1) + matcher.group(IMAGE_MATCHER_GROUP) + matcher.group(5)

            // split all text with image url
            val content = descCopy?.split(iTag)

            if (content != null && content.size == 2) {
                els.add(TextArticleElement(context, content[0]))
                els.add(ImageArticleElement(context, image))
                els.add(TextArticleElement(context, content[1]))
                descCopy = content[1]
            }
        }
        return els
    }
}