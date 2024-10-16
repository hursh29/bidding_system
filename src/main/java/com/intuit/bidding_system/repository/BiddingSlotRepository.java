package com.intuit.bidding_system.repository;

import com.intuit.bidding_system.entity.BiddingSlot;
import com.intuit.bidding_system.entity.SlotStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BiddingSlotRepository extends JpaRepository<BiddingSlot, Long> {

    Optional<BiddingSlot> findById(Long slotId);

    Optional<BiddingSlot> findFirstBySlotStatusOrderByStartTime(SlotStatus status);

    Optional<BiddingSlot> findFirstBySlotStatusOrderByEndTime(SlotStatus status);
}
