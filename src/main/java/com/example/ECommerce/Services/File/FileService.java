package com.example.ECommerce.Services.File;


import com.example.ECommerce.DAOs.File.FileData;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {

    public void deleteFileById(final long fileId);
    public FileData processUploadedFile(@NotNull final MultipartFile file) throws IOException;
    public void deleteAllFiles(@NotNull final List<FileData> files) throws IOException;
    public String determineContentType(@NotNull String filePath);
    public FileData getFileDataById(long fileDataId);
    public ResponseEntity<byte[]> downloadFile(@NotNull final  FileData fileData) throws IOException ;
    public boolean deleteFileFromFileSystem(@NotNull final FileData fileData) throws IOException ;

}
