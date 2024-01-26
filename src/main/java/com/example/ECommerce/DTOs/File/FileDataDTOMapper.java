package com.example.ECommerce.DTOs.File;

import com.example.ECommerce.DAOs.File.FileData;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class FileDataDTOMapper implements Function<FileData, FileDataDTO> {
    @Override
    public FileDataDTO apply(FileData fileData) {
        return new FileDataDTO(
                fileData.getId(),
                fileData.getName(),
                fileData.getType(),
                fileData.getFilePath()
        );
    }
}
