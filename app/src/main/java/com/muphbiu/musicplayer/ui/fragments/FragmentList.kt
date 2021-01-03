package com.muphbiu.musicplayer.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.muphbiu.musicplayer.R
import com.muphbiu.musicplayer.base.fragments.FragmentListInterface
import com.muphbiu.musicplayer.base.interfaces.DeletePlaylistDialogListener
import com.muphbiu.musicplayer.base.interfaces.FileExplorerAdapterListener
import com.muphbiu.musicplayer.base.interfaces.PlaylistAdapterListener
import com.muphbiu.musicplayer.base.interfaces.PlaylistRenameDialogListener
import com.muphbiu.musicplayer.ui.adapters.FileExplorerAdapter
import com.muphbiu.musicplayer.ui.adapters.PlaylistAdapter
import kotlinx.android.synthetic.main.fragment_list.view.*
import java.io.File

class FragmentList() : Fragment(),
    PlaylistAdapterListener, FileExplorerAdapterListener,
    PlaylistRenameDialogListener, DeletePlaylistDialogListener{
    private lateinit var activityI: FragmentListInterface
    val KEY_LIST = "LIST"
    val KEY_LIST_ID = "LIST_ID"

    private var deleteName = ""
    private var renameName = ""

    private lateinit var adapterPlaylist: PlaylistAdapter
    private lateinit var adapterFile: FileExplorerAdapter
    private lateinit var recyclerView: RecyclerView

    fun setListener(activityI: FragmentListInterface) {
        this.activityI = activityI
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        recyclerView = view.fragmentListRecyclerView

        val listId = arguments?.getInt(KEY_LIST_ID, 0) ?: 0
        val list : List<String> = arguments?.getStringArrayList(KEY_LIST)?.toList() ?: emptyList<String>()
        val listFiles: MutableList<File> = mutableListOf()
        for(path in list)
            listFiles.add(File(path))
        when (listId) {
            0 -> {
                adapterPlaylist = PlaylistAdapter(this, listFiles)
                recyclerView.layoutManager = LinearLayoutManager(activity)
                recyclerView.adapter = adapterPlaylist
            }
            1 -> {
                adapterFile = FileExplorerAdapter(this, listFiles)
                recyclerView.layoutManager = LinearLayoutManager(activity)
                recyclerView.adapter = adapterFile
            }
        }
        return view
    }

    // ========== Playlists ==========
    override fun itemSelected(item: Int) {
        activityI.itemSelected(item)
    }
    override fun showDialogRename(name: String) {
        renameName = name
        val renameDialog = PlaylistRenameDialog(this)
        renameDialog.show(activity?.supportFragmentManager!!, "RenameDialog")
    }
    override fun showDialogDelete(name: String) {
        deleteName = name
        val deleteDialog = DeletePlaylistDialog(this)
        deleteDialog.show(activity?.supportFragmentManager!!, "DialogDelete")
    }
    // ========== Playlists ==========

    // ========== Files ==========
    override fun getLocationToMainDir(): String {
        return activityI.getLocationToMainDir()
    }

    override fun showAddToPlayList(location: String) {
        activityI.showAddToPlayList(location)
    }

    override fun showFolder(folder: File) {
        activityI.showFolder(folder)
    }

    override fun showFile(file: File) {
        activityI.showFile(file)
    }

    override fun rename(newName: String) {
        activityI.renamePlaylist(renameName, newName)
    }
    override fun wrongData() {
        activityI.showMessage("Wrong data!")
    }
    override fun delete() {
        activityI.deletePlaylist(deleteName)
    }
    // ========== Files ==========

}