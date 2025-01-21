package net.orekhov.demosecuritytry.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity  // Явно включает поддержку веб-безопасности
public class SecurityConfig {

    // Конфигурация для настройки безопасности
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        // Разрешаем доступ без логина только к URL /home
                        .requestMatchers("/home").permitAll()
                        // Все остальные запросы требуют аутентификации
                        .anyRequest().authenticated())
                // Настроим форму логина с настройками по умолчанию
                .formLogin(withDefaults());  // Новый способ настройки логина

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
