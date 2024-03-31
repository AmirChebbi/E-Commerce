package com.example.ECommerce.Services.Product;

import com.example.ECommerce.DAOs.Category.Category;
import com.example.ECommerce.DAOs.File.FileData;
import com.example.ECommerce.DAOs.Product.Product;
import com.example.ECommerce.DAOs.SubCategory.SubCategory;
import com.example.ECommerce.DTOs.Product.ProductDTO;
import com.example.ECommerce.DTOs.Product.ProductDTOMapper;
import com.example.ECommerce.Exceptions.ResourceNotFoundException;
import com.example.ECommerce.Repositories.ProductRepository;
import com.example.ECommerce.Services.File.FileService;
import com.example.ECommerce.Services.Options.OptionService;
import com.example.ECommerce.Utility.ResponseHandler;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static java.lang.String.format;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductDTOMapper productDTOMapper;
    private final ProductRepository productRepository;
    private final OptionService optionsService;
    private final FileService fileService;

    public ProductServiceImpl(ProductDTOMapper productDTOMapper, ProductRepository productRepository, OptionService optionsService, FileService fileService) {
        this.productDTOMapper = productDTOMapper;
        this.productRepository = productRepository;
        this.optionsService = optionsService;
        this.fileService = fileService;
    }

    @Override
    public ResponseEntity<Object> fetchProductById(final long productId) {
        final Product currentProduct = getProductById(productId);
        final ProductDTO productDTO = productDTOMapper.apply(currentProduct);
        return ResponseHandler.generateResponse(productDTO , HttpStatus.OK);
    }
    @Override
    public ResponseEntity<Object> updateProductById(final long productId, @NotNull ProductDTO productDTO) throws IOException {
        Product product = getProductById(productId);
        if (productDTO.title() !=null) product.setTitle(productDTO.title());
        if (productDTO.price() != 0) product.setPrice(productDTO.price());
        if (productDTO.reference()!=null) product.setReference(product.getReference());
        if (productDTO.layoutDescription()!=null) product.setLayoutDescription(productDTO.layoutDescription());
        productRepository.save(product);
        String successMessage = String.format("Product with Id &d was updated successfully", productId);
        return ResponseHandler.generateResponse(successMessage,HttpStatus.OK);
    }

    @Override
    public boolean deleteProductById(Product product) throws IOException {
        productRepository.delete(product);
        for (FileData fileData : product.getFiles()){
            fileService.deleteFileFromFileSystem(fileData);
        }
        return true;
    }

    @Override
    public ResponseEntity<Object> addImageToProduct(long productId, @NotNull MultipartFile image) throws IOException {
        final Product existingProduct =  getProductById(productId);
        final FileData newImage = fileService.processUploadedFile(image);
        List<FileData> files = existingProduct.getFiles();
        files.add(newImage);
        existingProduct.setFiles(files);
        productRepository.save(existingProduct);
        final String successResponse ="The image is added successfully.";
        return ResponseHandler.generateResponse(successResponse , HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> removeImageFromProduct(long productId, long imageId) throws IOException {
        final Product existingProduct= getProductById(productId);
        final FileData existingImage = fileService.getFileDataById(imageId);

        if(!existingProduct.getFiles().contains(existingImage))
        {
            throw new IllegalStateException(format("The Image with ID : %d  does not belong to this product", imageId));
        }

        existingProduct.getFiles().remove(existingImage);
        productRepository.save(existingProduct);
        boolean delete = fileService.deleteFileFromFileSystem(existingImage);
        final String successResponse = format("The image with ID : %d deleted successfully, file deleted for FS ? : %s",imageId ,delete);
        return ResponseHandler.generateResponse(successResponse , HttpStatus.OK);
    }
    @Override
    public  ResponseEntity<byte[]> fetchImageFromProduct(final long productId,final int fileIndex) throws IOException {

        final Product product = getProductById(productId);
        if(fileIndex >= product.getFiles().size())
        {
            throw new IllegalStateException("The file index is out of range.");
        }
        final FileData fileData = product.getFiles().get(fileIndex);
        return fileService.downloadFile(fileData);
    }

    @Override
    public ResponseEntity<Object> fetchAllArticle(final long pageNumber) {
        final Pageable pageable = PageRequest.of((int) pageNumber - 1, 10);

        //final List<ProductDTO> productDTOS = productRepository.fetchAllProducts(pageable).stream().map(productDTOMapper).toList();
        final List<ProductDTO> productDTOS = productRepository.findAll().stream().map(productDTOMapper).toList();
        if(productDTOS.isEmpty() && pageNumber > 1)
        {
            return fetchAllArticle(1);
        }
        final long total  = productRepository.getTotalProductCount();
        return ResponseHandler.generateResponse(productDTOS , HttpStatus.OK , productDTOS.size() , total);
    }

    @Override
    public void deleteAllArticles(List<Product> products) {
        productRepository.deleteAllProducts(products);
    }

    @Override
    public ProductDTO mapToDTOItem( Product product) {
        return productDTOMapper.apply(product);
    }

    @Override
    public List<ProductDTO> mapToDTOList(List<Product> products)
    {
        return products.stream().map(productDTOMapper).toList();
    }

    @Override
    public Product getProductById(final long productId)
    {
        return productRepository.findById(productId).orElseThrow(
                () -> new ResourceNotFoundException(format("The Product with ID : %d could not be found in our system", productId))
        );
    }

    @Override
    public Product findByTitleAndReference(String title, String reference) {
        return productRepository.findByTitleAndReference(title,reference).orElseThrow(() -> new ResourceNotFoundException("Product doesn't exist !"));
    }
}
