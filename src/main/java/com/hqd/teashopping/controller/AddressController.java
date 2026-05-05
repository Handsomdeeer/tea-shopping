package com.hqd.teashopping.controller;

import com.hqd.teashopping.common.Result;
import com.hqd.teashopping.entity.Address;
import com.hqd.teashopping.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 收货地址控制器
 */
@RestController
@RequestMapping("/api/v1/addresses")
@CrossOrigin(origins = "*")
public class AddressController {

    @Autowired
    private AddressService addressService;

    /**
     * 获取收货地址列表
     * GET /api/v1/addresses
     */
    @GetMapping
    public Result<List<Address>> getAddresses(@RequestParam Long userId) {
        List<Address> addresses = addressService.getAddressesByUserId(userId);
        return Result.success(addresses);
    }

    /**
     * 添加收货地址
     * POST /api/v1/addresses
     */
    @PostMapping
    public Result<Address> addAddress(@RequestBody Address address, @RequestParam Long userId) {
        address.setUserId(userId);
        boolean success = addressService.addAddress(address);
        if (success) {
            return Result.success(address);
        } else {
            return Result.error("添加失败");
        }
    }

    /**
     * 更新收货地址
     * PUT /api/v1/addresses/{id}
     */
    @PutMapping("/{id}")
    public Result<Void> updateAddress(@PathVariable Long id, @RequestBody Address address) {
        address.setId(id);
        boolean success = addressService.updateAddress(address);
        if (success) {
            return Result.success(null);
        } else {
            return Result.error("更新失败");
        }
    }

    /**
     * 删除收货地址
     * DELETE /api/v1/addresses/{id}
     */
    @DeleteMapping("/{id}")
    public Result<Void> deleteAddress(@PathVariable Long id) {
        boolean success = addressService.deleteAddress(id);
        if (success) {
            return Result.success(null);
        } else {
            return Result.error("删除失败");
        }
    }
}
