package com.ssu.travel.domain.storage.service;

import com.ssu.travel.global.dto.ImageDto;

public interface StorageService {

    /**
     * 파일을 저장소에 저장하고 접근할 수 있는 경로정보를 반환한다.
     * @param data 저장 대상 파일
     * @param name 저장할 파일명
     * @return path 접근 경로 문자열
     */
    String store(byte[] data, String name);

    /**
     * 경로 내의 파일을 제거한다.
     * @param path 제거 대상 파일 경로
     */
    void delete(String path);

    /**
     * 해당 경로에 존재하는 파일의 값을 Base64 로 인코딩 하여 반환한다.
     * @param path 스토리지를 통해 접근가능한 파일 경로
     * @return file Base64 로 인코딩된 값
     */
    String getFileSource(String path);

    /**
     * 순서가 필요없는 image
     */
    ImageDto getFileSourceAndExtension(String path);
}
