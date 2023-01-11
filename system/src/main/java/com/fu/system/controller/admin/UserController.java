package com.fu.system.controller.admin;

import com.fu.server.CourseConst;
import com.fu.server.dto.LoginUserDto;
import com.fu.server.dto.UserDto;
import com.fu.server.dto.PageDto;
import com.fu.server.dto.ResponseDto;
import com.fu.server.service.UserService;
import com.fu.server.utils.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/admin/user")
public class UserController {
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    public static final String BUSINESS_NAME = "用户表 ";

    @Resource
    private UserService userService;

    @GetMapping("/list")
    public ResponseDto<PageDto> list(PageDto pageDto) {
        userService.list(pageDto);
        return ResponseDto.success(pageDto);
    }

    @PostMapping("/save")
    public ResponseDto<UserDto> save(@RequestBody UserDto userDto) {
        userDto.setPassword(DigestUtils.md5DigestAsHex(userDto.getPassword().getBytes()));
        // 保存校验
        ValidatorUtil.require(userDto.getLoginName(), "登陆名");
        ValidatorUtil.length(userDto.getLoginName(), "登陆名", 1, 50);
        ValidatorUtil.length(userDto.getName(), "昵称", 1, 50);
        ValidatorUtil.require(userDto.getPassword(), "密码");
        userService.save(userDto);
        return ResponseDto.success(userDto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseDto<UserDto> delete(@PathVariable String id) {
        userService.delete(id);
        return ResponseDto.success();
    }


    /**
     * 重置密码
     */
    @PostMapping("/save-password")
    public ResponseDto savePassword(@RequestBody UserDto userDto) {
        userDto.setPassword(DigestUtils.md5DigestAsHex(userDto.getPassword().getBytes()));
        userService.savePassword(userDto);
        return ResponseDto.success(userDto);
    }


    /**
     * 重置密码
     */
    @PostMapping("/login")
    public ResponseDto login(@RequestBody UserDto userDto, HttpServletRequest request) {
        LOG.info("用户登录开始");

        userDto.setPassword(DigestUtils.md5DigestAsHex(userDto.getPassword().getBytes()));

        // 根据验证码token去获取缓存中的验证码，和用户输入的验证码是否一致
        String imageCode = (String) request.getSession().getAttribute(userDto.getImageCodeToken());
        if (StringUtils.isEmpty(imageCode)) {
            LOG.info("用户登录失败，验证码已过期");
            return ResponseDto.error("验证码已过期");
        }
        if (!imageCode.toLowerCase().equals(userDto.getImageCode().toLowerCase())) {
            LOG.info("用户登录失败，验证码不正确");
            return ResponseDto.error("验证码不正确");
        } else {
            // 验证通过后，移除验证码
            request.getSession().removeAttribute(userDto.getImageCodeToken());
        }
        LoginUserDto loginUserDto = userService.login(userDto);
        request.getSession().setAttribute(CourseConst.LOGIN_USER,loginUserDto);
        return ResponseDto.success(loginUserDto);
    }

    /**
     * 退出登录
     */
    @GetMapping("/logout")
    public ResponseDto logout(HttpServletRequest request) {
        request.getSession().removeAttribute(CourseConst.LOGIN_USER);
        return ResponseDto.success();
    }
}