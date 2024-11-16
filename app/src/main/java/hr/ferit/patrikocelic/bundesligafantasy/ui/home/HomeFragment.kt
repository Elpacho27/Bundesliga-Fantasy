package hr.ferit.patrikocelic.bundesligafantasy.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import hr.ferit.patrikocelic.bundesligafantasy.R
import hr.ferit.patrikocelic.bundesligafantasy.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        binding.btnLineup.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToLineupFragment()
            )
        }

        binding.btnProfile.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToProfileFragment()
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}