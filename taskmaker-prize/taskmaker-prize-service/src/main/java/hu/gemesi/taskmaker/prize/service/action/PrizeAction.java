package hu.gemesi.taskmaker.prize.service.action;

import hu.gemesi.taskmaker.common.model.prize.Prize;
import hu.gemesi.taskmaker.common.rest.action.BaseAction;
import hu.gemesi.taskmaker.common.rest.userLogin.UserLoginHelper;
import hu.gemesi.taskmaker.dto.prize.prize.PrizeListResponse;
import hu.gemesi.taskmaker.dto.prize.prize.PrizeListType;
import hu.gemesi.taskmaker.dto.prize.prize.PrizeType;
import hu.gemesi.taskmaker.prize.service.service.PrizeService;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.util.List;

public class PrizeAction extends BaseAction {

    @Inject
    private PrizeService prizeService;

    @Inject
    private UserLoginHelper userLoginHelper;

    public PrizeListResponse getMyPrizes(String username) {
        var response = new PrizeListResponse();
        if (userLoginHelper.isLoggedIn(username)) {
            List<Prize> prizeList = prizeService.findByUsername(username);
            var prizeListType = new PrizeListType();
            for (Prize prize : prizeList) {
                var prizeType = new PrizeType();
                prizeType.setPrizeType(prize.getPrizeType());
                prizeType.setGroupName(prize.getGroup().getName());
                prizeType.setImageName("image_" + prize.getPrizeType());
                prizeListType.withPrize(prizeType);
            }
            return response.withPrizeList(prizeListType).withResult(createOKResponse().getResult());
        }
        return response.withResult(createNotPermittedResponse().getResult());
    }

    public Response getGroupPrizeImage(String groupName, String username, String fileName) {
        if (userLoginHelper.isLoggedIn(username)) {
            List<Prize> prizeList = prizeService.findByUsername(username);
            for (Prize prize : prizeList) {
                if (prize.getGroup().getName().equals(groupName) && prize.getUser().getUsername().equals(username)
                        && prize.getPrizeType().equals(fileName.split("_")[1])) {
                    File file = new File("C:/Users/gemes/Egyetem/Java webes alkalmazás/TaskMaker/DocumentResources/" + fileName + ".png");
                    return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM)
                            .header("Content-Disposition", "attachment; filename=\"" + fileName + "\"").build();
                }
            }
            return Response.status(500).entity(createInvalidResponse().getResult()).build();
        }
        return Response.status(403).entity(createNotPermittedResponse().getResult()).build();
    }
}
