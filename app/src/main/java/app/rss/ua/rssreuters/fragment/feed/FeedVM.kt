package app.rss.ua.rssreuters.fragment.feed

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import app.rss.ua.rssreuters.RssManager
import app.rss.ua.rssreuters.bean.SingleLiveEvent
import app.rss.ua.rssreuters.model.RssFeed
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

/**
 * Created by Spinner on 18.09.2018.
 */
class FeedVM : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private var listFeed = MutableLiveData<List<RssFeed>>()
    private val isLoading = MutableLiveData<Boolean>()
    private val toastMessage: MutableLiveData<String> = SingleLiveEvent<String>()

    private val rssManager = RssManager()


    fun getFeed() = listFeed
    fun getLoading() = isLoading
    fun getToast() = toastMessage

    fun loadNews() {
        rssManager.getNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { isLoading.value = true }
                .subscribe(::success, ::error)
                .addTo(compositeDisposable)
    }

    fun loadMergedFeed() {
        rssManager.getMergedFeed()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { isLoading.value = true }
                .subscribe(::success, ::error)
                .addTo(compositeDisposable)
    }

    private fun success(list: List<RssFeed>) {
        listFeed.value = list
        isLoading.value = false
    }

    private fun error(throwable: Throwable) {
        toastMessage.value = throwable.message
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

}