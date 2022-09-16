package com.example.demo.client;

import feign.Contract;
import feign.QueryMapEncoder;
import feign.Retryer;
import feign.codec.Decoder;
import feign.codec.Encoder;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.*;
import org.springframework.cloud.openfeign.clientconfig.FeignClientConfigurer;
import org.springframework.cloud.openfeign.support.AbstractFormWriter;
import org.springframework.cloud.openfeign.support.HttpMessageConverterCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@EnableFeignClients(value = {},defaultConfiguration = {})
@SpringBootApplication
public class DemoClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoClientApplication.class, args);
	}


	@RestController
	public class ClientController{

		@Autowired
		private OpenServerService openServerService;

		@RequestMapping(value = "/client",method = RequestMethod.GET)
		public String sayClient(){
			return "hello, this client";
		}

		@RequestMapping(value = "/api/server",method = RequestMethod.GET)
		public String remoteServer(){
			return openServerService.iSayServer();
		}

	}

	@FeignClient(name = "server",url = "http://localhost:8000")
	public interface OpenServerService{
		@RequestMapping(value = "/server",method = RequestMethod.GET)
		String iSayServer();
	}

}

