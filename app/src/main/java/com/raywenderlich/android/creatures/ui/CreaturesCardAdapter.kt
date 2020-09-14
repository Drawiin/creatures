package com.raywenderlich.android.creatures.ui

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import com.raywenderlich.android.creatures.R
import com.raywenderlich.android.creatures.app.inflate
import com.raywenderlich.android.creatures.model.Creature
import kotlinx.android.synthetic.main.creature_list_item.view.img
import kotlinx.android.synthetic.main.creature_list_item.view.nickname
import kotlinx.android.synthetic.main.list_item_creature_card.view.*

class CreaturesCardAdapter(private val creatures: MutableList<Creature>) : RecyclerView.Adapter<CreaturesCardAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var creature: Creature

        init {
            itemView.setOnClickListener { onItemClicked(it) }
        }

        fun bind(creature: Creature) {
            this.creature = creature
            val context = itemView.context
            val imgRes = context.resources.getIdentifier(creature.uri, null, context.packageName)
            itemView.apply {
                img.setImageResource(imgRes)
                nickname.text = creature.nickname
            }
            setBackgroundColor(context, imgRes)
        }

        private fun onItemClicked(view: View?) {
            view?.let {
                val context = it.context
                val intent = CreatureActivity.newIntent(context, creature.id)
                context.startActivity(intent)
            }
        }

        private fun setBackgroundColor(context: Context, imgResource: Int) {
            val img = BitmapFactory.decodeResource(context.resources, imgResource)
            Palette.from(img).generate { palette ->
                palette?.let {
                    val background = it.getDominantColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))
                    itemView.creature_card.setCardBackgroundColor(background)
                    itemView.nickname_container.setBackgroundColor(background)
                    val textColor = if (isColorDark(background)) Color.WHITE else Color.BLACK
                    itemView.nickname.setTextColor(textColor)
                }
            }
        }

        private fun isColorDark(color: Int): Boolean {
            val darkness = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255
            return darkness >= 0.5
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.list_item_creature_card))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(creatures[position])
    }

    override fun getItemCount() = creatures.size
}