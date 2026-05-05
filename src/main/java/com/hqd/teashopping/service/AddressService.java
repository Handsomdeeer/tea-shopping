package com.hqd.teashopping.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hqd.teashopping.entity.Address;
import com.hqd.teashopping.mapper.AddressMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 收货地址服务类
 */
@Service
public class AddressService {

    @Autowired
    private AddressMapper addressMapper;

    /**
     * 根据用户ID获取地址列表
     * @param userId 用户ID
     * @return 地址列表
     */
    public List<Address> getAddressesByUserId(Long userId) {
        LambdaQueryWrapper<Address> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Address::getUserId, userId);
        return addressMapper.selectList(wrapper);
    }

    /**
     * 添加收货地址
     * @param address 地址信息
     * @return 是否成功
     */
    public boolean addAddress(Address address) {
        address.setCreateTime(LocalDateTime.now());
        address.setUpdateTime(LocalDateTime.now());
        int result = addressMapper.insert(address);
        return result > 0;
    }

    /**
     * 更新收货地址
     * @param address 地址信息
     * @return 是否成功
     */
    public boolean updateAddress(Address address) {
        address.setUpdateTime(LocalDateTime.now());
        int result = addressMapper.updateById(address);
        return result > 0;
    }

    /**
     * 删除收货地址
     * @param id 地址ID
     * @return 是否成功
     */
    public boolean deleteAddress(Long id) {
        int result = addressMapper.deleteById(id);
        return result > 0;
    }
}
