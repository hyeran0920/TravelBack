package filminkorea.fik.controllers;

import filminkorea.fik.dtos.LayerDto;
import filminkorea.fik.services.LayerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/layer")
public class LayerController {

    @Value("${layer.upload.dir}")
    private String uploadDir;  // 파일이 저장된 디렉토리 경로

    private final LayerService layerService;

    public LayerController(LayerService layerService) {
        this.layerService = layerService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<LayerDto>> getAllLayers() {
        List<LayerDto> layers = layerService.getAllLayers();
        return new ResponseEntity<>(layers, HttpStatus.OK);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadLayerFile(@RequestParam("file") MultipartFile file) {
        try {
            LayerDto savedLayer = layerService.saveLayerFile(file);
            return new ResponseEntity<>("File uploaded successfully: " + savedLayer.getImgsrc(), HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("File upload failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // countNum을 사용해서 파일 다운로드
    @GetMapping("/files/{countNum}")
    public ResponseEntity<Resource> downloadLayerFile(@PathVariable int countNum) {
        try {
            Path filePath = layerService.getLayerFilePathByCountNum(countNum);  // 파일 경로를 얻음
            Resource resource = new UrlResource(filePath.toUri());  // 파일을 리소스로 변환

            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filePath.getFileName().toString() + "\"")
                        .body(resource);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{countNum}")
    public ResponseEntity<LayerDto> getLayerByCountNum(@PathVariable int countNum) {
        LayerDto layer = layerService.getLayerByCountNum(countNum);
        if (layer != null) {
            return new ResponseEntity<>(layer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

