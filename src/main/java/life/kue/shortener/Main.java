package life.kue.shortener;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import life.kue.shortener.dto.Link;
import life.kue.shortener.page.Page;
import life.kue.shortener.page.add.AddGet;
import life.kue.shortener.page.to.ToGet;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import static spark.Spark.get;

public class Main {
    public static void main(String[] args) {
        new Main();
    }

    private static Main instance;
    private final List<Link> links;
    private final Gson gson;
    private final String fileName;

    public Main() {
        setInstance(this);
        this.gson = new Gson();
        this.fileName = "./links.json";
        this.links = new LinkedList<>();

        loadLinks();

        spark.debug.DebugScreen.enableDebugScreen();

        get(Page.Web.TO, new ToGet());
        get(Page.Web.ADD, new AddGet());
    }

    private static synchronized void setInstance(final Main newInstance) {
        instance = newInstance;
    }

    public static synchronized Main getInstance() {
        return instance;
    }

    public List<Link> getLinks() {
        return links;
    }

    private void loadLinks() {
        File file = new File(this.fileName);
        List<Link> links = new LinkedList<>();
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            JsonParser jsonParser = new JsonParser();

            try(FileReader fileReader = new FileReader(this.fileName)) {
                JsonElement element = jsonParser.parse(fileReader);
                Type type = new TypeToken<List<Link>>(){}.getType();
                List<Link> links1 = this.gson.fromJson(element, type);
                if(links1 != null)
                    links.addAll(links1);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        getLinks().addAll(links);
    }

    private void saveLinks() {
        String json = this.gson.toJson(this.links);

        File file = new File(this.fileName);
        if(!file.exists())
            try {
                file.createNewFile();
            }
            catch (IOException e) {
                e.printStackTrace();
            }

        try(FileWriter writer = new FileWriter(file)) {
            writer.write(json);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Link findByToURL(final String incoming) {
        return this.links.stream().filter(link -> link.getIncoming().equalsIgnoreCase(incoming)).findFirst().orElse(null);
    }

    public Link generateLink(final String outgoing) {
        String incoming = RandomStringUtils.randomAlphanumeric(8).toUpperCase();
        while(this.findByToURL(incoming) != null) {
            incoming = RandomStringUtils.randomAlphanumeric(8).toUpperCase();
        }
        Link link = new Link(incoming, outgoing);
        this.links.add(link);
        saveLinks();
        return link;
    }
}
