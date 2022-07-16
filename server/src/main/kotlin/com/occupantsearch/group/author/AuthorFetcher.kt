package com.occupantsearch.group.author

import com.occupantsearch.vk.mobilePostUrl
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.koin.core.component.KoinComponent


class AuthorFetcher : KoinComponent {

    fun fetch(uniqueId: String): AuthorResult {
        val doc = Jsoup.connect(uniqueId.mobilePostUrl).get()
        val text = doc.text()
        return when {
            text.contains("429") -> TooManyRequests(text)
            text.contains("user has chosen to hide their page")
                || text.contains("profile is private")
                || text.contains("wall can be viewed by members only")
                || text.contains("user deleted their account")
                || text.contains("temporarily suspended")
                || text.contains("we had to block")
                || text.contains("sign in to view this page") -> Unavailable(text)
            else -> parse(doc, text)
        }
    }

    fun parse(doc: Document, text: String): AuthorResult {
        val head = doc.getElementsByClass("wi_head").first() ?: return ParseError(text)
        val avatar = head.getElementsByClass("Avatar__image Avatar__image-1").first() ?: return ParseError(text)
        val author = head.getElementsByClass("pi_author").first() ?: return ParseError(text)
        return Ok(
            Author(
                name = author.text(),
                url = author.attr("href"),
                avatar = getAvatarUrl(avatar.attr("style"))
            )
        )
    }
}