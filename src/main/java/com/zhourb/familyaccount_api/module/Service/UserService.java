package com.zhourb.familyaccount_api.module.Service;
import com.zhourb.familyaccount_api.module.Entity.User;
import com.zhourb.familyaccount_api.module.Entity.UserRole;
import com.zhourb.familyaccount_api.module.Vo.UserDetailVo;
import com.zhourb.familyaccount_api.utils.http.PageHelperEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface UserService {

    int insertUser(User user);

    int editUser(UserDetailVo user);

    int deleteUser(Integer id);

    User existUser(String username);

    //查询所有用户
    PageHelperEntity selectAllUsers(UserDetailVo user);
    //上传头像
    int uploadAvatar(Integer userId, MultipartFile file);
}
