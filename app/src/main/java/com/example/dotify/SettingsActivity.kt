package com.example.dotify

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.dotify.databinding.ActivitySettingsBinding
import com.example.dotify.databinding.ActivitySettingsBinding.*


class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private val navController by lazy { findNavController(R.id.navHost) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = getString(R.string.settings)
        binding = inflate(layoutInflater).apply { setContentView(root) }

        val extras: Bundle? = intent.extras
        if (extras != null) {
            navController.setGraph(R.navigation.nav_graph, intent.extras)
        }
        setupActionBarWithNavController(navController)
    }

    override fun onSupportNavigateUp() = navController.navigateUp()
    fun SettingsActivity(view: View) {}
}