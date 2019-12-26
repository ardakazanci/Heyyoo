package com.ardakazanci.samplesocialmediaapp.ui.main.ui.content

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.ardakazanci.samplesocialmediaapp.R

class ContentAddFragment : Fragment() {

    companion object {
        fun newInstance() = ContentAddFragment()
    }

    private lateinit var viewModel: ContentAddViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.content_add_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ContentAddViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
