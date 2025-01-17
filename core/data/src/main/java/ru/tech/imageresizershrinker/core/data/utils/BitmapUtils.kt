/*
 * ImageToolbox is an image editor for android
 * Copyright (c) 2024 T8RIN (Malik Mukhametzyanov)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * You should have received a copy of the Apache License
 * along with this program.  If not, see <http://www.apache.org/licenses/LICENSE-2.0>.
 */

package ru.tech.imageresizershrinker.core.data.utils

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.compose.ui.graphics.ImageBitmap
import androidx.core.graphics.drawable.toBitmap

private val possibleConfigs = mutableListOf<Bitmap.Config>().apply {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        add(Bitmap.Config.RGBA_1010102)
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        add(Bitmap.Config.RGBA_F16)
    }
    add(Bitmap.Config.ARGB_8888)
    add(Bitmap.Config.RGB_565)
}

fun getSuitableConfig(
    image: Bitmap? = null
): Bitmap.Config = image?.config?.takeIf {
    it in possibleConfigs
} ?: Bitmap.Config.ARGB_8888

fun Bitmap.toSoftware(): Bitmap = copy(getSuitableConfig(this), false) ?: this

val Bitmap.aspectRatio: Float get() = width / height.toFloat()

val ImageBitmap.aspectRatio: Float get() = width / height.toFloat()

val Drawable.aspectRatio: Float get() = intrinsicWidth / intrinsicHeight.toFloat()

val Bitmap.safeAspectRatio: Float
    get() = aspectRatio
        .coerceAtLeast(0.005f)
        .coerceAtMost(1000f)

val Bitmap.safeConfig: Bitmap.Config
    get() = config

val ImageBitmap.safeAspectRatio: Float
    get() = aspectRatio
        .coerceAtLeast(0.005f)
        .coerceAtMost(1000f)

val Drawable.safeAspectRatio: Float
    get() = aspectRatio
        .coerceAtLeast(0.005f)
        .coerceAtMost(1000f)

fun Drawable.toBitmap(): Bitmap = toBitmap(config = getSuitableConfig())