package com.FangBianMian.security;
import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.FangBianMian.dao.IUserDao;
import com.FangBianMian.domain.SecurityUser;

/**
 * 登录验证处理类
 * @author dreamtec
 *
 */
public class UserDetailsServiceImpl implements UserDetailsService{
    
    private static Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    
    @Autowired
    private IUserDao userDao;
    
    /**
     * @param username 登录帐号
     */
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	
        log.info("登录账号：{}",username);
        
        //登录验证类, 该对象用于和前台传入的值进行比较
        SecurityUser userDetails = userDao.selectUserByUsername(username);
        //设置权限
        Collection<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
        
        if(userDetails.getType().intValue() == 0){
        	auths.add(new SimpleGrantedAuthority("ROLE_APP"));
		}else if(userDetails.getType().intValue() == 1){
			auths.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}else if(userDetails.getType().intValue() == 2){
			auths.add(new SimpleGrantedAuthority("ROLE_ENTERPRISE"));
		}
        
        userDetails.setAuthorities(auths);
        
        return userDetails;
    }
}