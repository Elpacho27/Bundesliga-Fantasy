package hr.ferit.patrikocelic.bundesligafantasy.ui.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hr.ferit.patrikocelic.bundesligafantasy.data.models.Result
import hr.ferit.patrikocelic.bundesligafantasy.databinding.ResultItemBinding
import hr.ferit.patrikocelic.bundesligafantasy.utils.toDate

class ResultsAdapter : ListAdapter<Result, ResultsAdapter.ViewHolder>(COMPARATOR) {

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Result>() {
            override fun areItemsTheSame(
                oldItem: Result,
                newItem: Result
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Result,
                newItem: Result
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ResultItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ResultItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(result: Result) {
            with(binding) {
                date.text = result.timestamp.toDate()
                value.text = result.value.toString()
            }
        }
    }
}