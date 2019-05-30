package com.crawlnews.study.config;

import java.net.InetAddress;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import com.crawlnews.study.vo.ElasticVO;

@EnableElasticsearchRepositories
@Configuration
public class ElasticConfiguration {

   @Value("${elasticsearch.host}")
   private String host;
   
   @Value("${elasticsearch.port}")
   private int port;
   
   @Value("${elasticsearch.clustername}")
   private String clusterName;
   
   
   @Bean 
    public Client client() throws Exception{ 
      System.out.println("clusterName : " + clusterName);
      System.out.println("host : " + host);
      System.out.println("port : " + port);
       Settings settings = Settings.builder().put("cluster.name", clusterName).build();
    
       TransportClient client = new PreBuiltTransportClient(settings);
       
       client.addTransportAddress(new TransportAddress(InetAddress.getByName(host), port)); 
       return client;
    }
     
     @Bean 
     public ElasticsearchOperations elasticsearchTemplate() throws Exception{ 
      ElasticsearchTemplate elasticsearchTemplate = new ElasticsearchTemplate(client());
      elasticsearchTemplate.putMapping(ElasticVO.class);
        return new ElasticsearchTemplate(client()); 
     }
}