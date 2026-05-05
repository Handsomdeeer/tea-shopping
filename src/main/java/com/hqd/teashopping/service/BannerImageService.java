package com.hqd.teashopping.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hqd.teashopping.entity.BannerImage;
import com.hqd.teashopping.mapper.BannerImageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BannerImageService {

    @Autowired
    private BannerImageMapper bannerImageMapper;

    public List<BannerImage> list() {
        LambdaQueryWrapper<BannerImage> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(BannerImage::getSortOrder);
        return bannerImageMapper.selectList(wrapper);
    }

    public List<BannerImage> listActive() {
        LambdaQueryWrapper<BannerImage> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BannerImage::getStatus, 1);
        wrapper.eq(BannerImage::getType, "BANNER");
        wrapper.orderByAsc(BannerImage::getSortOrder);
        return bannerImageMapper.selectList(wrapper);
    }

    public List<BannerImage> listByType(String type) {
        LambdaQueryWrapper<BannerImage> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BannerImage::getType, type);
        wrapper.eq(BannerImage::getStatus, 1);
        wrapper.orderByAsc(BannerImage::getSortOrder);
        return bannerImageMapper.selectList(wrapper);
    }

    public boolean save(BannerImage bannerImage) {
        if (bannerImage.getSortOrder() == null) {
            bannerImage.setSortOrder(0);
        }
        if (bannerImage.getStatus() == null) {
            bannerImage.setStatus(1);
        }
        if (bannerImage.getId() == null) {
            return bannerImageMapper.insert(bannerImage) > 0;
        }
        return bannerImageMapper.updateById(bannerImage) > 0;
    }

    public boolean delete(Long id) {
        return bannerImageMapper.deleteById(id) > 0;
    }
}
