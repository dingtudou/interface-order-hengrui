package com.cimc.order.common.utils;

import com.cimc.order.common.domain.ScheduleJob;
import com.cimc.order.common.domain.SysTask;
import org.springframework.beans.BeanUtils;

public class ScheduleJobUtils {
	public static ScheduleJob entityToData(SysTask sysTask) {
		ScheduleJob scheduleJob = new ScheduleJob();
		BeanUtils.copyProperties(sysTask,scheduleJob);
		return scheduleJob;
	}
}