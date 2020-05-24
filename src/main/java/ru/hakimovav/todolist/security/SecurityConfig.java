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

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter { // 38. Создаем класс SecurityConfig

    private PasswordEncoder passwordEncoder; // 39. Создаем объект passwordEncoder

    private UserAuthService userAuthService; // 51. Создаем объект из авторизации

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) { // 40. Делаем автозаполнение
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setUserAuthService(UserAuthService userAuthService) { // 52. Делаем автозаполнение
        this.userAuthService = userAuthService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) { // 41. Прописываем метод configure
        auth.authenticationProvider(authenticationProvider()); // 53. Внедряем authenticationProvider
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception { // 54. Пишем, как именно защищаем наше приложение
        http
                .authorizeRequests()
                .antMatchers("/").authenticated() // 55. Делаем страницу туду доступной только авторизованным пользователям
                .and()
                .formLogin() // 56. Делаем форму для логина
                .loginPage("/login") // 57. Адрес логирования
                .loginProcessingUrl("/authenticateTheUser") // 58. Куда отправляются данные логирования
                .permitAll() // 59. Доступ к странице всем кто зашел на сайт
                .and()
                .logout() // 60. Делаем логаут
                .logoutSuccessUrl("/login") // 61. После логаута снова переходим на страницу логина
                .permitAll(); // 62. Доступ к странице всем кто зашел на сайт
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() { // 52. Создаем бин authenticationProvider, для авторизации
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userAuthService);
        auth.setPasswordEncoder(passwordEncoder);
        return auth;
    }

}
