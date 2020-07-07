package ru.hakimovav.todolist.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

// В данном классе прописываем какие страницы доступны только после авторизации
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // Создаем бины passwordEncoder и авторизации
    private PasswordEncoder passwordEncoder;
    private UserAuthService userAuthService;
    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) { // 40. Делаем автозаполнение
        this.passwordEncoder = passwordEncoder;
    }
    @Autowired
    public void setUserAuthService(UserAuthService userAuthService) { // 52. Делаем автозаполнение
        this.userAuthService = userAuthService;
    }

    // Внедряем authenticationProvider
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    // Данный метод как раз указывает, какие страницы доступны, а какие нет
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/todo/*").authenticated() // Делаем страницу туду доступной только авторизованным пользователям
                .and()
                .formLogin() // Делаем форму для логина
                .loginPage("/login") // Адрес логирования
                .loginProcessingUrl("/authenticateTheUser") // Куда отправляются данные логирования
                .permitAll() // Доступ к странице всем кто зашел на сайт
                .and()
                .logout() // Делаем логаут
                .logoutSuccessUrl("/login") // Куда переходим после логаута
                .permitAll(); // Доступ к странице всем кто зашел на сайт
    }

    // Создаем бин authenticationProvider, для авторизации
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userAuthService);
        auth.setPasswordEncoder(passwordEncoder);
        return auth;
    }

}
