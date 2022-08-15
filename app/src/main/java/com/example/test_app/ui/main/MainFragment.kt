package com.example.test_app.ui.main

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
        activity?.let { viewModel.initContext(it.applicationContext) }
        txtEmail.addTextChangedListener {
            txtEmailLayout.error = null
        }
        txtPassword.addTextChangedListener { txtPasswordLayout.error = null }
        btnLogin.setOnClickListener {
            var password: String = txtPassword.text.toString()
            var email: String = txtEmail.text.toString()
            var user = User(email, password)
            viewModel.onLogin(user)
            viewModel.getAuthState()
                .observe(viewLifecycleOwner, Observer<State> { t: State ->
                    when (t) {
                        is State.Successful -> {
                            if (t.success) {
                                goHomeFragment()
                            } else {
                                showDialog("Fail")
                            }
                        }
                        else -> {
                            return@Observer
                        }
                    }
                })
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
                ?.addToBackStack("LOGIN")
                ?.commit()
        } catch (e: Exception) {
            println("ERROR REPLACE FRAGMENT : ${e.toString()}")
        }

    }

    private fun showDialog(message: String) {
        AlertDialog.Builder(context)
            .setTitle("Login fail")
            .setMessage(message) // Specifying a listener allows you to take an action before dismissing the dialog.
            // The dialog is automatically dismissed when a dialog button is clicked.
            .setNegativeButton("close", null)
            .setIcon(android.R.drawable.ic_dialog_alert).show()
    }
}






