/*
 * Copyright 2018 Manuel Wrage
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

package com.ivianuu.list.common

import android.view.View
import com.ivianuu.list.Holder
import com.ivianuu.list.Item
import com.ivianuu.list.ItemEvents
import com.ivianuu.list.addListener

/**
 * [View] on click listener
 */
typealias OnClickListener = (View) -> Unit

/**
 * Returns [ItemEvents] for view clicks
 */
fun <I : Item<H>, H : Holder> I.clicks(
    viewProvider: (H) -> View = { it.view }
): Lazy<ItemEvents<OnClickListener>> = lazy(LazyThreadSafetyMode.NONE) {
    ItemClicks(this, viewProvider)
}

/**
 * Returns [ItemEvents] for view clicks
 */
fun <I : Item<H>, H : Holder> I.clicks(
    viewId: Int
): Lazy<ItemEvents<OnClickListener>> = lazy(LazyThreadSafetyMode.NONE) {
    ItemClicks(this) { it.view.findViewById(viewId) }
}

private class ItemClicks<T : Item<H>, H : Holder>(
    private val item: T,
    private val viewProvider: (H) -> View
) : ItemEvents<OnClickListener> {

    private var _callback: ((view: View) -> Unit)? = null

    init {
        item.addListener(
            postBind = { _, holder -> viewProvider(holder as H).setOnClickListener(_callback) },
            preUnbind = { _, holder -> viewProvider(holder as H).setOnClickListener(null) }
        )
    }

    override fun setCallback(callback: (View) -> Unit) {
        _callback = callback
    }
}