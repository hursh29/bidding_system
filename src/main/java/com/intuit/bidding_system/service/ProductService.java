package com.intuit.bidding_system.service;

import com.intuit.bidding_system.entity.AuctionStatus;
import com.intuit.bidding_system.entity.Category;
import com.intuit.bidding_system.entity.Product;
import com.intuit.bidding_system.entity.ProductSlot;
import com.intuit.bidding_system.exceptions.ProductNotFoundException;
import com.intuit.bidding_system.exceptions.ProductRegistrationFailureException;
import com.intuit.bidding_system.model.request.ProductPayload;
import com.intuit.bidding_system.model.response.RegistrationResponse;
import com.intuit.bidding_system.repository.BiddingSlotRepository;
import com.intuit.bidding_system.repository.ProductRepository;
import com.intuit.bidding_system.repository.ProductSlotRepository;
import jakarta.persistence.OptimisticLockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.intuit.bidding_system.entity.SlotStatus.SCHEDULED;

@Service
public class ProductService {

    @Autowired
    private ProductSlotRepository productSlotRepository;

    @Autowired
    private BiddingSlotRepository biddingSlotRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserService userService;

    private boolean validateSlotExistence(final Long slotId) {
        return biddingSlotRepository.findById(slotId)
            .map(slot -> SCHEDULED.equals(slot.getStatus()))
            .isPresent();
    }

    public RegistrationResponse registerProduct(ProductPayload productPayload) throws ProductRegistrationFailureException {
        final Long vendorId = productPayload.getVendorId();
        final Long slotId = productPayload.getSlotId();
        final Boolean isActive = productPayload.getIsActive();
        if (!isActive) {
            return RegistrationResponse.builder()
                .buildWithAcknowledged(false)
                .buildWithMessage("is active field needs to be true, when persisting the object for the first-time")
                .build();
        }
        if (!userService.validateVendorExistence(vendorId)) {
            throw new ProductRegistrationFailureException("Vendor Id doesn't exist in the system");
        }

        if (!this.validateSlotExistence(slotId)) {
            throw new ProductRegistrationFailureException("Slot Id doesn't exist in the System");
        }
        try {
            final var currentTime = LocalDateTime.now();
            final var productToBeSaved = Product.builder()
                .buildWithBasePrice(productPayload.getBasePrice())
                .buildWithVendorId(productPayload.getVendorId())
                .buildWithCategory(productPayload.getCategory())
                .buildWithName(productPayload.getName())
                .buildWithDescription(productPayload.getDescription())
                .buildWithIsActive(productPayload.getIsActive())
                .buildWithCreatedAt(currentTime)
                .buildWithUpdatedAt(currentTime)
                .build();
            final var productSlot = ProductSlot.builder()
                .buildWithUpdatedAt(currentTime)
                .buildWithSlotId(slotId)
                .buildWithUpdatedAt(currentTime)
                .buildWithStatus(AuctionStatus.OPEN)
                .build();

            productRepository.save(productToBeSaved);
            productSlotRepository.save(productSlot);

            return RegistrationResponse.builder()
                .buildWithAcknowledged(true)
                .build();
        } catch (OptimisticLockException ex) {
            return RegistrationResponse.builder()
                .buildWithAcknowledged(false)
                .buildWithMessage(ex.getMessage())
                .build();
        }
    }

    public Optional<Product> findProductByProductSlotId(final Long productSlotId) {
        return productSlotRepository.findById(productSlotId)
            .map(ProductSlot::getProductId)
            .flatMap(productRepository::findByProductId);
    }

    public Optional<ProductSlot> findProductSlotById(final Long productSlotId) {
        return productSlotRepository.findById(productSlotId);
    }

    public void markProductAsSold(final Long productId) throws ProductNotFoundException {
        final var product = productRepository.findByProductId(productId)
            .orElseThrow(() -> new ProductNotFoundException("Product is not available in the inventory"));
        product.setIsActive(false);
        product.setUpdatedAt(LocalDateTime.now());

        productRepository.save(product);
    }

    public void closeAllProductSlotsBySlotId(final Long slotId) {
        productSlotRepository.findAllBySlotId(slotId)
            .forEach(productSlot -> {
                final var expiredSlot = ProductSlot.builder()
                    .buildWithProductSlotId(productSlot.getProductSlotId())
                    .buildWithProductId(productSlot.getProductId())
                    .buildWithStatus(AuctionStatus.CLOSED)
                    .buildWithSlotId(productSlot.getSlotId())
                    .buildWithCreatedAt(productSlot.getCreatedAt())
                    .buildWithUpdatedAt(LocalDateTime.now())
                    .build();

                productSlotRepository.save(expiredSlot);
            });
    }

    public List<Product> getListOfActiveProduct() {
        return productRepository.findByIsActive(true);
    }

    public List<Product> getProductsByCategory(final Category category) {
        return productRepository.findByCategory(category);
    }

    public List<ProductSlot> getAllProductSlotsBySlotId(final Long slotId) {
        return productSlotRepository.findAllBySlotId(slotId);
    }
}
