package skripsi.uki.smartroom

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController

class MainActivity : AppCompatActivity() {

    companion object{
        var EXTRA_USERNAME = "name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        

        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_chart, R.id.navigation_power, R.id.navigation_user,R.id.navigation_admin))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val sesion = intent.getStringExtra(EXTRA_USERNAME)
//
//        if (sesion=="admin"){
            navView.getMenu().removeItem(R.id.navigation_user)
//        }else{
//            navView.getMenu().removeItem(R.id.navigation_admin)
//        }
    }
}