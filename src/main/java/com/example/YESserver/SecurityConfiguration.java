package com.example.YESserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;



@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);

        auth.inMemoryAuthentication()
                .withUser("admin").password("admin").roles("ADMIN")
                .and()
                .withUser("Nicholas_Zeppos_01").password("nicholas1").roles("ADMIN")
                .and()
                .withUser("Charlie_Xu_01").password("charlie1").roles("USER")
                .and()
                .withUser("Charlie_Xu_02").password("charlie2").roles("USER")
                .and()
                .withUser("Frank_Tian_01").password("frank1").roles("USER")
                .and()
                .withUser("Acar_Ary_01").password("acar1").roles("USER")
                .and()
                .withUser("John_Rafter_01").password("john1").roles("USER")
                .and()
                .withUser("Lori_Rafter_03").password("lori3").roles("USER")
                .and()
                .withUser("Dan_Arena_01").password("dan1").roles("USER")
                .and()
                .withUser("Daniel_Solomon_01").password("daniel1").roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/admin").hasRole("ADMIN")
//                .antMatchers("/admin/sql_injection_test").hasRole("ADMIN")
//                .antMatchers("/user").hasAnyRole("ADMIN", "USER")
//                .antMatchers("/").permitAll()
//                .and().formLogin();

        // Charlie's note
        //
        // Security loophole!!!
        //
        // The "/**" is placed at the top and everything below will be ignored
        // Consequence: Anyone with the role "User" can access "Admin" contents even
        // though they are not supposed to

        http.authorizeRequests()
                .antMatchers("/").permitAll()
//                .antMatchers("/admin/*").hasRole("ADMIN")
                .antMatchers("/admin/*").permitAll()
                .antMatchers("/user/*").hasAnyRole("ADMIN", "USER")
                .antMatchers("/student/*").hasAnyRole("ADMIN", "USER")
                .antMatchers("/instructor/*").hasAnyRole("ADMIN", "USER")
                .antMatchers("/add").permitAll()
                .antMatchers("/catalog/*").hasAnyRole("ADMIN", "USER")
                .antMatchers("/schedule/*").hasAnyRole("ADMIN", "USER")



                .antMatchers("/all").permitAll()
                .antMatchers("/sql_injection_test").permitAll()
                .antMatchers("/XSS_test").permitAll()
                .antMatchers("/admin/get_id_by_name").hasRole("ADMIN")
                .and().formLogin();

        http.csrf().disable();
        // Charlie's note
        //
        // Security loophole!!!
        //
        //

        // http.csrf().disable();


    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {

        // Charlie's note
        //
        // Security loophole!!!
        //
        // This actually does nothing
        // Dealing with clear texts
        // Need an actually password encoder!!!
        // Consequence: clear texts password can be easily leaked


//        return new BCryptPasswordEncoder();
//        return new Pbkdf2PasswordEncoder();
        return NoOpPasswordEncoder.getInstance();

    }

}

