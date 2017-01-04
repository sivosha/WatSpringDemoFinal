package pl.edu.agh.wat.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * by defuault allow all http request to come thriugh the app
 */
@Configuration
@EnableWebSecurity
public class WatSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .authorizeRequests()
                .antMatchers("/**").hasAnyRole("ADMIN","USER")
                .and()
            .formLogin();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) {
        try {
            auth
                .inMemoryAuthentication()
                    .withUser("user").password("password").roles("USER")
                    .and()
                    .withUser("user1").password("1").roles("USER")
                    .and()
                    .withUser("user2").password("2").roles("USER")
                    .and()
                    .withUser("use3").password("3").roles("USER")
                    .and()
                    .withUser("admin").password("password").roles("ADMIN")
            ;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
