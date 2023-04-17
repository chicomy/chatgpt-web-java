package com.hncboy.chatgpt.admin.service;

import cn.dev33.satoken.stp.StpUtil;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hncboy.chatgpt.admin.domain.entity.GptUserDO;
import com.hncboy.chatgpt.admin.domain.query.GptUserPageQuery;
import com.hncboy.chatgpt.admin.mapper.UserMapper;
import com.hncboy.chatgpt.base.domain.vo.UserVO;
import com.hncboy.chatgpt.base.enums.GptUserStatusEnum;
import com.hncboy.chatgpt.base.exception.AuthException;
import com.hncboy.chatgpt.base.util.PageUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
public class UserService extends ServiceImpl<UserMapper, GptUserDO> implements IService<GptUserDO> {
    @Autowired
    private UserMapper userMapper;

    public GptUserDO login(UserVO vo) {
        QueryWrapper<GptUserDO> wrapper= new QueryWrapper<GptUserDO>();
        wrapper.eq("account",vo.getAccount()).or( );
        wrapper.eq("phone",vo.getAccount());
        GptUserDO user= userMapper.selectOne(wrapper);
        if(null==user) throw new AuthException("账号校验失败");
        if(!user.getPassword().equals(vo.getPassword())) throw new AuthException("账号密码错误");
        if(!user.getStatus().equals(GptUserStatusEnum.ACTIVED)) throw new AuthException("账号状态异常");
        StpUtil.login(vo.getAccount(),vo.getRememberMe());
        return user;
    }

    public void change(UserVO vo) {
        UpdateWrapper<GptUserDO> wrapper=new UpdateWrapper<GptUserDO>();
        wrapper.eq("account",vo.getAccount());
        GptUserDO user= userMapper.selectOne(wrapper);
        user.setPassword(vo.getPassword());
        userMapper.update(user,wrapper)  ;
    }


    public IPage<GptUserDO> pageGptUser(GptUserPageQuery gptUserPageQuery) {
        LambdaQueryWrapper<GptUserDO> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(Objects.nonNull(gptUserPageQuery.getStatus()), GptUserDO::getStatus, gptUserPageQuery.getStatus())
                .eq(Objects.nonNull(gptUserPageQuery.getAccount()), GptUserDO::getAccount, gptUserPageQuery.getAccount());
        IPage<GptUserDO> gptUserPage = page(new Page<>(gptUserPageQuery.getPageNum(), gptUserPageQuery.getPageSize()),wrapper );
        return PageUtil.toPage(gptUserPage,gptUserPage.getRecords());
    }

    public void blocked(String account, int time) {
        QueryWrapper<GptUserDO> wrapper= new QueryWrapper<GptUserDO>();
        wrapper.eq("account",account);
        GptUserDO user= userMapper.selectOne(wrapper);
        if(null!=user){
            user.setStatus(time==-1?GptUserStatusEnum.BLOCKED_FOREVER:GptUserStatusEnum.BLOCKED_LIMIT);
            UpdateWrapper<GptUserDO> updateWrapper=new UpdateWrapper<GptUserDO>();
            wrapper.eq("account",account);
            userMapper.update(user,updateWrapper);
        }
        StpUtil.kickout(account);
        StpUtil.disable(account,time);
    }

    public GptUserDO build(UserVO user) {

        QueryWrapper<GptUserDO> wrapper= new QueryWrapper<GptUserDO>();
        wrapper.eq("account",user.getAccount());
        GptUserDO gptUser= userMapper.selectOne(wrapper);
        if(null!=gptUser){
            throw new AuthException("账号已存在");
        }
        GptUserDO u=new GptUserDO();
        BeanUtils.copyProperties(user,u);
        u.setStatus(GptUserStatusEnum.ACTIVED);
        return u;
    }
}
