/**
 *
 */
package com.mytest.app.users.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Logger;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig.Feature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import com.mytest.app.activities.dao.ActivityDAO;
import com.mytest.app.activities.model.Activity;
import com.mytest.app.users.dao.UserDAO;
import com.mytest.app.users.model.User;

/**
 * @author yqwu
 *
 */
@Controller
public class UserController {

	private static final String JSON_PRODUCE = "application/json;charset=UTF-8";
	private Logger logger = Logger.getLogger(UserController.class.getName());

	ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	private ActivityDAO activityDao;

	@Autowired(required = true)
	private UserDAO userDao;

	void setObjectMapper(ObjectMapper objectMapper) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		objectMapper.configure(Feature.WRITE_DATES_AS_TIMESTAMPS, false);
		objectMapper.setDateFormat(df);
	}

	@RequestMapping(value = "/users",
					method = RequestMethod.GET,
					produces = { JSON_PRODUCE },
					params = { "un", "mn" })
	public @ResponseBody String getJoinedActivities(@RequestParam(	value = "un",
																	required = false) String userName,
													@RequestParam("mn") Long mobileNumber)
			throws Exception {
		User user = userDao.getUser(userName, mobileNumber);
		List<Activity> activities = activityDao.getJoinedActivitiesOfUser(user);
		setObjectMapper(objectMapper);
		String userJson = objectMapper.writeValueAsString(user);

		JsonElement jsonElement = new JsonParser().parse(userJson);
		String activitiesJsonStr = objectMapper.writeValueAsString(activities);
		JsonElement joinedElement = new JsonParser().parse(activitiesJsonStr);
		jsonElement.getAsJsonObject().add("joinedActivities", joinedElement);
		return jsonElement.getAsJsonObject().toString();
	}

	@RequestMapping(value = "/users/new",
					method = RequestMethod.POST,
					consumes = MediaType.APPLICATION_JSON_VALUE,
					headers = "Content-Type=application/json")
	public @ResponseBody int add(@RequestBody User user) {
		User userIfExist = userDao
				.getUser(user.getUserName(), user.getMobile());
		if (userIfExist == null) {
			return 0;
		}
		return userDao.add(user);
	}

	@RequestMapping(value = "/users/update",
					method = RequestMethod.POST,
					consumes = MediaType.APPLICATION_JSON_VALUE,
					headers = "Content-Type=application/json")
	public @ResponseBody int update(@RequestBody User user) {
		User userIfExist = userDao
				.getUser(user.getUserName(), user.getMobile());
		if ((user.getId() == null || user.getId() == 0) && userIfExist == null) {
			System.out.println("===================== ca");
			return userDao.add(user);
		}
		return userDao.update(user);
	}
}
