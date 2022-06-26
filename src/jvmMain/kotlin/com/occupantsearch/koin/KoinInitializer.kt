package com.occupantsearch.koin

import com.occupantsearch.db.Database
import com.occupantsearch.image.FaceDetector
import com.occupantsearch.image.ImageDownloader
import com.occupantsearch.image.ImageFaceController
import com.occupantsearch.occupant.OccupantController
import com.occupantsearch.person.PersonTextSearcher
import com.occupantsearch.text.TextBlockSplitter
import com.occupantsearch.properties.PropertiesController
import com.occupantsearch.update.UpdateController
import com.occupantsearch.resource.ResourceReader
import com.occupantsearch.vk.VkNewsfeedSearcher
import com.occupantsearch.vk.PostController
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

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
        }
    )
}