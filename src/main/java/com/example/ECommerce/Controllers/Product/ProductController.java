package com.example.ECommerce.Controllers.Product;
import com.example.ECommerce.DTOs.Product.ProductDTO;
import com.example.ECommerce.Services.Product.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Object> fetchProductById(@PathVariable("productId") final long productId)
    {
        return productService.fetchProductById(productId);
    }
    @PutMapping("/{productId}")
    public  ResponseEntity<Object> updateProductById(
            @PathVariable("productId") final long productId,
            @RequestBody(required = true) final ProductDTO productDTO
            ) throws IOException {
        return productService.updateProductById(productId , productDTO);
    }
    @PutMapping("/{productId}/images")
    public ResponseEntity<Object> addImageToProduct(@PathVariable("productId") final long productId , @RequestParam(value = "image" , required = true) final MultipartFile multipartFile) throws IOException {
        return productService.addImageToProduct(productId, multipartFile);
    }
    @DeleteMapping("/{productId}/images/{imageId}")
    public ResponseEntity<Object> removeImageFromProduct(@PathVariable("productId") final long productId , @PathVariable("imageId") final long imageId) throws IOException {
        return productService.removeImageFromProduct(productId, imageId);
    }
    @DeleteMapping("/{productId}")
    public ResponseEntity<Object> deleteProductById(@PathVariable("productId") final long productId) throws IOException {
        return productService.deleteProductById(productId);
    }
    @GetMapping("/getAll")
    public  ResponseEntity<Object> fetchAllProducts(@RequestParam("pageNumber") final long pageNumber)
    {
        return productService.fetchAllArticle(pageNumber);
    }
    @GetMapping("/{productId}/images/{fileIndex}")
    public ResponseEntity<byte[]>  fetchImageFromProduct(@PathVariable("productId") final long productId , @PathVariable("fileIndex") final int fileIndex, @PathVariable String articleId) throws IOException {
        return productService.fetchImageFromProduct(productId , fileIndex);
    }

}
