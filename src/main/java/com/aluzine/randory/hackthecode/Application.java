package com.aluzine.randory.hackthecode;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.SparkBase.staticFileLocation;
import java.util.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.aluzine.randory.hackthecode.user.User;
import spark.Request;
import spark.Response;

public class Application {
    final static Logger log = LoggerFactory.getLogger(Application.class);
    private static final long DELAY = 0 * 60 * 1000;
    private static final long PERIOD = 1 * 60 * 1000;

    private static GameEngine engine = new GameEngine();
    private static Timer timer = new Timer("Timer");

    public static void main(String[] args) {
        timer.scheduleAtFixedRate(engine, DELAY, PERIOD);
        staticFileLocation("/public");
        post("/user", (rq, rs) -> createUser(rq, rs));
        get("/ranking", (rq, rs) -> engine.rankedUsersAsJson());
        get("/instructions", (rq, rs) -> engine.isTimeForExtendedInstructions());
    }

    private static String createUser(Request rq, Response rs) {
        try {
            log.info(rq.queryParams("username"));
            engine.addUser(rq.queryParams("username"), new User(rq.queryParams("username"), rq.queryParams("url")));
            log.info("Adding user {}", rq.queryParams("username"));
            rs.status(200);
            return "created";
        } catch (Exception e) {
            rs.status(409);
            return "User already exist";
        }
    }
}
