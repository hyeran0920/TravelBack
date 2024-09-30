package filminkorea.fik.controllers;

import filminkorea.fik.dtos.PhotoDto;
import filminkorea.fik.services.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/upload")
@CrossOrigin(origins = "http://localhost:3000")
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    // MultipartFile : 클라이언트가 업로드한 파일 데이터를 처리하는 객체
    @PostMapping
    public ResponseEntity<PhotoDto> uploadPhoto(@RequestParam("file") MultipartFile file) {
        try {
            // PhotoService를 통해 파일을 저장하고 저장된 photoDto를 반환
            PhotoDto savedPhoto = photoService.savePhoto(file);
            return ResponseEntity.ok(savedPhoto);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
