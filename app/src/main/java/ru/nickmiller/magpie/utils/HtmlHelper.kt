package ru.nickmiller.magpie.utils

import android.content.Context
import android.os.Build
import android.text.Html
import java.util.regex.Pattern


class HtmlHelper {


    companion object {

        fun findImg(description: String?, imgMatcherGroup: Int): String? {
            val matcher = IMG_URL_PATTERN.matcher(description)
            if (matcher.find()) {
                return matcher.group(imgMatcherGroup)
            }

            return null
        }

        fun fromHtml(html: String) =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
                } else {
                    Html.fromHtml(html)
                }


        val ESCAPES: MutableMap<String, String> = mutableMapOf(
                // Mapping HTML characters to ISO-8859-1 equivalents.
                "&nbsp;" to "\u00A0", // non-breaking space
                "&iexcl;" to "\u00A1", // inverted exclamation mark
                "&cent;" to "\u00A2", // cent sign
                "&pound;" to "\u00A3", // pound sign
                "&curren;" to "\u00A4", // currency sign
                "&yen;" to "\u00A5", // yen sign = yuan sign
                "&brvbar;" to "\u00A6", // broken bar = broken vertical bar
                "sect" to "\u00A7", // section sign
                "&uml;" to "\u00A8", // diaeresis = spacing diaeresis
                "&copy;" to "\u00A9", // © - copyright sign
                "&ordf;" to "\u00AA", // feminine ordinal indicator
                "&laquo;" to "\u00AB", // left-pointing double angle quotation mark = left pointing guillemet
                "&not;" to "\u00AC", // not sign
                "&shy;" to "\u00AD", // soft hyphen = discretionary hyphen
                "&reg;" to "\u00AE", // ® - registered trademark sign
                "&macr;" to "\u00AF", // macron = spacing macron = overline = APL overbar
                "&deg;" to "\u00B0", // degree sign
                "&plusmn;" to "\u00B1", // plus-minus sign = plus-or-minus sign
                "&sup2;" to "\u00B2", // superscript two = superscript digit two = squared
                "&sup3;" to "\u00B3", // superscript three = superscript digit three = cubed
                "&acute;" to "\u00B4", // acute accent = spacing acute
                "&micro;" to "\u00B5", // micro sign
                "&para;" to "\u00B6", // pilcrow sign = paragraph sign
                "&middot;" to "\u00B7", // middle dot = Georgian comma = Greek middle dot
                "&cedil;" to "\u00B8", // cedilla = spacing cedilla
                "&sup1;" to "\u00B9", // superscript one = superscript digit one
                "&ordm;" to "\u00BA", // masculine ordinal indicator
                "&raquo;" to "\u00BB", // right-pointing double angle quotation mark = right pointing guillemet
                "&frac14;" to "\u00BC", // vulgar fraction one quarter = fraction one quarter
                "&frac12;" to "\u00BD", // vulgar fraction one half = fraction one half
                "&frac34;" to "\u00BE", // vulgar fraction three quarters = fraction three quarters
                "&iquest;" to "\u00BF", // inverted question mark = turned question mark
                "&Agrave;" to "\u00C0", // А - uppercase A, grave accent
                "&Aacute;" to "\u00C1", // Б - uppercase A, acute accent
                "&Acirc;" to "\u00C2", // В - uppercase A, circumflex accent
                "&Atilde;" to "\u00C3", // Г - uppercase A, tilde
                "&Auml;" to "\u00C4", // Д - uppercase A, umlaut
                "&Aring;" to "\u00C5", // Е - uppercase A, ring
                "&AElig;" to "\u00C6", // Ж - uppercase AE
                "&Ccedil;" to "\u00C7", // З - uppercase C, cedilla
                "&Egrave;" to "\u00C8", // И - uppercase E, grave accent
                "&Eacute;" to "\u00C9", // Й - uppercase E, acute accent
                "&Ecirc;" to "\u00CA", // К - uppercase E, circumflex accent
                "&Euml;" to "\u00CB", // Л - uppercase E, umlaut
                "&Igrave;" to "\u00CC", // М - uppercase I, grave accent
                "&Iacute;" to "\u00CD", // Н - uppercase I, acute accent
                "&Icirc;" to "\u00CE", // О - uppercase I, circumflex accent
                "&Iuml;" to "\u00CF", // П - uppercase I, umlaut
                "&ETH;" to "\u00D0", // Р - uppercase Eth, Icelandic
                "&Ntilde;" to "\u00D1", // С - uppercase N, tilde
                "&Ograve;" to "\u00D2", // Т - uppercase O, grave accent
                "&Oacute;" to "\u00D3", // У - uppercase O, acute accent
                "&Ocirc;" to "\u00D4", // Ф - uppercase O, circumflex accent
                "&Otilde;" to "\u00D5", // Х - uppercase O, tilde
                "&uml;" to "\u00D6", // Ц - uppercase O, umlaut
                "&times;" to "\u00D7", // multiplication sign
                "&Oslash;" to "\u00D8", // Ш - uppercase O, slash
                "&Ugrave;" to "\u00D9", // Щ - uppercase U, grave accent
                "&Uacute;" to "\u00DA", // Ъ - uppercase U, acute accent
                "&Ucirc;" to "\u00DB", // Ы - uppercase U, circumflex accent
                "&Uuml;" to "\u00DC", // Ь - uppercase U, umlaut
                "&Yacute;" to "\u00DD", // Э - uppercase Y, acute accent
                "&THORN;" to "\u00DE", // Ю - uppercase THORN, Icelandic
                "&szlig;" to "\u00DF", // Я - lowercase sharps, German
                "&agrave;" to "\u00E0", // а - lowercase a, grave accent
                "&aacute;" to "\u00E1", // б - lowercase a, acute accent
                "&acirc;" to "\u00E2", // в - lowercase a, circumflex accent
                "&atilde;" to "\u00E3", // г - lowercase a, tilde
                "&auml;" to "\u00E4", // д - lowercase a, umlaut
                "&aring;" to "\u00E5", // е - lowercase a, ring
                "&aelig;" to "\u00E6", // ж - lowercase ae
                "&ccedil;" to "\u00E7", // з - lowercase c, cedilla
                "&egrave;" to "\u00E8", // и - lowercase e, grave accent
                "&eacute;" to "\u00E9", // й - lowercase e, acute accent
                "&ecirc;" to "\u00EA", // к - lowercase e, circumflex accent
                "&euml;" to "\u00EB", // л - lowercase e, umlaut
                "&igrave;" to "\u00EC", // м - lowercase i, grave accent
                "&iacute;" to "\u00ED", // н - lowercase i, acute accent
                "&icirc;" to "\u00EE", // о - lowercase i, circumflex accent
                "&iuml;" to "\u00EF", // п - lowercase i, umlaut
                "&eth;" to "\u00F0", // р - lowercase eth, Icelandic
                "&ntilde;" to "\u00F1", // с - lowercase n, tilde
                "&ograve;" to "\u00F2", // т - lowercase o, grave accent
                "&oacute;" to "\u00F3", // у - lowercase o, acute accent
                "&ocirc;" to "\u00F4", // ф - lowercase o, circumflex accent
                "&otilde;" to "\u00F5", // х - lowercase o, tilde
                "&ouml;" to "\u00F6", // ц - lowercase o, umlaut
                "&divide;" to "\u00F7", // division sign
                "&oslash;" to "\u00F8", // ш - lowercase o, slash
                "&ugrave;" to "\u00F9", // щ - lowercase u, grave accent
                "&uacute;" to "\u00FA", // ъ - lowercase u, acute accent
                "&ucirc;" to "\u00FB", // ы - lowercase u, circumflex accent
                "&uuml;" to "\u00FC", // ь - lowercase u, umlaut
                "&yacute;" to "\u00FD", // э - lowercase y, acute accent
                "&thorn;" to "\u00FE", // ю - lowercase thorn, Icelandic
                "&yuml;" to "\u00FF"   // я - lowercase y, umlaut
        )

