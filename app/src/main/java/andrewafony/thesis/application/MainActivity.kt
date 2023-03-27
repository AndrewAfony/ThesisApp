package andrewafony.thesis.application

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val mainViewModel by viewModels<MainViewModel> { (application as ViewModelFactoryProvider).provide() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel.init(savedInstanceState == null)

        mainViewModel.observeNavigation(this) { navigation ->
            navigation.navigate(supportFragmentManager)
        }
    }
}