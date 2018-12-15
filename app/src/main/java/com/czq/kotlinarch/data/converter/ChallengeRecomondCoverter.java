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
        if (challengeRecomand != null && !challengeRecomand.getChallenges().isEmpty()) {
            ChallengeRecomandCircleVm circleVm = new ChallengeRecomandCircleVm();
            circleVm.setId(challengeRecomand.getId());
            circleVm.setTitle(challengeRecomand.getTitle());
            circleVm.setChallengeCount(challengeRecomand.getChallengeCount());
            circleVm.setSubscribedCount(challengeRecomand.getSubscribedCount());
            circleVm.setHasSubscribed(challengeRecomand.getHasSubscribed());
            circleVm.setIcon(challengeRecomand.getIcon());
            list.add(circleVm);
            for (int i = 0; i < challengeRecomand.getChallenges().size() && i < 4; i++) {
                ChallengeRecomand.ChallengesBean challengesBean = challengeRecomand.getChallenges().get(i);
                ChallengeRecommandItemVm itemVm = new ChallengeRecommandItemVm();
                itemVm.setIndex(i);
                itemVm.setId(challengesBean.getId());
                itemVm.setCover(challengesBean.getCover());
                itemVm.setTakeOnCount(challengesBean.getTakeOnCount());
                itemVm.setCandidatePoint(challengesBean.getCandidatePoint());
                itemVm.setCandidateCount(challengesBean.getCandidateCount());
                itemVm.setTitle(challengesBean.getTitle());
                list.add(itemVm);
            }
            if (challengeRecomand.getChallenges().size() > 4) {
                ChallengeRecommandSeeMoreVm seeMoreVm = new ChallengeRecommandSeeMoreVm();
                seeMoreVm.setId(challengeRecomand.getId());
                list.add(seeMoreVm);
            }
        }
        return list;
    }
}