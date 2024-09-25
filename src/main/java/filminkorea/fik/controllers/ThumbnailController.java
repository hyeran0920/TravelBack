package filminkorea.fik.controllers;

import filminkorea.fik.dtos.ThumbnailDto;
import filminkorea.fik.services.ThumbnailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/thumbnails")
public class ThumbnailController {

    private final ThumbnailService thumbnailService;

    @Autowired
    public ThumbnailController(ThumbnailService thumbnailService) {
        this.thumbnailService = thumbnailService;
    }

    @PostMapping("/upload")
    public ResponseEntity<ThumbnailDto> uploadThumbnail(@RequestParam("file") MultipartFile file) {
        try {
            ThumbnailDto savedThumbnail = thumbnailService.saveThumbnail(file);
            return new ResponseEntity<>(savedThumbnail, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ThumbnailDto> getThumbnail(@PathVariable Integer id) {
        Optional<ThumbnailDto> thumbnailDto = thumbnailService.getThumbnail(id);
        return thumbnailDto.map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ThumbnailDto>> getAllThumbnails() {
        List<ThumbnailDto> thumbnails = thumbnailService.getAllThumbnails();
        return new ResponseEntity<>(thumbnails, HttpStatus.OK);
    }

}
