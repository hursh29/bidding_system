package com.intuit.bidding_system.tasks;

import com.intuit.bidding_system.entity.AuctionStatus;
import com.intuit.bidding_system.entity.Product;
import com.intuit.bidding_system.entity.ProductSlot;
import com.intuit.bidding_system.repository.ProductSlotRepository;
import com.intuit.bidding_system.service.BiddingService;
import com.intuit.bidding_system.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Callable;

@Component
public class SlotStartTask implements Task<ProductSlot> {

    @Autowired
    private ProductSlotRepository productSlotRepository;

    @Autowired
    private BiddingService biddingService;

    @Override
    public List<ProductSlot> invoke(final Long slotId) {
        final var persistedProductSlots = productSlotRepository.findAllBySlotId(slotId);

        if(persistedProductSlots.isEmpty()) {
            biddingService.cancelBiddingWindow(slotId);
            return List.of();
        }

        biddingService.startBiddingWindow(slotId);
        return persistedProductSlots;
    }
}
