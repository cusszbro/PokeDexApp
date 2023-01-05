package com.luthfirr.pokedexapp.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.luthfirr.pokedexapp.databinding.ActivityPokemonDetailBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PokemonDetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityPokemonDetailBinding
    private val viewModel by viewModels<PokemonDetailViewModel>()
    private lateinit var url : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokemonDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra(EXTRA_NAME)
        name?.let { viewModel.setPokemonDetail(it) }

        viewModel.getPokemonDetail().observe(this) {
            if (it != null) {
                binding.apply {
                    Glide.with(this@PokemonDetailActivity)
                        .load(it.sprites?.frontDefault)
                        .into(ivPokemonDetail)
                    url = it.sprites?.frontDefault.toString()
                    val pokeName = it.name
                    tvPokemonNameDetail.text = pokeName
                    tvType.text = it.types?.get(0)?.type!!.name
                    val height = it.height?.times(10)
                    tvHeight.text = "$height cm"
                    val weight = it.weight?.div(10)
                    tvWeight.text = "$weight kg"
                    var id = it.id!!

                    var _isChecked = false
                    CoroutineScope(Dispatchers.IO).launch {
                        val count = viewModel.checkPokemon(id)
                        withContext(Dispatchers.Main) {
                            if (count != null) {
                                if (count > 0) {
                                    binding.togglePokeball.isChecked = true
                                    _isChecked = true
                                }
                                else {
                                    binding.togglePokeball.isChecked = false
                                    _isChecked = false
                                }
                            }
                        }
                    }

                    /**
                     * catch the pokemon with success probability 50%
                     */
                    binding.togglePokeball.setOnClickListener {
//                        _isChecked = !_isChecked
                        val randomCatch = (1..2).random()
                        if (randomCatch == 2) {
                            pokeName?.let { it1 -> viewModel.addToMyList(id, it1, url) }
                            binding.togglePokeball.isChecked = !_isChecked
                            Toast.makeText(
                                this@PokemonDetailActivity,
                                "congratulations, you caught it",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                this@PokemonDetailActivity,
                                "Sorry, fail catch the pokemon",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }

    companion object {
        const val EXTRA_NAME = "extra name"
    }
}