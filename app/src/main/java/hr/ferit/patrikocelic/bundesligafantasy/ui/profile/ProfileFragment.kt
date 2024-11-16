package hr.ferit.patrikocelic.bundesligafantasy.ui.profile

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import hr.ferit.patrikocelic.bundesligafantasy.R
import hr.ferit.patrikocelic.bundesligafantasy.databinding.FragmentProfileBinding
import hr.ferit.patrikocelic.bundesligafantasy.ui.PlayersViewModel

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<PlayersViewModel>()
    private val adapter by lazy { ResultsAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProfileBinding.bind(view)

        initRecycler()
        render()
    }

    private fun initRecycler() {
        binding.resultsList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@ProfileFragment.adapter
            setHasFixedSize(true)
        }
    }

    private fun render() {
        viewModel.observeResults().observe(viewLifecycleOwner) {
            it?.let { results ->
                adapter.submitList(results)
                binding.emptyListMessage.isVisible = results.isEmpty()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}