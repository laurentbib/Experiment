package sillapps.com.experiment.app

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import sillapps.com.experiment.R

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onSupportNavigateUp() = findNavController(R.id.nav_host_fragment).navigateUp()
}