package com.lealpy.simbirsoft_training.ui.news

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.lealpy.simbirsoft_training.R
import com.lealpy.simbirsoft_training.databinding.FragmentNewsBinding
import com.lealpy.simbirsoft_training.ui.news.news_description.NewsDescriptionFragment

class NewsFragment : Fragment(R.layout.fragment_news) {

    private lateinit var binding : FragmentNewsBinding

    private val viewModel : NewsViewModel by activityViewModels()

    private val newsAdapter = NewsItemAdapter(
        object : NewsItemAdapter.OnItemClickListener {
            override fun onItemClick(newsItem: NewsItem) {

                val args = bundleOf(
                    NewsDescriptionFragment.TITLE_KEY to newsItem.title,
                    NewsDescriptionFragment.DATE_KEY to newsItem.date,
                    NewsDescriptionFragment.FUND_NAME_KEY to newsItem.fundName,
                    NewsDescriptionFragment.ADDRESS_KEY to newsItem.address,
                    NewsDescriptionFragment.PHONE_KEY to newsItem.phone,
                    NewsDescriptionFragment.FULL_TEXT_KEY to newsItem.fullText,
                    NewsDescriptionFragment.IMAGE_KEY to newsItem.image,
                    NewsDescriptionFragment.IMAGE_2_KEY to newsItem.image2,
                    NewsDescriptionFragment.IMAGE_3_KEY to newsItem.image3,
                )

                findNavController().navigate(
                    R.id.actionNavigationNewsToNewsDescriptionFragment,
                    args

                )
            }
        }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentNewsBinding.bind(view)

        initViews()
        initObservers()
        initToolbar()
    }

    private fun initToolbar() {
        setHasOptionsMenu(true);

        val appCompatActivity = (activity as? AppCompatActivity)
        appCompatActivity?.setSupportActionBar(binding.toolbar)
        appCompatActivity?.supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun initViews() {
        binding.recyclerView.adapter = newsAdapter

        val newsItemDecoration = activity?.resources?.getDimension(R.dimen.dimen_8_dp)?.let { spacing ->
            NewsItemDecoration(spacing.toInt())
        }

        if(newsItemDecoration != null) {
            binding.recyclerView.addItemDecoration(newsItemDecoration)
        }

    }

    private fun initObservers() {
        viewModel.newsItems.observe(viewLifecycleOwner) { newsItems ->
            newsAdapter.submitList(newsItems)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.news_toolbar_menu, menu)
        super.onCreateOptionsMenu(menu,inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.newsToolbarFilter -> {
                findNavController().navigate(R.id.actionNavigationNewsToNewsFilterFragment)
                // save to VM ->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            }
        }
        return true
    }

}
