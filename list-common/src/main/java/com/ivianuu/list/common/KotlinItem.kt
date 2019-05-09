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

import com.ivianuu.list.Item

/**
 * A [Item] which uses [KotlinHolder]s
 */
abstract class KotlinItem(
    id: Any? = null,
    layoutRes: Int = -1,
    properties: Iterable<Any?>? = null
) : Item<KotlinHolder>(id, layoutRes, properties) {
    override fun createHolder(): KotlinHolder = KotlinHolder()
}