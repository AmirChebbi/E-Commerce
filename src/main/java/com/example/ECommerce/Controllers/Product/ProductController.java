package com.example.ECommerce.Controllers.Product;
import com.example.ECommerce.Services.Product.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/articles")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{articleId}")
    public ResponseEntity<Object> fetchArticleById(@PathVariable("articleId") final long articleId)
    {
        return productService.fetchProductById(articleId);
    }
    @PutMapping("/{articleId}")
    public  ResponseEntity<Object> updateArticleById(
            @PathVariable("articleId") final long articleId,
            @RequestParam(value = "articleJson", required = true) final String articleJson
    ) throws IOException {
        return productService.updateProductById(articleId , articleJson);
    }
    @PutMapping("/{articleId}/images")
    public ResponseEntity<Object> addImageToArticle(@PathVariable("articleId") final long articleId , @RequestParam(value = "image" , required = true) final MultipartFile multipartFile) throws IOException {
        return productService.addImageToProduct(articleId, multipartFile);
    }
    @DeleteMapping("/{articleId}/images/{imageId}")
    public ResponseEntity<Object> removeImageFromArticle(@PathVariable("articleId") final long articleId , @PathVariable("imageId") final long imageId) throws IOException {
        return productService.removeImageFromProduct(articleId, imageId);
    }
    @DeleteMapping("/{articleId}")
    public ResponseEntity<Object> deleteArticleById(@PathVariable("articleId") final long articleId) throws IOException {
        return productService.deleteProductById(articleId);
    }
    @GetMapping()
    public  ResponseEntity<Object> fetchAllArticles(@RequestParam("pageNumber") final long pageNumber)
    {
        return productService.fetchAllArticle(pageNumber);
    }
    @GetMapping("/{articleId}/images/{fileIndex}")
    public ResponseEntity<byte[]>  fetchImageFromArticle(@PathVariable("articleId") final long articleId ,@PathVariable("fileIndex") final int fileIndex) throws IOException {
        return productService.fetchImageFromProduct(articleId , fileIndex);
    }

}
