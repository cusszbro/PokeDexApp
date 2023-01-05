package com.luthfirr.pokedexapp.mylist

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.luthfirr.core.data.source.local.entity.PokemonEntity
import com.luthfirr.core.data.source.remote.response.ResultsItem
import com.luthfirr.core.ui.PokemonAdapter
import com.luthfirr.pokedexapp.databinding.ActivityMyPokemonListBinding
import com.luthfirr.pokedexapp.detail.PokemonDetailActivity

class MyPokemonListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyPokemonListBinding
    private lateinit var adapter: PokemonAdapter
    private val viewModel by viewModels<MyPokemonListViewmodel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPokemonListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()

        adapter = PokemonAdapter()
        adapter.notifyDataSetChanged()

        adapter.setOnitemClickCallback(object : PokemonAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ResultsItem) {
                Intent(this@MyPokemonListActivity, PokemonDetailActivity::class.java).also {
                    it.putExtra(PokemonDetailActivity.EXTRA_NAME, data.name)
                    startActivity(it)
                }
            }
        })

        binding.apply {
            rvMyPokemon.setHasFixedSize(true)
            rvMyPokemon.layoutManager = LinearLayoutManager(this@MyPokemonListActivity)
            rvMyPokemon.adapter = adapter
        }

        viewModel.getMyPokemonList()?.observe(this) {
            showLoading(true)
            if (it != null) {
                val myPokemonList = mapList(it)
                adapter.setList(myPokemonList)
            }
        }
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun mapList(pokemon: List<PokemonEntity>): ArrayList<ResultsItem> {
        val listUser = ArrayList<ResultsItem>()
        for (poke in pokemon) {
            val userMapped = ResultsItem (
                poke.name,
                poke.url
            )
            listUser.add(userMapped)
        }
        showLoading(false)
        return listUser

    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE

    }
}