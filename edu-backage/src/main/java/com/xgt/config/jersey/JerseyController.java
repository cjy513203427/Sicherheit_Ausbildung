package com.xgt.config.jersey;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.xgt.dao.BuildLabourerDao;
import com.xgt.service.fingerprint.FingerPrintService;
import com.xgt.service.rdcard.RdCardService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;
@Controller
@Path("/jersey")
public class JerseyController {

	// path注解指定路径,get注解指定访问方式,produces注解指定了返回值类型，这里返回JSON
	@Path("/city")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> get() {

		Map<String,Object> map = new HashMap<>();
		map.put("aaa", "aaaaa");
		return map;
	}

}