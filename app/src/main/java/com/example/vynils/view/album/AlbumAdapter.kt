package com.example.vynils.view.album

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.vynils.R
import com.example.vynils.databinding.AlbumRowBinding
import com.example.vynils.model.Album
import com.squareup.picasso.Picasso

class AlbumAdapter : RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>(){

    var albums :List<Album> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val withDataBinding: AlbumRowBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            AlbumViewHolder.LAYOUT,
            parent,
            false)
        return AlbumViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.album = albums[position]
            try{
                Picasso.get()
                    .load(albums[position].cover)
                    .error(R.mipmap.ic_launcher_round)
                    .into(holder.viewDataBinding.imageCover)
            }
            catch (e: Exception) { }
            val performer = holder.viewDataBinding.textView3
            var performersString = ""
            for (i in 0 until albums[position].performers.size) {
                if(i+1 > albums[position].performers.size){
                    performersString += (albums[position].performers[i].name + ", ")
                }
                else {
                    performersString += albums[position].performers[i].name
                }
            }
            performer.setText(performersString)
            holder.viewDataBinding.root.setOnClickListener{
                val action = AlbumFragmentDirections.actionAlbumListToAlbumDetail(albums[position].id)
                holder.viewDataBinding.root.findNavController().navigate(action)
            }
        }

    }

    override fun getItemCount(): Int {
        return albums.size
    }

    class AlbumViewHolder(val viewDataBinding: AlbumRowBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.album_row
        }
    }
}