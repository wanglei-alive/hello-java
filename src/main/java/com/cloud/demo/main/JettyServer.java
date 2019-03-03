package com.cloud.demo.main;

import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

public class JettyServer {

    public static void start() {

        final AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        applicationContext.register(WebConfig.class);

        final ServletHolder servletHolder = new ServletHolder(new DispatcherServlet(applicationContext));
        final ServletContextHandler context = new ServletContextHandler();
        context.setContextPath("/");
        context.addServlet(servletHolder, "/*");


        // 编码过滤器
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("utf-8");
        characterEncodingFilter.setForceEncoding(true);
        FilterHolder filterHolder = new FilterHolder(characterEncodingFilter);
        context.addFilter(filterHolder, "/*", EnumSet.allOf(DispatcherType.class));

        QueuedThreadPool queuedThreadPool = new QueuedThreadPool();
        queuedThreadPool.setMinThreads(100);
        queuedThreadPool.setMaxThreads(1000);
        queuedThreadPool.setIdleTimeout(60000);
        queuedThreadPool.setStopTimeout(5000);
        queuedThreadPool.setDetailedDump(false);

        final Server server = new Server();
        server.setHandler(context);
        ServerConnector networkTrafficServerConnector =
                new ServerConnector(server, queuedThreadPool, null, null, -1, -1,
                        new HttpConnectionFactory());
        networkTrafficServerConnector.setAcceptQueueSize(128);
        networkTrafficServerConnector.setPort(8080);
        server.addConnector(networkTrafficServerConnector);
        try {
            server.start();
        } catch (Exception e) {
            System.exit(1);
        }

    }
}
