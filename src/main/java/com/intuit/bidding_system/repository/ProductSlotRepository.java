package com.intuit.bidding_system.repository;

import com.intuit.bidding_system.entity.ProductSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductSlotRepository extends JpaRepository<ProductSlot, Long> {

    List<ProductSlot> findAllBySlotId(Long slotId);

    Optional<ProductSlot> findByProductSlotId(Long productSlotId);
}
