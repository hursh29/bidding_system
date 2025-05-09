package com.intuit.bidding_system.repository;

import com.intuit.bidding_system.entity.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {
    List<Bid> findAllByProductSlotId(Long productSlotId);

    Bid findFirstByProductSlotIdOrderByBidAmountDesc(Long productSlotId);
}
