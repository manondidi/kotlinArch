package com.czq.kotlinarch.example

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.czq.kotlinarch.R
import com.czq.kotlinarch.example.fragment.CoverFragment
import com.czq.kotlinarch.example.fragment.PagingListFragment
import kotlinx.android.synthetic.main.activity_normal_fragment.*

class NormalFragmentActivity : AppCompatActivity() {

    var currentFragment: Fragment? = null

    val fragment1: CoverFragment by lazy {
        CoverFragment.newInstance()
    }
    val fragment2: PagingListFragment by lazy {
        PagingListFragment.newInstance()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_normal_fragment)

        showFragment(fragment1)

        btn1.setOnClickListener {
            showFragment(fragment1)
        }

        btn2.setOnClickListener {
            showFragment(fragment2)
        }
    }

    private fun showFragment(targetFragment: Fragment) {
        val transaction = supportFragmentManager
            .beginTransaction()
        if (!targetFragment.isAdded) {
            if (currentFragment != null) {
                transaction.hide(currentFragment!!)
            }
            transaction.add(R.id.flContainer, targetFragment)
                .commit()
        } else {
            if (currentFragment != null) {
                transaction.hide(currentFragment!!)
            }
            transaction
                .show(targetFragment)
                .commit()
        }
        currentFragment = targetFragment
    }


}