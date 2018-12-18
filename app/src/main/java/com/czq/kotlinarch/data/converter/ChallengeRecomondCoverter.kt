package com.czq.kotlinarch.data.converter


import com.czq.kotlinarch.data.model.ChallengeRecomand
import com.czq.kotlinarch.data.viewModel.ChallengeRecomandCircleVm
import com.czq.kotlinarch.data.viewModel.ChallengeRecommandItemVm
import com.czq.kotlinarch.data.viewModel.ChallengeRecommandSeeMoreVm

import java.util.ArrayList

object ChallengeRecomondCoverter {

    fun convert(challengeRecomand: ChallengeRecomand?): List<Any> {
        val list = arrayListOf<Any>()
        if (challengeRecomand != null && !challengeRecomand.challenges!!.isEmpty()) {
            val circleVm = ChallengeRecomandCircleVm()
            circleVm.id = challengeRecomand.id
            circleVm.title = challengeRecomand.title
            circleVm.challengeCount = challengeRecomand.challengeCount
            circleVm.subscribedCount = challengeRecomand.subscribedCount
            circleVm.hasSubscribed = challengeRecomand.hasSubscribed
            circleVm.icon = challengeRecomand.icon
            list.add(circleVm)
            var i = 0
            while (i < challengeRecomand.challenges!!.size && i < 4) {
                val challengesBean = challengeRecomand.challenges!![i]
                val itemVm = ChallengeRecommandItemVm()
                itemVm.index = i
                itemVm.id = challengesBean.id
                itemVm.cover = challengesBean.cover
                itemVm.takeOnCount = challengesBean.takeOnCount
                itemVm.candidatePoint = challengesBean.candidatePoint
                itemVm.candidateCount = challengesBean.candidateCount
                itemVm.title = challengesBean.title
                list.add(itemVm)
                i++
            }
            if (challengeRecomand.challenges!!.size > 4) {
                val seeMoreVm = ChallengeRecommandSeeMoreVm()
                seeMoreVm.id = challengeRecomand.id
                list.add(seeMoreVm)
            }
        }
        return list
    }
}