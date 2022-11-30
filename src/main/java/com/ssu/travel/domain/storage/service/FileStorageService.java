package com.ssu.travel.domain.storage.service;

import com.ssu.travel.domain.storage.exception.ImageFileException;
import com.ssu.travel.global.dto.ImageDto;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class FileStorageService implements StorageService {

    private static String storageRoot;
    private static String location;


    /**
     * static 변수 @value Injection
     */

    @Value("${app.storage.root}")
    public void setStorageRoot(String storageRoot) {
        FileStorageService.storageRoot = storageRoot;
    }

    @Value("${app.storage.location}")
    public void setLocation(String location) {
        FileStorageService.location = location;
    }

    @Override
    @Transactional
    public String store(byte[] data, String name) {
        String path = storageRoot + "/" + name;
        try (FileOutputStream fileOutputStream = new FileOutputStream(path)) {
            fileOutputStream.write(data);
        } catch (IOException e) {
            throw new ImageFileException("파일 저장중 문제가 발생했습니다.");
        }
        return location + "/" + name;
    }

    @Override
    @Transactional
    public void delete(String path) {
        String relPath = path.substring(location.length());
        String absPath = storageRoot + relPath;
        File target = new File(absPath);
        target.delete();
    }

    @Override
    public String getFileSource(String path) {
        String relPath = path.substring(location.length());
        String absPath = storageRoot + relPath;
        File target = new File(absPath);

        try (FileInputStream fileInputStream = new FileInputStream(target)) {
            byte[] raw = fileInputStream.readAllBytes();
            return Base64.getEncoder().encodeToString(raw);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ImageDto getFileSourceAndExtension(String path) {
        String extension = path.substring(path.lastIndexOf(".") + 1);
        String relPath = path.substring(location.length());
        String absPath = storageRoot + relPath;
        File target = new File(absPath);

        try (FileInputStream fileInputStream = new FileInputStream(target)) {
            byte[] raw = fileInputStream.readAllBytes();
            return ImageDto.builder()
                    .imageSource(Base64.getEncoder().encodeToString(raw))
                    .extension(extension)
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * static 메서드로 함
     */
    public static String getFileSourceForPhotoDto(String path) {
        String relPath = path.substring(location.length());
        String absPath = storageRoot + relPath;
        File target = new File(absPath);

        try (FileInputStream fileInputStream = new FileInputStream(target)) {
            byte[] raw = fileInputStream.readAllBytes();
            return Base64.getEncoder().encodeToString(raw);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
