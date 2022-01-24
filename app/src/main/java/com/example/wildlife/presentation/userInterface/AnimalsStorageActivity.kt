package com.example.wildlife.presentation.userInterface

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.wildlife.R
import com.example.wildlife.WildlifeApplication
import com.example.wildlife.databinding.ActivityAnimalsStorageBinding
import com.example.wildlife.domain.models.Animal
import com.example.wildlife.presentation.adapters.StoredAnimalsAdapter
import com.example.wildlife.presentation.viewModels.*

class AnimalsStorageActivity : AppCompatActivity() {
    private val viewModel: AnimalsStorageViewModel by viewModels {
        AnimalsStorageViewModelFactory((application as WildlifeApplication).repository)
    }
    private lateinit var binding: ActivityAnimalsStorageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimalsStorageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        animalsObserver()
        checkStatus()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun animalsObserver() {
        viewModel.storedAnimals.observe(this, { list ->
            if (list.isNotEmpty()) {
                setRecyclerView(list)
            }
        })
    }

    private fun setRecyclerView(list: List<Animal>) {
        if(list.isNotEmpty()) {
            val animalsAdapter = StoredAnimalsAdapter(this, list)
            binding.rvAnimals.setHasFixedSize(true)
            binding.rvAnimals.adapter = animalsAdapter

            animalsAdapter.listenerDelete = {
                viewModel.delete(it)
            }

            animalsAdapter.setOnClickListener(object: StoredAnimalsAdapter.OnClickListener {
                override fun onClick(position: Int, model: Animal) {
                    val intent = Intent(this@AnimalsStorageActivity, AnimalDetailsActivity::class.java)
                    intent.putExtra(ANIMAL_DETAILS, model)
                    startActivity(intent)
                }
            })
        }
    }

    private fun checkStatus() {
        viewModel.status.observe(this, { status ->
            when (status) {
                WildlifeDBStatus.LOADING -> {
                    binding.ivStatus.visibility = View.VISIBLE
                    binding.ivStatus.setImageResource(R.drawable.loading_animation)
                }

                WildlifeDBStatus.ERROR -> {
                    binding.ivStatus.visibility = View.VISIBLE
                    binding.ivStatus.setImageResource(R.drawable.ic_connection_error)
                }

                WildlifeDBStatus.DONE -> binding.ivStatus.visibility = View.GONE

                else -> binding.ivStatus.visibility = View.GONE
            }
        })
    }

    companion object{
        internal const val ANIMAL_DETAILS = "animal details"
    }
}