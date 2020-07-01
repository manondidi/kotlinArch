package com.czq.kotlinarch.example

import com.czq.kotlin_arch.basePage.base.IBasePagingPrensenter
import com.czq.kotlin_arch.basePage.base.IBasePagingView

class GameListContact {


    interface PagingListView : IBasePagingView {
        fun hello()

    }

    interface PagingListPresenter : IBasePagingPrensenter {

    }


}