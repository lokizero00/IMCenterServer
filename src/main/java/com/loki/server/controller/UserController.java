package com.loki.server.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.loki.server.model.User;
import com.loki.server.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
//	@Autowired
//	private UserService userService;
//	
//	//返回单个json对象
//	@RequestMapping(value="/getUserInJson",method=RequestMethod.GET)
//	public @ResponseBody User getUserInJSON(int id) {
//		User user=userService.findById(id);
//		return user;
//	}
	
//	//返回复杂json对象集合
//	@RequestMapping(value="/getUserListByJson",method=RequestMethod.GET)
//	public String getUserListByJson(int id,ModelMap mm) {
//		System.out.println("-----------id------------="+id);
//		List<User> userList=userService.findAll();
//		mm.addAttribute("userList", userList);
//		mm.addAttribute("School", "SuZhou");
//		mm.addAttribute("Work", "YanFa");
//		return "userListJson";
//	}
	
//	@RequestMapping(value="/addUserMobile",method=RequestMethod.POST)
//	public void addUserMobile(User user,HttpServletRequest request,HttpServletResponse response){
//		userService.insert(user);
//		try {
//			PrintWriter writer = response.getWriter();
//			JSONObject object = new JSONObject();
//			object.put("status", "ok");
//			object.put("msg", "新增User成功");
//			writer.println(object.toString());
//			writer.flush();
//			writer.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
}
