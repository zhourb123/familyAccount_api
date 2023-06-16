package com.zhourb.familyaccount_api.module.mapper;

import com.zhourb.familyaccount_api.module.Entity.User;
import com.zhourb.familyaccount_api.module.Vo.UserDetailVo;
import org.apache.ibatis.annotations.Mapper;

import javax.annotation.Nullable;
import java.util.List;

@Mapper
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    //选择性插入数据
    int insertSelective(User user);

    User selectByPrimaryKey(Integer id);

    //选择性更新数据
    int updateByPrimaryKeySelective(UserDetailVo user);

    // 根据用户名查询用户
    User selectByUsername(String username);


    //查询所有用户
    List<User> selectAllUsers(UserDetailVo user);


}