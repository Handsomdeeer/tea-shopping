package com.hqd.teashopping.service;

import com.hqd.teashopping.entity.PaymentConfig;
import com.hqd.teashopping.mapper.PaymentConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 支付配置服务类
 */
@Service
public class PaymentConfigService {

    @Autowired
    private PaymentConfigMapper paymentConfigMapper;

    /**
     * 获取支付配置（只有一条记录）
     * @return 支付配置
     */
    public PaymentConfig getConfig() {
        return paymentConfigMapper.selectById(1L);
    }

    /**
     * 保存或更新支付配置
     * @param config 支付配置
     * @return 是否成功
     */
    public boolean saveConfig(PaymentConfig config) {
        PaymentConfig exist = paymentConfigMapper.selectById(1L);
        if (exist == null) {
            config.setId(1L);
            return paymentConfigMapper.insert(config) > 0;
        } else {
            config.setId(1L);
            return paymentConfigMapper.updateById(config) > 0;
        }
    }
}
