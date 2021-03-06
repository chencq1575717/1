package com.test.work.login.controller;

import com.test.work.login.service.ILoginService;
import com.test.work.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by renrunkeji_059 on 2017/3/22.
 */
@Controller
@RequestMapping("/login")
public class LoginCtrl {

    @Autowired
    private ILoginService loginService;

    @RequestMapping(value="/login",method=RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> login(HttpServletRequest request,String username,String password){
        Map<String,Object> returnMap = new HashMap<String,Object>();

        try {
            Map<String,Object> map = loginService.login(username, password);
            //获取user实体
            Object object = map.get("value");
            if(object != null){
                User user = (User) object;
                HttpSession session = request.getSession();
                session.setAttribute("userId", user.getId());
            }
            returnMap.put("value", object);
            returnMap.put("message", map.get("message"));
            returnMap.put("success", map.get("success"));
        } catch (Exception e) {
            returnMap.put("message", "异常：登录失败!");
            returnMap.put("success", false);
            e.printStackTrace();
        }
        return returnMap;
    }

    @RequestMapping(value="/register",method= RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> register(HttpServletRequest request,String username,String password,String tel,String email){
        Map<String,Object> returnMap = new HashMap<String,Object>();

        try {
            Map<String,Object> map = loginService.register(username, password,tel,email);
            //获取user实体
            Object object = map.get("value");
            if(object != null){
                User user = (User) object;
                HttpSession session = request.getSession();
                session.setAttribute("userId", user.getId());
            }
            returnMap.put("value", object);
            returnMap.put("message", map.get("message"));
            returnMap.put("success", map.get("success"));
        } catch (Exception e) {
            returnMap.put("message", "异常：注册失败!");
            returnMap.put("success", false);
            e.printStackTrace();
        }
        return returnMap;
    }
}
