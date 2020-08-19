package com.muphbiu.musicplayer.base.presenters

import com.muphbiu.musicplayer.base.BasePresenter
import java.io.File

interface StartActivityPresenterInterface : BasePresenter {
    fun openFolder(file: File) : List<File>
    fun openFile(file: File)
}