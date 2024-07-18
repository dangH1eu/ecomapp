package com.project.ecomapp.serviceimpl;

import com.project.ecomapp.dto.ProductDTO;
import com.project.ecomapp.dto.ProductImageDTO;
import com.project.ecomapp.exception.DataNotFoundException;
import com.project.ecomapp.exception.InvalidParameterException;
import com.project.ecomapp.model.Category;
import com.project.ecomapp.model.Product;
import com.project.ecomapp.model.ProductImage;
import com.project.ecomapp.repository.CategoryRepository;
import com.project.ecomapp.repository.ProductImageRepository;
import com.project.ecomapp.repository.ProductRepository;
import com.project.ecomapp.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    private ProductImageRepository productImageRepository;


    public ProductServiceImpl(ProductRepository productRepository,
                              CategoryRepository categoryRepository,
                              ProductImageRepository productImageRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productImageRepository = productImageRepository;
    }

    @Override
    public Product createProduct(ProductDTO productDTO) throws DataNotFoundException {

        Category existingCategory = categoryRepository
                .findById(productDTO.getCategoryId())
                .orElseThrow(() ->
                        new DataNotFoundException(
                                "cannot find category with id: " + productDTO.getCategoryId()));
        Product newProduct = Product.builder()
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .thumbnail(productDTO.getThumbnail())
                .category(existingCategory)
                .build();
        return productRepository.save(newProduct);
    }

    @Override
    public Product getProductById(Long productId) throws Exception {
        return productRepository.findById(productId)
                .orElseThrow(() ->
                        new DataNotFoundException("cannot find product with id: " + productId));
    }

    @Override
    public Page<Product> getAllProducts(PageRequest pageRequest) {
        // get products list by page and limit
        return productRepository.findAll(pageRequest);

    }

    @Override
    public Product updateProduct(Long id, ProductDTO productDTO) throws Exception {
        Product existingProduct = getProductById(id);
        if (existingProduct != null) {
            Category existingCategory = categoryRepository
                    .findById(productDTO.getCategoryId())
                    .orElseThrow(() ->
                            new DataNotFoundException(
                                    "cannot find category with id: " + productDTO.getCategoryId()
                            ));
            existingProduct.setName(productDTO.getName());
            existingProduct.setCategory(existingCategory);
            existingProduct.setPrice(productDTO.getPrice());
            existingProduct.setDescription(productDTO.getDescription());
            existingProduct.setThumbnail(productDTO.getThumbnail());
            return productRepository.save(existingProduct);
        }
        return null;
    }

    @Override
    public void deleteProduct(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        optionalProduct.ifPresent(productRepository::delete);
    }
    @Override
    public boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }
    @Override
    public ProductImage createProductImage(
            Long productId,
            ProductImageDTO productImageDTO) throws Exception {
        Product existingProduct = productRepository
                .findById(productId)
                .orElseThrow(() ->
                        new DataNotFoundException(
                                "cannot found product with id: " + productImageDTO.getProductId()));
        ProductImage newProductImage = ProductImage.builder()
                .product(existingProduct)
                .imageUrl(productImageDTO.getImageUrl())
                .build();
        int size = productImageRepository.findByProductId(productId).size();
        if(size >= ProductImage.MAXIMUM_IMAGES_PER_PRODUCT) {
            throw new InvalidParameterException(
                    "number of images must be <= "
            + ProductImage.MAXIMUM_IMAGES_PER_PRODUCT);
        }
        return productImageRepository.save(newProductImage);


    }
}
