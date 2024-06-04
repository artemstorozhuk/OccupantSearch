package com.occupantsearch.group.author

sealed interface AuthorResult
class Ok(val author: Author) : AuthorResult
class Unavailable(val text: String) : AuthorResult
class TooManyRequests(val text: String) : AuthorResult
class ParseError(val text: String) : AuthorResult
