package com.luthfirr.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.luthfirr.core.data.source.remote.response.ResultsItem
import com.luthfirr.core.databinding.ItemListPokemonBinding

class PokemonAdapter : RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {
    private val list = ArrayList<ResultsItem>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnitemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setList(pokemon: ArrayList<ResultsItem>) {
        list.clear()
        list.addAll(pokemon)
        notifyDataSetChanged()
    }

    inner class PokemonViewHolder(private val binding: ItemListPokemonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pokemon: ResultsItem) {
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(pokemon)
            }
            binding.apply {
                Glide.with(itemView)
                    .load(pokemon.url)
                    .into(ivPokemon)
                tvPokemonName.text = pokemon.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val view = ItemListPokemonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokemonViewHolder((view))
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickCallback {
        fun onItemClicked(data: ResultsItem)
    }
}