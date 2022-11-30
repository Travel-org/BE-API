package com.ssu.travel.domain.photo.service;

import com.ssu.travel.domain.event.entity.Event;
import com.ssu.travel.domain.notice.entity.Notice;
import com.ssu.travel.domain.photo.dto.PhotoProperties;
import com.ssu.travel.domain.photo.entity.Photo;
import com.ssu.travel.domain.photo.repository.PhotoRepository;
import com.ssu.travel.domain.photo.web.request.CRUDPhotoRequest;
import com.ssu.travel.domain.post.entity.Post;
import com.ssu.travel.domain.storage.service.StorageService;
import com.ssu.travel.domain.storage.util.Base64Decoder;
import com.ssu.travel.domain.storage.util.NameGenerator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PhotoService {

    private final PhotoRepository photoRepository;
    private final StorageService storageService;
    private final Base64Decoder base64Decoder;
    private final NameGenerator nameGenerator;
    private final PhotoProperties photoProperties;

    public <T> void savePhotos(T record, List<CRUDPhotoRequest> photoRequests) {
        if (photoRequests.isEmpty()) {
            CRUDPhotoRequest defaultPhoto = CRUDPhotoRequest.builder()
                    .order(0)
                    .imageSource(registerDefaultImage(record.getClass().getSimpleName().toLowerCase()))
                    .build();

            Photo photo = Photo.builder()
                    .record(record)
                    .path(defaultPhoto.getImageSource())
                    .extension(defaultPhoto.getExtension())
                    .order(defaultPhoto.getOrder())
                    .build();

            photoRepository.save(photo);
            return;
        }

        for (CRUDPhotoRequest CRUDPhotoRequest : photoRequests) {
            // Photo 세부 정보
            String encodedImageSource = CRUDPhotoRequest.getImageSource();
            byte[] imageSource = base64Decoder.decode(encodedImageSource);
            String extension = CRUDPhotoRequest.getExtension();
            String fileName = nameGenerator.generateRandomName().concat(".").concat(extension);
            String imagePath = storageService.store(imageSource, fileName);

            Photo photo = Photo.builder()
                    .record(record)
                    .path(imagePath)
                    .extension(extension)
                    .order(CRUDPhotoRequest.getOrder())
                    .build();

            photoRepository.save(photo);
        }
    }

    public <T> void updatePhotos(T record, List<CRUDPhotoRequest> photoRequests) {
        if (record.getClass().equals(Event.class)) {
            Event event = (Event) record;
            removeLocalImages(event.getPhotos());
            event.deletePhotos();
            savePhotos(event, photoRequests);

        } else if (record.getClass().equals(Notice.class)) {
            Notice notice = (Notice) record;
            removeLocalImages(notice.getPhotos());
            notice.deletePhotos();
            savePhotos(notice, photoRequests);

        } else if (record.getClass().equals(Post.class)) {
            Post post = (Post) record;
            removeLocalImages(post.getPhotos());
            post.deletePhotos();
            savePhotos(post, photoRequests);
        }
    }


    /**
     * Entity가 삭제 될 때 로컬 이미지도 삭제 메서드
     *
     * @param record
     * @param <T>
     */
    public <T> void removeLocalImages(T record) {
        if (record.getClass().equals(Event.class)) {
            Event event = (Event) record;
            removeLocalImages(event.getPhotos());
        } else if (record.getClass().equals(Notice.class)) {
            Notice notice = (Notice) record;
            removeLocalImages(notice.getPhotos());
        } else if (record.getClass().equals(Post.class)) {
            Post post = (Post) record;
            removeLocalImages(post.getPhotos());
        }
    }


    /**
     * private Method
     */
    private String registerDefaultImage(String className) {
        return photoProperties.getImageProperties().get(className);
    }

    // 로컬 이미지 삭제
    private void removeLocalImages(List<Photo> photos) {
        photos.stream()
                .map(Photo::getPath)
                .filter(path -> !photoProperties.checkDefaultImage(path))
                .forEach(storageService::delete);
    }

}
