package com.occupantsearch.koin

import com.occupantsearch.analytics.AnalyticsController
import com.occupantsearch.db.Database
import com.occupantsearch.export.ExportController
import com.occupantsearch.image.FaceDetector
import com.occupantsearch.image.ImageDownloader
import com.occupantsearch.image.ImageFaceController
import com.occupantsearch.occupant.OccupantController
import com.occupantsearch.person.PersonTextSearcher
import com.occupantsearch.properties.PropertiesController
import com.occupantsearch.resource.ResourceReader
import com.occupantsearch.server.Server
import com.occupantsearch.server.routing.AnalyticsResponder
import com.occupantsearch.server.routing.ExportResponder
import com.occupantsearch.server.routing.IndexHtmlResponder
import com.occupantsearch.server.routing.OccupantsResponder
import com.occupantsearch.text.TextBlockSplitter
import com.occupantsearch.update.UpdateController
import com.occupantsearch.vk.PostController
import com.occupantsearch.vk.VkNewsfeedSearcher
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent.getKoin

fun initKoin() {
    startKoin {
    }
    loadKoinModules(
        module {
            singleOf(::VkNewsfeedSearcher)
            singleOf(::Database)
            singleOf(::ImageDownloader)
            singleOf(::FaceDetector)
            singleOf(::ImageFaceController)
            singleOf(::OccupantController)
            singleOf(::PersonTextSearcher)
            singleOf(::ResourceReader)
            singleOf(::TextBlockSplitter)
            singleOf(::PostController)
            singleOf(::PropertiesController)
            singleOf(::UpdateController)
            singleOf(::ExportController)
            singleOf(::ExportResponder)
            singleOf(::IndexHtmlResponder)
            singleOf(::OccupantsResponder)
            singleOf(::AnalyticsController)
            singleOf(::AnalyticsResponder)
            singleOf(::Server)
        }
    )
}

inline fun <reified T : Any> get() = getKoin().get<T>()