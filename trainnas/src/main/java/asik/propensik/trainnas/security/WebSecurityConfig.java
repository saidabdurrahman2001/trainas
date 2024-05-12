package asik.propensik.trainnas.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(Customizer.withDefaults())
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(new AntPathRequestMatcher("/css/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/js/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/images/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/resources/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/dashboard-trainer")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/pelatihan/viewall")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/trainee/add")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/")).permitAll()
                        // .requestMatchers(new
                        // AntPathRequestMatcher("/user/add")).hasAnyAuthority("admin")
                        // .requestMatchers(new AntPathRequestMatcher("/**")).hasAnyAuthority("admin")
                        // .requestMatchers(new AntPathRequestMatcher("/pelatihan/viewall"))
                        // .hasAnyAuthority("trainee", "admin")
                        .requestMatchers(new AntPathRequestMatcher("/pelatihan/daftar"))
                        .hasAnyAuthority("trainee", "admin")
                        .requestMatchers(new AntPathRequestMatcher("/pelatihan/searchTrainee"))
                        .hasAnyAuthority("trainee", "admin", "trainermanager")
                        .requestMatchers(new AntPathRequestMatcher("/pelatihan/searchDaftarSaya"))
                        .hasAnyAuthority("trainee", "admin")
                        .requestMatchers(new AntPathRequestMatcher("/pelatihan/daftarPelatihanSaya"))
                        .hasAnyAuthority("trainee", "admin")
                        .requestMatchers(new AntPathRequestMatcher("/pelatihan/cancel/{id}"))
                        .hasAnyAuthority("trainee", "admin")
                        .requestMatchers(new AntPathRequestMatcher("/pelatihan/detail"))
                        .hasAnyAuthority("trainee", "admin", "trainermanager")
                        .requestMatchers(new AntPathRequestMatcher("/pelatihan/taka"))
                        .hasAnyAuthority("trainee", "admin", "trainermanager")
                        .requestMatchers(new AntPathRequestMatcher("/pelatihan/taba"))
                        .hasAnyAuthority("trainee", "admin", "trainermanager")
                        .requestMatchers(new AntPathRequestMatcher("/pelatihan/takaSaya"))
                        .hasAnyAuthority("trainee", "admin")
                        .requestMatchers(new AntPathRequestMatcher("/pelatihan/tabaSaya"))
                        .hasAnyAuthority("trainee", "admin")
                        .requestMatchers(new AntPathRequestMatcher("/pelatihan/filterpelatihanTrainee"))
                        .hasAnyAuthority("trainee", "admin")
                        .requestMatchers(new AntPathRequestMatcher("/pelatihan/filterDaftarPelatihanSaya"))
                        .hasAnyAuthority("trainee", "admin")
                        .requestMatchers(new AntPathRequestMatcher("/pelatihan/viewall-pelatihan"))
                        .hasAnyAuthority("admin", "trainermanager")
                        .requestMatchers(new AntPathRequestMatcher("/silabus/viewall"))
                        .hasAnyAuthority("trainee", "admin", "trainermanager")
                        .requestMatchers(new AntPathRequestMatcher("/testimoni/add"))
                        .hasAnyAuthority("trainee", "admin")
                        .requestMatchers(new AntPathRequestMatcher("/pelatihan/add"))
                        .hasAnyAuthority("trainer", "admin")
                        .requestMatchers(new AntPathRequestMatcher("/pelatihan/viewall-trainer"))
                        .hasAnyAuthority("trainer", "admin")
                        .requestMatchers(new AntPathRequestMatcher("/pelatihan/search-trainer"))
                        .hasAnyAuthority("trainer", "admin")
                        .requestMatchers(new AntPathRequestMatcher("/pelatihan/detail-trainer"))
                        .hasAnyAuthority("trainer", "admin")
                        .requestMatchers(new AntPathRequestMatcher("/pelatihan/update"))
                        .hasAnyAuthority("trainer", "admin")
                        .requestMatchers(new AntPathRequestMatcher("/pelatihan/delete"))
                        .hasAnyAuthority("trainer", "admin")
                        .requestMatchers(new AntPathRequestMatcher("/pelatihan/takaTrainer"))
                        .hasAnyAuthority("trainer", "admin", "trainermanager")
                        .requestMatchers(new AntPathRequestMatcher("/pelatihan/tabaTrainer"))
                        .hasAnyAuthority("trainer", "admin")
                        .requestMatchers(new AntPathRequestMatcher("/pelatihan/filterPelatihantrainer"))
                        .hasAnyAuthority("trainer", "admin")
                        .requestMatchers(new AntPathRequestMatcher("/silabus/viewall-trainer"))
                        .hasAnyAuthority("trainer", "admin")
                        .requestMatchers(new AntPathRequestMatcher("/silabus/tambah"))
                        .hasAnyAuthority("trainer", "admin")
                        .requestMatchers(new AntPathRequestMatcher("/silabus/uploadToGoogleDrive"))
                        .hasAnyAuthority("trainer", "admin")
                        .requestMatchers(new AntPathRequestMatcher("/silabus/detail-trainer"))
                        .hasAnyAuthority("trainer", "admin")
                        .requestMatchers(new AntPathRequestMatcher("/silabus/update"))
                        .hasAnyAuthority("trainer", "admin")
                        .requestMatchers(new AntPathRequestMatcher("/silabus/delete"))
                        .hasAnyAuthority("trainer", "admin")

                        .anyRequest().authenticated())
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll()
                        .defaultSuccessUrl("/"))
                .logout((logout) -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/login"));
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
    }

    // @Autowired
    // public void configureGlobal(AuthenticationManagerBuilder auth) throws
    // Exception{
    // auth.inMemoryAuthentication()
    // .passwordEncoder(encoder())
    // .withUser("admin")
    // .password(encoder().encode("admin123"))
    // .roles("admin");
    // }
}
