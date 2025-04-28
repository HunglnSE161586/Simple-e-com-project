package com.hung.shop.auth.mapper;

import com.hung.shop.auth.dto.request.UserAuthCreateDto;
import com.hung.shop.auth.entity.UserAuth;
import com.hung.shop.share.UserAuthPOJO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserAuthMapper {
    UserAuth toEntity(UserAuthCreateDto userAuthCreateDto);
    UserAuthPOJO toPOJO(UserAuth userAuth);
    UserAuth toEntity(UserAuthPOJO userAuthPOJO);
}
