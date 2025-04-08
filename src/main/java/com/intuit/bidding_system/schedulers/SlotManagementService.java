package com.intuit.bidding_system.schedulers;

import com.intuit.bidding_system.entity.BiddingSlot;
import com.intuit.bidding_system.entity.SlotStatus;
import com.intuit.bidding_system.repository.BiddingSlotRepository;
import com.intuit.bidding_system.tasks.SlotClosingTask;
import com.intuit.bidding_system.tasks.SlotStartTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.time.LocalDateTime;

import static com.intuit.bidding_system.entity.SlotStatus.ACTIVE;
import static com.intuit.bidding_system.entity.SlotStatus.COMPLETED;
import static com.intuit.bidding_system.entity.SlotStatus.SCHEDULED;

@Service
public class SlotManagementService {

    @Autowired
    private BiddingSlotRepository biddingSlotRepository;

    @Autowired
    private SlotStartTask slotStartTask;

    @Autowired
    private SlotClosingTask slotClosingTask;

    private final Long TIME_GAP = 60L;

    @Scheduled(cron = "0 * * * * *")
    public void startBiddingSlot() {
        final var scheduledSlot = biddingSlotRepository.findFirstByStatusOrderByStartTime(SCHEDULED);

        final var startTimeGapWithCurrentTime = scheduledSlot
            .map(BiddingSlot::getStartTime)
            .map(startTime -> Duration.between(startTime, LocalDateTime.now()))
            .map(Duration::getSeconds)
            .map(Math::abs)
            .orElse(TIME_GAP);
        System.out.println(startTimeGapWithCurrentTime);

        if (startTimeGapWithCurrentTime < TIME_GAP) {
            final var slotId = scheduledSlot.map(BiddingSlot::getSlotId).get();
            System.out.println("Found one slot to open, id = " + scheduledSlot.get().getSlotId());
            updateBiddingStatus(scheduledSlot.get(), ACTIVE);

            slotStartTask.invoke(slotId);
        }
    }

    @Scheduled(cron = "0 * * * * *")
    public void closeBiddingSlot() {
        final var scheduledSlot = biddingSlotRepository.findFirstByStatusOrderByEndTime(ACTIVE);
        final var endTimeGapWithCurrentTime = scheduledSlot
            .map(BiddingSlot::getEndTime)
            .map(endTime -> Duration.between(endTime, LocalDateTime.now()))
            .map(Duration::getSeconds)
            .map(Math::abs)
            .orElse(TIME_GAP);

        if (endTimeGapWithCurrentTime < TIME_GAP) {
            final var slotId = scheduledSlot.map(BiddingSlot::getSlotId).get();
            System.out.println("Found one slot to close, id = " + scheduledSlot.get().getSlotId());

            slotClosingTask.invoke(slotId);
            updateBiddingStatus(scheduledSlot.get(), COMPLETED);
        }
    }

    private BiddingSlot updateBiddingStatus(final BiddingSlot biddingSlot, final SlotStatus targetStatus) {
        final var toBeUpdated = BiddingSlot.builder()
            .buildWithUpdatedAt(LocalDateTime.now())
            .buildWithStatus(targetStatus)
            .buildWithCreatedAt(biddingSlot.getCreatedAt())
            .buildWithSlotId(biddingSlot.getSlotId())
            .buildWithEndTime(biddingSlot.getEndTime())
            .buildWithStartTime(biddingSlot.getStartTime())
            .build();

        return biddingSlotRepository.save(toBeUpdated);
    }
}
