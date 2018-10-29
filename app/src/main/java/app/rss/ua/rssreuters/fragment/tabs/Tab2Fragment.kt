package app.rss.ua.rssreuters.fragment.tabs


import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.rss.ua.rssreuters.KEY_TAB_POSITION

import app.rss.ua.rssreuters.R
import app.rss.ua.rssreuters.fragment.feed.FeedFragment
import kotlinx.android.synthetic.main.fragment_tab2.*

class Tab2Fragment : Fragment(), TabLayout.OnTabSelectedListener {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_tab2, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        loadFeedFragment(0)
        with(tabLayout) {
            setTabTextColors(Color.BLACK, Color.WHITE)
            addTab(tabLayout.newTab().setText("Feed 1"))
            addTab(tabLayout.newTab().setText("Feed 2"))
            addOnTabSelectedListener(this@Tab2Fragment)
        }
    }


    override fun onTabReselected(tab: TabLayout.Tab?) {

    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {

    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        tab?.let { loadFeedFragment(it.position) }
    }

    private fun loadFeedFragment(tabPosition: Int) {
        val fragment = FeedFragment()
        val bundle = Bundle()
        bundle.putInt(KEY_TAB_POSITION, tabPosition)
        fragment.arguments = bundle

        val transaction = fragmentManager?.beginTransaction()
        transaction?.run {
            replace(R.id.frame_child_container, fragment)
            addToBackStack(null)
            commit()
        }

    }

}
