package dog.configuration;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;

@EnableWebSecurity
public class DogSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       UserBuilder userBuilder = User.withDefaultPasswordEncoder();
       auth.inMemoryAuthentication()
               .withUser(userBuilder.username("ivan")
                       .password("ivan")
                       .roles("BREEDER"))
               .withUser(userBuilder.username("elena")
                       .password("elena")
                       .roles("CUSTOMER"))
               .withUser(userBuilder.username("petr")
                       .password("petr")
                       .roles("BREEDER", "CUSTOMER"));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/api/dogs").hasAnyRole("CUSTOMER", "BREEDER")
                .antMatchers("/api/dogs/{id}").hasAnyRole("CUSTOMER")
                .antMatchers(HttpMethod.PUT,"/api/dogs").hasRole("BREEDER")
                .antMatchers(HttpMethod.POST,"/api/dogs").hasRole("BREEDER")
                .antMatchers(HttpMethod.DELETE,"/api/dogs/{id}").hasRole("CUSTOMER")
                .and().formLogin().permitAll() //formLogin() - стандартная страница логин-пароль permitAll - для всех пользователелей
                .and().httpBasic() //тип авторицации Basic
                .and().
                anonymous().disable()
                ;

    }
}
