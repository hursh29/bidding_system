package com.intuit.bidding_system.tasks;

import java.util.List;

public interface Task<T> {
    List<T> invoke(Long slotId);
}
