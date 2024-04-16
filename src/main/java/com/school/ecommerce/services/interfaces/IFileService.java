package com.school.ecommerce.services.interfaces;

import com.school.ecommerce.controllers.dtos.responses.BaseResponse;
import org.springframework.web.multipart.MultipartFile;

public interface IFileService {

    BaseResponse upload(MultipartFile multipartFile);

    void delete(String imageUrl);

}
