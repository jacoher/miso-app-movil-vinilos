package com.example.vynils.view.collector

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.vynils.R
import com.example.vynils.databinding.CollectorRowBinding
import com.example.vynils.model.Collector

class CollectorAdapter: RecyclerView.Adapter<CollectorAdapter.CollectorViewHolder>(){

    var collectors :List<Collector> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectorViewHolder {
        val withDataBinding: CollectorRowBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            CollectorViewHolder.LAYOUT,
            parent,
            false)
        return CollectorAdapter.CollectorViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: CollectorViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.collector = collectors[position]
            holder.viewDataBinding.root.setOnClickListener{
                val action = CollectorFragmentDirections.actionCollectorListToCollectorDetail(collectors[position].id)
                holder.viewDataBinding.root.findNavController().navigate(action)
            }
        }
    }

    override fun getItemCount(): Int {
        return collectors.size
    }

    class CollectorViewHolder(val viewDataBinding: CollectorRowBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.collector_row
        }
    }
}