package com.example.pokeglass.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.pokeglass.R
import com.google.android.material.navigation.NavigationView

class TeamActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)

        val navView = findViewById<NavigationView>(R.id.nav_view)
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_pokedex -> {
                    // Naviga verso la PokedexActivity
                    val intent = Intent(this, PokedexActivity::class.java)
                    startActivity(intent)
                }
                R.id.nav_team -> {
                    // Naviga verso la TeamActivity
                }
            }
            val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
            drawerLayout.closeDrawers()
            true
        }

        toolbar.setNavigationOnClickListener {
            val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
            drawerLayout.openDrawer(GravityCompat.START)
        }
    }
}