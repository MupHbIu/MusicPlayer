package com.muphbiu.musicplayer.presenters

import android.content.Context
import com.muphbiu.musicplayer.base.views.FileExplorerViewInterface
import com.muphbiu.musicplayer.base.presenters.FileExplorerPresenterInterface
import com.muphbiu.musicplayer.models.FileExplorerModel
import java.io.File

class FileExplorerPresenter(context: Context, private val activity: FileExplorerViewInterface) : FileExplorerPresenterInterface {

    private val model = FileExplorerModel(context)
    private var fileList = mutableListOf<File>()


    fun getFileList(location: String) : List<File> {
        fileList = model.getFiles(location).toMutableList()
        if(location == getSecondDirLocation() && model.haveMusic(getMainDirLocation()))
            fileList.add(0, File(getMainDirLocation()))
        else if(location == getMainDirLocation())
            fileList.add(0, File(File(File(location).parent).parent))
        else
            fileList.add(0, File(File(location).parent))
        return fileList
    }
    fun getMainDirLocation() : String {
        return model.getDir(true)
    }
    fun getSecondDirLocation() : String {
        return model.getDir(false)
    }
    fun getListPlaylists() : List<File> {
        return model.getPlaylists()
    }

    override fun openFolder(folder: File) : List<File> {
        return model.getFiles(folder)
    }
    override fun openFile(file: File) {
        activity.showMessage("File opening...")
        // TODO: Open song in PlayingActivity
    }

    fun addToPlaylist(location: String, playlistName: String) : Boolean {
        return model.addSongToPlaylist(location, playlistName)

    }

    fun createNewPlaylist(playlistName: String) : String {
        return model.createNewPlaylistFile(playlistName)
    }

    // ========== D E F A U L T ==========
    override fun activityDestroyed() {
        TODO("Not yet implemented")
    }
}