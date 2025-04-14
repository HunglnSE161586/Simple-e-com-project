package com.hung.shop.mapper;

import com.hung.shop.dto.request.UserAuthCreateDto;
import com.hung.shop.entity.UserAuth;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserAuthMapper {
    UserAuth toEntity(UserAuthCreateDto userAuthCreateDto);
}
