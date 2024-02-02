package com.example.ECommerce.Services.SubCategory;


import com.example.ECommerce.DAOs.File.FileData;
import com.example.ECommerce.DAOs.Option.Option;
import com.example.ECommerce.DAOs.Product.Product;
import com.example.ECommerce.DAOs.SubCategory.SubCategory;
import com.example.ECommerce.DTOs.Product.ProductDTO;
import com.example.ECommerce.DTOs.SubCategory.SubCategoryDTO;
import com.example.ECommerce.DTOs.SubCategory.SubCategoryDTOMapper;
import com.example.ECommerce.Exceptions.ResourceNotFoundException;
import com.example.ECommerce.Repositories.ProductRepository;
import com.example.ECommerce.Repositories.SubCategoryRepository;
import com.example.ECommerce.Services.File.FileService;
import com.example.ECommerce.Services.Product.ProductService;
import com.example.ECommerce.Utility.ResponseHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SubCategoryServiceImpl implements  SubCategoryService{

    private final SubCategoryRepository subCategoryRepository;
    private final SubCategoryDTOMapper subCategoryDTOMapper;
    private final ProductService productService;
    private final FileService fileService;

    public SubCategoryServiceImpl(SubCategoryRepository subCategoryRepository, SubCategoryDTOMapper subCategoryDTOMapper, ProductService productService, FileService fileService) {
        this.subCategoryRepository = subCategoryRepository;
        this.subCategoryDTOMapper = subCategoryDTOMapper;
        this.productService = productService;
        this.fileService = fileService;
    }

    @Override
    public ResponseEntity<Object> updateSubCategory(long subCategoryId, @NotNull SubCategory subCategory) {
        final SubCategory currentSubCategory = getSubCategoryById(subCategoryId);
        currentSubCategory.setTitle(subCategory.getTitle());
        subCategoryRepository.save(currentSubCategory);

        final String successResponse = String.format("The Sub category with ID : %d updated successfully", subCategoryId);
        return ResponseHandler.generateResponse(successResponse ,HttpStatus.OK);
    }

    @Transactional
    @Override
    public ResponseEntity<Object> deleteSubCategory(long subCategoryId) {
        final SubCategory currentSubCategory = getSubCategoryById(subCategoryId);
        final List<Product> products = currentSubCategory.getProducts();

        if(products.size() > 0)
        {
            productService.deleteAllArticles(products);
        }

        deleteSubCategoryById(currentSubCategory.getId());

        final String successResponse = String.format("The Sub Category with ID : %d deleted successfully.",subCategoryId);
        return ResponseHandler.generateResponse(successResponse,HttpStatus.OK);

    }

    @Override
    public ResponseEntity<Object> fetchSubCategoryById(long subCategoryId) {
        final SubCategory currentSubCategory = getSubCategoryById(subCategoryId);
        final SubCategoryDTO subCategory = subCategoryDTOMapper.apply(currentSubCategory);
        return ResponseHandler.generateResponse(subCategory , HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> fetchAllSubCategory() {
        final List<SubCategory> currentSubCategories = subCategoryRepository.fetchAllSubCategories();
        final List<SubCategoryDTO> subCategories = currentSubCategories.stream().map(subCategoryDTOMapper).toList();

        return ResponseHandler.generateResponse(subCategories,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> fetchProductsFromSubCategory(final long subCategoryId , final long pageNumber) {

        final int pageLength = 10;
        final int startIndex = (int) (pageLength * (pageNumber - 1));

        final SubCategory currentSubCategory = getSubCategoryById(subCategoryId);
        final List<Product> currentProducts = currentSubCategory.getProducts().stream().skip(startIndex).limit(10).toList();
        if(currentProducts.size() == 0 && pageNumber > 1)
        {
            return fetchProductsFromSubCategory(subCategoryId , 1);
        }
        final List<ProductDTO> articles = productService.mapToDTOList(currentProducts);

        return ResponseHandler.generateResponse(articles , HttpStatus.OK , articles.size() , currentSubCategory.getProducts().size());
    }

  /*  @Override
    public ResponseEntity<Object> addProductToSubCategoryById(long subCategoryId, @NotNull List<MultipartFile> multipartFiles, @NotNull String articleJson) throws IOException {
        final SubCategory currentSubCategory = getSubCategoryById(subCategoryId);
        final Product product = new ObjectMapper().readValue(articleJson , Product.class);
        currentSubCategory.getProducts().add(product);
        subCategoryRepository.save(currentSubCategory);
        int index = currentSubCategory.getProducts().indexOf(product);
        Product savedProduct = productRepository.findById(currentSubCategory.getProducts().get(index).getId()).orElseThrow(() -> new ResourceNotFoundException("Product doesn't exist !"));
        for (MultipartFile multipartFile : multipartFiles){
            productService.addImageToProduct(savedProduct.getId(), multipartFile);
        }
        final String successResponse = String.format("The Article with TITLE : %s added successfully",product.getTitle());
        return ResponseHandler.generateResponse(successResponse, HttpStatus.OK);
    }*/

    @Override
    public ResponseEntity<Object> addProductToSubCategoryById(long subCategoryId, @NotNull List<MultipartFile> multipartFiles, @NotNull String productJson) throws IOException {
        final SubCategory currentSubCategory = getSubCategoryById(subCategoryId);
        final Product product = new ObjectMapper().readValue(productJson , Product.class);

        for(Option option : product.getOptions())
        {
            option.setProduct(product);
        }
        currentSubCategory.getProducts().add(product);
        product.setSubCategory(currentSubCategory);
        subCategoryRepository.save(currentSubCategory);
        Product savedProduct = productService.findByTitleAndReference(product.getTitle(),product.getReference());
        final List<FileData> images  = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            FileData image = fileService.processUploadedFile(multipartFile);
            image.setProduct(savedProduct);
            images.add(image);
        }
        product.setFiles(images);

        final String successResponse = String.format("The Product with TITLE : %s added successfully",product.getTitle());
        return ResponseHandler.generateResponse(successResponse, HttpStatus.OK);
    }

    @Override
    public SubCategoryDTO mapToDTOItem(SubCategory subCategory) {
        return subCategoryDTOMapper.apply(subCategory);
    }

    @Override
    public List<SubCategoryDTO> mapToDTOList(List<SubCategory> subCategories) {
        return subCategories.stream().map(subCategoryDTOMapper).toList();
    }


    @Override
    public SubCategory getSubCategoryById(final long subCategoryId) {
        return subCategoryRepository.fetchSubCategoryById(subCategoryId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("The Sub Category with ID : %d could not be found in our system.", subCategoryId))
        );
    }

    @Transactional
    @Override
    public void deleteSubCategoryById(long subCategoryId) {
        final SubCategory subCategory = getSubCategoryById(subCategoryId);
        subCategoryRepository.deleteSubCategoryById(subCategoryId);
    }

    @Transactional
    @Override
    public void deleteSubCategoryAll(List<SubCategory> subCategories)
    {
        subCategoryRepository.deleteAllSubCategories(subCategories);
    }
}
