package com.ivianuu.list.sample

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ivianuu.list.addItemListener
import com.ivianuu.list.common.ItemTouchHelper
import com.ivianuu.list.common.itemController
import kotlinx.android.synthetic.main.activity_main.list
import java.util.*

class MainActivity : AppCompatActivity() {

    private val titles = mutableListOf<String>()

    private var countItemCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        titles.addAll((1..100).map { "Title: $it" })

        list.layoutManager = LinearLayoutManager(this)

        val controller = itemController {
            ButtonItem {
                buttonText = "Shuffle"

                onClick {
                    titles.shuffle()
                    requestItemBuild()
                }
            }

            CountItem {
                count = countItemCount
                onIncClick {
                    countItemCount++
                    requestItemBuild()
                }
                onDecClick {
                    countItemCount--
                    requestItemBuild()
                }
            }

            ButtonItem {
                buttonText = "Add Random"
                onClick {
                    titles.add(0, "Random ${UUID.randomUUID()}")
                    requestItemBuild()
                }
            }

            titles.forEach { title ->
                SimpleItem {
                    text = title
                    onClick {
                        Toast.makeText(
                            this@MainActivity, "Clicked item: $title",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        controller.adapter.addItemListener(
            preBind = { item, holder ->
                println("pre bind $item $holder view type is ${item.viewType}")
            },
            postUnbind = { item, holder ->
                println("post unbind $item $holder view type is ${item.viewType}")
            }
        )

        list.adapter = controller.adapter

        ItemTouchHelper.dragging(list)
            .vertical()
            .target(SimpleItem::class)
            .callbacks(
                object : ItemTouchHelper.DragCallbacks<SimpleItem>() {
                    override fun onItemMoved(
                        fromPosition: Int,
                        toPosition: Int,
                        itemBeingMoved: SimpleItem,
                        itemView: View
                    ) {
                        super.onItemMoved(fromPosition, toPosition, itemBeingMoved, itemView)
                        Collections.swap(titles, fromPosition - 3, toPosition - 3)
                        controller.requestItemBuild()
                    }
                }
            )

        ItemTouchHelper.swiping(list)
            .leftAndRight()
            .target(SimpleItem::class)
            .callbacks(
                object : ItemTouchHelper.SwipeCallbacks<SimpleItem>() {
                    override fun onSwipeCompleted(
                        item: SimpleItem,
                        itemView: View,
                        position: Int,
                        direction: Int
                    ) {
                        super.onSwipeCompleted(item, itemView, position, direction)
                        titles.removeAt(position - 3)
                        controller.requestItemBuild()
                    }
                }
            )

        controller.requestItemBuild()
    }
}