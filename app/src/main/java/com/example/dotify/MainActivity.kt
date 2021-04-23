package com.example.dotify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.ericchee.songdataprovider.Song
import com.example.dotify.SongListActivity.Companion.SONG_KEY
import kotlinx.android.synthetic.main.activity_main.*

import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private var num = Random.nextInt(1000, 99999)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val playContent = "$num plays"
        tvNumPlays.text = playContent

        ibAlbumCover.setOnLongClickListener{
            changeTextColor()
            true
        }
        // Setting data on the basis of Song List screen
        val currSong = intent.getParcelableExtra<Song>(SONG_KEY)
        if(currSong != null) {
            tvSongTitle.text = currSong.title
            tvArtistInfo.text = currSong.artist
            ibAlbumCover.setImageResource(currSong.largeImageID)
        }
        // Implement back button
        val actionBar = supportActionBar
        actionBar!!.title = "Dotify"
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)
    }

    // When back button is pressed, goes back to SongList
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun iteratePlays(view: View) {
        num += 1;
        val playContent = "$num plays"
        tvNumPlays.text = playContent
    }
    fun prevTrack(view: View) {
        skippingToast(view,"previous")
    }
    fun nextTrack(view: View) {
        skippingToast(view,"next")
    }
    private fun skippingToast(view: View, direction: String) {
        Toast.makeText(this, "Skipping to $direction track", Toast.LENGTH_SHORT).show()
    }
    fun changeUser(view: View) {
        llChangeUserContainer.visibility = View.GONE
        llApplyUserContainer.visibility = View.VISIBLE

    }

    fun applyUser(view: View) {
        val newUsername = etUsername.text.toString()
        if(newUsername.trim().isEmpty()) {
            Toast.makeText(this, "Please enter a username", Toast.LENGTH_SHORT).show()
        } else {
            tvUsername.text = newUsername
            llChangeUserContainer.visibility = View.VISIBLE
            llApplyUserContainer.visibility = View.INVISIBLE
        }
    }

    private fun changeTvColor(color: Int) {
        tvUsername.setTextColor(ContextCompat.getColor(this, color))
        tvSongTitle.setTextColor(ContextCompat.getColor(this, color))
        tvArtistInfo.setTextColor(ContextCompat.getColor(this, color))
        tvNumPlays.setTextColor(ContextCompat.getColor(this, color))

    }
    private fun changeTextColor() {
        val colors = listOf(R.color.purple_200, R.color.purple_500, R.color.green, R.color.teal_700, R.color.azalea, R.color.cyan)
        changeTvColor(colors[Random.nextInt(1, 6)])


    }

}