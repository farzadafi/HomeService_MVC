package com.example.HomeService_MVC.validation.image;

import net.sf.jmimemagic.*;
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
        MagicMatch match ;
        try {
            match = Magic.getMagicMatch(multipartFile.getBytes());
        } catch (MagicParseException | MagicMatchNotFoundException | MagicException | IOException e) {
            e.printStackTrace();
            return false;
        }
        String mimeType = match.getMimeType();
        if(mimeType.length() < 1)
            return false;

        String[] array = mimeType.split("/");
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
