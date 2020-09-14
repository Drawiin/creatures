package com.raywenderlich.android.creatures.ui

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.raywenderlich.android.creatures.R
import com.raywenderlich.android.creatures.app.inflate
import com.raywenderlich.android.creatures.model.Creature
import com.raywenderlich.android.creatures.model.CreatureStore
import kotlinx.android.synthetic.main.creature_list_item.view.img
import kotlinx.android.synthetic.main.creature_list_item_with_food.view.*

class CreaturesWithFoodAdapter(private val creatures: MutableList<Creature>) : RecyclerView.Adapter<CreaturesWithFoodAdapter.ViewHolder>() {
    private val viewPool = RecyclerView.RecycledViewPool()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var creature: Creature
        private val adapter = FoodsAdapter(mutableListOf())

        init {
            itemView.setOnClickListener { onItemClicked(it) }
        }

        private fun setupFoods() {
            itemView.nested_foods_list.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            itemView.nested_foods_list.adapter = adapter
            val foods = CreatureStore.getCreatureFoods(creature)
            adapter.updateFoods(foods)
        }

        fun bind(creature: Creature) {
            this.creature = creature
            val context = itemView.context
            itemView.apply {
                img.setImageResource(context.resources.getIdentifier(creature.uri, null, context.packageName))
            }
            setupFoods()
        }

        private fun onItemClicked(view: View?) {
            view?.let {
                val context = it.context
                val intent = CreatureActivity.newIntent(context, creature.id)
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.creature_list_item_with_food)).apply {
            itemView.nested_foods_list.setRecycledViewPool(viewPool)
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(creatures[position])
    }

    override fun getItemCount() = creatures.size
}