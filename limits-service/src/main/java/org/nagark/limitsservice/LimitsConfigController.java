package org.nagark.limitsservice;

import org.nagark.limitsservice.bean.LimitsConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LimitsConfigController {

	@Autowired
	Configuration configuration;
	
	@GetMapping("/limits")
	public LimitsConfiguration retrieveLimitsFromConfig() {
		return new LimitsConfiguration(configuration.getMinimum(),configuration.getMaximum());
	}
}
