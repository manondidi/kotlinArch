package com.czq.kotlinarch.example

import androidx.fragment.app.Fragment
import com.czq.kotlin_arch.basePage.base.BaseActivity
import com.czq.kotlinarch.R
import com.czq.kotlinarch.example.fragment.CoverFragment
import com.czq.kotlinarch.example.fragment.PagingListFragment
import kotlinx.android.synthetic.main.activity_normal_fragment.*


class CoverFragmentActivity : BaseActivity<CoverContact.CoverPrensenter>(), CoverContact.CoverView {

    var currentFragment: Fragment? = null

    val fragment1: CoverFragment by lazy {
        CoverFragment.newInstance()
    }
    val fragment2: PagingListFragment by lazy {
        PagingListFragment.newInstance()
    }

    override fun createPresenter(): CoverContact.CoverPrensenter {
        return CoverPresenter(this)
    }

    override fun initView() {
        super.initView()
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

    override fun getLayoutId(): Int {
        return R.layout.activity_cover_fragment
    }
}