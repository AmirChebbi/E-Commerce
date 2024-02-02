package com.example.ECommerce.Services.Product;


import com.example.ECommerce.DAOs.Product.Product;
import com.example.ECommerce.DTOs.Product.ProductDTO;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ProductService {

    public ResponseEntity<Object> fetchProductById(final long productId);
    public ResponseEntity<Object> updateProductById(final long productId, @NotNull ProductDTO productDTO) throws IOException;
    public ResponseEntity<Object> deleteProductById(final long productId) throws IOException;
    public ResponseEntity<Object> addImageToProduct(final long productId , @NotNull final MultipartFile image) throws IOException;
    public ResponseEntity<Object> removeImageFromProduct(final long productId , final long imageId) throws IOException;
    public ResponseEntity<byte[]>  fetchImageFromProduct(final long productId, final int fileIndex) throws IOException;
    public ResponseEntity<Object> fetchAllArticle(final long pageNumber);
    public void deleteAllArticles (final List<Product> products);
    public ProductDTO mapToDTOItem(final Product product);
    public List<ProductDTO> mapToDTOList(List<Product> products);
    public Product getProductById(final long productId);

    Product findByTitleAndReference(String title, String reference);
}
