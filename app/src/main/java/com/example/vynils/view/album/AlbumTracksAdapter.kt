package com.example.vynils.view.album

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.vynils.R
import com.example.vynils.databinding.AlbumDetailTrackItemBinding
import com.example.vynils.model.Track

class AlbumTracksAdapter : RecyclerView.Adapter<AlbumTracksAdapter.AlbumTrackViewHolder> (){

    var tracks: List<Track> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumTrackViewHolder {
        val withDataBinding: AlbumDetailTrackItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            AlbumTrackViewHolder.LAYOUT,
            parent,
            false)
        return AlbumTrackViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: AlbumTrackViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.track = tracks[position]
        }
    }

    override fun getItemCount(): Int {
        return tracks.size
    }

    class AlbumTrackViewHolder(val viewDataBinding: AlbumDetailTrackItemBinding):
        RecyclerView.ViewHolder(viewDataBinding.root){
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.album_detail_track_item
        }
    }

}