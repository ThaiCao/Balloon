/*
 * Copyright (C) 2019 skydoves
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.skydoves.balloon.extensions

import android.graphics.drawable.Drawable
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.text.HtmlCompat
import com.skydoves.balloon.IconForm
import com.skydoves.balloon.IconGravity
import com.skydoves.balloon.NO_INT_VALUE
import com.skydoves.balloon.TextForm
import com.skydoves.balloon.vectortext.VectorTextView
import com.skydoves.balloon.vectortext.VectorTextViewParams

/** applies text form attributes to a TextView instance. */
@Suppress("unused")
internal fun TextView.applyTextForm(textForm: TextForm) {
  text = when (textForm.textIsHtml) {
    true -> fromHtml(textForm.text.toString())
    false -> textForm.text
  }
  textSize = textForm.textSize
  gravity = textForm.textGravity
  setTextColor(textForm.textColor)
  textForm.textTypeface?.let { typeface = it } ?: setTypeface(typeface, textForm.textStyle)
  textForm.movementMethod?.let { movementMethod = it }
}

private fun fromHtml(text: String): Spanned? {
  return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
    Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY)
  } else {
    HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY)
  }
}

/** applies icon form attributes to a ImageView instance. */
internal fun VectorTextView.applyIconForm(iconForm: IconForm) {
  iconForm.drawable?.let {
    drawableTextViewParams = VectorTextViewParams(
      iconSize = iconForm.iconSize,
      compoundDrawablePadding = iconForm.iconSpace,
      tintColor = iconForm.iconColor.takeIf { it != NO_INT_VALUE }
    ).apply {
      when (iconForm.iconGravity) {
        IconGravity.LEFT -> {
          drawableLeft = iconForm.drawable
          drawableLeftRes = iconForm.drawableRes
        }
        IconGravity.TOP -> {
          drawableTop = iconForm.drawable
          drawableTopRes = iconForm.drawableRes
        }
        IconGravity.BOTTOM -> {
          drawableBottom = iconForm.drawable
          drawableBottomRes = iconForm.drawableRes
        }
        IconGravity.RIGHT -> {
          drawableRight = iconForm.drawable
          drawableRightRes = iconForm.drawableRes
        }
      }
    }
  }
}

internal fun TextView.applyDrawable(vectorTextViewParams: VectorTextViewParams) {
  val height = vectorTextViewParams.iconSize
    ?: vectorTextViewParams.heightRes?.let { context.resources.getDimensionPixelSize(it) }
    ?: vectorTextViewParams.squareSizeRes?.let { context.resources.getDimensionPixelSize(it) }

  val width = vectorTextViewParams.iconSize
    ?: vectorTextViewParams.widthRes?.let { context.resources.getDimensionPixelSize(it) }
    ?: vectorTextViewParams.squareSizeRes?.let { context.resources.getDimensionPixelSize(it) }

  val drawableLeft: Drawable? =
    vectorTextViewParams.drawableLeft ?: vectorTextViewParams.drawableLeftRes?.let {
      AppCompatResources.getDrawable(context, it)
    }
  drawableLeft?.tint(vectorTextViewParams.tintColor)?.resize(context, width, height)

  val drawableRight: Drawable? =
    vectorTextViewParams.drawableRight ?: vectorTextViewParams.drawableRightRes?.let {
      AppCompatResources.getDrawable(context, it)
    }
  drawableRight?.tint(vectorTextViewParams.tintColor)?.resize(context, width, height)

  val drawableBottom: Drawable? =
    vectorTextViewParams.drawableBottom ?: vectorTextViewParams.drawableBottomRes?.let {
      AppCompatResources.getDrawable(context, it)
    }
  drawableBottom?.tint(vectorTextViewParams.tintColor)?.resize(context, width, height)

  val drawableTop: Drawable? =
    vectorTextViewParams.drawableTop ?: vectorTextViewParams.drawableTopRes?.let {
      AppCompatResources.getDrawable(context, it)
    }
  drawableTop?.tint(vectorTextViewParams.tintColor)?.resize(context, width, height)

  setCompoundDrawablesWithIntrinsicBounds(drawableLeft, drawableTop, drawableRight, drawableBottom)

  vectorTextViewParams.compoundDrawablePadding?.let { compoundDrawablePadding = it }
    ?: vectorTextViewParams.compoundDrawablePaddingRes?.let {
      compoundDrawablePadding = context.resources.getDimensionPixelSize(it)
    }
}
