package com.example.vynils.view.album

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vynils.R
import com.example.vynils.databinding.AlbumDetailBinding
import com.example.vynils.model.Album
import com.example.vynils.viewmodel.AlbumDetailViewModel
import com.squareup.picasso.Picasso

class AlbumDetailFragment : Fragment() {
    private var _binding: AlbumDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: AlbumDetailViewModel
    private var viewModelAdapter: AlbumTracksAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AlbumDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModelAdapter = AlbumTracksAdapter()
        return view
    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshDataFromNetwork()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.albumTracks
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = viewModelAdapter

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity){
            "You can only access the viewModel after onActivityCreated()"
        }
        val args: AlbumDetailFragmentArgs by navArgs()
        val name = binding.albumName
        val release = binding.albumReleaseDate
        val performers = binding.albumPerformers
        val imageCover = binding.imageCover

        viewModel = ViewModelProvider(
            this,
            AlbumDetailViewModel.Factory(activity.application, args.albumId)
        ).get(AlbumDetailViewModel::class.java)
        viewModel.album.observe(viewLifecycleOwner, Observer<Album> {
            it.apply {
                val album : Album? = viewModel.album.value
                if (album != null) {
                    name.setText(album.name)
                    release.setText(album.releaseDate.substring(0,4))
                    var performersString = ""
                    for (i in 0 until album.performers.size) {
                        if(i+1 > album.performers.size){
                            performersString += (album.performers[i].name + ", ")
                        }
                        else {
                            performersString += album.performers[i].name
                        }
                    }
                    performers.setText(performersString)
                    try{
                        Picasso.get()
                            .load(album.cover)
                            .error(R.mipmap.ic_launcher_round)
                            .into(imageCover)
                    }
                    catch (e: Exception) { }

                    viewModelAdapter!!.tracks = album.tracks
                }
            }
        })

        viewModel.eventNetworkError.observe(
            viewLifecycleOwner,
            Observer<Boolean> { isNetworkError ->
                if (isNetworkError) onNetworkError()
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onNetworkError() {
        if (!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }
}