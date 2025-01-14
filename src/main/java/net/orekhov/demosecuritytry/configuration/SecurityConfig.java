package net.orekhov.demosecuritytry.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // Конфигурация для настройки безопасности
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                // Разрешаем доступ без логина только к URL /home
                .antMatchers("/home").permitAll()
                // Все остальные запросы требуют аутентификации
                .anyRequest().authenticated()
                .and()
                // Настроим форму логина
                .formLogin()
                // Разрешим доступ к форме логина всем
                .permitAll();

        return http.build();  // Строим и возвращаем объект конфигурации
    }

    // Создание и настройка UserDetailsService с пользователем в памяти
    @Bean
    public UserDetailsService userDetailsService() {
        var userDetailsManager = new InMemoryUserDetailsManager();
        // Создаем пользователя с именем "user" и паролем "password"
        userDetailsManager.createUser(User.withUsername("user")
                // {noop} указывает, что пароль хранится в незашифрованном виде (обычный пароль)
                .password("{noop}password")
                .roles("USER")  // Роль пользователя "USER"
                .build());
        return userDetailsManager;  // Возвращаем объект UserDetailsService с созданным пользователем
    }
}
