package gov.va.ascent.discovery;

import org.apache.catalina.Context;
import org.apache.catalina.webresources.StandardRoot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.Bean;
import org.springframework.metrics.export.prometheus.EnablePrometheusMetrics;

/**
 * An <tt>Ascent Discovery Server</tt> enabled for Spring Boot Application and
 * Spring Cloud Netflix Eureka service registry.
 *
 */
@SpringBootApplication
@EnableEurekaServer
@EnableDiscoveryClient
@EnablePrometheusMetrics
public class AscentDiscoveryApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(AscentDiscoveryApplication.class);

	/*
	 * @ V a l u e("${s e r v e r . p o r t:8761}")
	 * p r i v a t e i n t port;
	 */

	public static void main(final String[] args) {
		SpringApplication.run(AscentDiscoveryApplication.class, args);
	}

	@Bean
	public EmbeddedServletContainerFactory servletContainer() {
		return new TomcatEmbeddedServletContainerFactory() {
			@Override
			protected void postProcessContext(final Context context) {
				final int cacheSize = 40 * 1024;
				final StandardRoot standardRoot = new StandardRoot(context);
				standardRoot.setCacheMaxSize(cacheSize);
				context.setResources(standardRoot); // This is what made it work in my case.

				LOGGER.info(String.format("New cache size (KB): %d", context.getResources().getCacheMaxSize()));
			}
		};
	}
}
