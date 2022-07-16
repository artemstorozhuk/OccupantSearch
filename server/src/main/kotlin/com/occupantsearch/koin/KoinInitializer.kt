package com.occupantsearch.koin

import com.occupantsearch.analytics.AnalyticsController
import com.occupantsearch.analytics.OccupantsCountByDateController
import com.occupantsearch.analytics.PostsCountByDateController
import com.occupantsearch.args.AppArgsController
import com.occupantsearch.db.Database
import com.occupantsearch.export.ExportController
import com.occupantsearch.image.FaceDetector
import com.occupantsearch.image.ImageDownloader
import com.occupantsearch.image.ImageFaceController
import com.occupantsearch.occupant.OccupantController
import com.occupantsearch.occupant.PostFilter
import com.occupantsearch.person.PersonTextSearcher
import com.occupantsearch.properties.PropertiesController
import com.occupantsearch.resource.ResourceReader
import com.occupantsearch.server.Server
import com.occupantsearch.server.routing.AnalyticsResponder
import com.occupantsearch.server.routing.ExportResponder
import com.occupantsearch.server.routing.IndexHtmlResponder
import com.occupantsearch.server.routing.OccupantResponder
import com.occupantsearch.server.routing.OccupantsResponder
import com.occupantsearch.text.TextBlockSplitter
import com.occupantsearch.update.UpdateController
import com.occupantsearch.vk.PostController
import com.occupantsearch.vk.VkNewsfeedSearcher
import org.koin.core.Koin
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun initKoin(): Koin {
    val app = startKoin {}
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
            singleOf(::PostsCountByDateController)
            singleOf(::OccupantsCountByDateController)
            singleOf(::AnalyticsController)
            singleOf(::AnalyticsResponder)
            singleOf(::AppArgsController)
            singleOf(::OccupantResponder)
            singleOf(::PostFilter)
            singleOf(::Server)
        }
    )
    return app.koin
}