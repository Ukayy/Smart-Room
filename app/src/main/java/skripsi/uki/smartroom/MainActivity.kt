package skripsi.uki.smartroom

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import skripsi.uki.smartroom.data.UserPreference
import skripsi.uki.smartroom.ui.account.ChangePasswordActivity
import skripsi.uki.smartroom.ui.login.LoginActivity

class MainActivity : AppCompatActivity() {

    private lateinit var preference: UserPreference
    var doubleBack:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)

        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_chart, R.id.navigation_power, R.id.navigation_user,R.id.navigation_admin))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        preference = UserPreference(this)
        val username = preference.getUsername().toString()

         if (username=="admin"){
            navView.getMenu().removeItem(R.id.navigation_user)
        }else{
            navView.getMenu().removeItem(R.id.navigation_admin)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.admin_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId==R.id.change_password){
            val intent = Intent(this,ChangePasswordActivity::class.java)
            startActivity(intent)
        }else if(item.itemId==R.id.logout){
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
            preference.clearUsername()
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (doubleBack ==true){
            super.onBackPressed()
            return;
        }
        doubleBack = true
        Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show()
        Handler().postDelayed( {
            doubleBack = false
        },2000)
    }
}