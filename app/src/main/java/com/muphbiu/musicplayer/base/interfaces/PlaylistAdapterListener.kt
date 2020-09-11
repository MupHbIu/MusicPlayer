package com.muphbiu.musicplayer.base.interfaces

interface PlaylistAdapterListener {
    fun itemSelected(position: Int)
    fun showDialogRename(name: String)
    fun showDialogDelete(name: String)
}