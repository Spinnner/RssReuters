package app.rss.ua.rssreuters

import android.util.Xml
import app.rss.ua.rssreuters.model.RssFeed
import io.reactivex.Observable
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.net.URL
import java.util.concurrent.TimeUnit

/**
 * Created by Spinner on 18.09.2018.
 */
class RssManager {

    private val urlNews = URL("http://feeds.reuters.com/reuters/businessNews")
    private val urlEntertainment = URL("http://feeds.reuters.com/reuters/entertainment")
    private val urlEnvironment = URL("http://feeds.reuters.com/reuters/environment")

    private val UPDATE_PERIOD = 5L

    fun getNews() =
        Observable.interval(0, UPDATE_PERIOD, TimeUnit.SECONDS)
                .flatMap { parseFeed(urlNews) }

    fun getMergedFeed() = Observable.interval(0, 5, TimeUnit.SECONDS)
            .flatMap { parseFeed(urlEntertainment) }
            .map { val list2 = parseFeed(urlEnvironment).blockingFirst()
                val list3 = ArrayList<RssFeed>()
                list3.addAll(it)
                list3.addAll(list2)
                list3
            }


    @Throws(XmlPullParserException::class, IOException::class)
    fun parseFeed(url: URL): Observable<List<RssFeed>> {
        return Observable.create {emitter ->
            val inputStream = url.openConnection().getInputStream()

            var title = ""
            var link = ""
            var description = ""
            var date = ""
            var isItem = false
            val items = mutableListOf<RssFeed>()

            try {
                val xmlPullParser = Xml.newPullParser()
                xmlPullParser.setInput(inputStream, null)

                xmlPullParser.nextTag()
                while (xmlPullParser.next() != XmlPullParser.END_DOCUMENT) {
                    val eventType = xmlPullParser.eventType

                    val name = xmlPullParser.name ?: continue

                    if (eventType == XmlPullParser.START_TAG) {
                        if (name.equals("item", ignoreCase = true)) {
                            isItem = true
                            continue
                        }
                    }

                    else if (eventType == XmlPullParser.END_TAG) {
                        if (name.equals("item", ignoreCase = true)) {
                            isItem = false
                        }
                        continue
                    }

                    var result = ""
                    if (xmlPullParser.next() == XmlPullParser.TEXT) {
                        result = xmlPullParser.text
                        xmlPullParser.nextTag()
                    }

                    if (name.equals("title", ignoreCase = true)) {
                        title = result
                    } else if (name.equals("link", ignoreCase = true)) {
                        link = result
                    } else if (name.equals("description", ignoreCase = true)) {
                        description = result.substringBefore("<div class")
                    }
                    else if (name.equals("pubDate", ignoreCase = true)) {
                        date = result
                    }

                    if (title.isNotEmpty() && description.isNotEmpty() && link.isNotEmpty() && date.isNotEmpty()) {
                        if (isItem) {
                            val item = RssFeed(title, description, date, link)
                            items.add(item)
                        }

                        title = ""
                        link = ""
                        description = ""
                        date = ""
                        isItem = false
                    }
                }

                emitter.onNext(items)
            }
            finally {
                inputStream.close()
            }
        }
    }
}