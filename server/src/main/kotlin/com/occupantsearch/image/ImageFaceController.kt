package com.occupantsearch.image

import com.occupantsearch.db.Database
import com.occupantsearch.lang.retryUntilSuccess
import com.occupantsearch.vk.uniqueId
import com.vk.api.sdk.objects.wall.WallpostFull
import nu.pattern.OpenCV
import org.koin.core.annotation.Single
import org.opencv.imgcodecs.Imgcodecs
import org.slf4j.LoggerFactory
import kotlin.io.path.absolutePathString
import kotlin.io.path.deleteIfExists

@Single
class ImageFaceController(
    database: Database,
    private val faceDetector: FaceDetector,
    private val imageDownloader: ImageDownloader,
) {
    companion object {
        init {
            OpenCV.loadLocally()
        }
    }

    private val logger = LoggerFactory.getLogger(ImageFaceController::class.java)
    private val postsRepository = database.load(WallpostFull::class.java)
    private val faceDetectionsRepository = database[FaceDetections::class.java]

    fun processNewPosts() = faceDetectionsRepository.getAll().keys.let { keys ->
        postsRepository.getAll().entries
            .stream()
            .parallel()
            .filter { it.key !in keys }
            .forEach { detectAndSaveFaces(it.value) }
    }

    operator fun get(wallpostFull: WallpostFull) =
        (faceDetectionsRepository[wallpostFull.uniqueId]?.faceDetections ?: detectAndSaveFaces(wallpostFull))!!
            .filter { it.result.isNotEmpty() }
            .map { it.uri }

    fun detectAndSaveFaces(wallpostFull: WallpostFull) = retryUntilSuccess(
        lambda = {
            detectFaces(wallpostFull).also {
                faceDetectionsRepository[wallpostFull.uniqueId] = FaceDetections(it)
            }
        },
        onError = {
            logger.info(it.message, it)
            false
        }
    )

    fun detectFaces(wallpostFull: WallpostFull) = imageDownloader.download(wallpostFull)
        .map { entry ->
            try {
                val path = entry.value.absolutePathString()
                logger.info("Processing image $path")
                val src = Imgcodecs.imread(path)
                val faceDetection = FaceDetection(
                    uri = entry.key.toString(),
                    size = src.size(),
                    result = faceDetector.detect(src)
                )
                src.release()
                faceDetection
            } finally {
                entry.value.deleteIfExists()
            }
        }
}
