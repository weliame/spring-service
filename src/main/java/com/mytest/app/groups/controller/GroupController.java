/**
 *
 */
package com.mytest.app.groups.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig.Feature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mytest.app.groups.dao.GroupDAO;
import com.mytest.app.groups.model.Group;

/**
 * @author yqwu
 *
 */
@Controller
public class GroupController {

	private static final String JSON_PRODUCE = "application/json;charset=UTF-8";
	private Logger logger = Logger.getLogger(GroupController.class.getName());

	@Autowired(required = true)
	private GroupDAO groupDao;

	@RequestMapping(value = "/groups",
					method = RequestMethod.GET,
					produces = { JSON_PRODUCE })
	public @ResponseBody String getGroups() throws Exception {
		List<Group> groups = new ArrayList<Group>();
		groups = groupDao.getGroups();
		Map<String, List<Group>> jsonData = new HashMap<String, List<Group>>();
		jsonData.put("data", groups);
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(Feature.WRITE_DATES_AS_TIMESTAMPS, false);
		objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		String result = objectMapper.writeValueAsString(jsonData);
		return result;
	}

}
