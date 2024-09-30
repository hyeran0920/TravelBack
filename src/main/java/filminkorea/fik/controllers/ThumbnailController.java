package filminkorea.fik.controllers;

import filminkorea.fik.dtos.ThumbnailDto;
import filminkorea.fik.services.ThumbnailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/thumbnails")
public class ThumbnailController {

    private final ThumbnailService thumbnailService;

    // 이미지 파일이 저장될 디렉토리 경로 지정
    private final String uploadDir = "c:/upload/";

    // 이미지 파일을 제공하는 엔드포인트 추가
    @GetMapping("/images/{image_Name}")
    public ResponseEntity<Resource> getImage(@PathVariable String image_Name) {
        try {
            // 경로 생성
            Path imagePath = Paths.get(uploadDir, image_Name);
            Resource resource = new UrlResource(imagePath.toUri());

            // 파일이 존재하고 읽을 수 있을 때만 응답
            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok() // 성공 응답 생성
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + image_Name + "\"") //파일명 설정
                        .body(resource); // 파일 데이터를 응답에 포함
            } else {
                // 파일 없으면 404 응답
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            // 서버 에러 발생 시 500 응답
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @Autowired
    public ThumbnailController(ThumbnailService thumbnailService) {
        this.thumbnailService = thumbnailService;
    }

    // 파일 업로드를 처리하는 메서드
    @PostMapping("/upload")
    public ResponseEntity<ThumbnailDto> uploadThumbnail(@RequestParam("file") MultipartFile file) {
        try {
            // ThumbnailService를 이용해 파일을 저장하고, 저장된 ThumbnailDto를 반환
            ThumbnailDto savedThumbnail = thumbnailService.saveThumbnail(file);
            return new ResponseEntity<>(savedThumbnail, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ID로 Thumbnail을 조회하는 메서드
    @GetMapping("/{id}")
    public ResponseEntity<ThumbnailDto> getThumbnail(@PathVariable Integer id) {
        
        //ID로 조회
        Optional<ThumbnailDto> thumbnailDto = thumbnailService.getThumbnail(id);
        return thumbnailDto.map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // 모든 썸네일을 조회하는 메서드
    @GetMapping("/all")
    public ResponseEntity<List<ThumbnailDto>> getAllThumbnails() {
        // 전체 썸네일 조회
        List<ThumbnailDto> thumbnails = thumbnailService.getAllThumbnails();
        return new ResponseEntity<>(thumbnails, HttpStatus.OK);
    }

}

//package filminkorea.fik.controllers;
//
//import filminkorea.fik.dtos.ThumbnailDto;
//import filminkorea.fik.services.ThumbnailService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.UrlResource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.*;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/thumbnails")
//public class ThumbnailController {
//
//    private final ThumbnailService thumbnailService;
//
//    private final String uploadDir = "c:/upload/";
//
//    // 이미지 파일을 제공하는 엔드포인트 추가
//    @GetMapping("/images/{image_Name}")
//    public ResponseEntity<Resource> getImage(@PathVariable String image_Name) {
//        try {
//            Path imagePath = Paths.get(uploadDir, image_Name + ".jpg");
//            Resource resource = new UrlResource(imagePath.toUri());
//
//            if (resource.exists() || resource.isReadable()) {
//                return ResponseEntity.ok()
//                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + image_Name + "\"")
//                        .body(resource);
//            } else {
//                throw new RuntimeException("Could not read the file!");
//            }
//        } catch (Exception e) {
//            throw new RuntimeException("Error: " + e.getMessage());
//        }
//    }
//
//    @Autowired
//    public ThumbnailController(ThumbnailService thumbnailService) {
//        this.thumbnailService = thumbnailService;
//    }
//
//    @PostMapping("/upload")
//    public ResponseEntity<ThumbnailDto> uploadThumbnail(@RequestParam("file") MultipartFile file) {
//        try {
//            ThumbnailDto savedThumbnail = thumbnailService.saveThumbnail(file);
//            return new ResponseEntity<>(savedThumbnail, HttpStatus.OK);
//        } catch (IOException e) {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<ThumbnailDto> getThumbnail(@PathVariable Integer id) {
//        Optional<ThumbnailDto> thumbnailDto = thumbnailService.getThumbnail(id);
//        return thumbnailDto.map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
//                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }
//
//    @GetMapping("/all")
//    public ResponseEntity<List<ThumbnailDto>> getAllThumbnails() {
//        List<ThumbnailDto> thumbnails = thumbnailService.getAllThumbnails();
//        return new ResponseEntity<>(thumbnails, HttpStatus.OK);
//    }
//
//}
