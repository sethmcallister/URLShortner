package life.kue.shortener.page.add;

import com.google.gson.Gson;
import life.kue.shortener.Main;
import life.kue.shortener.dto.AddResponse;
import life.kue.shortener.dto.Link;
import life.kue.shortener.page.Page;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.Map;

public class AddGet extends Page implements Route {
    @Override
    public Object handle(Request request, Response response) throws Exception {
        Map<String, Object> map = new HashMap<>();

        String url = request.params("url");

        String json;

        try {
            Link link = Main.getInstance().generateLink(url);
            AddResponse addResponse = new AddResponse(AddResponse.Status.OK, link.getIncoming());
            json = new Gson().toJson(addResponse);
        } catch (Exception e) {
            AddResponse addResponse = new AddResponse(AddResponse.Status.BAD, null);
            json = new Gson().toJson(addResponse);
        }

        map.put("json", json);

        response.header("Content-type", "application/json");
        response.type("application/json");
        response.raw().addHeader("Content-type", "application/json");
        return render(request, map, Template.ADD);
    }
}
