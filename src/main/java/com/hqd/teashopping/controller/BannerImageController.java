package com.hqd.teashopping.controller;

import com.hqd.teashopping.common.Result;
import com.hqd.teashopping.entity.BannerImage;
import com.hqd.teashopping.service.BannerImageService;
import com.hqd.teashopping.utils.AliOssUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin/banners")
@CrossOrigin(origins = "*")
public class BannerImageController {

    @Autowired
    private BannerImageService bannerImageService;

    @Autowired
    private AliOssUtil aliOssUtil;

    @GetMapping
    public Result<List<BannerImage>> list() {
        return Result.success(bannerImageService.list());
    }

    @PostMapping
    public Result<Void> save(@RequestBody BannerImage bannerImage) {
        boolean success = bannerImageService.save(bannerImage);
        if (success) {
            return Result.success(null);
        }
        return Result.error("保存失败");
    }

    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Long id, @RequestBody BannerImage bannerImage) {
        bannerImage.setId(id);
        boolean success = bannerImageService.save(bannerImage);
        if (success) {
            return Result.success(null);
        }
        return Result.error("更新失败");
    }

    @PostMapping("/upload")
    public Result<String> upload(@RequestParam("file") MultipartFile file) {
        try {
            String url = aliOssUtil.upload(file.getInputStream(), file.getOriginalFilename());
            return Result.success(url);
        } catch (Exception e) {
            return Result.error("上传失败：" + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        boolean success = bannerImageService.delete(id);
        if (success) {
            return Result.success(null);
        }
        return Result.error("删除失败");
    }
}
