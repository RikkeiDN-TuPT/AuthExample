package com.example.test_app.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.test_app.R
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment : Fragment() {

    companion object {
        fun newInstance() = RegisterFragment()
    }

    private lateinit var viewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[RegisterViewModel::class.java]
        setAction()

    }

    fun setAction() {
        btnRegisterBackLogin.setOnClickListener {
            activity?.supportFragmentManager?.popBackStackImmediate()
        }
        btnSubmitRegister.setOnClickListener {
            onCreateUser()
        }
        txtEmail_Register.addTextChangedListener { txtEmail_Register.error = null }
        txtPasswordRegister.addTextChangedListener { txtPasswordRegister.error = null }
        txtPasswordConfirmRegister.addTextChangedListener {
            txtPasswordConfirmRegister.error = null
        }
    }

    private fun onCreateUser() {
        var username = txtEmail_Register.text
        var password = txtPasswordRegister.text
        var passwordConfirm = txtPasswordConfirmRegister.text
        if (username.isNullOrEmpty()) {
            txtEmail_Register.error = "Email is empty"
            return
        }
        if (password.isNullOrEmpty()) {
            txtPasswordRegister.error = "Password is empty"
            return
        }
        if (passwordConfirm.isNullOrEmpty()) {
            txtPasswordConfirmRegister.error = "Password confirm is empty"
            return
        }
        if (password != passwordConfirm) {
            txtPasswordConfirmRegister.error = "Confirmation password is incorrect"
        }
    }

}