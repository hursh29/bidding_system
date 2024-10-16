package com.intuit.bidding_system.service;

import com.intuit.bidding_system.entity.Bid;
import com.intuit.bidding_system.entity.BidWinner;
import com.intuit.bidding_system.repository.BidWinnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BidWinnerService {

    @Autowired
    private BidWinnerRepository bidWinnerRepository;

    public BidWinner markUserAsBidWinner(final Bid bid) {
        final Long bidderId = bid.getBidderId();

        // validate winner is user not admin and vendor
        final BidWinner bidWinner = BidWinner.builder()
            .buildWithBidId(bid.getBidId())
            .buildWithProductSlotId(bid.getProductSlotId())
            .buildWithWinnerId(bid.getBidderId())
            .buildWithSelectedAt(LocalDateTime.now())
            .build();

        return bidWinnerRepository.save(bidWinner);
    }


    // write more apis on this one
    public List<BidWinner> getWinnersFromSlotId(final Long slotId) {
//        bidWinnerRepository.find
        return List.of();
    }
}
