package hr.ferit.patrikocelic.bundesligafantasy.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import hr.ferit.patrikocelic.bundesligafantasy.R
import hr.ferit.patrikocelic.bundesligafantasy.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBarWithNavController(
            (supportFragmentManager.findFragmentById(binding.fragmentContainer.id) as NavHostFragment).navController,
            AppBarConfiguration(topLevelDestinationIds = setOf(R.id.homeFragment))
        )
    }

    override fun onSupportNavigateUp() =
        Navigation.findNavController(this, binding.fragmentContainer.id)
            .navigateUp() || super.onSupportNavigateUp()
}