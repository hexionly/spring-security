package com.hsx.security.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author HEXIONLY
 * @date 2022/3/7 15:10
 */
@Data
@NoArgsConstructor
public class SecurityUser implements UserDetails {

    /**
     * 当前登录用户
     * transient：该关键字时不需要被序列化
     */
    private transient User currentUserInfo;

    /**
     * 当前权限
     */
    private List<String> permissionValueList;

    public SecurityUser(User user) {
        if (!ObjectUtils.isEmpty(user)) {
            this.currentUserInfo = user;
        }
    }

    /**
     * 获取权限
     *
     * @return 权限
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        for (String permisssionValue : permissionValueList) {
            if (StringUtils.isBlank(permisssionValue)) {
                continue;
            }
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(permisssionValue);
            authorities.add(authority);
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return currentUserInfo.getPassword();
    }

    @Override
    public String getUsername() {
        return currentUserInfo.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
