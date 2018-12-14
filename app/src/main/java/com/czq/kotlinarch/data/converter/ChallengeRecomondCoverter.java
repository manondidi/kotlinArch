package com.czq.kotlinarch.data.converter;


import com.czq.kotlinarch.data.model.ChallengeRecomand;
import com.czq.kotlinarch.data.viewModel.ChallengeRecomandCircleVm;
import com.czq.kotlinarch.data.viewModel.ChallengeRecommandItemVm;
import com.czq.kotlinarch.data.viewModel.ChallengeRecommandSeeMoreVm;

import java.util.ArrayList;
import java.util.List;

public class ChallengeRecomondCoverter {

    public static List<Object> convert(ChallengeRecomand challengeRecomand) {
        List list = new ArrayList();
        if (challengeRecomand != null && !challengeRecomand.challenges.isEmpty()) {
            ChallengeRecomandCircleVm circleVm = new ChallengeRecomandCircleVm();
            circleVm.id = challengeRecomand.id;
            circleVm.title = challengeRecomand.title;
            circleVm.challengeCount = challengeRecomand.challengeCount;
            circleVm.subscribedCount = challengeRecomand.subscribedCount;
            circleVm.hasSubscribed = challengeRecomand.hasSubscribed;
            circleVm.icon = challengeRecomand.icon;
            list.add(circleVm);
            for (int i = 0; i < challengeRecomand.challenges.size() && i < 4; i++) {
                ChallengeRecomand.ChallengesBean challengesBean = challengeRecomand.challenges.get(i);
                ChallengeRecommandItemVm itemVm = new ChallengeRecommandItemVm();
                itemVm.index = i;
                itemVm.id = challengesBean.id;
                itemVm.cover = challengesBean.cover;
                itemVm.takeOnCount = challengesBean.takeOnCount;
                itemVm.candidatePoint = challengesBean.candidatePoint;
                itemVm.candidateCount = challengesBean.candidateCount;
                itemVm.title = challengesBean.title;
                list.add(itemVm);
            }
            if (challengeRecomand.challenges.size() > 4) {
                ChallengeRecommandSeeMoreVm seeMoreVm = new ChallengeRecommandSeeMoreVm();
                seeMoreVm.id = challengeRecomand.id;
                list.add(seeMoreVm);
            }
        }
        return list;
    }
}