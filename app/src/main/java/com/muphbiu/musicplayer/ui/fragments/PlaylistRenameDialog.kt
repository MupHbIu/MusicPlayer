package com.muphbiu.musicplayer.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.muphbiu.musicplayer.R
import com.muphbiu.musicplayer.base.interfaces.PlaylistRenameDialogListener
import kotlinx.android.synthetic.main.fragment_player_dialog_edit_text.view.*

class PlaylistRenameDialog(private val listener: PlaylistRenameDialogListener) : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_player_dialog_edit_text, container, false)
        view.playerDialogETText.text = "Enter new playlist name:"
        view.playerDialogETEditText.hint = "New playlist's name"
        view.playerDialogETButton.text = "Rename"
        view.playerDialogETEditText.requestFocus()
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        view.playerDialogETButton.setOnClickListener {
            if(view.playerDialogETEditText.text.toString() != "") {
                listener.rename(view.playerDialogETEditText.text.toString())
            } else
                listener.wrongData()
            dismiss()
        }
        return view
    }
}