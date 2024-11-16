package hr.ferit.patrikocelic.bundesligafantasy.ui.lineup

import android.app.Fragment
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import hr.ferit.patrikocelic.bundesligafantasy.R
import hr.ferit.patrikocelic.bundesligafantasy.data.models.PlayerUiModel
import hr.ferit.patrikocelic.bundesligafantasy.databinding.FragmentLineupBinding
import hr.ferit.patrikocelic.bundesligafantasy.ui.PlayersViewModel

class LineupFragment : Fragment(R.layout.fragment_lineup) {

    private var _binding: FragmentLineupBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<PlayersViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLineupBinding.bind(view)

        binding.teamView.setOnPlayerClickListener { position, viewId ->
            findNavController().navigate(
                LineupFragmentDirections.actionLineupFragmentToPlayersFragment(viewId, position)
            )
        }

        render()
        setHasOptionsMenu(true)
    }

    private fun render() {
        viewModel.selectedPlayers.observe(viewLifecycleOwner) {
            it?.let { selectedPlayers ->
                setPlayers(selectedPlayers)
            }
        }

        viewModel.formation.observe(viewLifecycleOwner) {
            it?.let { formation ->
                binding.teamView.changeFormation(formation)
                viewModel.selectedPlayers.value?.let { player -> setPlayers(player) }
            }
        }
    }

    private fun setPlayers(selectedPlayers: List<Pair<PlayerUiModel, Int>>) {
        if (selectedPlayers.isEmpty()) {
            binding.teamView.reset()
        } else {
            selectedPlayers.forEach {
                binding.teamView.setPlayer(it.first, it.second)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.lineup_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.confirm -> {
                val selectedPlayerCount = viewModel.selectedPlayers.value?.size ?: 0
                if (selectedPlayerCount != 11) {
                    Toast.makeText(requireContext(), getString(R.string.lineup_select_all_players), Toast.LENGTH_SHORT).show()

                } else {
                    viewModel.getPointsForTeam()
                    findNavController().navigate(
                        LineupFragmentDirections.actionLineupFragmentToResultFragment()
                    )
                }
                true
            }
            R.id.clear -> {
                viewModel.removeAllSelectedPlayers()
                true
            }
            R.id.formation442 -> {
                viewModel.setFormation(R.layout.formation_4_4_2)
                true
            }
            R.id.formation433 -> {
                viewModel.setFormation(R.layout.formation_4_3_3)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}