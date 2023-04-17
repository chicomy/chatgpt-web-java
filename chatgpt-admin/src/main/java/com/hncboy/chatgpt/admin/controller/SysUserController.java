package com.hncboy.chatgpt.admin.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hncboy.chatgpt.admin.domain.entity.GptUserDO;
import com.hncboy.chatgpt.admin.domain.query.GptUserPageQuery;
import com.hncboy.chatgpt.admin.domain.request.SysUserLoginRequest;
import com.hncboy.chatgpt.admin.service.SysUserService;
import com.hncboy.chatgpt.admin.service.UserService;
import com.hncboy.chatgpt.base.annotation.ApiAdminRestController;
import com.hncboy.chatgpt.base.domain.vo.UserVO;
import com.hncboy.chatgpt.base.enums.GptUserStatusEnum;
import com.hncboy.chatgpt.base.handler.response.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author hncboy
 * @date 2023/3/28 09:54
 * 系统用户相关接口
 */
@AllArgsConstructor
@Tag(name = "管理端-系统用户相关接口")
@ApiAdminRestController("/sys_user")
public class SysUserController {

    private final SysUserService sysUserService;

    @Autowired
    private UserService userService;

    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public R<Void> login(@Validated @RequestBody SysUserLoginRequest sysUserLoginRequest) {
        sysUserService.login(sysUserLoginRequest);
        return R.success("登录成功");
    }


    @Operation(summary = "用户管理列表")
    @RequestMapping("/page")
    public R<IPage<GptUserDO>> page(@Validated GptUserPageQuery gptUserPageQuery){
        return  R.data(userService.pageGptUser(gptUserPageQuery));
    }

    @Operation(summary = "封禁用户")
    @RequestMapping("/blocked")
    public R<IPage<GptUserDO>> blocked(@NonNull String account,@NonNull  int time ){
        userService.blocked(account,time);
        return  R.success("封禁成功");
    }

    @Operation(summary = "账号状态查询")
    @RequestMapping("/status")
    public R<GptUserStatusEnum> status(@NonNull String account){
        GptUserStatusEnum status=GptUserStatusEnum.ACTIVED;
         boolean disable = StpUtil.isDisable(account);
         if(disable){
              long disableTime = StpUtil.getDisableTime(account);
             status=-1==disableTime?GptUserStatusEnum.BLOCKED_FOREVER:GptUserStatusEnum.BLOCKED_LIMIT;
         }
        return  R.data(HttpServletResponse.SC_OK,status,"查询成功");
    }

    @Operation(summary = "强制注销账号")
    @RequestMapping("/logout")
    public R<IPage<GptUserDO>> logout(@NonNull String account){
        StpUtil.logout(account);
        return  R.success("注销成功");
    }

    @Operation(summary = "修改账号信息")
    @RequestMapping("/change")
    public  R<GptUserDO> change(@Valid @RequestBody UserVO user) {
        userService.change(user);
        return R.success("修改成功");
    }

    @Operation(summary = "创建账号信息")
    @RequestMapping("/save")
    public  R<GptUserDO> save(@Valid @RequestBody UserVO user) {
        GptUserDO u=  userService.build(user);
        userService.save(u);
        return R.success("创建成功");
    }
}
