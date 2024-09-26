package filminkorea.fik.services;

import filminkorea.fik.dtos.LayerDto;
import filminkorea.fik.entities.Layer;
import filminkorea.fik.repositories.LayerRepository;
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
public class LayerService {

    @Value("${layer.upload.dir}")
    private String uploadDir;  // C:/layer 경로로 설정

    private final LayerRepository layerRepository;

    public LayerService(LayerRepository layerRepository) {
        this.layerRepository = layerRepository;
    }

    // 파일 저장
    public LayerDto saveLayerFile(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();

        // 파일 저장 경로 생성
        Path filePath = Paths.get(uploadDir, fileName);

        // 디렉토리가 없을 경우 생성
        Files.createDirectories(filePath.getParent());

        // 파일 저장
        Files.write(filePath, file.getBytes());

        // 엔티티 생성 및 저장
        Layer layer = new Layer();
        layer.setImgsrc("/layer/" + fileName); // 파일 URL 경로

        layerRepository.save(layer);

        // DTO 반환
        LayerDto layerDto = new LayerDto();
        layerDto.setImgsrc("/layer/" + fileName);
        return layerDto;
    }

    public List<LayerDto> getAllLayers() {
        List<Layer> layers = layerRepository.findAll();
        return layers.stream().map(layer -> {
            LayerDto dto = new LayerDto();
            dto.setCountNum(layer.getCountNum());
            dto.setTitle_nm(layer.getTitle_nm());
            dto.setPlace_name(layer.getPlace_name());
            dto.setImgsrc(layer.getImgsrc());
            return dto;
        }).collect(Collectors.toList());
    }

    // countNum으로 LayerDto 찾기
    public LayerDto getLayerByCountNum(int countNum) {
        Optional<Layer> layer = layerRepository.findById(countNum);
        return layer.map(l -> {
            LayerDto dto = new LayerDto();
            dto.setCountNum(l.getCountNum());
            dto.setTitle_nm(l.getTitle_nm());
            dto.setPlace_name(l.getPlace_name());
            dto.setImgsrc(l.getImgsrc());
            return dto;
        }).orElse(null);  // Layer가 존재하지 않을 경우 null 반환
    }

    // 파일 경로 가져오는 메소드 (파일 다운로드용)
    public Path getLayerFilePathByCountNum(int countNum) throws IOException {
        // countNum으로 Layer 정보를 찾기
        Optional<Layer> layerOptional = layerRepository.findById(countNum);
        if (layerOptional.isPresent()) {
            Layer layer = layerOptional.get();
            String imgSrc = layer.getImgsrc();  // DB에서 이미지 경로 가져오기

            Path filePath = Paths.get(imgSrc); // 경로에서 바로 가져오기
            return filePath.normalize();
        } else {
            throw new IOException("Layer not found for countNum: " + countNum);
        }
    }
}
