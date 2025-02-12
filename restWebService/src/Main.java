import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.net.httpserver.HttpServer;
import org.apache.log4j.Logger;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static final Logger LOGGER = Logger.getLogger(String.valueOf(Main.class));

    public static void main(String[] args) throws Exception {
        LOGGER.info("this is info");
        LOGGER.debug("this is debug");
        LOGGER.error("this is error");
        LOGGER.warn("this is warning");
        ResourceConfig config = new PackagesResourceConfig("api");
        HttpServer server = HttpServerFactory.create("http://localhost:8080/",config);
        server.start();
    }
}