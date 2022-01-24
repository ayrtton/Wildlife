package com.example.wildlife.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.wildlife.R
import com.example.wildlife.databinding.ItemAnimalBinding
import com.example.wildlife.domain.models.Animal

class AnimalsAdapter(
    private val context: Context,
    private var list: List<Animal>
): RecyclerView.Adapter<AnimalsAdapter.AnimalsViewHolder>() {
    private var onClickListener: OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalsViewHolder {
        return AnimalsViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_animal, parent, false)
        )
    }

    override fun onBindViewHolder(holder: AnimalsViewHolder, position: Int) {
        val animal = list[position]

        holder.binding.apply {
            ivAnimal.load(animal.imgSrc) {
                allowHardware(false)
                crossfade(true)
                crossfade(1000 - position * 150)
            }
            tvSpecie.text = animal.specie
        }
        holder.itemView.setOnClickListener {
            if(onClickListener != null) {
                onClickListener!!.onClick(position, animal)
            }
        }
    }

    override fun getItemCount(): Int = list.size

    class AnimalsViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val binding: ItemAnimalBinding = ItemAnimalBinding.bind(view)
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnClickListener {
        fun onClick(position: Int, model: Animal)
    }
}