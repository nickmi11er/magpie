package ru.nickmiller.magpie.ui.article.layout

import android.graphics.Canvas
import android.graphics.Paint
import android.text.Layout
import android.text.style.LeadingMarginSpan
import android.text.style.LineBackgroundSpan


class MyQuoteSpan(private val bgColor: Int,
                  private val stpColor: Int,
                  private val stpWidth: Float,
                  private val gap: Float) :
        LeadingMarginSpan, LineBackgroundSpan {


    override fun drawBackground(c: Canvas?, p: Paint?, left: Int, right: Int, top: Int,
                                baseline: Int, bottom: Int, text: CharSequence?, start: Int,
                                end: Int, lnum: Int) {

        val paintColor = p?.color
        p?.color = bgColor
        c?.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), p)
        p?.color = paintColor ?: bgColor
    }

    override fun drawLeadingMargin(c: Canvas?, p: Paint?, x: Int, dir: Int, top: Int,
                                   baseline: Int, bottom: Int, text: CharSequence?, start: Int,
                                   end: Int, first: Boolean, layout: Layout?) {

        val style = p?.style
        val paintColor = p?.color

        p?.style = Paint.Style.FILL
        p?.color = stpColor

        c?.drawRect(x.toFloat(), top.toFloat(), x + dir * stpWidth, bottom.toFloat(), p)

        p?.style = style
        p?.color = paintColor ?: stpColor
    }

    override fun getLeadingMargin(first: Boolean): Int = (stpWidth + gap).toInt()
}