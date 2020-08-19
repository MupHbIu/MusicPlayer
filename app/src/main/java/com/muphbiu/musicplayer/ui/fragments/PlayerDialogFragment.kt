package com.muphbiu.musicplayer.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.muphbiu.musicplayer.R
import com.muphbiu.musicplayer.base.interfaces.PlayerDialogFragmentInterface
import com.muphbiu.musicplayer.base.interfaces.PlayerDialogListener
import com.muphbiu.musicplayer.base.views.FileExplorerViewInterface
import com.muphbiu.musicplayer.ui.adapters.PlayerDialogFragmentAdapter
import kotlinx.android.synthetic.main.fragment_player_dialog.view.*
import kotlinx.android.synthetic.main.fragment_player_dialog_new_playlist.view.*
import java.io.File

class PlayerDialogFragment(private val activityInterface: PlayerDialogListener, private val newPlaylist: Boolean):
    DialogFragment(), PlayerDialogFragmentInterface {

    private var playlists = mutableListOf<File>()
    private lateinit var adapter: PlayerDialogFragmentAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View
        if(newPlaylist) {
            view = inflater.inflate(R.layout.fragment_player_dialog_new_playlist, container, false)
            view.playerDialogNewPlaylistEditText.requestFocus()
            dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
            view.playerDialogNewPlaylistButton.setOnClickListener {
                if(view.playerDialogNewPlaylistEditText.text.toString() == "" ||
                    view.playerDialogNewPlaylistEditText.text.toString().startsWith('/')) {
                    activityInterface.showMessage("Wrong name!")
                } else {
                    playlistGot(view.playerDialogNewPlaylistEditText.text.toString())
                }
            }
        } else {
            adapter = PlayerDialogFragmentAdapter(this, playlists)
            view = inflater.inflate(R.layout.fragment_player_dialog, container, false)
            view.playerDialogTitle.text = "Add to playlist"
            view.playerDialogButton.setOnClickListener { activityInterface.showCreateNewPlaylistDialog(); dismiss() }
            view.playerDialogButton.text = "Create new playlist..."
            view.playerDialogRecyclerView.layoutManager = LinearLayoutManager(activity)
            view.playerDialogRecyclerView.adapter = adapter
        }
        return view
    }

    fun addPlaylists(playlists: List<File>) {
        this.playlists = playlists.toMutableList()
    }

    override fun playlistGot(playlist: String) {
        if(newPlaylist)
            activityInterface.playlistNameGot(playlist)
        else
            activityInterface.playlistFileGot(playlist)
        dismiss()
    }

}