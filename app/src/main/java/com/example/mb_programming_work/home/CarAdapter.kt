package com.example.mb_programming_work.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mb_programming_work.R
import com.example.mb_programming_work.databinding.CarItemLayoutBinding

// ListAdapter უზრუნველყოფს სიის ოპტიმიზებულ განახლებას DiffUtil-ის მეშვეობით.
class CarAdapter(private val onClick: (Car) -> Unit) :
    ListAdapter<Car, CarAdapter.CarViewHolder>(CarDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val binding =
            CarItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CarViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        holder.bind()
    }

    inner class CarViewHolder(val binding: CarItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        // მონაცემების დაკავშირება UI ელემენტებთან
        fun bind() = with(binding) {
            val item = getItem(adapterPosition)
            title.text = item.title
            cost.text = root.context.getString(R.string.cost, item.cost)
            photo.setImageResource(item.photoResId)
            root.setOnClickListener {
                onClick(item)
            }
        }
    }

    // DiffUtil გამოიყენება ძველი და ახალი სიის შესადარებლად და მხოლოდ განსხვავებების დასახატად.
    class CarDiffCallback : DiffUtil.ItemCallback<Car>() {
        override fun areItemsTheSame(oldItem: Car, newItem: Car) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Car, newItem: Car) = oldItem == newItem
    }
}