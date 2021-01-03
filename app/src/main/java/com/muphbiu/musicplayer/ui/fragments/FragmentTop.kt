package com.muphbiu.musicplayer.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.muphbiu.musicplayer.R
import com.muphbiu.musicplayer.base.fragments.FragmentTopInterface
import kotlinx.android.synthetic.main.fragment_top.view.*

class FragmentTop() : Fragment() {
    private lateinit var activityI: FragmentTopInterface
    val KEY_TITLE = "TITLE"
    val KEY_IMG = "KEY_IMG"

    private lateinit var textView: TextView
    private lateinit var btnBack: ImageButton
    private lateinit var btnMenu: ImageButton

    fun setListener(activityI: FragmentTopInterface) {
        this.activityI = activityI
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_top, container, false)
        val str = this.arguments?.getString(KEY_TITLE, "") ?: ""
        val imgId = arguments?.getInt(KEY_IMG) ?: 0

        textView = v.fragmentTopTextView
        btnBack = v.fragmentTopBack
        btnMenu = v.fragmentTopMenu

        textView.text = str
        when(imgId) {
            0 -> btnBack.setImageResource(R.drawable.arrow_back)
            1 -> btnBack.setImageResource(R.drawable.btn_exit)
        }
        btnBack.setOnClickListener { activityI.goBack() }
        btnMenu.setOnClickListener { activityI.goMenu() }

        return v
    }
}