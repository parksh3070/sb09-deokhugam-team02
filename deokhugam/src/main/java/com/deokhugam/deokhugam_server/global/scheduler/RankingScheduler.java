package com.deokhugam.deokhugam_server.global.scheduler;

import com.deokhugam.deokhugam_server.global.type.Period;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RankingScheduler {

  private final JobLauncher jobLauncher;

  @Qualifier("rankingJob")
  private final Job rankingJob;

  @Scheduled(cron = "0 0 3 * * *")
  public void runDailyRanking() {
    try {
      jobLauncher.run(rankingJob, new JobParametersBuilder()
          .addString("period", Period.DAILY.name())
          .addLong("requestedAt", System.currentTimeMillis())
          .toJobParameters());
    } catch (Exception e) {
      log.error("[Batch] Daily ranking job launch failed", e);
    }
  }
}
