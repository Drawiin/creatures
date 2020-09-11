package com.raywenderlich.android.creatures.ui

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raywenderlich.android.creatures.R
import com.raywenderlich.android.creatures.app.inflate
import com.raywenderlich.android.creatures.model.Creature
import kotlinx.android.synthetic.main.creature_list_item.view.*

class CreaturesAdapter(private val creatures: MutableList<Creature>) : RecyclerView.Adapter<CreaturesAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        private lateinit var creature: Creature

        init {
            itemView.setOnClickListener { onItemClicked(it) }
        }


        fun bind(creature: Creature){
            this.creature = creature
            val context = itemView.context
            itemView.apply {
                img.setImageResource(context.resources.getIdentifier(creature.uri, null, context.packageName))
                fullName.text = creature.fullName
                nickname.text = creature.nickname
            }
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
        return ViewHolder(parent.inflate(R.layout.creature_list_item))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(creatures[position])
    }

    override fun getItemCount() = creatures.size

    fun updateCreatures(creatures: List<Creature>){
        this.creatures.clear()
        this.creatures.addAll(creatures)
        notifyDataSetChanged()
    }
}