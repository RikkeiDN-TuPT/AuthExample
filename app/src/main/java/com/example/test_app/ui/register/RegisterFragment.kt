package com.example.test_app.ui.register

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.test_app.R
import com.example.test_app.data.models.State.State
import com.example.test_app.data.models.User
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

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        activity?.let { viewModel.initContext(it.applicationContext) }
        setAction()

    }

    private fun setAction() {
        btnRegisterBackLogin.setOnClickListener {
            goBackLogin()
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
        val username = txtEmail_Register.text.toString()
        val password = txtPasswordRegister.text.toString()
        val passwordConfirm = txtPasswordConfirmRegister.text.toString()
        if (username.isEmpty()) {
            txtEmail_Register.error = "Email is empty"
            return
        }
        if (password.isEmpty()) {
            txtPasswordRegister.error = "Password is empty"
            return
        }
        if (passwordConfirm.isEmpty()) {
            txtPasswordConfirmRegister.error = "Password confirm is empty"
            return
        }
        if (!password.equals(passwordConfirm)) {
            txtPasswordConfirmRegister.error = "Confirmation password is incorrect"
            return
        }

        viewModel.register(User(username, password))
        viewModel.getRegisterState().observe(viewLifecycleOwner, Observer<State> { t: State ->
            when (t) {
                is State.Successful -> {
                    if (t.success) {
                        goBackLogin()
                    }
                }
                is State.Failure -> {
                    showDialog(t.msg, "Register fail")
                }
                else -> {}
            }

        })

    }

    private fun goBackLogin() {
        activity?.supportFragmentManager?.popBackStackImmediate()
    }

    private fun showDialog(message: String, title: String) {
        AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message) // Specifying a listener allows you to take an action before dismissing the dialog.
            // The dialog is automatically dismissed when a dialog button is clicked.
            .setNegativeButton("close", null)
            .setIcon(android.R.drawable.ic_dialog_alert).show()
    }
}