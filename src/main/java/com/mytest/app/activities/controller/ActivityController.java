/**
 *
 */
package com.mytest.app.activities.controller;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig.Feature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import com.mytest.app.activities.dao.ActivityDAO;
import com.mytest.app.activities.model.Activity;
import com.mytest.app.users.dao.UserDAO;
import com.mytest.app.users.model.User;
import com.mytest.app.users.model.UsersActivitiesRef;

/**
 * @author yqwu
 *
 */
@Controller
public class ActivityController {

	private static final String JSON_PRODUCE = "application/json;charset=UTF-8";
	private Logger logger = Logger
			.getLogger(ActivityController.class.getName());

	ObjectMapper objectMapper = new ObjectMapper();

	@Autowired(required = true)
	private ActivityDAO activityDao;

	@Autowired
	private UserDAO userDao;

	@RequestMapping(value = "/activities/new",
					method = RequestMethod.POST,
					consumes = MediaType.APPLICATION_JSON_VALUE,
					headers = "Content-Type=application/json")
	public @ResponseBody int add(@RequestBody Activity activity)
			throws Exception {
		System.out.println("My test is the best ================ "
				+ activity.getContent());
		return activityDao.add(activity);
	}

	@RequestMapping(value = "/activities/action",
					method = RequestMethod.POST,
					consumes = MediaType.APPLICATION_JSON_VALUE,
					headers = "Content-Type=application/json")
	public @ResponseBody int joinOrLeave(	@RequestParam("un") String userName,
											@RequestParam("mn") Long mobile,
											@RequestParam("activityId") Long activityId,
											@RequestParam("joinFlag") Integer joinFlag)
			throws Exception {
		User user = userDao.getUser(userName, mobile);
		if (joinFlag == 1) {
			if (ifUserJoinedActivity(user.getId(), activityId) == 1) {
				System.out.println("User " + user.getId()
						+ " has joined before, can't join again");
				return 0;
			} else {
				return activityDao.joinActivity(user.getId(), activityId);
			}

		} else if (joinFlag == 0) {
			// activityDao.leaveActivity(user.getId(), activityId);
			if (ifUserJoinedActivity(user.getId(), activityId) == 0) {
				System.out.println("User " + user.getId()
						+ "not joined before, can't leave again");
				return 0;
			} else {
				return activityDao.leaveActivity(user.getId(), activityId);
			}
		} else {
			throw new Exception("jsonFlat value is not z");
		}
	}

	@RequestMapping(value = "/activities/{activityId}",
					method = RequestMethod.GET,
					produces = { JSON_PRODUCE })
	public @ResponseBody String getActivity(@PathVariable Long activityId)
			throws Exception {
		setObjectMapper(objectMapper);
		Activity activity = activityDao.getActivity(activityId);
		String result = objectMapper.writeValueAsString(activity);
		return result;
	}

