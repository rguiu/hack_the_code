import static java.net.URLDecoder.decode;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.SparkBase.port;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import spark.Request;


public class Example {

    public static void main(String[] args) {
        port(3000);
        get("/solve/:magicword", (rq, rs) -> calculate(decode(rq.params("magicword"), "UTF-8")));
        post("/stats", (rq, rs) -> print(rq));
    }

    private static String calculate(String magicWord) {
        return "+.<-.";
    }

    private static int print(Request rq) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .disableHtmlEscaping()
                .create();
        JsonParser parser = new JsonParser();
        JsonElement je = parser.parse(rq.body());
        System.out.println(gson.toJson(je));
        return 200;
    }
}
