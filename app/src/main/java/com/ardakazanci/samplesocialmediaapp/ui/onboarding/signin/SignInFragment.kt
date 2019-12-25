package com.ardakazanci.samplesocialmediaapp.ui.onboarding.signin

import android.content.Intent
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.transition.TransitionInflater

import com.ardakazanci.samplesocialmediaapp.R
import com.ardakazanci.samplesocialmediaapp.databinding.SignInFragmentBinding
import com.ardakazanci.samplesocialmediaapp.ui.main.SocialMainActivity
import com.ardakazanci.samplesocialmediaapp.utils.Constants
import com.ardakazanci.samplesocialmediaapp.utils.extensions.intent
import com.ardakazanci.samplesocialmediaapp.utils.extensions.toast
import com.securepreferences.SecurePreferences
import es.dmoral.toasty.Toasty

class SignInFragment : Fragment() {



    private lateinit var viewModel: SignInViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<SignInFragmentBinding>(
            inflater, R.layout.sign_in_fragment, container, false
        )

        binding.lifecycleOwner = this

        viewModel = ViewModelProviders.of(this).get(SignInViewModel::class.java)

        binding.signInViewModel = viewModel


        viewModel.loginSucessControl.observe(this, Observer { control ->
            if (control) {

                this.context?.toast("Giriş Başarılı")
                val intent = Intent(
                    this.context,
                    SocialMainActivity::class.java
                ).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION or Intent.FLAG_ACTIVITY_NO_HISTORY)
                startActivity(intent)
                this.activity?.finish()

            } else {

                this.context?.toast("Giriş Başarısız")

            }
        })


        binding.tvSignupTextLink.setOnClickListener { view: View ->
            viewModel.cancelCoroutines()
            view.findNavController().navigate(R.id.action_signInFragment_to_forgetPasswordFragment)
        }

        return binding.root


    }


}
