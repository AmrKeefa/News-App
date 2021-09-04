package com.clicks.newsapp.ui.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.clicks.newsapp.R
import com.clicks.newsapp.data.model.NewsResponse
import com.clicks.newsapp.ui.home.adapter.NewsRecyclerAdapter
import com.clicks.newsapp.utils.Constants
import com.clicks.newsapp.utils.ResourceState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*


@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModels()
    private val adapter by lazy { NewsRecyclerAdapter() }
    private var newList = mutableListOf<NewsResponse.Article>()
    private var filteredList = mutableListOf<NewsResponse.Article>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getNews()
        setupNewsSwipeListener()
        setupSearch()

    }

    private fun getNews() {
        viewModel.getNews().observe(viewLifecycleOwner) { resources ->
            when (resources.state) {
                ResourceState.LOADING -> {
                    newsSwipe.isRefreshing = true

                }
                ResourceState.SUCCESS -> {
                    newsSwipe.isRefreshing = false

                    resources.data?.let { response ->
                        response.articles?.let {
                            adapter.setData(it)
                            newList.addAll(it)
                        }



                        setNewsAdapter()
                    }
                }
                ResourceState.ERROR -> {
                    newsSwipe.isRefreshing = false
                    resources.message.let {
                    }
                }
            }
        }

    }

    private fun setupNewsSwipeListener() {
        newsSwipe.setOnRefreshListener {
            getNews()
        }
    }

    private fun setNewsAdapter() {
        homeRecycler.adapter = adapter

        adapter.setItemCallBack { article ->
            val bundle = bundleOf(Constants.Key.ARTICLE to article)
            view?.findNavController()
                ?.navigate(R.id.action_homeFragment_to_newsDetailsFragment, bundle)
        }
    }

    private fun setupSearch() {
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                filter(p0.toString())


            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }


        })
    }

    fun filter(text: String) {
        for (items in newList) {
            if (items.title?.lowercase()!!.contains(text.lowercase())) {

                filteredList.add(items)
            } else {
                filteredList.remove(items)
            }
        }
        adapter.clear()
        adapter.setData(filteredList)

    }
}