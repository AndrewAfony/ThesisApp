package andrewafony.thesis.application

import andrewafony.thesis.application.databinding.ActivityMainBinding
import andrewafony.thesis.application.di.ViewModelFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController

class MainActivity : AppCompatActivity(), ViewModelFactoryProvider {

    private val mainViewModel by viewModels<MainViewModel> { (application as ViewModelFactoryProvider).provide() }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel.init(savedInstanceState == null)

        val navHost = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment

        mainViewModel.observeNavigation(this) { navigation ->
            navigation.navigate(navHost.navController)
        }

        binding.bottomNavigation.setupWithNavController(navHost.navController)

        navHost.navController.addOnDestinationChangedListener { _, dest, _ ->
            when(dest.id) {
                R.id.fragmentHome, R.id.fragmentSearch, R.id.fragmentNews, R.id.fragmentDeadlines -> {
                    binding.bottomNavigation.visibility = View.VISIBLE
                }
                else -> {
                    binding.bottomNavigation.visibility = View.GONE
                }
            }
        }
    }

    override fun provide(): ViewModelFactory {
        return (application as ViewModelFactoryProvider).provide()
    }
}