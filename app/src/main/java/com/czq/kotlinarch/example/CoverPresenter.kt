package com.czq.kotlinarch.example

import com.czq.kotlinarch.data.converter.ChallengeRecomondCoverter
import com.czq.kotlinarch.data.remote.RemoteDataRepository
import com.uber.autodispose.lifecycle.autoDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CoverPresenter(var mView: CoverContact.CoverView) : CoverContact.CoverPrensenter {


    val mRemoteDataRepository: RemoteDataRepository by lazy {
        RemoteDataRepository()
    }

    override fun start() {

        mView.showLoading()
        mRemoteDataRepository.getUser()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .autoDisposable(mView.autoDispose())
            .subscribe({ it ->
                mView.showContent()
            }, {
                mView.showError()
            })

    }

}