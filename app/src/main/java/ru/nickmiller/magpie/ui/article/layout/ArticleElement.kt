package ru.nickmiller.magpie.ui.article.layout

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.graphics.Rect
import android.net.Uri
import android.os.Build
import android.support.v4.content.ContextCompat
import android.support.v4.content.ContextCompat.startActivity
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.*
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import ru.nickmiller.magpie.R
import ru.nickmiller.magpie.utils.HtmlHelper
import android.text.style.LineHeightSpan
import android.text.TextPaint






private val GIF_EXTENTION_TAG = ".gif"
private val IMG_TAG = "#IMG"

interface ArticleElement {
    val context: Context
    val data: String
    fun generate(): View
}

class TextArticleElement(override val context: Context, override val data: String) : ArticleElement {

    override fun generate(): TextView = TextView(context).apply {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setTextAppearance(R.style.ArticleBodyTextView)
        } else {
            setTextAppearance(context, R.style.ArticleBodyTextView)
        }
        val defHorMargin: Int = resources.getDimensionPixelSize(R.dimen.def_hor_margin)
        val defVerMargin: Int = resources.getDimensionPixelSize(R.dimen.def_ver_margin)
        setPadding(defHorMargin, defVerMargin, defHorMargin, defVerMargin)
        text = htmlText(data)
        movementMethod = LinkMovementMethod.getInstance()
        setLineSpacing(10f, 1.2f)
    }

    private fun htmlText(html: String): SpannableStringBuilder {
        var test = html
        test = test.replace("<pre>", "<p>").replace("</pre>", "</p>")
        val sequence = HtmlHelper.fromHtml(test)
        val strBuilder = SpannableStringBuilder(sequence)

        val quoteSpans = strBuilder.getSpans(0, sequence.length, QuoteSpan::class.java)
        replaceQuoteSpans(strBuilder, quoteSpans)

        val urls = strBuilder.getSpans(0, sequence.length, URLSpan::class.java)
        for (span in urls) {
            makeLinkClickable(strBuilder, span)
        }
        return strBuilder
    }

    private fun replaceQuoteSpans(strBuilder: SpannableStringBuilder, quoteSpans: Array<QuoteSpan>) {
        quoteSpans.forEach {
            val start = strBuilder.getSpanStart(it)
            val end = strBuilder.getSpanEnd(it)
            val flags = strBuilder.getSpanFlags(it)

            //strBuilder.removeSpan(it)
            //strBuilder.setSpan(MyQuoteSpan(resources.getColor(R.color.colorDivider), resources.getColor(R.color.colorBlack), 12F, 24F), start, end, 0)
        }
    }

    private fun makeLinkClickable(strBuilder: SpannableStringBuilder, span: URLSpan) {
        val start = strBuilder.getSpanStart(span)
        val end = strBuilder.getSpanEnd(span)
        val flags = strBuilder.getSpanFlags(span)
        val clickable = object : ClickableSpan() {
            override fun onClick(view: View) {
//                val isIA = PreferenceManager.getDefaultSharedPreferences(context)
//                        .getBoolean(context.getString(R.string.instant_articles_key), false)

//                val browserIntent =
//                        if (!isIA) Intent(Intent.ACTION_VIEW, Uri.parse(span.url))
//                        else Intent(context, WebViewActivity::class.java)
//                                .putExtra(ARTICLE_TITLE, span.url)
//                                .putExtra(ARTICLE_URL, span.url)
//                                .putExtra(FROM_SECONDARY_LINK, true)
                startActivity(context, Intent(Intent.ACTION_VIEW, Uri.parse(span.url)), null)
            }
        }
        strBuilder.removeSpan(span)
        strBuilder.setSpan(clickable, start, end, flags)
    }
}


class ImageArticleElement(override val context: Context, override val data: String) : ArticleElement {
    override fun generate(): ImageView {
        var elCopy = data

        //imgCount++
        //val imgPos = imgCount - 1
        elCopy = elCopy.replace(IMG_TAG, "")

        val view = ImageView(context)
        if (data.contains(GIF_EXTENTION_TAG, true)) {
            Glide.with(context).asGif().load(elCopy).into(view)
        } else {
            Glide.with(context).load(elCopy).into(view)
        }
        //view.setOnClickListener { imgClick(imgPos) }
        return view
    }
}

private class Height(private val mSize: Int) : LineHeightSpan.WithDensity {

    override fun chooseHeight(text: CharSequence, start: Int, end: Int,
                              spanstartv: Int, v: Int,
                              fm: Paint.FontMetricsInt) {
        // Should not get called, at least not by StaticLayout.
        chooseHeight(text, start, end, spanstartv, v, fm, null)
    }

    override fun chooseHeight(text: CharSequence, start: Int, end: Int,
                              spanstartv: Int, v: Int,
                              fm: Paint.FontMetricsInt, paint: TextPaint?) {
        var size = mSize
        if (paint != null) {
            size *= paint.density.toInt()
        }

        if (fm.bottom - fm.top < size) {
            fm.top = fm.bottom - size
            fm.ascent = fm.ascent - size
        } else {
            if (sProportion == 0f) {
                /*
                 * Calculate what fraction of the nominal ascent
                 * the height of a capital letter actually is,
                 * so that we won't reduce the ascent to less than
                 * that unless we absolutely have to.
                 */

                val p = Paint()
                p.textSize = 100f
                val r = Rect()
                p.getTextBounds("ABCDEFG", 0, 7, r)

                sProportion = r.top / p.ascent()
            }

            val need = Math.ceil((-fm.top * sProportion).toDouble()).toInt()

            when {
                size - fm.descent >= need -> {
                    /*
                     * It is safe to shrink the ascent this much.
                     */

                    fm.top = fm.bottom - size
                    fm.ascent = fm.descent - size
                }
                size >= need -> {
                    /*
                     * We can't show all the descent, but we can at least
                     * show all the ascent.
                     */

                    fm.ascent = -need
                    fm.top = fm.ascent
                    fm.descent = fm.top + size
                    fm.bottom = fm.descent
                }
                else -> {
                    /*
                     * Show as much of the ascent as we can, and no descent.
                     */

                    fm.ascent = -size
                    fm.top = fm.ascent
                    fm.descent = 0
                    fm.bottom = fm.descent
                }
            }
        }
    }

    companion object {
        private var sProportion = 0f
    }
}