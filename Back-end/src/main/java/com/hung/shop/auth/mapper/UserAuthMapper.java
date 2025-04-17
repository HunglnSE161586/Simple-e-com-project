package com.hung.shop.auth.mapper;

import com.hung.shop.auth.dto.request.UserAuthCreateDto;
import com.hung.shop.auth.entity.UserAuth;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserAuthMapper {
    UserAuth toEntity(UserAuthCreateDto userAuthCreateDto);
}
