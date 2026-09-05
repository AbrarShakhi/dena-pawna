package com.abrarshakhi.denapawna.domain

import com.abrarshakhi.denapawna.domain.model.TransactionCategory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategorizerEngine @Inject constructor() {

    private val keywordAnchors = mapOf(

        listOf(
            "WEB",
            "WEBSITE",
            "FRONTEND",
            "BACKEND",
            "FULLSTACK",
            "HTML",
            "CSS",
            "JS"
        ) to TransactionCategory.WEB_DEV,
        listOf(
            "APP",
            "ANDROID",
            "IOS",
            "MOBILE",
            "FLUTTER",
            "REACT NATIVE"
        ) to TransactionCategory.APP_DEV,
        listOf(
            "UI",
            "UX",
            "FIGMA",
            "DESIGN",
            "LOGO",
            "POSTER",
            "GRAPHIC"
        ) to TransactionCategory.DESIGN,
        listOf(
            "VIDEO",
            "EDIT",
            "EDITING",
            "PREMIERE",
            "AFTER EFFECTS"
        ) to TransactionCategory.EDITING,
        listOf(
            "CONTENT",
            "WRITING",
            "BLOG",
            "ARTICLE",
            "COPYWRITING"
        ) to TransactionCategory.CONTENT,
        listOf(
            "MARKETING",
            "SEO",
            "ADS",
            "PROMOTION",
            "SOCIAL MEDIA"
        ) to TransactionCategory.MARKETING
    )

    fun categorize(workName: String): TransactionCategory {
        val normalized = workName.uppercase().trim()

        for ((keywords, category) in keywordAnchors) {
            if (keywords.any { normalized.contains(it) }) {
                return category
            }
        }

        return TransactionCategory.OTHERS
    }
}