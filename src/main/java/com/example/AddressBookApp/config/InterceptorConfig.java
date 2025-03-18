package com.example.AddressBookApp.config;
import com.example.AddressBookApp.Interceptor.Interceptor1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//CHANGES

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    Interceptor1 addressInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(addressInterceptor)
                .addPathPatterns("/addressbook/**");
    }
}
