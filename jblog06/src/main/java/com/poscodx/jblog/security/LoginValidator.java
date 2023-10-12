package com.poscodx.jblog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.poscodx.jblog.service.UserService;
import com.poscodx.jblog.vo.UserVo;

@Component
public class LoginValidator implements Validator {
	@Autowired
	private UserService userService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(UserVo.class);
	}

	@Override
	public void validate(Object object, Errors errors) {
		UserVo userVo = (UserVo) object;

		if (userService.checkId(userVo.getId())) {
			errors.rejectValue("id", "invalid.id", 
					new Object[] { userVo.getId() }, "이미 사용중인 ID 입니다.");
		}
	}

}
