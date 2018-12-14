package com.czq.kotlinarch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.czq.kotlinarch.example.CoverActivity
import com.czq.kotlinarch.example.PagingListActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnBasePage.setOnClickListener {
            startActivity(Intent(this@MainActivity,CoverActivity::class.java))
        }
        btnPagingList.setOnClickListener {
            startActivity(Intent(this@MainActivity, PagingListActivity::class.java))
        }
    }
}
