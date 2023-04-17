package com.hncboy.chatgpt.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hncboy.chatgpt.admin.domain.entity.GptUserDO;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<GptUserDO> {
}
