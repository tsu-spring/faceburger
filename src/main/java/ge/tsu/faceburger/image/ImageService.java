package ge.tsu.faceburger.image;

import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

@Service
public class ImageService {

    @Value("${faceburger.root-image-path}")
    private String rootImageFolderPath;

    private Path rootFolder;

    @PostConstruct
    public void init() throws IOException {
        rootFolder = Path.of(this.rootImageFolderPath)
                .resolve("image");
        Files.createDirectories(rootFolder);
    }

    @SneakyThrows
    public String saveImageFile(MultipartFile file) {
        // Construct relative path for image file
        Path imagePath = rootFolder.resolve(String.format("%s.%s",
                UUID.randomUUID(), FilenameUtils.getExtension(file.getOriginalFilename())));

        // Save image to file
        try (InputStream inputStream = file.getInputStream();
             OutputStream outputStream = Files.newOutputStream(imagePath)) {
            IOUtils.copy(inputStream, outputStream);
        }

        // Remove root folder prefix
        return Path.of("/").resolve(rootFolder.getParent().relativize(imagePath)).toString();
    }

    public List<String> saveImageFiles(List<MultipartFile> images) {
        return images.stream().map(this::saveImageFile).toList();
    }
}
