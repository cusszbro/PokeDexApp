package com.luthfirr.core.data.source.remote.response

data class PokemonResponse(
	val next: String,
	val previous: Any,
	val count: Int,
	val results: ArrayList<ResultsItem>
)

