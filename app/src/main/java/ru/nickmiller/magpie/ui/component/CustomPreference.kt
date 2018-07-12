package ru.nickmiller.magpie.ui.component

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.preference.Preference
import android.support.v7.preference.PreferenceViewHolder
import android.util.AttributeSet
import android.widget.TextView
import ru.nickmiller.magpie.R

class CustomPreference : Preference {

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?) : super(context)


    override fun onBindViewHolder(holder: PreferenceViewHolder?) {
        super.onBindViewHolder(holder)
        val robotoTf = ResourcesCompat.getFont(context, R.font.roboto_light)
        val titleView = holder?.findViewById(android.R.id.title) as TextView
        titleView.typeface = robotoTf
        titleView.setTextColor(ContextCompat.getColor(context, R.color.textMain))

        val summaryView = holder.findViewById(android.R.id.summary) as TextView
        summaryView.typeface = robotoTf
        summaryView.setTextColor(ContextCompat.getColor(context, R.color.textSecondary))

    }
}
 
