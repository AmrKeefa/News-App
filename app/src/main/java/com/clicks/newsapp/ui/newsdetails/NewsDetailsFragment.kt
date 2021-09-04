package com.clicks.newsapp.ui.newsdetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.clicks.newsapp.R
import com.clicks.newsapp.data.model.NewsResponse
import com.clicks.newsapp.utils.Constants
import com.clicks.newsapp.utils.extensions.load
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_news_details.*

@AndroidEntryPoint
class NewsDetailsFragment : Fragment(R.layout.fragment_news_details) {

    private val article: NewsResponse.Article? by lazy {
        arguments?.getParcelable(Constants.Key.ARTICLE)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setNewsDetailsData()
    }

    private fun setNewsDetailsData() {
        article?.let { article ->
            article.urlToImage?.let { newsImageView.load(it) }
            sourceName.text = article.source?.name
            newsTitle.text = article.title
            newsDescription.text = article.description
        }
    }
}