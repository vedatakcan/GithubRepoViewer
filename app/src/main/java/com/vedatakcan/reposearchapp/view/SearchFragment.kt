package com.vedatakcan.reposearchapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.vedatakcan.reposearchapp.R
import com.vedatakcan.reposearchapp.databinding.FragmentSearchBinding
import com.vedatakcan.reposearchapp.viewmodel.ThemeViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ThemeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentSearchBinding.bind(view)
        binding.btnSearch.setOnClickListener {
            val username = binding.etUserName.text.toString().trim()
            if (username.isNotEmpty()) {
                //RepoList Fragment'a git
                val action =
                    SearchFragmentDirections.actionSearchFragmentToRepoListFragment(username)
                findNavController().navigate(action)
            }
        }

        // LiveData gözlemle
        viewModel.isDarkModeEnabled.observe(viewLifecycleOwner) { isDarkMode ->
            binding.themeToggle.isChecked = isDarkMode
        }

        // Switch butonuna tıklanınca tema değiştir
        binding.themeToggle.setOnCheckedChangeListener { _, isChecked ->
            viewModel.toggleTheme(isChecked) // Tema Değiştir
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}