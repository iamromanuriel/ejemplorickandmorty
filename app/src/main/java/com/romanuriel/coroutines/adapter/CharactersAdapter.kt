package com.romanuriel.coroutines.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.romanuriel.coroutines.R
import com.romanuriel.coroutines.databinding.CardItemBinding
import com.romanuriel.coroutines.model.item.CharacterItem

class CharactersAdapter (
    private val listCharacters: List<CharacterItem?>): RecyclerView
    .Adapter<CharactersAdapter.ViewHolder>(){
    var onItemClick: ((CharacterItem?) -> Unit)? = null


    inner class ViewHolder(val view: View): RecyclerView.ViewHolder(view){
        val binding = CardItemBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_item, parent,false)
        return ViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listCharacters[position]
        //render card
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(item)
        }
        with(holder.binding){
            editName.text = item?.name
            Glide.with(holder.view.context)
                .load(item?.image)
                .into(imageCharacter)
        }
    }

    override fun getItemCount(): Int = listCharacters.size
}