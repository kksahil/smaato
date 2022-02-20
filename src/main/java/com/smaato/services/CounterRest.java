package com.smaato.services;

import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CounterRest {

	final Logger logger = LogManager.getLogger(this.getClass());
	public static volatile Map<Integer, Integer> counter = new HashMap<Integer, Integer>();

	@GetMapping("/api/smaato/accept")
	public String count(@RequestParam(value = "id", defaultValue = "") Integer id,
			@RequestParam(value = "str", defaultValue = "") String str) {

		if (id == null)
			return "failed";

		if (counter.containsKey(id))
			counter.put(id, counter.get(id) + 1);
		else
			counter.put(id, 1);

		// logger.info(counter.toString());

		return "ok";
	}

	@GetMapping("/api/smaato/getcurrentcount")
	public String getStat() {
		logger.info(HttpStatus.OK);
		return "Current Count ( id=number of requests ) " + counter.toString();
	}
}