        val IMG_URL_PATTERN: Pattern =
                Pattern.compile("(<img.+src\\s*=\\s*\")((http|https).+?\\.(jpeg|png|jpg|gif))(\\s*\".*?>)")

        val TEST_IMG_URL_PATTERN: Pattern = Pattern.compile("(url\\(|<(?:link|script|img)[^>]+(?:src|href)\\s*=\\s*['\"]\\s*)(?:((?:https|http)[^'\"]+))(.*?>)")
        private val pattern = Pattern.compile("(http|https)://.*?\\.(jpeg|png|jpg|gif)")
        private val patternHtml = Pattern.compile("&#([0-9]{1,5});|&.*?;")

        fun correct(desc: String): String {
            var res = desc.replace("(<.*?>)|(\n)|(\r)".toRegex(), "")

            val matcher = patternHtml.matcher(res)
            while (matcher.find()) {
                if (matcher.group().contains("&#")) {
                    try {
                        val dec: Int = matcher.group(1).toInt()
                        val ch: Char = dec.toChar()
                        res = res.replace("&#$dec;", ch.toString())
                    } catch (e: IllegalStateException) {
                        println(e.message)
                    }
                } else {
                    val v: String? = ESCAPES[matcher.group()]
                    if (v != null) res = res.replace(matcher.group(), v)
                }
            }
            return res
        }
    }
}