package com.example.dotify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import kotlinx.android.synthetic.main.activity_song_list.*

class SongListActivity : AppCompatActivity() {
    companion object {
        const val SONG_KEY = "SONG_KEY"
    }
    var clickedSong: Song? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_list)
        title = "All Songs"
        var allSongs: List<Song> = SongDataProvider.getAllSongs()
        val songAdapter = SongAdapter(allSongs)

        // Clicking on a song will change the name on the mini player
        songAdapter.onSongClickListener = {song ->
            tvCurrSong.text = getString(R.string.song_artist_names, song.title, song.artist)
            clickedSong = song
            Unit
        }
        // Long clicking on a song will delete it
        // Triggers index out of bounds in SongDiffCallback -> areContentsTheSame
        songAdapter.onSongLongClickListener = {song ->
            val toastText = getString(R.string.song_artist_names, song.title, song.artist) + " deleted"
            Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show()
            val mutableListOfSongs = allSongs.toMutableList()
            songAdapter.change(mutableListOfSongs.apply {
                remove(song)
            })
            allSongs = mutableListOfSongs.toList()
            Unit
        }
        // Clicking shuffle button will shuffle order of list
        btnShuffle.setOnClickListener {
            val newSongs = allSongs.shuffled()
            songAdapter.change(newSongs)
        }

        // Launch player page when mini player is clicked
        miniPlayer.setOnClickListener {
            if(clickedSong == null) {
                Toast.makeText(this, "Click a song to play", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("SONG_KEY", clickedSong)
                startActivity(intent)
            }
        }
        rvSongs.adapter = songAdapter
    }
}