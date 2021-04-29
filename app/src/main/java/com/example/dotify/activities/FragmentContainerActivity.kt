package com.example.dotify.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentTransaction
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import com.example.dotify.OnSongClickListener
import com.example.dotify.R
import com.example.dotify.fragments.NowPlayingFragment
import com.example.dotify.fragments.NowPlayingFragment.Companion.ARG_SONG
import com.example.dotify.fragments.NowPlayingFragment.Companion.TAG
import com.example.dotify.fragments.SongListFragment
import com.example.dotify.fragments.SongListFragment.Companion.ARG_SONG_LIST
import kotlinx.android.synthetic.main.fragment_container_activity.*

class FragmentContainerActivity : AppCompatActivity(), OnSongClickListener {
    private var clickedSong: Song? = null
    private val ARG_CURR_SONG: String = "arg_curr_song"
    private lateinit var songList: List<Song>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_container_activity)
        val songListFragment = SongListFragment()
        if (savedInstanceState != null) {
            with(savedInstanceState) {
                clickedSong = getParcelable(ARG_CURR_SONG)
                clickedSong?.let {
                    onSongClicked(it)
                }
            }
        } else {
            songList = SongDataProvider.getAllSongs()
            showSongList(songListFragment)
        }
        checkBackStack()
        setOnClickListeners()
    }
    override fun onSongClicked(song: Song) {
        tvCurrSong.text = getString(R.string.song_artist_names, song.title, song.artist)
        clickedSong = song
    }

    override fun onSupportNavigateUp(): Boolean {
        supportFragmentManager.popBackStack()
        return super.onNavigateUp()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(ARG_CURR_SONG, clickedSong)
    }
    private fun getNowPlayingFragment() = supportFragmentManager.findFragmentByTag(TAG) as? NowPlayingFragment
    private fun showNowPlaying() {
        // Add Now playing fragment with selected song
        var nowPlayingFragment = getNowPlayingFragment()
        if(nowPlayingFragment == null) {
            nowPlayingFragment = NowPlayingFragment()
            val argumentBundle = Bundle().apply {
                putParcelable(ARG_SONG, clickedSong)
            }
            nowPlayingFragment.arguments = argumentBundle
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragContainer, nowPlayingFragment, TAG)
                .addToBackStack(TAG)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit()
        } else {
            // If it already exists then update song
            nowPlayingFragment.updateSong(clickedSong)

        }

    }
    private fun showSongList(songListFragment: SongListFragment) {
        val allSongsBundle = getBundleSongList(songList)
        songListFragment.arguments = allSongsBundle
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragContainer, songListFragment, SongListFragment.TAG)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }

    private fun getBundleSongList(songList: List<Song>): Bundle {
        val allSongsBundle = Bundle().apply {
            val list = ArrayList(songList)
            putParcelableArrayList(ARG_SONG_LIST, list)
        }
        return (allSongsBundle)
    }
    private fun setOnClickListeners() {
        miniPlayer.setOnClickListener {
            if(clickedSong != null) {
                miniPlayer.visibility = View.INVISIBLE
                showNowPlaying()
            }
        }
        btnShuffle.setOnClickListener {
            val songListFragment = supportFragmentManager.findFragmentByTag(SongListFragment.TAG) as? SongListFragment
            songListFragment?.shuffleList()
        }
        // to show back button based on stack
        supportFragmentManager.addOnBackStackChangedListener {
            val hasBackStack = supportFragmentManager.backStackEntryCount > 0
            if(hasBackStack) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            } else {
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                miniPlayer.visibility = View.VISIBLE
            }
        }
    }
    private fun checkBackStack() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            miniPlayer.visibility = View.INVISIBLE
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    fun AboutFragment(view: View) {}

}