package com.muphbiu.musicplayer.base.views

import com.muphbiu.musicplayer.base.BaseView
import com.muphbiu.musicplayer.base.interfaces.FileExplorerAdapterListener
import com.muphbiu.musicplayer.base.interfaces.PlayerDialogListener
import java.io.File

interface FileExplorerViewInterface : BaseView, FileExplorerAdapterListener, PlayerDialogListener {
    fun getLocationToSecondDir() : String
    fun getNewFileList(location: String) : List<File>

}