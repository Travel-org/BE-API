package com.ssu.travel.domain.photo.dto;

import java.util.Map;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "app")
public class PhotoProperties {

    private Map<String, String> photoDefaultImages;

    public PhotoProperties(Map<String, String> photoDefaultImages) {
        this.photoDefaultImages = photoDefaultImages;
    }

    public Map<String, String> getImageProperties() {
        return this.photoDefaultImages;
    }

    public boolean checkDefaultImage(String path) {
        return this.photoDefaultImages.containsValue(path);
    }
}
