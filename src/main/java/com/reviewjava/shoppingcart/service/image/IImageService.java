package com.reviewjava.shoppingcart.service.image;

import com.reviewjava.shoppingcart.dto.ImageDto;
import com.reviewjava.shoppingcart.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    Image getImage(Long id);
    void deleteImage(Long id);
    List<ImageDto> saveImages(List<MultipartFile> file, Long productId);
    void updateImage(MultipartFile file, Long imageId);
}
