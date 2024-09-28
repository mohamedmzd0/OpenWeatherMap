package com.mohamed.weatherforecastapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mohamed.domain.entity.WeatherEntity
import com.mohamed.utils.ext.load
import com.mohamed.weatherforecastapp.databinding.ItemWeatherMainBinding

class WeatherAdapter :
    ListAdapter<WeatherEntity, WeatherAdapter.ViewHolder>(WeatherDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemWeatherMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun clear() {
        submitList(emptyList())
    }

    class ViewHolder(private val binding: ItemWeatherMainBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: WeatherEntity) {
            binding.apply {
                txtDateValue.text = item.formattedDate
                txtDescription.text = item.description
                imageIcon.load(item.icon)
            }
        }
    }

    private class WeatherDiffCallback : DiffUtil.ItemCallback<WeatherEntity>() {
        override fun areItemsTheSame(oldItem: WeatherEntity, newItem: WeatherEntity): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: WeatherEntity, newItem: WeatherEntity): Boolean {
            return oldItem == newItem
        }
    }
}