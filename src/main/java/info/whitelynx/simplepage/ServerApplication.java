package info.whitelynx.simplepage;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import info.whitelynx.simplepage.rest.MainResource;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.core.UriBuilder;
import java.time.Clock;

public class ServerApplication {
    static final int SERVER_PORT = 8181;

    public static void main(String[] args) throws Exception {
        Server server = JettyHttpContainerFactory.createServer(UriBuilder.fromUri("http://localhost")
                .port(ServerApplication.SERVER_PORT).build(), new MainResourceConfig(), false);
        ContextHandler contextResourceHandler = new ContextHandler();
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(true);
        resourceHandler.setWelcomeFiles(new String[] { "index.html" });
        resourceHandler.setResourceBase(
                ServerApplication.class.getClassLoader().getResource("webclient").toExternalForm());
        contextResourceHandler.setHandler(resourceHandler);

        ContextHandler contextHandler = new ContextHandler("/rest");
        contextHandler.setHandler(server.getHandler());

        HandlerList handlerList = new HandlerList();
        handlerList.setHandlers(new Handler[] {contextResourceHandler, contextHandler, new DefaultHandler()});
        server.setHandler(handlerList);
        server.start();
    }
}

class MainModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Clock.class).toInstance(Clock.systemUTC());
    }
}

class MainResourceConfig extends ResourceConfig {
    public MainResourceConfig() {
        Injector injector = Guice.createInjector(new MainModule());
        // register all resources
        register(injector.getInstance(MainResource.class));
    }
}
