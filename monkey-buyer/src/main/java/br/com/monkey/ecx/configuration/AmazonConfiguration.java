package br.com.monkey.ecx.configuration;

import com.netflix.appinfo.AmazonInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import static com.netflix.appinfo.AmazonInfo.MetaDataKey.localHostname;
import static com.netflix.appinfo.AmazonInfo.MetaDataKey.localIpv4;

@Configuration
public class AmazonConfiguration {

    @Value("${server.port}")
    private Integer port;

    @Bean
    @Profile("!default")
    public EurekaInstanceConfigBean eurekaInstanceConfig(InetUtils inetUtils) {
        EurekaInstanceConfigBean config = new EurekaInstanceConfigBean(inetUtils);
        AmazonInfo info = AmazonInfo.Builder.newBuilder().autoBuild("eureka");
        config.setDataCenterInfo(info);
        info.getMetadata().put(localHostname.getName(), info.get(localIpv4));
        config.setHostname(info.get(localHostname));
        config.setIpAddress(info.get(localIpv4));
        config.setNonSecurePort(port);
        config.setPreferIpAddress(true);
        config.setLeaseRenewalIntervalInSeconds(1);
        config.setLeaseExpirationDurationInSeconds(5);
        return config;
    }
}
