package com.customcamerafilters.app.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.customcamerafilters.app.R.id
import com.customcamerafilters.app.R.layout
import com.customcamerafilters.app.adapter.FilterAdapter.ViewHolder
import com.customcamerafilters.app.interfaces.FilterClickListener
import com.customcamerafilters.app.model.FilterModel

class FilterAdapter(
    var context: Context,
    private var filterList: ArrayList<FilterModel>,
    private var capturedImageUri: String,
    var filterClickListener: FilterClickListener?
) : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(layout.filter_list_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.filterName?.text =
            (filterList[position].filterName)
        holder.filteredImage?.let {
            it.setOnClickListener { filterClickListener?.onFilterClick(position) }
        }
    }

    override fun getItemCount(): Int {
        return filterList.size
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var filterName: AppCompatTextView? = null
        var filteredImage: ImageView? = null

        init {
            filterName = view.findViewById(id.tv_filer_name)
            filteredImage = view.findViewById(id.img_filter)
        }
    }
}

