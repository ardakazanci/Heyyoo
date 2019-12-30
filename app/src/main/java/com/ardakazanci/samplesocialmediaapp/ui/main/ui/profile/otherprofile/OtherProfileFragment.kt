package com.ardakazanci.samplesocialmediaapp.ui.main.ui.profile.otherprofile

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil

import com.ardakazanci.samplesocialmediaapp.R
import com.ardakazanci.samplesocialmediaapp.databinding.OtherprofileFragmentBinding

class OtherProfileFragment : Fragment() {

    companion object {
        fun newInstance() =
            OtherProfileFragment()
    }

    private lateinit var viewModel: OtherProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<OtherprofileFragmentBinding>(
            inflater,
            R.layout.otherprofile_fragment,
            container,
            false
        )

        viewModel = ViewModelProviders.of(this).get(OtherProfileViewModel::class.java)

        arguments!!.let { arguments ->
            val args = OtherProfileFragmentArgs.fromBundle(arguments)
            Toast.makeText(context, "Kullanıcı ID->${args.userid}", Toast.LENGTH_LONG).show()

        }



        binding.otherprofileviewmodel = viewModel

        binding.lifecycleOwner = this

        return binding.root
    }


}
