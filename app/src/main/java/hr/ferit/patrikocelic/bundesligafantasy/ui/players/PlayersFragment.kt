package hr.ferit.patrikocelic.bundesligafantasy.ui.players

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import hr.ferit.patrikocelic.bundesligafantasy.data.models.PlayerUiModel
import hr.ferit.patrikocelic.bundesligafantasy.R
import hr.ferit.patrikocelic.bundesligafantasy.databinding.FragmentPlayersBinding
import hr.ferit.patrikocelic.bundesligafantasy.ui.PlayersViewModel

class PlayersFragment : Fragment(R.layout.fragment_players) {

    private val args by navArgs<PlayersFragmentArgs>()

    private var _binding: FragmentPlayersBinding? = null
    private val binding get() = _binding!!

    private val adapter by lazy { PlayersAdapter { onPlayerClick(it) } }
    private val viewModel by activityViewModels<PlayersViewModel>()

    private var menu: Menu? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPlayersBinding.bind(view)

        initRecycler()
        render()
        setHasOptionsMenu(true)
    }

    private fun render() {
        viewModel.observePlayersByPosition(args.position).observe(viewLifecycleOwner) {
            it?.let { players ->
                adapter.submitList(players)
            }
        }

        viewModel.playersLoading.observe(viewLifecycleOwner) {
            it?.let { isLoading ->
                menu?.findItem(R.id.refresh)?.isEnabled = !isLoading
                binding.progress.isVisible = isLoading
            }
        }
    }

    private fun initRecycler() {
        binding.playersList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@PlayersFragment.adapter
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(requireContext(), (layoutManager as LinearLayoutManager).orientation))
        }
    }

    private fun onPlayerClick(playerUiModel: PlayerUiModel) {
        viewModel.addSelectedPlayer(playerUiModel, args.viewId)
        findNavController().popBackStack()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.players_menu, menu)
        this.menu = menu
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.refresh -> {
                viewModel.refreshPlayers()
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