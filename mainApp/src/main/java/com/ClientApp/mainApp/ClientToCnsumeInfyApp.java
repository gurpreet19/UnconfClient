package com.ClientApp.mainApp;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@RestController
public class ClientToCnsumeInfyApp {
	@RequestMapping(value="/GetAll", method=RequestMethod.GET)
	@HystrixCommand(commandProperties={@HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="2000")},fallbackMethod="fallbackForGetAll")
	public String GetAll()
	{
		try{
			Client c = Client.create();
		
			WebResource wr = c.resource("http://localhost:8082/MockInfy/swipeOut/7");
			ClientResponse response = wr.accept("application/json").get(ClientResponse.class);
			if(response.getStatus()!=200){
				System.out.println("Error code "+response.getStatus());
				return "Error code "+response.getStatus();
			}
			else{
				String res=response.getEntity(String.class);
				System.out.println("----------Response----------");
				System.out.println(res);
				return "done";
			}	
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return "done";
	}
	public String fallbackForGetAll(){
		System.out.println("fallback mehod called");
		return "fallback mehod called";
	}

}
