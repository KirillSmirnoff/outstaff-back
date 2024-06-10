package ru.k2.outstaff.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import ru.k2.outstaff.repository.UserRepository
import ru.k2.outstaff.service.SecUserService

@EnableWebSecurity
//class SecurityConfiguration(private val userRepository: UserRepository) : WebSecurityConfigurerAdapter(){
class SecurityConfiguration: WebSecurityConfigurerAdapter(){

//    override fun configure(web: WebSecurity?) {
//        web!!.ignoring()
//                .antMatchers("/")
//                .antMatchers("/static/**")
//                .antMatchers("/templates/**")
//
//    }
//
    override fun configure(http: HttpSecurity?) {
//        http!!
//                .csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/home").authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/auth").loginProcessingUrl("/auth/process").successForwardUrl("/home")
//                .and()
//                .logout()
    }

//    override fun configure(auth: AuthenticationManagerBuilder?) {
//        auth!!.inMemoryAuthentication()
//                .withUser("man_anton").password("0").roles("MANAGER")
//    }

//    @Bean
//    fun passwordEncoder(): PasswordEncoder{
//        return BCryptPasswordEncoder()
//    }

//    @Bean
//    fun daoAuthenticationProvider(): DaoAuthenticationProvider{
//        val daoAuthenticationProvider = DaoAuthenticationProvider()
//        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder())
//        daoAuthenticationProvider.setUserDetailsService(SecUserService(userRepository))
//
//        return daoAuthenticationProvider
//    }
}