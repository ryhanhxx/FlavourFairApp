package com.ch.flavourfair.presentation.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import coil.load
import coil.transform.CircleCropTransformation
import com.ch.flavourfair.R
import com.ch.flavourfair.databinding.FragmentProfileBinding
import com.ch.flavourfair.presentation.login.LoginActivity
import com.ch.flavourfair.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    /*private val viewModel: ProfileViewModel by viewModels {
        GenericViewModelFactory.create(createViewModel())
    }

    private fun createViewModel(): ProfileViewModel {
        val firebaseAuth = FirebaseAuth.getInstance()
        val dataSource = FirebaseAuthDataSourceImpl(firebaseAuth)
        val repo = UserRepositoryImpl(dataSource)
        return ProfileViewModel(repo)
    }*/

    private val viewModel: ProfileViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupForm()
        showUserData()
        setClickListeners()
        observeData()
    }

    private fun changeProfileData() {
        val fullName = binding.layoutForm.etName.text.toString().trim()
        viewModel.updateFullName(fullName)
    }

    private fun checkNameValidation(): Boolean {
        val fullName = binding.layoutForm.etName.text.toString().trim()
        return if (fullName.isEmpty()) {
            binding.layoutForm.tilName.isErrorEnabled = true
            binding.layoutForm.tilName.error = getString(R.string.text_error_name_cannot_empty)
            false
        } else {
            binding.layoutForm.tilName.isErrorEnabled = false
            true
        }
    }

    private fun observeData() {
        viewModel.changeProfileResult.observe(viewLifecycleOwner) {
            it.proceedWhen(doOnSuccess = {
                binding.pbLoading.isVisible = false
                binding.btnChangeProfile.isVisible = true
                Toast.makeText(
                    requireContext(),
                    "Change Profile data Success !",
                    Toast.LENGTH_SHORT
                ).show()
            }, doOnError = {
                    binding.pbLoading.isVisible = false
                    binding.btnChangeProfile.isVisible = true
                    Toast.makeText(
                        requireContext(),
                        "Change Profile data Failed !",
                        Toast.LENGTH_SHORT
                    ).show()
                }, doOnLoading = {
                    binding.pbLoading.isVisible = true
                    binding.btnChangeProfile.isVisible = false
                })
        }
    }

    private fun setClickListeners() {
        binding.tvChangePwd.setOnClickListener {
            requestChangePassword()
        }
        binding.tvLogout.setOnClickListener {
            doLogout()
        }
        binding.btnChangeProfile.setOnClickListener {
            if (checkNameValidation()) {
                changeProfileData()
            }
        }
    }

    private fun requestChangePassword() {
        viewModel.createChangePwdRequest()
        val dialog = AlertDialog.Builder(requireContext()).setMessage(
            "Change password request sent to your email " + "${viewModel.getCurrentUser()?.email}"
        ).setPositiveButton("Okay") { _, _ ->
            // do nothing
        }.create().show()
    }

    private fun doLogout() {
        viewModel.createChangePwdRequest()
        val dialog = AlertDialog.Builder(requireContext()).setMessage(
            "Do you wanna logout ?" + "${viewModel.getCurrentUser()?.email}"
        ).setPositiveButton("Okay") { _, _ ->
            viewModel.doLogout()
            navigateToLogin()
            // do nothing
        }.setNegativeButton("No") { _, _ ->
            viewModel.doLogout()
        }.create().show()
    }

    private fun navigateToLogin() {
        val intent = Intent(requireContext(), LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        requireContext().startActivity(intent)
    }

    private fun showUserData() {
        viewModel.getCurrentUser()?.let {
            binding.layoutForm.etName.setText(it.fullName)
            binding.layoutForm.etEmail.setText(it.email)
            binding.ivProfile.load(it.photoUrl) {
                crossfade(true)
                placeholder(R.drawable.img_avatar)
                error(R.drawable.img_avatar)
                transformations(CircleCropTransformation())
            }
        }
    }

    private fun setupForm() {
        binding.layoutForm.tilName.isVisible = true
        binding.layoutForm.tilEmail.isVisible = true
        binding.layoutForm.etEmail.isEnabled = false
        binding.layoutForm.clNotification.isVisible = true
    }
}
