package com.hqd.teashopping.controller;

import com.hqd.teashopping.common.Result;
import com.hqd.teashopping.entity.BannerImage;
import com.hqd.teashopping.service.BannerImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class BannerController {

    @Autowired
    private BannerImageService bannerImageService;

    @GetMapping("/banners")
    public Result<List<BannerImage>> listActive() {
        return Result.success(bannerImageService.listActive());
    }

    @GetMapping("/banners/ad")
    public Result<List<BannerImage>> listAds() {
        return Result.success(bannerImageService.listByType("AD"));
    }
}
