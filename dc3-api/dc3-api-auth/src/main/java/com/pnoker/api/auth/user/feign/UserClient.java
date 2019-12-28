/*
 * Copyright 2019 Pnoker. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pnoker.api.auth.user.feign;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pnoker.api.auth.user.hystrix.UserClientHystrix;
import com.pnoker.common.bean.R;
import com.pnoker.common.constant.Common;
import com.pnoker.common.dto.auth.UserDto;
import com.pnoker.common.model.auth.User;
import com.pnoker.common.valid.Insert;
import com.pnoker.common.valid.Update;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 用户 FeignClient
 *
 * @author pnoker
 */
@FeignClient(path = Common.Service.DC3_USER_URL_PREFIX, name = Common.Service.DC3_AUTH, fallbackFactory = UserClientHystrix.class)
public interface UserClient {

    /**
     * 新增 User 记录
     *
     * @param user
     * @return User
     */
    @PostMapping("/add")
    R<User> add(@Validated(Insert.class) @RequestBody User user);

    /**
     * 根据 ID 删除 User
     *
     * @param id userId
     * @return Boolean
     */
    @PostMapping("/delete/{id}")
    R<Boolean> delete(@PathVariable(value = "id") Long id);

    /**
     * 修改 User 记录
     *
     * @param user
     * @return User
     */
    @PostMapping("/update")
    R<User> update(@Validated(Update.class) @RequestBody User user);

    /**
     * 根据 ID 重置 User 密码
     *
     * @param id userId
     * @return Boolean
     */
    @PostMapping("/restPassword/{id}")
    R<Boolean> restPassword(@PathVariable(value = "id") Long id);

    /**
     * 根据 ID 查询 User
     *
     * @param id
     * @return User
     */
    @GetMapping("/id/{id}")
    R<User> selectById(@PathVariable(value = "id") Long id);

    /**
     * 根据 ID 查询 User
     *
     * @param name
     * @return User
     */
    @GetMapping("/name/{name}")
    R<User> selectByName(@PathVariable(value = "name") String name);

    /**
     * 分页查询 User
     *
     * @param userDto
     * @return Page<User>
     */
    @PostMapping("/list")
    R<Page<User>> list(@RequestBody(required = false) UserDto userDto);

    /**
     * 检测用户是否存在
     *
     * @param name
     * @return Boolean
     */
    @GetMapping("/check/{name}")
    R<Boolean> checkUserValid(@PathVariable(value = "name") String name);

}
