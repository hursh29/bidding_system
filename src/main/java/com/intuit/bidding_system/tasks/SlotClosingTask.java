package com.intuit.bidding_system.tasks;

import com.intuit.bidding_system.entity.Bid;
import com.intuit.bidding_system.entity.BidWinner;
import com.intuit.bidding_system.entity.BiddingSlot;
import com.intuit.bidding_system.entity.SlotStatus;
import com.intuit.bidding_system.exceptions.ProductNotFoundException;
import com.intuit.bidding_system.repository.BiddingSlotRepository;
import com.intuit.bidding_system.repository.ProductSlotRepository;
import com.intuit.bidding_system.service.BidWinnerService;
import com.intuit.bidding_system.service.BiddingService;
import com.intuit.bidding_system.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

// appoints winner of every product
// closes the slot
@Component
public class SlotClosingTask implements Task<BidWinner> {

    @Autowired
    private BiddingService biddingService;

    @Autowired
    private BidWinnerService bidWinnerService;

    @Autowired
    private ProductService productService;

    // mark the product as gone in inventory and creates the winner.
    private List<BidWinner> winnerEvaluationTask(final Long slotId) {
        final var productsFromTheSlot = productService.getAllProductSlotsBySlotId(slotId);

        return productsFromTheSlot.stream()
            .map(productSlot -> {
                final Long productSlotId = productSlot.getProductSlotId();
                final Bid highestBid = biddingService.getCurrentHighestBidByProductSlotId(productSlotId);

                if (highestBid == null) {
                    return null;
                }

                try {
                    productService.markProductAsSold(productSlot.getProductId());
                } catch (ProductNotFoundException e) {
                    e.printStackTrace();
                }

                return bidWinnerService.markUserAsBidWinner(highestBid);
            })
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }

    @Override
    public List<BidWinner> invoke(Long slotId) {
        biddingService.closeOutSlotWindow(slotId);
        final List<BidWinner> bidWinners = winnerEvaluationTask(slotId);
        productService.closeAllProductSlotsBySlotId(slotId);

        return bidWinners;
    }
}
