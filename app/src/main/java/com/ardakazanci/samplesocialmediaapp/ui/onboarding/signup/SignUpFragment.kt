package com.ardakazanci.samplesocialmediaapp.ui.onboarding.signup

import android.content.res.Resources
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.ardakazanci.samplesocialmediaapp.R
import com.ardakazanci.samplesocialmediaapp.databinding.SignUpFragmentBinding

import es.dmoral.toasty.Toasty

class SignUpFragment : Fragment() {

    private lateinit var signUpViewModel: SignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<SignUpFragmentBinding>(
            inflater, R.layout.sign_up_fragment, container, false
        )
        binding.lifecycleOwner = this
        val viewModelFactory = SignUpViewModelFactory()
        signUpViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(SignUpViewModel::class.java)
        binding.signUpViewModel = signUpViewModel

        val extras =
            FragmentNavigatorExtras(binding.imgTopPhotoSelect to "transation_signin_signup")

        binding.tvSigninLinkText.setOnClickListener { view: View ->
            view.findNavController()
                .navigate(R.id.action_signUpFragment_to_signInFragment2, null, null, extras)
        }

        signUpViewModel.fieldEmptyControl.observe(this, Observer { message ->
            this.context!!.let {
                Toasty.custom(
                    it,
                    message,
                    ContextCompat.getDrawable(it, R.drawable.ic_info),
                    ContextCompat.getColor(it, R.color.color_sign_button),
                    ContextCompat.getColor(it, android.R.color.white),
                    Toasty.LENGTH_SHORT,
                    true,
                    true
                ).show();
            }
        })
        signUpViewModel.signUpSuccess.observe(this, Observer {
            if (it) {
                this.context!!.let {
                    Toasty.custom(
                        it,
                        "Kayıt Başarılı",
                        ContextCompat.getDrawable(it, R.drawable.ic_info),
                        ContextCompat.getColor(it, R.color.color_sign_button),
                        ContextCompat.getColor(it, android.R.color.white),
                        Toasty.LENGTH_SHORT,
                        true,
                        true
                    ).show();
                }
            }

        })



        return binding.root
    }


}