	@RequestMapping(value = "/activities/{activityId}",
					method = RequestMethod.GET,
					produces = { JSON_PRODUCE },
					params = { "un", "mn" })
	public @ResponseBody String getActivityOfUser(	@PathVariable Long activityId,
													@RequestParam(	value = "un",
																	required = false) String userName,
													@RequestParam(	value = "mn",
																	required = false) Long mobileNumber)
			throws Exception {
		User user;
		List<Long> activityIds = new ArrayList<Long>();
		setObjectMapper(objectMapper);
		System.out.println("=============act Id from request :" + activityId);
		Activity activity = activityDao.getActivity(activityId);
		System.out.println("=============act Id :" + activity.getId());
		String activityJsonStr = objectMapper.writeValueAsString(activity);
		System.out.println("=============act json :" + activityJsonStr);
		JsonElement jsonElement = new JsonParser().parse(activityJsonStr);
		if (mobileNumber != null) {
			user = userDao.getUser(userName, mobileNumber);
			activityIds = activityDao.getActivityIdsOfUser(user);
			if (activityIds
					.contains(new BigInteger(activity.getId().toString()))) {
				jsonElement.getAsJsonObject().addProperty("joinStatus", 1);
			} else {
				jsonElement.getAsJsonObject().addProperty("joinStatus", 0);
			}
		} else {
			jsonElement.getAsJsonObject().addProperty("joinStatus", 0);
		}
		List<User> joinedUsers = userDao.getJoinedUsersOfActivity(activity);
		String joinedUsersStr = objectMapper.writeValueAsString(joinedUsers);
		JsonElement joinedElement = new JsonParser().parse(joinedUsersStr);
		jsonElement.getAsJsonObject().add("joinedUsers", joinedElement);

		List<User> notJoinedUsers = userDao
				.getNotJoinedUsersOfActivity(activity);
		String notJoinedUsersStr = objectMapper
				.writeValueAsString(notJoinedUsers);
		JsonElement notJoinedElement = new JsonParser()
				.parse(notJoinedUsersStr);
		jsonElement.getAsJsonObject().add("notJoinedUsers", notJoinedElement);
		return jsonElement.getAsJsonObject().toString();
	}

	@RequestMapping(value = "/activities",
					method = RequestMethod.GET,
					produces = { JSON_PRODUCE })
	public @ResponseBody String getActivitiesWithJoinStatus(@RequestParam(	value = "un",
																			required = false) String userName,
															@RequestParam(	value = "mn",
																			required = false) Long mobileNumber)
			throws Exception {
		List<Activity> activities = new ArrayList<Activity>();
		activities = activityDao.getActivities();
		setObjectMapper(objectMapper);
		JsonArray activitiesWithStatus = new JsonArray();

		User user;
		List<Long> activityIds = new ArrayList<Long>();
		if (userName == null && mobileNumber == null) {
			user = userDao.getUser(userName, mobileNumber);
			activityIds = activityDao.getActivityIdsOfUser(user);
		}
		for (Activity activity : activities) {
			String actJsonStr = objectMapper.writeValueAsString(activity);
			JsonElement jsonElement = new JsonParser().parse(actJsonStr);
			if (userName == null && mobileNumber == null) {
				jsonElement.getAsJsonObject().addProperty("joinStatus", 0);
			} else {
				if (activityIds.contains(new BigInteger(activity.getId()
						.toString()))) {
					jsonElement.getAsJsonObject().addProperty("joinStatus", 1);
				} else {
					jsonElement.getAsJsonObject().addProperty("joinStatus", 0);
				}
			}
			activitiesWithStatus.add(jsonElement);
		}
		JsonObject jo = new JsonObject();
		jo.add("data", activitiesWithStatus);
		return jo.toString();
	}

	void setObjectMapper(ObjectMapper objectMapper) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		objectMapper.configure(Feature.WRITE_DATES_AS_TIMESTAMPS, false);
		objectMapper.setDateFormat(df);
	}

	public int ifUserJoinedActivity(Long userId, Long activityId) {
		UsersActivitiesRef ref = activityDao.getRef(userId, activityId);
		if (ref == null) {
			return 0;
		} else {
			return 1;
		}
	}

	@RequestMapping(value = "/activities/joined/",
					method = RequestMethod.GET,
					produces = { JSON_PRODUCE },
					params = { "un", "mn" })
	public @ResponseBody String getJoinedActivities(@PathVariable Long activityId,
													@RequestParam(	value = "un",
																	required = false) String userName,
													@RequestParam("mn") Long mobileNumber)
			throws Exception {
		User user = userDao.getUser(userName, mobileNumber);
		List<Activity> activities = activityDao.getJoinedActivitiesOfUser(user);
		setObjectMapper(objectMapper);
		Map<String, List<Activity>> jsonData = new HashMap<String, List<Activity>>();
		jsonData.put("data", activities);
		String result = objectMapper.writeValueAsString(jsonData);
		return result;
	}
}
