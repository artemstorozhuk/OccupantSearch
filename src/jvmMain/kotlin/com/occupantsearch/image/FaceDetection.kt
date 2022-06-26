package com.occupantsearch.image

import org.opencv.core.Rect
import org.opencv.core.Size

data class FaceDetection(
    val uri: String,
    val size: Size,
    val result: List<Rect>,
)