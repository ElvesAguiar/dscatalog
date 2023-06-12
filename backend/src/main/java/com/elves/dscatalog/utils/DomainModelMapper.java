package com.elves.dscatalog.utils;

import org.springframework.context.annotation.Bean;

public class DomainModelMapper extends org.modelmapper.ModelMapper {
    @Bean
    public org.modelmapper.ModelMapper modelMapper(){
        return new org.modelmapper.ModelMapper();
    }
}
