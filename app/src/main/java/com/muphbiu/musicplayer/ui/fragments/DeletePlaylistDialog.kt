package com.muphbiu.musicplayer.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.muphbiu.musicplayer.R
import com.muphbiu.musicplayer.base.interfaces.DeletePlaylistDialogListener
import kotlinx.android.synthetic.main.fragment_delete_playlist_dialog.view.*

class DeletePlaylistDialog(private val listener: DeletePlaylistDialogListener) : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_delete_playlist_dialog, container, false)
        view.deletePlaylistDialogBtnYes.setOnClickListener { listener.delete(); dismiss()}
        view.deletePlaylistDialogBtnNo.setOnClickListener { dismiss() }
        return view
    }
}