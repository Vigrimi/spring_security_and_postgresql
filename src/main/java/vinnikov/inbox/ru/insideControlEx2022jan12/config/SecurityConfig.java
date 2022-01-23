package vinnikov.inbox.ru.insideControlEx2022jan12.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import vinnikov.inbox.ru.insideControlEx2022jan12.service.CustomUserDetailsService;

@EnableWebSecurity//(debug = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private CustomUserDetailsService userDetailsService;

    @Autowired
    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override // метод для настроек безопасности
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin/**").access("hasRole('ADMIN')")
                .antMatchers("/user/**").access("hasAnyRole('USER', 'ADMIN')")
// три строки выше - это какие запросы каким ролям доступны
    // а строки ниже - это всё что связано со входом и выходом
                .and().formLogin().permitAll()  //login configuration - форма входа доступна всем
                .loginPage("/login") // форма входа доступна по ссылке /login
                .loginProcessingUrl("/login-handler")
                .usernameParameter("login")
                .passwordParameter("password")
                .defaultSuccessUrl("/user/account") // если пользователь авторизовался успешно, то будет
                // перенаправлен на свой аккаунт
                .and().logout()// настройки для выхода
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // гет запрос на логаут
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .and()
                .exceptionHandling()
                .accessDeniedPage("/error");
    }

    @Override // метод для регистрации сервиса или провайдера
    public void configure(AuthenticationManagerBuilder auth) throws Exception
    { //
      auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//System.out.println("--------99999 " + auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder()));
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    { // нужен если в нашей таблице БД пароли хранятся в зашифрованном виде - так и должно быть
        BCryptPasswordEncoder b = new BCryptPasswordEncoder();
        //System.out.println("------8888 Encoder:" + b);
        return b;
    }
}