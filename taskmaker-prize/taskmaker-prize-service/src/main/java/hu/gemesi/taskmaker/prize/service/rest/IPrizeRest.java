package hu.gemesi.taskmaker.prize.service.rest;

import hu.gemesi.taskmaker.dto.prize.prize.PrizeListResponse;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/prize")
public interface IPrizeRest {

    String myString = "";

    @Path("/myPrizes/{username}")
    @GET
    @Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_HTML, MediaType.TEXT_PLAIN})
    PrizeListResponse getMyPrizes(@PathParam("username") String username);

    @Path("/prize/image/{groupName}/{username}/{fileName}")
    @GET
    @Produces(value = {MediaType.APPLICATION_OCTET_STREAM})
    Response getPrizeImage(@PathParam("groupName") String groupName, @PathParam("username") String username, @PathParam("fileName") String fileName);
}
