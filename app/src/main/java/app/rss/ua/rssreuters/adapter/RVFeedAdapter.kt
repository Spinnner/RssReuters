package app.rss.ua.rssreuters.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.rss.ua.rssreuters.R
import app.rss.ua.rssreuters.model.RssFeed
import kotlinx.android.synthetic.main.rv_item_feed.view.*

/**
 * Created by Spinner on 18.09.2018.
 */
class RVFeedAdapter(private var list: List<RssFeed>, var listener: RVFeedListener) : RecyclerView.Adapter<RVFeedAdapter.MyViewHolder>() {

    interface RVFeedListener {
        fun onFeedClicked(rssFeed: RssFeed)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.rv_item_feed, parent, false)
        return MyViewHolder(view)
    }

    fun addList(list: List<RssFeed>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val rss = list[position]

        with(holder) {
            tvTitle.text = rss.title
            tvDescription.text = rss.description
            tvDate.text = rss.date
        }

        holder.layoutRoot.setOnClickListener { listener.onFeedClicked(rss) }
    }

    class MyViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        var layoutRoot = view.layoutRoot
        var tvTitle = view.tvTitle
        var tvDescription = view.tvDescription
        var tvDate = view.tvDate
    }

}