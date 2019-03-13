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

import com.ivianuu.list.ListPlugins
import com.ivianuu.list.ModelController
import com.ivianuu.list.defaultBuildingExecutor
import com.ivianuu.list.defaultDiffingExecutor
import java.util.concurrent.Executor

/**
 * Simple [ModelController] which takes invokes [buildModels] to build models
 */
class SimpleModelController(
    diffingExecutor: Executor = ListPlugins.defaultDiffingExecutor,
    buildingExecutor: Executor = ListPlugins.defaultBuildingExecutor,
    private val buildModels: ModelController.() -> Unit
) : ModelController(diffingExecutor, buildingExecutor) {
    override fun buildModels() {
        buildModels.invoke(this)
    }
}

/**
 * Returns a [SimpleModelController] which uses [buildModels] to build it's models
 */
fun modelController(buildModels: ModelController.() -> Unit): SimpleModelController =
    SimpleModelController(buildModels = buildModels)