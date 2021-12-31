package hu.gemesi.taskmaker.common.rest.client;

import hu.gemesi.taskmaker.dto.user.user.AddingUserRequestType;
import hu.gemesi.taskmaker.rest.apache.BaseApacheClient;
import org.apache.http.HttpResponse;

import javax.inject.Inject;

public class MatcherClient {

    @Inject
    private BaseApacheClient baseApacheClient;

    private static final String baseMatcherUrl = "http://localhost:8080/rest/matcherService/internal/task/";

    public HttpResponse getCheckTaskSolutionAdding(String taskName) throws Exception {
        String url = baseMatcherUrl.concat("checkTask/active/").concat(baseApacheClient.encodeURL(taskName));
        return baseApacheClient.sendClientBaseGet(url);
    }

    public HttpResponse getCheckTaskResult(String taskName) throws Exception {
        String url = baseMatcherUrl.concat("checkTask/exist/").concat(baseApacheClient.encodeURL(taskName));
        return baseApacheClient.sendClientBaseGet(url);
    }

    public HttpResponse getPointsForGroup(String loggedInUser, String taskName) throws Exception {
        String url = baseMatcherUrl.concat("result/points/").concat(baseApacheClient.encodeURL(loggedInUser) + "/")
                .concat(baseApacheClient.encodeURL(taskName));
        return baseApacheClient.sendClientBaseGet(url);
    }

    public HttpResponse deleteUserFromTask(String loggedInUser, String removingUser, String groupName) throws Exception {
        String url = baseMatcherUrl.concat("remove/user/").concat(baseApacheClient.encodeURL(loggedInUser) + "/")
                .concat(baseApacheClient.encodeURL(removingUser) + "/").concat(baseApacheClient.encodeURL(groupName));
        return baseApacheClient.sendClientBaseDelete(url);
    }

    public HttpResponse postAddUsersToTask(AddingUserRequestType addingUserRequestType) throws Exception {
        String url = baseMatcherUrl.concat("add/users");
        return baseApacheClient.sendClientBasePost(url, addingUserRequestType);
    }

    public HttpResponse getCheckTaskSolutionRemove(String taskName, String username) throws Exception {
        String url = baseMatcherUrl.concat("checkTask/solution/added/").concat(baseApacheClient.encodeURL(taskName) + "/")
                .concat(baseApacheClient.encodeURL(username));
        return baseApacheClient.sendClientBaseGet(url);
    }
}
