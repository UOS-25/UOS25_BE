package com.uos25.uos25.products.service;

import com.uos25.uos25.common.error.products.DuplicateProductException;
import com.uos25.uos25.common.error.products.ProductNotFoundException;
import com.uos25.uos25.products.dto.ProductsDTO;
import com.uos25.uos25.products.entity.Products;
import com.uos25.uos25.products.repository.ProductsRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductsService {
    private final ProductsRepository productsRepository;

    @Transactional // 새로운 상품 등록
    public void saveProducts(ProductsDTO productsDTO) {
        Products products = Products.toSaveProducts(productsDTO);

        //상품 명이나 코드 존재하면 예외 발생시킴.
        if (productsRepository.existsByProductCode(productsDTO.getProductCode())
                || productsRepository.existsByProductName(productsDTO.getProductName())) {
            throw new DuplicateProductException();
        }

        //중복 없을 경우 상품 등록 완료.
        productsRepository.save(products);
    }

    @Transactional // 상품 가격 수정
    public void updateProductPrice(ProductsDTO productsDTO) {
        // 상품 코드로 상품을 조회합니다.
        Optional<Products> optionalProduct = productsRepository.findByProductCode(productsDTO.getProductCode());

        // 상품이 존재하지 않으면 예외를 발생시킵니다.
        if (!optionalProduct.isPresent()) {
            throw new ProductNotFoundException();
        }

        Products product = optionalProduct.get();

        // 새로운 가격을 설정합니다.
        product.setSalePrice(productsDTO.getSalePrice());

        // 상품을 저장하여 가격을 업데이트합니다.
        productsRepository.save(product);
    }


    @Transactional // 상품 삭제
    public void deleteProduct(String productCode) {
        // 상품 코드로 상품을 찾아옴
        Optional<Products> existingProduct = productsRepository.findByProductCode(productCode);

        // 상품이 존재하지 않으면 예외 발생
        if (existingProduct.isEmpty()) {
            throw new ProductNotFoundException();
        }

        // 상품이 존재하면 삭제
        productsRepository.delete(existingProduct.get());
    }

    @Transactional // 상품 코드로 검색
    public ProductsDTO findByProductCode(String productCode) {
        // 상품 코드로 상품을 찾아옴
        Optional<Products> existingProduct = productsRepository.findByProductCode(productCode);

        // 상품이 존재하지 않으면 예외 발생
        if (existingProduct.isEmpty()) {
            throw new ProductNotFoundException();
        }

        // 상품이 존재하면 제공
        return ProductsDTO.toProductsDTO(existingProduct.get());
    }

    @Transactional // 상품 코드로 검색
    public ProductsDTO findByProductName(String productName) {
        // 상품 코드로 상품을 찾아옴
        Optional<Products> existingProduct = productsRepository.findByProductName(productName);

        // 상품이 존재하지 않으면 예외 발생
        if (existingProduct.isEmpty()) {
            throw new ProductNotFoundException();
        }

        // 상품이 존재하면 제공
        return ProductsDTO.toProductsDTO(existingProduct.get());
    }

    @Transactional // 상품 코드로 검색
    public Products getProductByName(String productName) {
        // 상품 코드로 상품을 찾아옴
        return productsRepository.findByProductName(productName).orElseThrow(
                ProductNotFoundException::new
        );
    }
}
