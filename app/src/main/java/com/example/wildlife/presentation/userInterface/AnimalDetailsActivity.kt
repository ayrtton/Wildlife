package com.example.wildlife.presentation.userInterface

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.wildlife.WildlifeApplication
import com.example.wildlife.domain.models.Animal
import com.example.wildlife.databinding.ActivityAnimalDetailsBinding
import com.example.wildlife.presentation.viewModels.AnimalsStorageViewModel
import com.example.wildlife.presentation.viewModels.AnimalsStorageViewModelFactory

class AnimalDetailsActivity : AppCompatActivity() {
    private val viewModel: AnimalsStorageViewModel by viewModels {
        AnimalsStorageViewModelFactory((application as WildlifeApplication).repository)
    }
    private lateinit var binding: ActivityAnimalDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimalDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        var animal: Animal? = null

        if(intent.hasExtra(MainActivity.ANIMAL_DETAILS)) {
            animal = intent.getParcelableExtra(MainActivity.ANIMAL_DETAILS) as Animal?
        }

        if(animal != null) {
            binding.apply {
                ivAnimal.load(animal.imgSrc) {
                    allowHardware(false)
                }
                tvSpecie.text = animal.specie
                tvScientificName.text = animal.scientificName
                tvDescription.text = animal.description
                if(animal.referenceLink == null) tvReference.visibility = View.GONE
                tvReferenceLink.text = animal.referenceLink
                tvReferenceLink.setOnClickListener {
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(tvReferenceLink.text.toString()))
                    startActivity(browserIntent)
                }
                fabButton.setOnClickListener {
                    viewModel.insert(animal)
                    setResult(Activity.RESULT_OK)
                    finish()
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
