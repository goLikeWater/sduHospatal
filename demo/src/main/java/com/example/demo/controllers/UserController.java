package com.example.demo.controllers;


import com.example.demo.pojo.Result;
import com.example.demo.pojo.User;
import com.example.demo.service.UserService;
import com.example.demo.utils.JwtUtil;
import com.example.demo.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$")String password){
        if(username == null || password == null)return Result.error("用户名或密码不能为空");
        User u = userService.findByUsername(username);
        if(u==null){
            User nu = new User();
            nu.setPassword(password);
            nu.setUsername(username);
            userService.register(nu);
            return Result.success();
        }
        else{
            return Result.error("用户名已被占用");
        }
    }


    @PostMapping("/login")
    public Result login(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$")String password){
        User loginuser = userService.findByUsername(username);
        if(loginuser == null)return Result.error("用户名错误");
        if(password.equals(loginuser.getPassword())){
            Map<String,Object> claims = new HashMap<>();
            claims.put("userId",loginuser.getUserId());
            claims.put("username",loginuser.getUsername());

            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();

            String token = JwtUtil.genToken(claims);
            operations.set(token,token,12, TimeUnit.HOURS);
            Map m = new HashMap<>();
            m.put("token",token);
            m.put("role",loginuser.getRole());
            return Result.success(m);
        }
        return Result.error("密码错误");
    }

    @GetMapping("/info")
    public Result<User> userInfo(/*@RequestHeader(name = "Authorization")String token*/){
/*
        Map<String,Object> map = JwtUtil.parseToken(token);
*/
        Map<String,Object> map = ThreadLocalUtil.get();
        String username = (String)map.get("username");
        User user = userService.findByUsername(username);
        return Result.success(user);
    }
    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user){
        userService.update(user);
        return Result.success();

    }
    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam String avatarUrl){
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }
    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String,String> params,@RequestHeader("Authorization") String token){
        String old_pwd = params.get("oldPwd");
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("userId");
        User user = userService.findById(id);
        if(old_pwd.equals(user.getPassword())){
            if(params.get("newPwd").equals(params.get("rePwd"))){
            userService.updatePwd(params.get("newPwd"),id);
                ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
                operations.getOperations().delete(token);

                return Result.success();
            }
            return Result.error("新密码不一致");
        }
        return Result.error("原密码错误");
    }

}
