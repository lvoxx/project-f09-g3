package com.cp2396f09_sem4_grp3.onmart_service.event;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cp2396f09_sem4_grp3.onmart_service.service.PromotionService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PromotionScheduler {

    private final PromotionService promotionService;

    // Run at the 30th minute of every hour
    @Scheduled(cron = "0 0,30 * * * ?")
    public void checkPromotions() {
        promotionService.updateExpiredPromotions();
    }
}
