package com.zhourb.familyaccount_api.module.Service.ServiceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhourb.familyaccount_api.module.Entity.User;
import com.zhourb.familyaccount_api.module.Entity.UserRole;
import com.zhourb.familyaccount_api.module.Service.UserService;
import com.zhourb.familyaccount_api.module.Vo.UserDetailVo;
import com.zhourb.familyaccount_api.module.mapper.UserMapper;
import com.zhourb.familyaccount_api.utils.http.PageHelperEntity;
import com.zhourb.familyaccount_api.utils.minio.MinioUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 周如彬
 * @description 用户登录 实现UserDetailsService
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    @Resource
    UserMapper userMapper;
    @Resource
    MinioUtils minioUtils;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertUser(User user) {
        int insert = 0;
        try {
            user.setEnabled(true);
            Date date = new Date();
            user.setCreatetime(date);
            user.setCreator((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            insert = userMapper.insertSelective(user);
//            if (null != user.getRidList() && user.getRidList().size() > 0) {
//                HashMap<String, Object> map = new HashMap<>();
//                map.put("ridList", user.getRidList());
//                map.put("uid", String.valueOf(user.getId()));
//                userRoleMapper.insert(map);
//            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return 0;
        }
        return insert;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int editUser(UserDetailVo user) {
        if (user.getId() == null) {
            log.info("id不能为空");
        }
        //这里使用UserDetailVo的原因是 其enabled默认为null 不会修改数据库中的enabled 而User中的enabled默认为false
        Date date = new Date();
        user.setUpdatetime(date);
//        userRoleMapper.deleteByUid(String.valueOf(user.getId()));
//        if (null != user.getRidList() && user.getRidList().size() > 0) {
//            HashMap<String, Object> map = new HashMap<>();
//            map.put("ridList", user.getRidList());
//            map.put("uid", String.valueOf(user.getId()));
//            userRoleMapper.insert(map);
//        }
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteUser(Integer id) {
//        userRoleMapper.deleteByUid(id.toString());
        return userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public User existUser(String username) {
        User user = userMapper.selectByUsername(username);
        try {
            if (user.getAvatar() != null) {
                user.setAvatarSrc(minioUtils.getPresignedObjectUrl("avatar", user.getAvatar()));
            } else {
                //没有头像设置为默认头像
                user.setAvatarSrc(minioUtils.getPresignedObjectUrl("avatar", "duck.jpeg"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }


    @Override
    public PageHelperEntity selectAllUsers(UserDetailVo user) {
        PageHelperEntity<User> pageHelperEntity = new PageHelperEntity<User>();
        PageHelper.startPage(user.getPage(), user.getSize());
        List<User> users = userMapper.selectAllUsers(user);
        PageInfo pageInfo = new PageInfo(users);
        pageHelperEntity.setTotal(pageInfo.getTotal());
        pageHelperEntity.setPages(pageInfo.getPages());
        pageHelperEntity.setContent(users);
        return pageHelperEntity;

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int uploadAvatar(Integer userId, MultipartFile file) {
        String fileName = file.getOriginalFilename() + ".jpg";
        try {
            User user = userMapper.selectByPrimaryKey(userId);
            if (user == null) {
                log.info("此用户不存在");
                return 0;
            }
            //如果用户有头像则删除原先的 得先判空 不然会报空指针异常
            if (user.getAvatar() != null && !user.getAvatar().trim().equals("")) {
                minioUtils.deleteObject("avatar", user.getAvatar());
            }
            minioUtils.uploadFile(file.getInputStream(), "avatar", fileName);
            //获取图片的地址 存到数据库中 这样不行 因为图片链接默认7天过期
            //String avatar = minioUtils.getPresignedObjectUrl("avatar", fileName);
            //直接存储图片的名称 这样可以查询到最新的图片链接
            return userMapper.updateByPrimaryKeySelective(new UserDetailVo(userId, fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    //实现springsecurity的登陆方法
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("账户不存在");
        }
        Set<GrantedAuthority> authorities = new HashSet<>();
        user.setAuthorities(authorities);
        return user;
    }
}
