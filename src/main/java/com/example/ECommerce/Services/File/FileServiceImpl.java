package com.example.ECommerce.Services.File;


import com.example.ECommerce.DAOs.File.FileData;
import com.example.ECommerce.DAOs.Product.Product;
import com.example.ECommerce.Exceptions.ResourceNotFoundException;
import com.example.ECommerce.Repositories.FileDataRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
public class FileServiceImpl implements  FileService{

    private final FileDataRepository fileDataRepository;

    @Autowired
    public FileServiceImpl(FileDataRepository fileDataRepository)
    {
        this.fileDataRepository = fileDataRepository;
    }

    public FileData save(FileData fileData)
    {
        return fileDataRepository.save(fileData);
    }

    private final String  FILE_SYSTEM_PATH= Paths.get("").toAbsolutePath().resolve("src").resolve("main").resolve("resources").resolve("FileSystem").toString() + "/";
    @Transactional
    public void deleteFileById(final long fileId)
    {
        FileData fileToDelete = getFileDataById(fileId);
        fileDataRepository.deleteFileDataById(fileId);
    }

    @Override
    public FileData processUploadedFile(@NotNull final MultipartFile file) throws IOException {
        var originalFileName = file.getOriginalFilename();
        var fileName = originalFileName.substring(0, originalFileName.indexOf('.'));
        var extension = originalFileName.substring(originalFileName.indexOf('.'));
        var filePath = FILE_SYSTEM_PATH + fileName + UUID.randomUUID() + extension;
        FileData fileData = new FileData(
                file.getOriginalFilename(),
                file.getContentType(),
                filePath);
        fileDataRepository.save(fileData);
        file.transferTo(new File(filePath));
        return fileData;
    }

    @Override
    public ResponseEntity<byte[]> downloadFile(@NotNull final  FileData fileData) throws IOException {
        final String filePath = fileData.getFilePath();
        byte[] file = Files.readAllBytes(new File(filePath).toPath());
        HttpHeaders headers = new HttpHeaders();
        String contentType = determineContentType(filePath);
        headers.setContentDispositionFormData("attachment", fileData.getFilePath());
        headers.setContentType(MediaType.parseMediaType(contentType));

        return new ResponseEntity<>(file, headers, HttpStatus.OK);
    }



    @Override
    @Transactional
    public boolean deleteFileFromFileSystem(@NotNull final FileData fileData) throws IOException {
        File fileToDelete = new File(fileData.getFilePath());
        boolean delete = fileToDelete.delete();
        fileDataRepository.deleteFileDataById(fileData.getId());
        return delete;
    }

    @Override
    @Transactional
    public void deleteAllFiles(@NotNull final List<FileData> files) throws IOException {
        for(var file : files)
        {
            File fileToDelete = new File(file.getFilePath());
            if(!fileToDelete.delete())
            {
                throw new IOException(String.format("Failed to delete file with file path : %s",file.getFilePath()));
            }
        }
        fileDataRepository.deleteAllFiles(files);
    }


    public String determineContentType(@NotNull String filePath) {

        String extension = filePath.substring(filePath.lastIndexOf(".") + 1).toLowerCase();

        HashMap<String, String> extensionToContentTypeMap = new HashMap<>();
        extensionToContentTypeMap.put("png", "image/png");
        extensionToContentTypeMap.put("jpg", "image/jpeg");
        extensionToContentTypeMap.put("jpeg", "image/jpeg");
        extensionToContentTypeMap.put("ico", "image/vnd.microsoft.icon");
        return extensionToContentTypeMap.getOrDefault(extension, "application/octet-stream");
    }
    public FileData getFileDataById(long fileDataId)
    {
        return fileDataRepository.fetchFileDataById(fileDataId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("The file with ID : %s could not be found.", fileDataId)));
    }
}
