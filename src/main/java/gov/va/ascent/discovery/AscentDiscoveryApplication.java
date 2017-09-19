package gov.va.ascent.discovery;

import org.apache.catalina.Context;
import org.apache.catalina.webresources.StandardRoot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;

import com.netflix.appinfo.AmazonInfo;

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
	
	@Bean
    @Autowired
    @Profile({ "aws-ci", "aws-dev", "aws-stage", "aws-prod" })
    public EurekaInstanceConfigBean eurekaInstanceConfigBean(InetUtils inetUtils) {
	  LOGGER.info("*** eurekaInstanceConfigBean starts here ***");
	  
	  final EurekaInstanceConfigBean config = new EurekaInstanceConfigBean(inetUtils) {
	   @Scheduled(initialDelay = 30000L, fixedRate = 30000L)
	   public void refreshInfo() {
	    AmazonInfo newInfo = AmazonInfo.Builder.newBuilder().autoBuild("eureka");
	    if (!this.getDataCenterInfo().equals(newInfo)) {
	     ((AmazonInfo) this.getDataCenterInfo()).setMetadata(newInfo.getMetadata());
	     this.setHostname(newInfo.get(AmazonInfo.MetaDataKey.publicHostname));
	     this.setIpAddress(newInfo.get(AmazonInfo.MetaDataKey.publicIpv4));
	     this.setDataCenterInfo(newInfo);
	     this.setNonSecurePort(port);
	    }
	   }         
	  };
	  
      AmazonInfo info = AmazonInfo.Builder.newBuilder().autoBuild("eureka");
      LOGGER.info("Info: {}", info);
      config.setHostname(info.get(AmazonInfo.MetaDataKey.publicHostname));
      config.setIpAddress(info.get(AmazonInfo.MetaDataKey.publicIpv4));
      LOGGER.info("*** LOCAL HOSTNAME: {}", info.get(AmazonInfo.MetaDataKey.localHostname));
      LOGGER.info("*** LOCAL IP: {}", info.get(AmazonInfo.MetaDataKey.localIpv4));
      LOGGER.info("*** PUBLIC HOSTNAME: {}", info.get(AmazonInfo.MetaDataKey.publicHostname));
      LOGGER.info("*** PUBLIC IP: {}", info.get(AmazonInfo.MetaDataKey.publicIpv4));
      config.setNonSecurePort(port);
      config.setDataCenterInfo(info);
      return config;
    }
}
