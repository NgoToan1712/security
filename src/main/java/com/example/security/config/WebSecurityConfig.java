package com.example.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableWebSecurity
@EnableTransactionManagement
@ComponentScan()//chỉ định bean
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;

    //Mã hóa password người dùng(băm)
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //kỹ thuật tấn công bằng cách sử dụng quyền chứng thực của người sử dụng
//        //đối vs 1 loiaj web khác
//        http.csrf().disable();
//
//        //Các trang không yêu cầu login
//        http.authorizeRequests().antMatchers(HttpMethod.GET,"/login").permitAll();
//
//        //Các trang yêu cầu login với vai trò user hoặc admin. dùng hasAnyRole để cho phép ai được quyền vào
//        http.authorizeRequests().antMatchers("/").access("hasAnyRole('Role_USER','ROLE_ADMIN')");
//
//        //Trang chỉ dành cho admin
//        http.authorizeRequests().antMatchers("/").access("hasAnyRole('ROLE_ADMIN')");
//
//        //Khi người dùng đã login với vai trò user nhưng cố ý truy cập vào trang admin
//        //ngoại lệ sẽ được ném ra
//        http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");
//
//
//        //Cấu hình cho login form
//        http.authorizeRequests().and().formLogin()
//                // gửi tên người dùng và mật kẩu đên
//                .loginProcessingUrl("/")
//                .loginPage("/login") //trang đăng nhập tùy chỉnh
//                .usernameParameter("userName").passwordParameter("password")
//                .defaultSuccessUrl("/", true)//chuyển trang khi đăng nhâập thành công
//                .failureUrl("/") //chuyển hướng khi không đăng nhập thành công
//                .and().logout().permitAll();
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/").authenticated()
                .antMatchers("/user/**").access("hasAnyRole('ROLE_ADMIN')")
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .and();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**");
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
