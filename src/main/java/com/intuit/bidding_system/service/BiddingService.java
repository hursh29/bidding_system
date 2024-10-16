package com.intuit.bidding_system.service;

import com.intuit.bidding_system.entity.Bid;
import com.intuit.bidding_system.entity.BiddingSlot;
import com.intuit.bidding_system.entity.Product;
import com.intuit.bidding_system.entity.ProductSlot;
import com.intuit.bidding_system.entity.SlotStatus;
import com.intuit.bidding_system.exceptions.InvalidBidException;
import com.intuit.bidding_system.model.request.BiddingSlotRegistrationRequest;
import com.intuit.bidding_system.repository.BidRepository;
import com.intuit.bidding_system.repository.BiddingSlotRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.intuit.bidding_system.entity.SlotStatus.ACTIVE;
import static com.intuit.bidding_system.entity.SlotStatus.SCHEDULED;

@Data
class BidClaimRequest {
    private Long productSlotId;
    private Double bidAmount;
    private Long userId;
}

@Service
public class BiddingService {

    @Autowired
    private ProductService productService;

    @Autowired
    private BiddingSlotRepository biddingSlotRepository;

    @Autowired
    private BidRepository bidRepository;

    public boolean validateBiddingSlot(BiddingSlotRegistrationRequest req) {
        // avoid overlap check if such request already exists
        return true;
    }

    public boolean scheduleSlotWindow(BiddingSlotRegistrationRequest req) {
        scheduleBiddingWindow(req);
        scheduleBookingSlot();

        return false;
    }

    private void scheduleBiddingWindow(BiddingSlotRegistrationRequest request) {
        final LocalDateTime currentTime = LocalDateTime.now();
        final BiddingSlot newSlot = BiddingSlot.builder()
//            .buildWithSlotId(randomLong)
            .buildWithStartTime(request.getStartTime())
            .buildWithEndTime(request.getEndTime())
            .buildWithStatus(SCHEDULED)
            .buildWithCreatedAt(currentTime)
            .buildWithUpdatedAt(currentTime)
            .build();
        biddingSlotRepository.save(newSlot);
    }

    private void scheduleBookingSlot() {

    }

    public BiddingSlot closeOutSlotWindow(final Long slotId) {
        final BiddingSlot savedBiddingSlot = biddingSlotRepository.findById(slotId)
            .orElseThrow(IllegalStateException::new);
        final LocalDateTime currentTime = LocalDateTime.now();

        savedBiddingSlot.setStatus(SlotStatus.COMPLETED);
        savedBiddingSlot.setUpdatedAt(currentTime);

        return biddingSlotRepository.save(savedBiddingSlot);
    }

    public BiddingSlot cancelBiddingWindow(final Long slotId) {
        final BiddingSlot savedBiddingSlot = biddingSlotRepository.findById(slotId)
            .orElseThrow(IllegalStateException::new);
        final LocalDateTime currentTime = LocalDateTime.now();

        savedBiddingSlot.setStatus(SlotStatus.CANCELLED);
        savedBiddingSlot.setUpdatedAt(currentTime);

        return biddingSlotRepository.save(savedBiddingSlot);
    }

    public BiddingSlot startBiddingWindow(final Long slotId) {
        final BiddingSlot savedBiddingSlot = biddingSlotRepository.findById(slotId)
            .orElseThrow(IllegalStateException::new);
        final LocalDateTime currentTime = LocalDateTime.now();

        savedBiddingSlot.setStatus(SlotStatus.ACTIVE);
        savedBiddingSlot.setUpdatedAt(currentTime);

        return biddingSlotRepository.save(savedBiddingSlot);
    }

    public List<Bid> getBidsByProductSlotId(final Long productSlotId) {
        return bidRepository.findAllByProductSlotId(productSlotId);
    }

    public Bid getCurrentHighestBidByProductSlotId(final Long productSlotId) {
        return bidRepository.findFirstByProductSlotIdOrderByBidAmountDesc(productSlotId);
    }

    public Bid claimBid(final BidClaimRequest bidClaimRequest) throws InvalidBidException {
        final Long productSlotId = bidClaimRequest.getProductSlotId();
        final Double productBidAmount = bidClaimRequest.getBidAmount();
        final Long biddingSlotId = productService.findProductSlotById(productSlotId)
            .map(ProductSlot::getSlotId)
            .orElseThrow(IllegalStateException::new);
        final var bid = biddingSlotRepository.findById(biddingSlotId)
            .orElseThrow(IllegalStateException::new);
        if (bid.getStatus() != ACTIVE) {
            throw new InvalidBidException("No active slots are present, bidding not possible");
        }
        final var lastHighestBidPrice =
            Optional
                .ofNullable(bidRepository.findFirstByProductSlotIdOrderByBidAmountDesc(productSlotId))
                .map(Bid::getBidAmount)
                .orElse(0.00);

        if (Double.compare(productBidAmount,lastHighestBidPrice) < 0) {
            throw new InvalidBidException("product price should be greater than previous bid price");
        }

        final var productBasePrice =
            productService.findProductByProductSlotId(productSlotId)
                .map(Product::getBasePrice)
                .orElseThrow(IllegalStateException::new);

        if (Double.compare(productBidAmount, productBasePrice) < 0) {
            throw new InvalidBidException("product price cannot be less than bid price");
        }

        final Bid persistBid = Bid.builder()
            .buildWithBidderId(bidClaimRequest.getUserId())
            .buildWithBidTime(LocalDateTime.now())
            .buildWithProductSlotId(bidClaimRequest.getProductSlotId())
            .buildWithBidAmount(bidClaimRequest.getBidAmount())
            .build();

        return bidRepository.save(persistBid);
    }
}
