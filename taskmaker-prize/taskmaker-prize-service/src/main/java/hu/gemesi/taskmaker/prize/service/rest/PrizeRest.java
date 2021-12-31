package hu.gemesi.taskmaker.prize.service.rest;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import hu.gemesi.taskmaker.dto.prize.prize.PrizeListResponse;
import hu.gemesi.taskmaker.prize.service.action.PrizeAction;

public class PrizeRest implements IPrizeRest {

    @Inject
    private PrizeAction prizeAction;

    @Override
    public PrizeListResponse getMyPrizes(String username) {
        return prizeAction.getMyPrizes(username);
    }

    @Override
    public Response getPrizeImage(String groupName, String username, String fileName) {
        return prizeAction.getGroupPrizeImage(groupName, username, fileName);
    }

    private void wwww(String[] strings) {

    }
}
