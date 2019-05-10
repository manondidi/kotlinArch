package com.czq.kotlinarch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.czq.kotlinarch.example.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnBasePageNoCover.setOnClickListener {
            startActivity(Intent(this@MainActivity,NoCoverActivity::class.java))
        }

        btnBasePage.setOnClickListener {
            startActivity(Intent(this@MainActivity,CoverActivity::class.java))
        }
        btnPagingList.setOnClickListener {
            startActivity(Intent(this@MainActivity, PagingListActivity::class.java))
        }

        btnFragmentActivity.setOnClickListener {
            startActivity(Intent(this@MainActivity, NormalFragmentActivity::class.java))
        }

        btnFragmentBaseActivity.setOnClickListener {
            startActivity(Intent(this@MainActivity, CoverFragmentActivity::class.java))
        }

        btnGameList.setOnClickListener {
            startActivity(Intent(this@MainActivity, GameListActivity::class.java))
        }

        btnFeedArticle.setOnClickListener {
            startActivity(Intent(this@MainActivity, FeedArticleActivity::class.java))
        }

        btnDatabinding.setOnClickListener {
            startActivity(Intent(this@MainActivity, DatabindingActivity::class.java))

        }
    }
}
