package com.hqd.teashopping.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hqd.teashopping.entity.Address;
import org.apache.ibatis.annotations.Mapper;

/**
 * 收货地址 Mapper 接口
 */
@Mapper
public interface AddressMapper extends BaseMapper<Address> {
}
