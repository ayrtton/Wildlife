package com.example.wildlife.presentation.userInterface

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import com.example.wildlife.R
import com.example.wildlife.WildlifeApplication
import com.example.wildlife.presentation.adapters.AnimalsAdapter
import com.example.wildlife.domain.models.Animal
import com.example.wildlife.data.web.WildlifeAPIFilter
import com.example.wildlife.databinding.ActivityMainBinding
import com.example.wildlife.presentation.viewModels.MainViewModel
import com.example.wildlife.presentation.viewModels.MainViewModelFactory
import com.example.wildlife.presentation.viewModels.WildlifeAPIStatus

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory((application as WildlifeApplication).repository)
    }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        animalsObserver()
        checkStatus()

        binding.fabButton.setOnClickListener {
            startActivity(Intent(this, AnimalsStorageActivity::class.java))
        }
    }

    private fun animalsObserver() {
        viewModel.animals.observe(this, { list ->
            if (list.isNotEmpty()) {
                setRecyclerView(list)
            }
        })
    }

    private fun setRecyclerView(list: List<Animal>) {
        val animalsAdapter = AnimalsAdapter(this, list)
        binding.rvAnimals.setHasFixedSize(true)
        binding.rvAnimals.adapter = animalsAdapter

        animalsAdapter.setOnClickListener(object: AnimalsAdapter.OnClickListener {
            override fun onClick(position: Int, model: Animal) {
                val intent = Intent(this@MainActivity, AnimalDetailsActivity::class.java)
                intent.putExtra(ANIMAL_DETAILS, model)
                startActivity(intent)
            }
        })
    }

    private fun checkStatus() {
        viewModel.status.observe(this, { status ->
            when (status) {
                WildlifeAPIStatus.LOADING -> {
                    binding.ivStatus.visibility = View.VISIBLE
                    binding.ivStatus.setImageResource(R.drawable.loading_animation)
                }

                WildlifeAPIStatus.ERROR -> {
                    binding.ivStatus.visibility = View.VISIBLE
                    binding.ivStatus.setImageResource(R.drawable.ic_connection_error)
                }

                WildlifeAPIStatus.DONE -> binding.ivStatus.visibility = View.GONE

                else -> binding.ivStatus.visibility = View.GONE
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.filter_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        viewModel.updateFilter(
            when (item.itemId) {
                R.id.show_mammals -> WildlifeAPIFilter.SHOW_MAMMALS
                R.id.show_birds -> WildlifeAPIFilter.SHOW_BIRDS
                R.id.show_reptiles -> WildlifeAPIFilter.SHOW_REPTILES
                R.id.show_amphibians -> WildlifeAPIFilter.SHOW_AMPHIBIANS
                R.id.show_fish -> WildlifeAPIFilter.SHOW_FISH
                R.id.show_invertebrates -> WildlifeAPIFilter.SHOW_INVERTEBRATES
                else -> WildlifeAPIFilter.SHOW_ALL
            }
        )
        return true
    }

    companion object{
        internal const val ANIMAL_DETAILS = "animal details"
    }
}