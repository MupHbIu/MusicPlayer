package com.muphbiu.musicplayer.presenters

import android.content.Context
import com.muphbiu.musicplayer.base.presenters.StartActivityPresenterInterface
import com.muphbiu.musicplayer.base.views.StartActivityViewInterface
import com.muphbiu.musicplayer.models.StartActivityModel
import kotlinx.coroutines.*
import java.io.File

class StartActivityPresenter(private val activity: StartActivityViewInterface, context: Context) : StartActivityPresenterInterface {

    private val model = StartActivityModel(this, context)
    private var fileList = mutableListOf<File>()

    // ========== Playlists ==========
    fun getData(itemPath: String) {
        if(itemPath.endsWith(".mp3"))
            activity.playSong(itemPath)
        else {
            val listPlaylist = if(itemPath == "/")
                model.getListOfPlaylist()
            else
                model.getPlaylist(File(itemPath).name)
            activity.updatePlaylist(listPlaylist)
            activity.updatePlaylistView()
        }
    }
    fun getListPlaylists() : List<File> {
        return model.getPlaylists()
    }
    // ========== Playlists ==========

    // ========== Files ==========
    fun getFileList(location: String) {
        activity.showLoad()
        val job = GlobalScope.launch {
            val gettingData = async(Dispatchers.Unconfined) {
                fileList = model.getFiles(location).toMutableList()
                if(location == getSecondDirLocation() && model.haveMusic(getMainDirLocation()))
                    fileList.add(0, File(getMainDirLocation()))
                else if(location == getMainDirLocation())
                    fileList.add(0, File(File(File(location).parent).parent))
                else
                    fileList.add(0, File(File(location).parent))
                activity.updateFiles(fileList)
                activity.updateFileView()
            }
            gettingData.await()
            activity.hideLoad()
        }
    }
    fun getMainDirLocation() : String {
        return model.getDir(true)
    }
    fun getSecondDirLocation() : String {
        return model.getDir(false)
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
    fun renamePlaylist(oldName: String, newName: String): Boolean {
        return model.renamePlaylist(oldName, newName)
    }
    fun deletePlaylist(name: String) {
        model.deletePlaylist(name)
    }
    // ========== Files ==========

    // ========== D E F A U L T ==========
    override fun activityDestroyed() {
        TODO("Not yet implemented")
    }
}