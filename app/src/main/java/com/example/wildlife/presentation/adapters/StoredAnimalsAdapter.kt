package com.example.wildlife.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.wildlife.R
import com.example.wildlife.databinding.ItemStoredAnimalBinding
import com.example.wildlife.domain.models.Animal

class StoredAnimalsAdapter(
    private val context: Context,
    private var list: List<Animal>
): RecyclerView.Adapter<StoredAnimalsAdapter.StoredAnimalsViewHolder>() {
    private var onClickListener: OnClickListener? = null
    var listenerDelete: (Animal) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoredAnimalsViewHolder {
        return StoredAnimalsViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_stored_animal, parent, false)
        )
    }

    override fun onBindViewHolder(holder: StoredAnimalsViewHolder, position: Int) {
        val animal = list[position]

        holder.binding.apply {
            ivAnimal.load(animal.imgSrc) {
                allowHardware(false)
                crossfade(true)
                crossfade(1000 - position * 150)
            }
            tvSpecie.text = animal.specie
            btnOptionsMenu.setOnClickListener {
                showMenu(holder.binding, animal)
            }
        }
        holder.itemView.setOnClickListener {
            if(onClickListener != null) {
                onClickListener!!.onClick(position, animal)
            }
        }
    }

    private fun showMenu(binding: ItemStoredAnimalBinding, animal: Animal) {
        val btnMenu = binding.btnOptionsMenu
        val popupMenu = PopupMenu(btnMenu.context, btnMenu)

        popupMenu.menuInflater.inflate(R.menu.options_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.action_delete -> listenerDelete(animal)
            }
            return@setOnMenuItemClickListener true
        }
        popupMenu.show()
    }

    override fun getItemCount(): Int = list.size

    class StoredAnimalsViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val binding: ItemStoredAnimalBinding = ItemStoredAnimalBinding.bind(view)
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnClickListener {
        fun onClick(position: Int, model: Animal)
    }
}