package com.user.service.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.web.client.RestTemplate;

@Configuration
public class UserConfig {

	@Autowired
	ClientRegistrationRepository clientRegistrationRepository;
	
	@Autowired
	OAuth2AuthorizedClientRepository oAuth2AuthorizedClientRepository;
	
	
    @Bean
    @LoadBalanced
    RestTemplate resttemplate() {
    	RestTemplate temp=new RestTemplate();
    	List<ClientHttpRequestInterceptor> list=new ArrayList<ClientHttpRequestInterceptor>();
    	list.add(new RestTemplateInterceptor(manager(clientRegistrationRepository,oAuth2AuthorizedClientRepository)));
    	temp.setInterceptors(list);
		return temp;
	}
    
    
    
    
    
    public OAuth2AuthorizedClientManager manager(
    		ClientRegistrationRepository clientRegistrationRepository,
    		OAuth2AuthorizedClientRepository oAuth2AuthorizedClientRepository
    		) {
    	
    	OAuth2AuthorizedClientProvider provider=OAuth2AuthorizedClientProviderBuilder.builder().clientCredentials().build();
    	DefaultOAuth2AuthorizedClientManager defaultOAuth2AuthorizedClientManager=new DefaultOAuth2AuthorizedClientManager(clientRegistrationRepository, oAuth2AuthorizedClientRepository);
    	defaultOAuth2AuthorizedClientManager.setAuthorizedClientProvider(provider);
    	return defaultOAuth2AuthorizedClientManager;
    	
    }
}
