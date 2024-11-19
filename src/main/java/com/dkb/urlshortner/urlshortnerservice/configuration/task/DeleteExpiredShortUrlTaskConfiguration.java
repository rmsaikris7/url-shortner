package com.dkb.urlshortner.urlshortnerservice.configuration.task;

import com.dkb.urlshortner.urlshortnerservice.business.service.ShortUrlService;
import com.github.kagkarlsson.scheduler.task.helper.RecurringTask;
import com.github.kagkarlsson.scheduler.task.helper.Tasks;
import com.github.kagkarlsson.scheduler.task.schedule.Schedules;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class DeleteExpiredShortUrlTaskConfiguration {

    Logger logger = LoggerFactory.getLogger(DeleteExpiredShortUrlTaskConfiguration.class);

    private static final String TASK_NAME = "delete_expired_keys_task";

    @Bean
    public RecurringTask<Void> configureTask(@Lazy ShortUrlService shortUrlService) {
        final var schedule = Schedules.cron("0 23 * * * *");
        return Tasks.recurring(TASK_NAME, schedule)
                .doNotScheduleOnStartup()
                .execute(((taskInstance, executionContext) -> {
                    logger.debug("STARTING EXPIRED SHORTURL DELETION TASK ");
                    shortUrlService.forEachExpired(shortUrl -> shortUrlService.deleteShortUrlByKey(shortUrl.getKey()));
                }));
    }
}
