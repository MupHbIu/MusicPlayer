package com.muphbiu.musicplayer.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.muphbiu.musicplayer.R
import com.muphbiu.musicplayer.base.fragments.FragmentBottomInterface
import com.muphbiu.musicplayer.base.fragments.FragmentListInterface
import com.muphbiu.musicplayer.base.fragments.FragmentTopInterface
import com.muphbiu.musicplayer.base.interfaces.PlayerDialogListener
import com.muphbiu.musicplayer.base.views.StartActivityViewInterface
import com.muphbiu.musicplayer.presenters.StartActivityPresenter
import com.muphbiu.musicplayer.ui.fragments.*
import kotlinx.android.synthetic.main.activity_main_fragments.*
import java.io.File

class StartActivity : AppCompatActivity(), StartActivityViewInterface,
    FragmentTopInterface, FragmentListInterface, FragmentBottomInterface,
    PlayerDialogListener {
    private lateinit var presenter: StartActivityPresenter
    private lateinit var dialog: PlayerDialogFragment
    private val loadDialog = LoadDialogFragment()

    private var listId: Int = 0
    private var back_pressed: Long = 0
    private var isPlaying = false

    private var files = mutableListOf<File>()
    private lateinit var viewFolder: File

    private var playlists = mutableListOf<File>()
    private var viewPlaylist: File? = null
    private var songsToPlaylist = ""

    private lateinit var fragmentTop: FragmentTop
    private lateinit var fragmentList: FragmentList
    private lateinit var fragmentBottom: FragmentBottom


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_fragments)
        presenter = StartActivityPresenter(this, this)
        presenter.getFileList(getLocationToSecondDir())
        viewFolder = File(getLocationToSecondDir())

        listId = savedInstanceState?.getInt("LIST_ID") ?: 0
        when (listId) {
            0 -> {
                updateTopFragment("Playlist")
                presenter.getData("")
            }
            1 -> {
                updateTopFragment("Files")
                updateListFragment(filesToStrings(files), listId)
            }
        }
        updateBottomFragment("Song", "Author", isPlaying)

        mainFragmentRadioPlaylists.setOnClickListener {
            if(listId != 0) {
                listId = 0
                updateTopFragment("Playlist")
                presenter.getData(viewPlaylist?.absolutePath ?: "")
            }
        }
        mainFragmentRadioFiles.setOnClickListener {
            if(listId != 1) {
                listId = 1
                updateTopFragment("Files")
                updateListFragment(filesToStrings(files), listId)
            }
        }
    }

    override fun onBackPressed() {
        when (listId) {
            0 -> when(viewPlaylist?.absolutePath ?: "") {
                "" -> backExit()
                else -> {
                    viewPlaylist = null
                    presenter.getData("")
                }
            }
            1 -> when (viewFolder.absolutePath) {
                getLocationToSecondDir() -> backExit()
                getLocationToMainDir() -> showFolder(File(File(viewFolder.parent).parent))
                else -> showFolder(File(viewFolder.parent))
            }
            else -> backExit()
        }
    }
    private fun backExit() {
        if(back_pressed + 2000 > System.currentTimeMillis())
            super.onBackPressed()
        else
            showMessage("Press once again to exit!")
        back_pressed = System.currentTimeMillis()
    }
    // ========== Update fragments ==========
    private fun updateTopFragment(text: String) {
        fragmentTop = FragmentTop()
        val b = Bundle()
        b.putString(fragmentTop.KEY_TITLE, text)
        b.putInt(fragmentTop.KEY_IMG, 1)
        fragmentTop.setListener(this)
        fragmentTop.arguments = b
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.replace(R.id.mainFragmentTop, fragmentTop)
        ft.commit()
    }

    private fun updateListFragment(filesPath: ArrayList<String>, listId: Int) {
        fragmentList = FragmentList()
        val b = Bundle()
        b.putStringArrayList(fragmentList.KEY_LIST, filesPath)
        b.putInt(fragmentList.KEY_LIST_ID, listId)
        fragmentList.setListener(this)
        fragmentList.arguments = b
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.replace(R.id.mainFragmentList, fragmentList)
        ft.commit()
    }

    private fun updateBottomFragment(songName: String, songAuthor: String, isPlaying: Boolean) {
        fragmentBottom = FragmentBottom()
        val b = Bundle()
        b.putString(fragmentBottom.KEY_NAME, songName)
        b.putString(fragmentBottom.KEY_AUTHOR, songAuthor)
        b.putBoolean(fragmentBottom.KEY_IS_PLAYING, isPlaying)
        fragmentBottom.setListener(this)
        fragmentBottom.arguments = b
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.replace(R.id.mainFragmentBottom, fragmentBottom)
        ft.commit()
    }
    // ========== Update fragments ==========

    // ========== Callbacks from fragments ==========
    override fun goBack() {
        super.onBackPressed()
        showMessage("Back")
    }

    override fun goMenu() {
        showMessage("Menu")
    }

    override fun openPlayer() {
        showMessage("Opening player...")
    }

    override fun playPrevious() {
        showMessage("Previous")
    }

    override fun playPause() {
        isPlaying = !isPlaying
        updateBottomFragment("Song", "Author", isPlaying)
        showMessage("Play... Or pause?")
    }

    override fun playNext() {
        showMessage("Next")
    }
    // ========== Callbacks from fragments ==========

    private fun filesToStrings(files: List<File>) : ArrayList<String> {
        val filesPath = mutableListOf<String>()
        for(file in files)
            filesPath.add(file.absolutePath)
        return ArrayList(filesPath)
    }

    // ========== Callbacks playlist ==========
    override fun itemSelected(item: File) {
        if(!item.name.endsWith(".mp3"))
            viewPlaylist = item
        presenter.getData(item.name)
    }
    override fun updatePlaylistView() {
        val list = filesToStrings(playlists)
        if(listId == 0)
            updateListFragment(list, listId)
    }
    override fun updatePlaylist(playlist: List<File>) {
        playlists = playlist.toMutableList()
    }

    override fun playSong(songPath: String) {
        val intent = Intent(this, MusicPlayingActivity::class.java)
        intent.putExtra("SONG_PATH", songPath)
        intent.putExtra("PLAYLIST", viewPlaylist)
        startActivity(intent)
    }
    // ========== Callbacks playlist ==========

    // ========== Callbacks files ==========
    override fun updateFileView() {
        if(listId == 1)
        updateListFragment(filesToStrings(files), listId)
    }
    override fun updateFiles(files: List<File>) {
        this.files = files.toMutableList()
    }

    override fun getNewFileList(location: String) {
        presenter.getFileList(location)
    }
    override fun getLocationToMainDir(): String {
        return presenter.getMainDirLocation()
    }
    override fun getLocationToSecondDir(): String {
        return presenter.getSecondDirLocation()
    }

    override fun showFolder(folder: File) {
        viewFolder = folder
        getNewFileList(folder.absolutePath)
    }

    override fun showFile(file: File) {
        presenter.openFile(file)
    }
    override fun showAddToPlayList(location: String) {
        dialog = PlayerDialogFragment(this, false)
        dialog.addPlaylists(presenter.getListPlaylists())
        dialog.show(supportFragmentManager, "playlists")
        songsToPlaylist = location
    }

    override fun showCreateNewPlaylistDialog() {
        dialog = PlayerDialogFragment(this, true)
        dialog.show(supportFragmentManager, "newPlaylist")
    }

    override fun playlistNameGot(playlistName: String) {
        val playlistPath = presenter.createNewPlaylist(playlistName)
        if(playlistPath != "") {
            playlistFileGot(playlistPath)
            showMessage("Playlist created.")
        } else {
            showMessage("Error! Playlist not created!")
        }
    }
    override fun playlistFileGot(playlistFile: String) {
        if(presenter.addToPlaylist(songsToPlaylist, playlistFile))
            showMessage("Song $songsToPlaylist added to playlist $playlistFile")
        else
            showMessage("Error! Song not added")
    }
    // ========== Callbacks files ==========

    // ========== D E F A U L T ==========
    override fun showLoad() {
        loadDialog.isCancelable = false
        loadDialog.show(supportFragmentManager, "LoadDialog")
    }

    override fun hideLoad() {

        loadDialog.dismiss()
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}