package com.naldana.pokedex.ui.pokedex

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.naldana.pokedex.R
import com.naldana.pokedex.databinding.FragmentPokedexBinding
import com.naldana.pokedex.repository.PokemonRepository

/**
 * Pantalla de busqueda de pokemons
 * Use the [PokedexFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PokedexFragment : Fragment() {

    private val pokedexFactory by lazy {
        val repository = PokemonRepository()
        PokedexViewModelFactory(repository)
    }

    private val pokedexViewModel: PokedexViewModel by viewModels {
        pokedexFactory
    }

    private var _binding: FragmentPokedexBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPokedexBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = pokedexViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        pokedexViewModel.pokemon.observe(viewLifecycleOwner) { pokemon ->
            val display = binding.displayPokemon
            if (pokemon.name.isNotEmpty()) {
                display.text =
                    getString(R.string.pokemon_info, pokemon.id, pokemon.name)
            } else {
                display.text = getString(R.string.hint_pokemon)
            }
        }

        pokedexViewModel.error.observe(viewLifecycleOwner) { error ->
            if (error == null) {
                binding.searchTextLayout.error = null
            } else {
                binding.searchTextLayout.error = getString(error)
            }

        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment PokedexFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PokedexFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}