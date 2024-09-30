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

    // 애플리케이션 프로퍼티에서 image.upload.dir 값을 주입
    @Value("${image.upload.dir}")
    private String uploadDir;

    // 의존성 주입을 통해 ThumbnailRepository를 사용 => 이거 왜 autowired를 안썼는지?
    private final ThumbnailRepository thumbnailRepository;

    public ThumbnailService(ThumbnailRepository thumbnailRepository) {
        this.thumbnailRepository = thumbnailRepository;
    }

    // 파일을 저장하고 Thumbnail 객체를 생성한 후, 이를 DTO로 반환하는 메서드
    public ThumbnailDto saveThumbnail(MultipartFile file) throws IOException {

        // 파일명 추출
        String imageName = file.getOriginalFilename();
        // 저장할 경로 생성
        Path path = Paths.get(uploadDir, imageName);

        // 파일 저장
        Files.write(path, file.getBytes());

        // 경로를 저장한 엔티티 생성 및 저장
        Thumbnail thumbnail = new Thumbnail();
        thumbnail.setImage_Name(imageName);
        thumbnail.setImage_Url(path.toString());

        // Thumbnail 엔티티를 DB에 저장
        thumbnailRepository.save(thumbnail);

        // 저장한 데이터를 바탕으로 DTO 객체 생성 및 반환
        ThumbnailDto thumbnailDto = new ThumbnailDto();
        thumbnailDto.setId(thumbnail.getId());
        thumbnailDto.setImage_Name(imageName);
        thumbnailDto.setImage_Url(path.toString());

        return thumbnailDto;
    }

    // ID로 Thumbnail을 조회하고 DTO로 반환하는 메서드
    public Optional<ThumbnailDto> getThumbnail(Integer id) {

        // 주어진 ID로 Thumbnail을 조회
        Optional<Thumbnail> thumbnail = thumbnailRepository.findById(id);

        // Optional을 사용하여 결과가 존재할 경우 DTO로 변환
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

    // 모든 Thumbnail을 조회하고 DTO 목록으로 반환하는 메서드
    public List<ThumbnailDto> getAllThumbnails() {
        // 모든 Thumbnail을 DB에서 조회
        List<Thumbnail> thumbnails = thumbnailRepository.findAll();
        // Stream API를 사용하여 엔티티 목록을 DTO 목록으로 변환
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


