package app.rss.ua.rssreuters.fragment.tabs


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.rss.ua.rssreuters.HAWK_LABEL
import app.rss.ua.rssreuters.R
import com.orhanobut.hawk.Hawk
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_tab1.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class Tab1Fragment : Fragment() {

    private val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm:ss")
    private var currentDate = ""
    private lateinit var disposable: Disposable

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tab1, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initViews()
        updateCurrentTime()
    }

    private fun initViews() {
        val savedTitle = Hawk.get(HAWK_LABEL, "")

        if(savedTitle.isNotEmpty()) {
            tvLabel.text = savedTitle
        }
    }

    private fun updateCurrentTime() {
        disposable = Observable.interval(0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    currentDate = sdf.format(Date().time)
                    tvCurrentDate.text = getString(R.string.current_date, currentDate)
                }
    }

    override fun onDestroyView() {
        disposable.dispose()
        super.onDestroyView()
    }
}
