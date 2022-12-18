package com.cimc.order.common.listenner;

import com.cimc.order.common.quartz.utils.QuartzManager;
import com.cimc.order.common.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value = 1)
public class ScheduleJobInitListener implements CommandLineRunner {

	@Autowired
	JobService jobService;

	@Autowired
	QuartzManager quartzManager;

	@Override
	public void run(String... arg0) {
		jobService.initSchedule();
	}

}