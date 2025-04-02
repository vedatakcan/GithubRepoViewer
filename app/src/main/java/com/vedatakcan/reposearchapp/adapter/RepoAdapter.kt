package com.vedatakcan.reposearchapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.vedatakcan.reposearchapp.databinding.ItemRepoBinding
import com.vedatakcan.reposearchapp.model.Repo
import java.text.NumberFormat
import javax.inject.Inject

class RepoAdapter @Inject constructor() : RecyclerView.Adapter<RepoAdapter.RepoViewHolder>() {

    private var repoList: List<Repo> = emptyList()
    private val numberFormat = NumberFormat.getInstance()
    var onItemClick: ((Repo) -> Unit)? = null
    inner class RepoViewHolder(private val binding: ItemRepoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(repo: Repo) {
            binding.tvRepoName.text = repo.name
            binding.tvDescription.text = repo.description
            binding.tvStars.text = numberFormat.format(repo.stars)
            binding.tvLanguage.text = repo.language

            binding.root.setOnClickListener {
                onItemClick?.invoke(repo)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val binding = ItemRepoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RepoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val repo = repoList[position]
        holder.bind(repo)
    }

    override fun getItemCount(): Int = repoList.size

    // DiffUtil ile veriyi güncellemek
    fun submitList(newList: List<Repo>) {
        val diffCallback = RepoDiffCallback(repoList, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        repoList = newList
        diffResult.dispatchUpdatesTo(this)
    }

    // DiffUtil Callback
    class RepoDiffCallback(
        private val oldList: List<Repo>,
        private val newList: List<Repo>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size
        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].name == newList[newItemPosition].name
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}
