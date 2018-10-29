package app.rss.ua.rssreuters.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.view.MenuItem
import app.rss.ua.rssreuters.R
import app.rss.ua.rssreuters.fragment.tabs.Tab1Fragment
import app.rss.ua.rssreuters.fragment.tabs.Tab2Fragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    private fun initViews() {
        loadFragment(Tab1Fragment())
        bottomNavigation.setOnNavigationItemSelectedListener (this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val fragment: Fragment
        when (item.itemId) {
            R.id.navigation_tab_1 -> {
                supportActionBar?.title = getString(R.string.tab_1)
                fragment = Tab1Fragment()
                loadFragment(fragment)
                return true
            }
            R.id.navigation_tab_2 -> {
                supportActionBar?.title = getString(R.string.tab_2)
                fragment = Tab2Fragment()
                loadFragment(fragment)
                return true
            }
        }
        return false
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        with(transaction) {
            replace(R.id.frame_container, fragment)
            addToBackStack(null)
            commit()
        }
    }
}
