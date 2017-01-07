package pl.edu.agh.wat.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.openid.OpenIDAuthenticationToken;

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
            .openidLogin()
                //.loginPage("/login1")
                .permitAll()
                .authenticationUserDetailsService(new AutoProvisioningUserDetailsService())
                .attributeExchange("https://www.google.com/.*")
                .attribute("email")
                .type("http://axschema.org/contact/email")
                .required(true)
                .and()
                .attribute("firstname")
                .type("http://axschema.org/namePerson/first")
                .required(true)
                .and()
                .attribute("lastname")
                .type("http://axschema.org/namePerson/last")
                .required(true)
                .and()
                .and()
                .attributeExchange(".*yahoo.com.*")
                .attribute("email")
                .type("http://schema.openid.net/contact/email")
                .required(true)
                .and()
                .attribute("fullname")
                .type("http://axschema.org/namePerson")
                .required(true)
                .and()
                .and()
                .attributeExchange(".*myopenid.com.*")
                .attribute("email")
                .type("http://schema.openid.net/contact/email")
                .required(true)
                .and()
                .attribute("fullname")
                .type("http://schema.openid.net/namePerson")
                .required(true);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) {
        try {
            auth
                .inMemoryAuthentication()
                    .withUser("https://www.google.com/accounts/o8/id?id=lmkCn9xzPdsxVwG7pjYMuDgNNdASFmobNkcRPaWU")
                    .password("password")
                    .roles("USER");
            ;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public class AutoProvisioningUserDetailsService implements
            AuthenticationUserDetailsService<OpenIDAuthenticationToken> {
        public UserDetails loadUserDetails(OpenIDAuthenticationToken token) throws UsernameNotFoundException {
            return new User(token.getName(), "NOTUSED", AuthorityUtils.createAuthorityList("ROLE_USER"));
        }
    }
}
