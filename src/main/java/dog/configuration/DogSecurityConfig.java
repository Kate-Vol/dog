package dog.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.sql.DataSource;

@EnableWebSecurity
public class DogSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource);

//       UserBuilder userBuilder = User.withDefaultPasswordEncoder();
//       auth.inMemoryAuthentication()
//               .withUser(userBuilder.username("ivan")
//                       .password("ivan")
//                       .roles("BREEDER"))
//               .withUser(userBuilder.username("elena")
//                       .password("elena")
//                       .roles("CUSTOMER"))
//               .withUser(userBuilder.username("petr")
//                       .password("petr")
//                       .roles("BREEDER", "CUSTOMER"));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();
//        http.csrf().disable();
//        http.authorizeRequests()
//                .antMatchers("/api/dogs").hasAnyRole("CUSTOMER", "BREEDER")
//                .antMatchers("/api/dogs/{id}").hasAnyRole("CUSTOMER")
//                .antMatchers(HttpMethod.PUT,"/api/dogs").hasRole("BREEDER")
//                .antMatchers(HttpMethod.POST,"/api/dogs").hasRole("BREEDER")
//                .antMatchers(HttpMethod.DELETE,"/api/dogs/{id}").hasRole("CUSTOMER")
//                .and().formLogin().permitAll() //formLogin() - стандартная страница логин-пароль permitAll - для всех пользователелей
//                .and().httpBasic() //тип авторицации Basic
//                .and().
//                anonymous().disable()
//                ;

    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }
}
