package com.fu.business.controller.web;

import com.alibaba.fastjson.JSON;
import com.fu.server.dto.*;
import com.fu.server.enums.SmsUseEnum;
import com.fu.server.exception.BusinessException;
import com.fu.server.service.MemberService;
import com.fu.server.service.SmsService;
import com.fu.server.utils.UuidUtil;
import com.fu.server.utils.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@RestController("webMemberController")
@RequestMapping("/web/member")
public class MemberController {

    private static final Logger LOG = LoggerFactory.getLogger(MemberController.class);

    public static final String BUSINESS_NAME = "会员";

    @Resource
    private MemberService memberService;

    @Resource
    private SmsService smsService;

    @Resource(name = "redisTemplate")
    private RedisTemplate redisTemplate;

    /**
     * 保存，id有值时更新，无值时新增
     */
    @PostMapping("/register")
    public ResponseDto register(@RequestBody MemberDto memberDto) {
        // 保存校验
        ValidatorUtil.require(memberDto.getMobile(), "手机号");
        ValidatorUtil.length(memberDto.getMobile(), "手机号", 11, 11);
        ValidatorUtil.require(memberDto.getPassword(), "密码");
        ValidatorUtil.length(memberDto.getName(), "昵称", 1, 50);
        ValidatorUtil.length(memberDto.getPhoto(), "头像url", 1, 200);

        // 密码加密
        memberDto.setPassword(DigestUtils.md5DigestAsHex(memberDto.getPassword().getBytes()));

        // 校验短信验证码
        SmsDto smsDto = new SmsDto();
        smsDto.setMobile(memberDto.getMobile());
        smsDto.setCode(memberDto.getSmsCode());
        smsDto.setUse(SmsUseEnum.REGISTER.getCode());
        smsService.validCode(smsDto);
        LOG.info("短信验证码校验通过");

        memberService.save(memberDto);
        return ResponseDto.success(memberDto);
    }


    /**
     * 登录
     */
    @PostMapping("/login")
    public ResponseDto login(@RequestBody MemberDto memberDto) {
        LOG.info("用户登录开始");
        memberDto.setPassword(DigestUtils.md5DigestAsHex(memberDto.getPassword().getBytes()));

        // 根据验证码token去获取缓存中的验证码，和用户输入的验证码是否一致
        String imageCode = (String) redisTemplate.opsForValue().get(memberDto.getImageCodeToken());
        LOG.info("从redis中获取到的验证码：{}", imageCode);
        if (StringUtils.isEmpty(imageCode)) {
            LOG.info("用户登录失败，验证码已过期");
            return ResponseDto.error("验证码已过期");
        }
        if (!imageCode.toLowerCase().equals(memberDto.getImageCode().toLowerCase())) {
            LOG.info("用户登录失败，验证码不正确");
            return ResponseDto.error("验证码不正确");
        } else {
            // 验证通过后，移除验证码
            redisTemplate.delete(memberDto.getImageCodeToken());
        }

        LoginMemberDto loginMemberDto = memberService.login(memberDto);
        String token = UuidUtil.getShortUuid();
        loginMemberDto.setToken(token);
        redisTemplate.opsForValue().set(token, JSON.toJSONString(loginMemberDto), 3600, TimeUnit.SECONDS);
        return ResponseDto.success(loginMemberDto);
    }

    /**
     * 退出登录
     */
    @GetMapping("/logout/{token}")
    public ResponseDto logout(@PathVariable String token) {
        redisTemplate.delete(token);
        LOG.info("从redis中删除token:{}", token);
        return ResponseDto.success();
    }

    /**
     * 校验手机号是否存在
     * 存在则success=true，不存在则success=false
     */
    @GetMapping(value = "/is-mobile-exist/{mobile}")
    public ResponseDto isMobileExist(@PathVariable(value = "mobile") String mobile) throws BusinessException {
        LOG.info("查询手机号是否存在开始");
        MemberDto memberDto = memberService.findByMobile(mobile);
        if (memberDto == null) {
            return ResponseDto.error();
        } else {
            return ResponseDto.success();
        }
    }

    @PostMapping("/reset-password")
    public ResponseDto resetPassword(@RequestBody MemberDto memberDto) throws BusinessException {
        LOG.info("会员密码重置开始:");
        memberDto.setPassword(DigestUtils.md5DigestAsHex(memberDto.getPassword().getBytes()));

        // 校验短信验证码
        SmsDto smsDto = new SmsDto();
        smsDto.setMobile(memberDto.getMobile());
        smsDto.setCode(memberDto.getSmsCode());
        smsDto.setUse(SmsUseEnum.FORGET.getCode());
        smsService.validCode(smsDto);
        LOG.info("短信验证码校验通过");

        // 重置密码
        memberService.resetPassword(memberDto);

        return ResponseDto.success();
    }
}