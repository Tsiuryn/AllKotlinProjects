package com.example.bottomnavigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bottom = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottom.setOnNavigationItemSelectedListener(navListener)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container,
                    HomeFragment()).commit();
        }

    }

    private val navListener = BottomNavigationView.OnNavigationItemSelectedListener {item: MenuItem ->
        var fragment: Fragment? = null
        when (item.itemId){
            R.id.nav_home ->{
                fragment = HomeFragment()
            }
            R.id.nav_fav ->{
                fragment = FavoritesFragment()
            }
            R.id.nav_find ->{
                fragment = SearchFragment()
            }
        }
        if (fragment != null) {
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
        }
        return@OnNavigationItemSelectedListener true
    }

}
