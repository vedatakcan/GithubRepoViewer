package com.vedatakcan.reposearchapp.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.vedatakcan.reposearchapp.R
import com.vedatakcan.reposearchapp.adapter.RepoAdapter
import com.vedatakcan.reposearchapp.databinding.FragmentRepoListBinding
import com.vedatakcan.reposearchapp.viewmodel.RepoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RepoListFragment : Fragment(R.layout.fragment_repo_list) {

    private var _binding: FragmentRepoListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RepoViewModel by viewModels()

    @Inject
    lateinit var adapter: RepoAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentRepoListBinding.bind(view)
        binding.recyclerView.adapter = adapter

        val username = RepoListFragmentArgs.fromBundle(requireArguments()).username
        viewModel.getRepos(username)

        // StateFlow ile veriyi gözlemleme
        lifecycleScope.launch {
            viewModel.repos.collect { repos ->
                if (repos.isEmpty()) {
                    // Eğer kullanıcı yoksa, "Kullanıcı Bulunamadı" mesajını göster
                    binding.recyclerView.visibility = View.GONE
                    binding.tvErrorMessage.visibility = View.VISIBLE
                    binding.icUserNotFoundId.visibility = View.VISIBLE
                } else {
                    // Eğer kullanıcı varsa, listeleri göster
                    binding.recyclerView.visibility = View.VISIBLE
                    binding.tvErrorMessage.visibility = View.GONE
                    binding.icUserNotFoundId.visibility = View.GONE
                    adapter.submitList(repos)
                }
            }
        }

        // StateFlow ile hata mesajını gözlemleme
        lifecycleScope.launch {
            viewModel.errorMessage.collect { errorMessage ->
                if (errorMessage != null) {
                    binding.tvErrorMessage.text = errorMessage
                    binding.tvErrorMessage.visibility = View.VISIBLE
                    binding.icUserNotFoundId.visibility = View.VISIBLE

                } else {
                    binding.tvErrorMessage.visibility = View.GONE
                    binding.icUserNotFoundId.visibility = View.GONE
                }
            }
        }

        // Yükleme Durumu için progressbar yönetimi
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isLoading.collect { isLoading ->
                binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            }
        }

        // Kullanıcı adını gözlemleyip TextView'da gösterme
        lifecycleScope.launch {
            viewModel.username.collect { username ->
                if (username != null) {
                    binding.tvOwnerName.text = username // Kullanıcı adı TextView'da gösteriliyor
                }
            }
        }

        adapter.onItemClick = { repo ->
            // Github reposunu tarayıcıda aç
            val url = "https://github.com/$username/${repo.name}"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
