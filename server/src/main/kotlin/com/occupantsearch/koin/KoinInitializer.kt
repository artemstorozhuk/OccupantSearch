package com.occupantsearch.koin

import com.occupantsearch.analytics.AnalyticsController
import com.occupantsearch.analytics.OccupantsCountByDateController
import com.occupantsearch.analytics.PostsCountByDateController
import com.occupantsearch.args.AppArgsController
import com.occupantsearch.db.Database
import com.occupantsearch.export.ExportController
import com.occupantsearch.group.GroupController
import com.occupantsearch.group.GroupDownloader
import com.occupantsearch.group.author.AuthorFetcher
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
import com.occupantsearch.server.routing.GroupResponder
import com.occupantsearch.server.routing.IndexHtmlResponder
import com.occupantsearch.server.routing.OccupantResponder
import com.occupantsearch.server.routing.OccupantsResponder
import com.occupantsearch.text.TextBlockSplitter
import com.occupantsearch.update.UpdateController
import com.occupantsearch.vk.PostDownloader
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
            singleOf(::AnalyticsController)
            singleOf(::AnalyticsResponder)
            singleOf(::AppArgsController)
            singleOf(::AuthorFetcher)
            singleOf(::Database)
            singleOf(::ExportController)
            singleOf(::ExportResponder)
            singleOf(::FaceDetector)
            singleOf(::GroupController)
            singleOf(::GroupDownloader)
            singleOf(::GroupResponder)
            singleOf(::ImageDownloader)
            singleOf(::ImageFaceController)
            singleOf(::IndexHtmlResponder)
            singleOf(::OccupantController)
            singleOf(::OccupantResponder)
            singleOf(::OccupantsCountByDateController)
            singleOf(::OccupantsResponder)
            singleOf(::PersonTextSearcher)
            singleOf(::PostDownloader)
            singleOf(::PostFilter)
            singleOf(::PostsCountByDateController)
            singleOf(::PropertiesController)
            singleOf(::ResourceReader)
            singleOf(::Server)
            singleOf(::TextBlockSplitter)
            singleOf(::UpdateController)
            singleOf(::VkNewsfeedSearcher)
        }
    )
    return app.koin
}