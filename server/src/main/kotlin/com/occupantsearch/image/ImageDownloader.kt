package com.occupantsearch.image

import com.occupantsearch.vk.getImageId
import com.occupantsearch.vk.getImageUris
import com.vk.api.sdk.objects.wall.WallpostFull
import org.koin.core.annotation.Single
import java.net.URI
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.absolute
import kotlin.io.path.deleteIfExists

@Single
class ImageDownloader {
    fun download(wallpostFull: WallpostFull): Map<URI, Path> = wallpostFull.getImageUris()
        .associateWith { uri ->
            val imageId = wallpostFull.getImageId(uri)
            val imageFile = Files.createTempFile(imageId, "")
            imageFile.deleteIfExists()
            uri.toURL().openStream().use {
                Files.copy(it, imageFile.absolute())
            }
            imageFile
        }
}
