package com.example.vynils.view.collector

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.vynils.R
import com.example.vynils.databinding.FragmentCollectorDetailBinding
import com.example.vynils.model.Collector
import com.example.vynils.viewmodel.CollectorDetailViewModel
import com.squareup.picasso.Picasso

class CollectorDetailFragment : Fragment() {
    private var _binding: FragmentCollectorDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CollectorDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCollectorDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity){
            "You can only access the viewModel after onActivityCreated()"
        }
        val args: CollectorDetailFragmentArgs by navArgs()
        val collectorName = binding.textViewCollectorName
        val albumCollector = binding.albumCollectorImg
        val layoutAlbumCollector = binding.containerCollectorAlbumList
        val performerCollector = binding.performerCollectorImg
        val layoutPerformerCollector = binding.containerCollectorPerformerList

        viewModel = ViewModelProvider(
            this,
            CollectorDetailViewModel.Factory(activity.application, args.collectorId)
        ).get(CollectorDetailViewModel::class.java)

        viewModel.collector.observe(this, Observer<Collector> {
            it.apply {
                val collector = viewModel.collector.value
                collectorName.text = collector?.name
                collectorName.visibility = View.VISIBLE

                val scale = resources.displayMetrics.density
                val dpWidthInPx = (200 * scale).toInt()

                collector?.albums?.forEach { album ->
                    val imageView = ImageView(albumCollector.context)
                    imageView.layoutParams = LinearLayout.LayoutParams(dpWidthInPx, dpWidthInPx)
                    val lp = LinearLayout.LayoutParams(dpWidthInPx, dpWidthInPx)
                    lp.setMargins(0, 0, 50, 0)
                    imageView.layoutParams = lp;
                    imageView.setOnClickListener {

                    }
                    layoutAlbumCollector?.addView(imageView)
                    loadImage(album.cover, imageView)
                }

                collector?.performers?.forEach { performer ->
                    val imageView = ImageView(performerCollector.context)
                    imageView.layoutParams = LinearLayout.LayoutParams(dpWidthInPx, dpWidthInPx)
                    val lp = LinearLayout.LayoutParams(dpWidthInPx, dpWidthInPx)
                    lp.setMargins(0, 0, 50, 0)
                    imageView.layoutParams = lp;
                    layoutPerformerCollector?.addView(imageView)
                    loadImage(performer.image, imageView)
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

    private fun loadImage(image:String?,control: ImageView){
        try{
            Picasso.get()
                .load(image)
                .error(R.mipmap.ic_launcher_round)
                .into(control)
        }catch (e: Exception) { }
    }
}