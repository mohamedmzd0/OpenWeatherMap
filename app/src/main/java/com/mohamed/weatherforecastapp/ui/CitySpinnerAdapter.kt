package com.mohamed.weatherforecastapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.mohamed.domain.entity.CityEntity

class CitySpinnerAdapter : BaseAdapter() {
    private var items: List<CityEntity> = emptyList()

    fun setItems(items: List<CityEntity>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun getCount() = items.size

    override fun getItem(position: Int) = items[position]
    override fun getItemId(position: Int) = position.toLong()

    private class ViewHolder(view: View) {
        val textView: TextView = view.findViewById(android.R.id.text1)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var viewHolder: ViewHolder

        val view = convertView ?: run {
            val inflater = LayoutInflater.from(parent?.context)
            val newView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false)
            viewHolder = ViewHolder(newView)
            viewHolder.textView.text = items[position].name
            newView.tag = viewHolder
            newView
        }.also {
            viewHolder = it.tag as ViewHolder
            viewHolder.textView.text = items[position].name
        }

        return view
    }


    fun clear() {
        setItems(emptyList())
    }

}