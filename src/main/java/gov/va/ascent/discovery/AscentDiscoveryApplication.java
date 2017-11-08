package gov.va.ascent.discovery;

import org.apache.catalina.Context;
import org.apache.catalina.webresources.StandardRoot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.Bean;

/**
 * An <tt>Ascent Discovery Server</tt> enabled for Spring Boot Application and 
 * Spring Cloud Netflix Eureka service registry.
 *
 */
@SpringBootApplication
@EnableEurekaServer
@EnableDiscoveryClient
public class AscentDiscoveryApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(AscentDiscoveryApplication.class);
	
	@Value("${server.port:8761}") 
    private int port;
	
	public static void main(String[] args) {	
        SpringApplication.run(AscentDiscoveryApplication.class, args);
	}

	@Bean
	public EmbeddedServletContainerFactory servletContainer() {
	    return new TomcatEmbeddedServletContainerFactory() {
	        @Override
	        protected void postProcessContext(Context context) {
	            final int cacheSize = 40 * 1024;
	            StandardRoot standardRoot = new StandardRoot(context);
	            standardRoot.setCacheMaxSize(cacheSize);
	            context.setResources(standardRoot); // This is what made it work in my case.

	            LOGGER.info(String.format("New cache size (KB): %d", context.getResources().getCacheMaxSize()));
	        }
	    };
	}
}
