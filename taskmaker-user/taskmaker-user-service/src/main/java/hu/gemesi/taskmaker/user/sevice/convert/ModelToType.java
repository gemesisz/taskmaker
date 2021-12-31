package hu.gemesi.taskmaker.user.sevice.convert;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import hu.gemesi.taskmaker.common.model.group.Group;
import hu.gemesi.taskmaker.common.model.group.Grouping;
import hu.gemesi.taskmaker.common.model.user.CustomerUser;
import hu.gemesi.taskmaker.common.rest.client.MatcherClient;
import hu.gemesi.taskmaker.dto.user.user.GroupType;
import hu.gemesi.taskmaker.dto.user.user.UserDetailsListType;
import hu.gemesi.taskmaker.dto.user.user.UserDetailsType;
import hu.gemesi.taskmaker.dto.user.user.UserFullDetailsType;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import javax.inject.Inject;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ModelToType {

    @Inject
    private MatcherClient matcherClient;

    public GroupType modelToType(Group group, String loggedInUser) throws Exception {
        var groupType = new GroupType();
        groupType.setGroupName(group.getName());
        groupType.setAdmin(modelToType(group.getAdmin()));
        groupType.setActive(group.isActive());
        var userDetailsList = new UserDetailsListType();
        HttpResponse response = matcherClient.getPointsForGroup(loggedInUser, group.getName());
        String entity = EntityUtils.toString(response.getEntity());
        JsonObject jsonObject = new JsonParser().parse(entity).getAsJsonObject();
        JsonArray jsonArray = jsonObject.getAsJsonArray("pointInfo");
        List<UserDetailsType> userDetailsTypeList = new ArrayList<>();
        for (Grouping grouping : group.getGroupingList()) {
            for (JsonElement jsonElement : jsonArray) {
                String username = jsonElement.getAsJsonObject().get("username").getAsString();
                if (username.equals(grouping.getUser().getUsername())) {
                    int point = jsonElement.getAsJsonObject().get("point").getAsInt();
                    userDetailsTypeList.add(modelToType(grouping.getUser()).withPoint(point));
                }
            }
        }

        List<UserDetailsType> sorted = userDetailsTypeList.stream().sorted(Comparator.comparing(UserDetailsType::getPoint).reversed())
                .collect(Collectors.toList());
        return groupType.withCustomerUserList(userDetailsList.withCustomerUserList(sorted));
    }

    public UserDetailsType modelToType(CustomerUser customerUser) {
        return new UserDetailsType().withFirstName(customerUser.getFirstName()).withLastName(customerUser.getLastName())
                .withUsername(customerUser.getUsername());
    }

    public UserFullDetailsType modelToFullType(CustomerUser customerUser) {
        var response = new UserFullDetailsType();
        response = response.withFirstName(customerUser.getFirstName()).withLastName(customerUser.getLastName())
                .withBirthday(customerUser.getBirthday()).withUsername(customerUser.getUsername());
        return response;
    }

    public ZoneOffset getZoneOffset() {
        return ZoneOffset.of(ZoneOffset.systemDefault().getId());
    }
}
