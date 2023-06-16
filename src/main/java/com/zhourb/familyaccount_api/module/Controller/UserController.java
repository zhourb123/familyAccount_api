package com.zhourb.familyaccount_api.module.Controller;

import com.zhourb.familyaccount_api.common.exception.UserExistException;
import com.zhourb.familyaccount_api.config.minio.MinioConfig;
import com.zhourb.familyaccount_api.module.Entity.User;
import com.zhourb.familyaccount_api.module.Service.ServiceImpl.UserServiceImpl;
import com.zhourb.familyaccount_api.module.Vo.UserDetailVo;
import com.zhourb.familyaccount_api.module.mapper.UserMapper;
import com.zhourb.familyaccount_api.utils.execl.ExcelUtils;
import com.zhourb.familyaccount_api.utils.http.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 周如彬
 */
@RestController
@RequestMapping("/sys/user")
@Validated
public class UserController {
    @Resource
    UserServiceImpl userServiceImp;
    @Resource
    MinioConfig minioConfig;

    @PostMapping("/list")
    public HttpResult listAllUserSelective(@RequestBody UserDetailVo user) {
        return HttpResult.ok(userServiceImp.selectAllUsers(user));
    }

    @PutMapping("/edit")
    public HttpResult editUser(@RequestBody UserDetailVo user) {
        int effectRow = userServiceImp.editUser(user);
        if (effectRow > 0) {
            return HttpResult.ok("修改用户成功");
        } else {
            return HttpResult.error("修改用户失败");
        }
    }

    /**
     * @description： 新增用户
     * @param:
     * @return:
     **/
    @PostMapping("/add")
    public HttpResult addUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        User userExit = userServiceImp.existUser(user.getUsername());
        if (bindingResult.hasErrors()) {
            return HttpResult.error(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        if (userExit != null) {
            throw new UserExistException("账号已存在");
        }
        int effectRow = userServiceImp.insertUser(user);
        if (effectRow > 0) {
            return HttpResult.ok("新增用户成功");
        } else {
            return HttpResult.error("新增用户失败");
        }
    }

    /**
     * @description： 删除用户
     * @param:
     * @return:
     * @date:2022/4/30 11:29
     **/
    @DeleteMapping("/delete")
    public HttpResult deleteUser(@RequestParam Integer id) {
        int effectRow = userServiceImp.deleteUser(id);
        if (effectRow > 0) {
            return HttpResult.ok("删除用户成功");
        } else {
            return HttpResult.error("删除用户失败");
        }
    }

    //查询当前登陆的用户信息
    @GetMapping("/currentUser")
    public HttpResult currentUser() {
        return HttpResult.ok(userServiceImp.existUser((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal()));
    }

    //上传用户头像
    @PostMapping("uploadAvatar")
    public HttpResult uploadAvatar(@RequestParam("id") Integer userId, @RequestParam("avatar") MultipartFile file) {
        if (userId == null || userId.equals("")) {
            return HttpResult.error(400,"用户id不能为空！");
        }
        ArrayList<String> validFormat = new ArrayList<>(Arrays.asList(".jpg",".jpeg",".png",".gif"));
        if(file.isEmpty()){
            return HttpResult.error(400,"请上传图片！");
        }else{
            String fileName = file.getOriginalFilename();
            if(fileName.lastIndexOf(".")!=-1){
                String suffixName = fileName.substring(fileName.lastIndexOf("."));
                if(!validFormat.contains(suffixName)){
                    return HttpResult.error(400,"上传的图片格式应为jpg,jpeg,png,gif！");
                }
            }
        }
        int effectRow = userServiceImp.uploadAvatar(userId, file);
        if (effectRow > 0) {
            return HttpResult.ok("上传成功");
        } else {
            return HttpResult.error("上传失败");
        }
    }
}
