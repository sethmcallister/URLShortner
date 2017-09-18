package life.kue.shortener.page.to;

import life.kue.shortener.Main;
import life.kue.shortener.dto.Link;
import life.kue.shortener.page.Page;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.Map;

public class ToGet extends Page implements Route {
    @Override
    public Object handle(Request request, Response response) throws Exception {
        Map<String, Object> map = new HashMap<>();

        String url = request.params("url");

        Link link = Main.getInstance().findByToURL(url);
        map.put("no_link", false);

        if(link == null) {
            map.put("no_link", true);
            return render(request, map, Template.TO);
        }
        System.out.println("no null");
        map.put("redirect", link.getOutgoing());

        return render(request, map, Template.TO);
    }
}
