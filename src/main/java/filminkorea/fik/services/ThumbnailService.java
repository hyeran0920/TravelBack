package filminkorea.fik.services;

import filminkorea.fik.dtos.ThumbnailDto;
import filminkorea.fik.entities.Thumbnail;
import filminkorea.fik.repositories.ThumbnailRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ThumbnailService {
    @Value("${image.upload.dir}")
    private String uploadDir;

    private final ThumbnailRepository thumbnailRepository;

    public ThumbnailService(ThumbnailRepository thumbnailRepository) {
        this.thumbnailRepository = thumbnailRepository;
    }

    public ThumbnailDto saveThumbnail(MultipartFile file) throws IOException {
        // 저장할 경로 생성
        String imageName = file.getOriginalFilename();
        Path path = Paths.get(uploadDir, imageName);

        // 파일 저장
        Files.write(path, file.getBytes());

        // 경로를 저장한 엔티티 생성 및 저장
        Thumbnail thumbnail = new Thumbnail();
        thumbnail.setImage_Name(imageName);
        thumbnail.setImage_Url(path.toString());

        thumbnailRepository.save(thumbnail);

        ThumbnailDto thumbnailDto = new ThumbnailDto();
        thumbnailDto.setId(thumbnail.getId());
        thumbnailDto.setImage_Name(imageName);
        thumbnailDto.setImage_Url(path.toString());

        return thumbnailDto;
    }

    public Optional<ThumbnailDto> getThumbnail(Integer id) {
        Optional<Thumbnail> thumbnail = thumbnailRepository.findById(id);

        return thumbnail.map(t -> {
            ThumbnailDto dto = new ThumbnailDto();
            dto.setId(t.getId());
            dto.setMedia_type(t.getMedia_type());
            dto.setTitle_nm(t.getTitle_nm());
            dto.setImage_Name(t.getImage_Name());
            dto.setImage_Url(t.getImage_Url());
            return dto;
        });
    }

    public List<ThumbnailDto> getAllThumbnails() {
        List<Thumbnail> thumbnails = thumbnailRepository.findAll();
        return thumbnails.stream().map(t -> {
            ThumbnailDto dto = new ThumbnailDto();
            dto.setId(t.getId());
            dto.setMedia_type(t.getMedia_type());
            dto.setTitle_nm(t.getTitle_nm());
            dto.setImage_Name(t.getImage_Name());
            dto.setImage_Url(t.getImage_Url()); // 이미지 파일 경로
            return dto;
        }).collect(Collectors.toList());
    }

}
