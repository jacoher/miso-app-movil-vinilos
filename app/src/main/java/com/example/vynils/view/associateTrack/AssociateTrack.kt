package com.example.vynils.view.associateTrack

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.vynils.R
import com.example.vynils.model.Track
import com.example.vynils.viewmodel.TrackViewModel

class AssociateTrack : AppCompatActivity() {

    private lateinit var viewModel: TrackViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_associate_track)

        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = getString(R.string.activity_associate_track_tittle);
        actionbar.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProvider(
            this,
            TrackViewModel.Factory(application)
        ).get(TrackViewModel::class.java)

        val extras = intent.extras
        if (extras != null) {
            val albumId = extras.getInt("albumId")

            viewModel.track.observe(this, Observer<Track> {
                it.apply {
                    viewModel.associateTrack(albumId)
                    onBackPressed()
                }
            })
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun cancelCreation(view: View) {
        this.onBackPressed()
    }

    fun associateTrack(view: View) {
        val trackName : EditText = findViewById(R.id.editTrackName)
        val trackMinutes : EditText = findViewById(R.id.editTrackMinutes)
        val trackSeconds : EditText = findViewById(R.id.editTrackSeconds)

        val duration = trackMinutes.text.toString().plus(":").plus(trackSeconds.text.toString())
        val trackView = Track(
            id = 0,
            name = trackName.text.toString(),
            duration= duration
        )

        viewModel.setTrack(trackView)
    }

    private fun onNetworkError() {
        if (!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(this@AssociateTrack, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }
}