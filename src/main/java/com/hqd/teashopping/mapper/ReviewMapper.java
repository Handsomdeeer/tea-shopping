package com.hqd.teashopping.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hqd.teashopping.entity.Review;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品评价 Mapper 接口
 */
@Mapper
public interface ReviewMapper extends BaseMapper<Review> {
}
