package app.rss.ua.rssreuters.fragment.feed


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.rss.ua.rssreuters.HAWK_LABEL
import app.rss.ua.rssreuters.KEY_RSS_URL
import app.rss.ua.rssreuters.KEY_TAB_POSITION
import app.rss.ua.rssreuters.R
import app.rss.ua.rssreuters.activity.FeedActivity
import app.rss.ua.rssreuters.adapter.RVFeedAdapter
import app.rss.ua.rssreuters.model.RssFeed
import com.orhanobut.hawk.Hawk
import kotlinx.android.synthetic.main.fragment_feed.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast


class FeedFragment : Fragment(), RVFeedAdapter.RVFeedListener {

    private lateinit var viewModel: FeedVM

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this).get(FeedVM::class.java)

        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initViews()
        loadFeed()
        observeVM()
    }

    private fun initViews() {
        swipeRefreshLayout.isEnabled = false
        rvFeed.adapter = RVFeedAdapter(listOf(), this)
    }

    private fun loadFeed() {
        swipeRefreshLayout.isRefreshing = true
        val tabPosition = arguments?.get(KEY_TAB_POSITION)
        when(tabPosition) {
            0 -> viewModel.loadNews()
            else -> viewModel.loadMergedFeed()
        }
    }

    private fun observeVM() {
        with(viewModel) {
            getLoading().observe(this@FeedFragment, Observer {
                it?.let { swipeRefreshLayout.isRefreshing = it }
            })

            getFeed().observe(this@FeedFragment, Observer {
                it?.let {
                    (rvFeed.adapter as RVFeedAdapter).addList(it)
                }
            })

            getToast().observe(this@FeedFragment, Observer {
                it?.let { activity?.toast(it) }
            })
        }
    }

    override fun onFeedClicked(rssFeed: RssFeed) {
        Hawk.put(HAWK_LABEL, rssFeed.title)
        activity?.let { startActivity(it.intentFor<FeedActivity>(KEY_RSS_URL to rssFeed.link)) }
    }

}
