package com.example.vynils.view.createAlbum

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import com.example.vynils.R
import com.example.vynils.model.Album
import com.example.vynils.viewmodel.AlbumViewModel
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.vynils.model.Performer
import com.example.vynils.model.Track
import com.example.vynils.view.album.AlbumAdapter

class CreateAlbum : AppCompatActivity() {

    private lateinit var viewModel: AlbumViewModel
    private var viewModelAdapter: AlbumAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_album)


        val actionbar = supportActionBar
        actionbar!!.title = getString(R.string.activity_create_album_tittle);
        actionbar.setDisplayHomeAsUpEnabled(true)

        val spinnerGenero: Spinner = findViewById(R.id.spinnerAlbumGenero)
        ArrayAdapter.createFromResource(
            this,
            R.array.genre_options,
            R.layout.spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.spinner_drawable)
            spinnerGenero.adapter = adapter
        }

        val spinnerDisquera: Spinner = findViewById(R.id.spinnerAlbumDisquera)
        ArrayAdapter.createFromResource(
            this,
            R.array.record_label_options,
            R.layout.spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.spinner_drawable)
            spinnerDisquera.adapter = adapter
        }

        viewModel = ViewModelProvider(
            this,
            AlbumViewModel.Factory(application)
        ).get(AlbumViewModel::class.java)

        viewModel.album.observe(this, Observer<Album> {
            it.apply {
                viewModel.refreshDataCreateFromNetwork()
                onBackPressed()
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun cancelCreation(view: View) {
        this.onBackPressed()
    }

    fun createAlbum(view: View) {
        val albumName : EditText = findViewById(R.id.editTextAlbumName)
        val albumDescription : EditText = findViewById(R.id.editTextAlbumDescripcion)
        val albumReleaseDate : EditText = findViewById(R.id.editTextAlbumFecha)
        val spinnerRecorLabel: Spinner = findViewById(R.id.spinnerAlbumDisquera)
        val spinnerGenre: Spinner = findViewById(R.id.spinnerAlbumGenero)
        val cover : EditText = findViewById(R.id.editTextAlbumPortada)

        val albumView = Album(
            id = 0,
            name = albumName.text.toString(),
            cover= cover.text.toString(),
            releaseDate= albumReleaseDate.text.toString(),
            description=albumDescription.text.toString(),
            genre= spinnerGenre.selectedItem.toString(),
            recordLabel= spinnerRecorLabel.selectedItem.toString(),
            performers = emptyList<Performer>(),
            tracks = emptyList<Track>()
        )

        viewModel.setAlbum(albumView)
    }
}

