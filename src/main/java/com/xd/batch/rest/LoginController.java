package com.xd.batch.rest;

import com.xd.batch.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest")
public class LoginController {
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public boolean login( @RequestBody User user){
        if(StringUtils.isBlank(user.getUserName()) || StringUtils.isBlank(user.getPassword())){
            return false;
        }
        return true;
    }

}
