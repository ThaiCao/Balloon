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

package com.skydoves.balloon.compose

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.skydoves.balloon.Balloon

@DslMarker
internal annotation class BalloonDsl

/**
 * Create and remember [Balloon.Builder].
 *
 * @param context context to create balloon.
 * @param block a receiver lambda that will be applied with [Balloon.Builder].
 */
@Composable
@BalloonDsl
public fun rememberBalloonBuilder(
  context: Context = LocalContext.current,
  block: Balloon.Builder.() -> Unit
): Balloon.Builder = remember {
  Balloon.Builder(context).apply(block)
}
