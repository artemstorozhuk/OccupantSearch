package com.occupantsearch.image

import org.koin.core.annotation.Single
import org.opencv.core.Mat
import org.opencv.core.MatOfRect
import org.opencv.core.Rect
import org.opencv.objdetect.CascadeClassifier
import java.nio.file.Files
import kotlin.concurrent.getOrSet
import kotlin.io.path.absolutePathString
import kotlin.io.path.deleteIfExists

@Single
class FaceDetector {
    private val cascadeClassifier = ThreadLocal<CascadeClassifier>()

    fun createCascadeClassifier(): CascadeClassifier {
        val temp = Files.createTempFile("lbpcascade_frontalface", ".xml")
        temp.deleteIfExists()
        FaceDetector::class.java.getResourceAsStream("/lbpcascade_frontalface.xml")!!.use {
            Files.copy(it, temp)
        }
        return CascadeClassifier(temp.absolutePathString())
            .also { temp.deleteIfExists() }
    }

    fun detect(src: Mat): List<Rect> = MatOfRect().let {
        cascadeClassifier.getOrSet { createCascadeClassifier() }
            .detectMultiScale(src, it)
        it.toList()
    }
}
