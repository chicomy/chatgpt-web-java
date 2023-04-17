package com.hncboy.chatgpt.admin.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hncboy.chatgpt.admin.domain.entity.GptUserDO;
import com.hncboy.chatgpt.admin.domain.query.GptUserPageQuery;
import com.hncboy.chatgpt.admin.service.UserService;
import com.hncboy.chatgpt.base.annotation.ApiAdminRestController;
import com.hncboy.chatgpt.base.domain.vo.UserVO;
import com.hncboy.chatgpt.base.handler.response.R;
import com.hncboy.chatgpt.base.handler.response.ResultCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@Tag(name = "用户个人操作")
@RestController
@RequestMapping("/user")
public class UserManageController {

    @Autowired
    private UserService userService;

    @Operation(summary = "账号登录")
    @RequestMapping("/login")
    public  R<GptUserDO> login(@Valid @RequestBody UserVO user) {

        GptUserDO login = userService.login(user);

        return R.data(ResultCode.SUCCESS.getCode(),login,"成功");
    }

    @Operation(summary = "修改账号信息")
    @RequestMapping("/change")
    public  R<GptUserDO> change(@Valid @RequestBody UserVO user) {
        userService.change(user);
        return R.success("修改成功");
    }

    @Operation(summary = "账号退出")
    @RequestMapping("/loginOut")
    public R<Void> loginOut() {
        String account = StpUtil.getLoginIdAsString();
        StpUtil.logout();
        return R.success("退出成功");
    }

}
