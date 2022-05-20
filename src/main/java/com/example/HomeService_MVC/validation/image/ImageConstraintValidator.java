package com.example.HomeService_MVC.validation.image;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.IOException;

public class ImageConstraintValidator implements ConstraintValidator<Image, MultipartFile> {

    @Override
    public void initialize(Image constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext constraintValidatorContext) {
        if(multipartFile.isEmpty())
            return false;

        String[] array = multipartFile.getContentType().split("/");
        if(!array[0].equals("image"))
            return false;
        if( (!array[1].equals("jpg")) && (!array[1].equals("jpeg")) )
            return false;
        try {
            if(multipartFile.getBytes().length > 300000) {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
