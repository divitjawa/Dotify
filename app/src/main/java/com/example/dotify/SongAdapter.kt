package com.example.dotify

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ericchee.songdataprovider.Song

class SongAdapter(listOfSongs: List<Song>): RecyclerView.Adapter<SongAdapter.SongViewHolder>() {
    private var listOfSongs: List<Song> = listOfSongs.toList()
    var onSongClickListener: ((song: Song) -> Unit?)? = null
    var onSongLongClickListener: ((song:Song) -> Unit?)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_song, parent, false)
        return SongViewHolder(view)
    }

    override fun getItemCount() = listOfSongs.size

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val currSong = listOfSongs[position]
        holder.bind(currSong)

    }

    fun change(newSongs: List<Song>) {
        val callback = SongDiffCallback(listOfSongs, newSongs)
        val diffRes = DiffUtil.calculateDiff(callback)
        diffRes.dispatchUpdatesTo(this)
        listOfSongs = newSongs
    }

    inner class SongViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val tvSongName by lazy {itemView.findViewById<TextView>(R.id.tvSongName)}
        private val tvArtistName by lazy {itemView.findViewById<TextView>(R.id.tvArtistName)}
        private val ivSongImage by lazy {itemView.findViewById<ImageView>(R.id.ivSongImage)}
        fun bind(song: Song) {
            tvSongName.text = song.title
            tvArtistName.text = song.artist
            ivSongImage.setImageResource(song.smallImageID)
            ivSongImage.contentDescription = song.title + "-" + song.title + "cover image"

            itemView.setOnClickListener {
                onSongClickListener?.invoke(song)
            }

            itemView.setOnLongClickListener{
                onSongLongClickListener?.invoke(song)
                true
            }
        }
    }
}