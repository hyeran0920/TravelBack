package filminkorea.fik.services;

import filminkorea.fik.dtos.PhotoDto;
import filminkorea.fik.entities.Photo;
import filminkorea.fik.repositories.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class PhotoService {
    @Value("${photo.upload.dir}")
    private String uplodadDir;

    @Autowired
    private PhotoRepository photoRepository;

    public PhotoDto savePhoto(MultipartFile file) throws IOException {
        // 고유한 파일 이름 생성 (타임스탬프 사용 => 마지막에 찍은 사진만 보이지 x)
        String originalFileName = file.getOriginalFilename();

        // 파일 이름이 null일 경우 예외 처리 => substring이 nullpointexception 오류를 발생할 수 있어서
        if (originalFileName == null || !originalFileName.contains(".")) {
            throw new IllegalArgumentException("유효하지 않은 파일 이름입니다.");
        }

        // 파일 확장자 추출
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));

        //고유한 파일 이름 생성
        String fileName = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + fileExtension;

        //파일 저장 경로
        String filePath = Paths.get(uplodadDir, fileName).toString();

        // 파일 저장
        Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);

        // 파일 정보 저장
        Photo photo = new Photo();
        photo.setFileName(fileName);
        photo.setFilePath(filePath);
        photo.setFileType(file.getContentType());

        Photo savedPhoto = photoRepository.save(photo);

        // DTO로 변환
        return new PhotoDto(savedPhoto.getId(), savedPhoto.getFileName(), savedPhoto.getFilePath(), savedPhoto.getFileType());
    }
}
