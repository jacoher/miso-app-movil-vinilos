package com.example.vynils.view.performer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.vynils.R
import com.example.vynils.databinding.PerformerRowBinding
import com.example.vynils.model.Performer
import com.squareup.picasso.Picasso

class PerformerAdapter : RecyclerView.Adapter<PerformerAdapter.PerformerViewHolder>() {

    private lateinit var mlistener : OnItemClickListener

    var performers :List<Performer> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        mlistener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PerformerViewHolder {
        val withDataBinding: PerformerRowBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            PerformerViewHolder.LAYOUT,
            parent,
            false)
        return PerformerViewHolder(withDataBinding,mlistener)
    }

    override fun onBindViewHolder(holder: PerformerViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.performer = performers[position]
            try{
                Picasso.get()
                    .load(performers[position].image)
                    .error(R.mipmap.ic_launcher_round)
                    .into(holder.viewDataBinding.imageCover)
            }
            catch (e: Exception) { }
        }

    }

    override fun getItemCount(): Int {
        return performers.size
    }

    class PerformerViewHolder(val viewDataBinding: PerformerRowBinding, listener: OnItemClickListener) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.performer_row
        }

        init {
            viewDataBinding.root.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }
}
