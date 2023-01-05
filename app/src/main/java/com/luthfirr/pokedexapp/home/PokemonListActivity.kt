package com.luthfirr.pokedexapp.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.luthfirr.core.data.source.remote.response.ResultsItem
import com.luthfirr.core.ui.PokemonAdapter
import com.luthfirr.pokedexapp.databinding.ActivityPokemonListBinding
import com.luthfirr.pokedexapp.detail.PokemonDetailActivity
import com.luthfirr.pokedexapp.mylist.MyPokemonListActivity

class PokemonListActivity : AppCompatActivity() {

    private lateinit var binding : ActivityPokemonListBinding
    private lateinit var adapter : PokemonAdapter
    private val viewModel by viewModels<PokemonListViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokemonListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = PokemonAdapter()
        adapter.notifyDataSetChanged()

        adapter.setOnitemClickCallback(object : PokemonAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ResultsItem) {
                Intent(this@PokemonListActivity, PokemonDetailActivity::class.java).also {
                    it.putExtra(PokemonDetailActivity.EXTRA_NAME, data.name)
                    startActivity(it)
                }
            }
        })

        binding.apply {
            rvPokemon.layoutManager = LinearLayoutManager(this@PokemonListActivity)
            rvPokemon.setHasFixedSize(true)
            rvPokemon.adapter = adapter
        }

        setPokemonList()
        moveToMyPokemonList()
    }

    private fun setPokemonList() {
        viewModel.setPokemonList()
        viewModel.getPokemonList().observe(this, {
            if (it != null){
                adapter.setList(it)
            } else {
                Toast.makeText(this, "Sorry, please Try Again Later...", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun moveToMyPokemonList() {
        binding.fabMyList.setOnClickListener {
            startActivity(Intent(this, MyPokemonListActivity::class.java))
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}