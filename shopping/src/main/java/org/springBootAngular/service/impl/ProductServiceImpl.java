package org.springBootAngular.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springBootAngular.dto.ProductDTO;
import org.springBootAngular.dto.Response;
import org.springBootAngular.dto.SupplierDTO;
import org.springBootAngular.entity.Category;
import org.springBootAngular.entity.Product;
import org.springBootAngular.exception.NotFoundException;
import org.springBootAngular.repository.CategoryRepository;
import org.springBootAngular.repository.ProductRepository;
import org.springBootAngular.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;
    private final String IMAGE_DIRECTORY = System.getProperty("user.dir") + "/product-image/" ;

    @Override
    public Response saveProduct(ProductDTO productDTO, MultipartFile imgFile) {
        Category category = categoryRepository.findById(productDTO.getCategoryId()).orElseThrow(()->new NotFoundException("category not found"));
        Product productToSave = Product.builder()
                .name(productDTO.getName())
                .sku(productDTO.getSku())
                .category(category)
                .price(productDTO.getPrice())
                .stockQuality(productDTO.getStockQuality())
                .decription(productDTO.getDecription())
                .build();
        if(imgFile != null){
            String imagePath = saveImage(imgFile);
            productToSave.setImageUrl(imagePath);
        }

        Product product = modelMapper.map(productDTO, Product.class);
        productRepository.save(product);
        return Response.builder()
                .status(200)
                .message("Product added successfully")
                .build();
    }

    @Override
    public Response getAllProduct() {
        List<Product> products = productRepository.findAll();
        List<ProductDTO> productDTOs = modelMapper.map(products, new TypeToken<List<ProductDTO>>() {}.getType());
        return Response.builder()
                .status(200)
                .message("All products successfully")
                .products(productDTOs)
                .build();

    }

    @Override
    public Response getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(()->new NotFoundException("Product not found"));
        ProductDTO productdto = modelMapper.map(product, ProductDTO.class);
        return Response.builder()
                .status(200)
                 .message("Product successfully")
                .product(productdto)
                .build();
    }

    @Override
    public Response updateProduct(ProductDTO productDTO , MultipartFile imgFile) {
        Product product = productRepository.findById(productDTO.getProductId()).orElseThrow(()-> new NotFoundException("product is not found"));

        if(imgFile !=null && imgFile.isEmpty()){
            String imagePath= saveImage(imgFile);
            product.setImageUrl(imagePath);
        }
        if(productDTO.getCategoryId() != null && productDTO.getCategoryId()>0){
            Category category = categoryRepository.findById(productDTO.getCategoryId()).orElseThrow(()->new NotFoundException("Category does not found"));
            product.setCategory(category);
        }

        if(productDTO.getName()!= null && !productDTO.getName().isBlank()) {
            product.setName(productDTO.getName());
        }
        if(productDTO.getPrice() != null && !(productDTO.getPrice().compareTo(BigDecimal.ZERO) >=0)){
            product.setPrice(productDTO.getPrice());
        }
        if(productDTO.getDecription() != null && !productDTO.getDecription().isBlank()){
            product.setDecription(productDTO.getDecription());
        }
        if(productDTO.getSku() != null && !productDTO.getSku().isBlank()){
            product.setSku(productDTO.getSku());
        }
        if(productDTO.getStockQuality() != null && !(productDTO.getStockQuality() >=0)){
            product.setStockQuality(productDTO.getStockQuality());
        }

        productRepository.save(product);
        return Response.builder()
                .status(200)
                .message("Product successfully")
                .build();

    }

    @Override
    public Response deleteProduct(Long id) {
        Product product =  productRepository.findById(id).orElseThrow(()-> new NotFoundException("product is not found"));
        productRepository.delete(product);
        return Response.builder()
                .status(200)
                .message("Product successfully")
                .build();
    }
    private String saveImage(MultipartFile imageFile) {
        //validation image check
        if(!imageFile.getContentType().startsWith("image/")){
            throw new IllegalArgumentException("only image file are alloed");
        }
        File directory = new File(IMAGE_DIRECTORY);
        if(!directory.exists()){
            directory.mkdir();
            log.info("directory was created");
        }
        //generate uniqe filename for the image
        String uniqueFileName = UUID.randomUUID() + "-" + imageFile.getOriginalFilename();
        String imagePath = IMAGE_DIRECTORY + uniqueFileName;
        try{
            File destinationFile = new File(imagePath);
            imageFile.transferTo(destinationFile);

        }catch (Exception e){
            throw new IllegalArgumentException(" Error accurend while savinf image" + e.getMessage());
        }
        return imagePath;
    }
}
