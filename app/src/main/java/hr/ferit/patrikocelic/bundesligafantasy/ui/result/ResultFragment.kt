package hr.ferit.patrikocelic.bundesligafantasy.ui.result

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import hr.ferit.patrikocelic.bundesligafantasy.R
import hr.ferit.patrikocelic.bundesligafantasy.databinding.FragmentResultBinding
import hr.ferit.patrikocelic.bundesligafantasy.ui.PlayersViewModel

class ResultFragment : Fragment(R.layout.fragment_result) {

    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<PlayersViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentResultBinding.bind(view)

        binding.btnProfile.setOnClickListener {
            findNavController().navigate(
                ResultFragmentDirections.actionResultFragmentToProfileFragment()
            )
        }

        render()
    }

    private fun render() {
        viewModel.totalPoints.observe(viewLifecycleOwner) {
            it?.let { points ->
                binding.pointsText.text = getString(R.string.result_total_points_message, points)
            }
        }

        viewModel.resultLoading.observe(viewLifecycleOwner) {
            it?.let { isLoading ->
                binding.progressContainer.isVisible = isLoading
                binding.resultContainer.isVisible = !isLoading
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}