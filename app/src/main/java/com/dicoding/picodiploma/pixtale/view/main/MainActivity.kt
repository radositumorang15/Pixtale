package com.dicoding.picodiploma.pixtale.view.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.PopupMenu
import androidx.appcompat.widget.Toolbar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.loginwithanimation.R
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivityMainBinding
import com.dicoding.picodiploma.pixtale.adapter.ListStoriesAdapter
import com.dicoding.picodiploma.pixtale.view.ViewModelFactory
import com.dicoding.picodiploma.pixtale.view.camera.CameraActivity
import com.dicoding.picodiploma.pixtale.view.login.LoginActivity
import com.dicoding.picodiploma.pixtale.view.welcome.WelcomeActivity


class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory(this)
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var listStoriesAdapter: ListStoriesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d("MainActivity", "MainActivity created")

        viewModel.getSession().observe(this) { user ->
            Log.e("MainActivity", "user: $user")
            if (!user.isLoggedIn) {
                startActivity(Intent(this@MainActivity, WelcomeActivity::class.java))
                finish()
            } else {
                setupRecyclerView()
                viewModel.fetchStories()
            }
        }

        viewModel.stories.observe(this) { stories ->
            listStoriesAdapter.submitList(stories)
            binding.rvStories.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE
        }

        viewModel.loading.observe(this) { isLoading ->
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
                binding.rvStories.visibility = View.GONE
            } else {
                binding.progressBar.visibility = View.GONE
                binding.rvStories.visibility = View.VISIBLE
            }
        }

        binding.createStory.setOnClickListener {
            val intent = Intent(this, CameraActivity::class.java)
            startActivity(intent)
        }

        setCustomActionBar()
    }

    private fun setCustomActionBar() {
        val toolbar = findViewById<Toolbar>(R.id.tool_bar)
        setSupportActionBar(toolbar)

        val inflater = layoutInflater
        val customActionBarView = inflater.inflate(R.layout.custom_action_bar, null)
        supportActionBar?.apply {
            displayOptions = androidx.appcompat.app.ActionBar.DISPLAY_SHOW_CUSTOM
            customView = customActionBarView
        }

        customActionBarView.findViewById<ImageView>(R.id.action_bar_menu).setOnClickListener {
            showPopupMenu(it)
        }
    }

    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.menuInflater.inflate(R.menu.menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.logout -> {
                    viewModel.logout()
                    true
                }
                android.R.id.home -> {
                    redirectToLogin()
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }
        }
        popupMenu.show()
    }


    override fun onDestroy() {
        super.onDestroy()
        viewModel.logout()
    }


    private fun redirectToLogin() {
        val intent = Intent(applicationContext, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun setupRecyclerView() {
        listStoriesAdapter = ListStoriesAdapter(emptyList())
        binding.rvStories.apply {
            adapter = listStoriesAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    //   private fun setupAction() {
    //      binding.logoutButton.setOnClickListener {
    //           viewModel.logout()
    //       }
    //     }

    //   private fun playAnimation() {
    //     ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
    //       duration = 6000
    //         repeatCount = ObjectAnimator.INFINITE
    //         repeatMode = ObjectAnimator.REVERSE
    //     }.start()
    //      val login = ObjectAnimator.ofFloat(binding.logoutButton, View.ALPHA, 1f).setDuration(300)
    //      val title = ObjectAnimator.ofFloat(binding.nameTextView, View.ALPHA, 1f).setDuration(300)
    //      val desc = ObjectAnimator.ofFloat(binding.messageTextView, View.ALPHA, 1f).setDuration(300)
    //     AnimatorSet().apply {
    //         start()
    //  //      }
    //   }
}