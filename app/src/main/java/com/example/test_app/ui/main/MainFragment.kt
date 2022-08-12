package com.example.test_app.ui.main

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.test_app.R
import com.example.test_app.data.models.User
import com.example.test_app.ui.home.HomeFragment
import com.example.test_app.ui.register.RegisterFragment
import com.example.test_app.ui.widget.BottomSheetMenu
import kotlinx.android.synthetic.main.fragment_main.*


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private val modalBottomSheet = BottomSheetMenu()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        setActions()
    }

    private fun setActions() {
        txtEmail.addTextChangedListener {
            txtEmailLayout.error = null
        }
        txtPassword.addTextChangedListener { txtPasswordLayout.error = null }
        btnLogin.setOnClickListener {
            txtPassword.clearFocus()
            txtEmail.clearFocus()
            val imm: InputMethodManager =
                activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(main.windowToken, 0)
            val password: String = txtPassword.text.toString()
            val email: String = txtEmail.text.toString()
            if (email.trim().isEmpty()) {
                txtEmail.text = null
                txtEmailLayout.error = "Email is empty"
                return@setOnClickListener;
            } else if (password.trim().isEmpty()) {
                txtPassword.text = null
                txtPasswordLayout.error = "Password is empty"
                return@setOnClickListener;
            } else {
                viewModel.onLogin(User(email, password)).observe(viewLifecycleOwner) { apiResult ->
                    if (apiResult.success == true) {
                        goHomeFragment()
                    } else {
                        showDialog(apiResult.message.toString())
                    }
                }
            }
        }

        btnGoRegister.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.container, RegisterFragment.newInstance())?.addToBackStack("LOGIN")
                ?.commit()
        }
    }

    private fun showBottomSheet() {
        modalBottomSheet.show(parentFragmentManager, BottomSheetMenu.TAG)
    }

    private fun goHomeFragment() {
        try {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.container, HomeFragment.newInstance())
                ?.commit()
        } catch (e: Exception) {
            println("ERROR REPLACE FRAGMENT : ${e.toString()}")
        }

    }

    private fun showDialog(message:String) {
        AlertDialog.Builder(context)
            .setTitle("Login fail")
            .setMessage(message) // Specifying a listener allows you to take an action before dismissing the dialog.
            // The dialog is automatically dismissed when a dialog button is clicked.
            .setNegativeButton("close", null)
            .setIcon(android.R.drawable.ic_dialog_alert).show()
    }
}



