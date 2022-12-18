package com.cimc.order.common.service.impl;


import com.cimc.order.common.cache.SystemCache;
import com.cimc.order.common.domain.ScheduleJob;
import com.cimc.order.common.domain.SysTask;
import com.cimc.order.common.quartz.utils.QuartzManager;
import com.cimc.order.common.service.JobService;
import com.cimc.order.common.utils.ScheduleJobUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobServiceImpl implements JobService {

	@Autowired
	QuartzManager quartzManager;
	@Autowired
	SystemCache systemCache;
	@Override
	public void initSchedule()  {
		// 这里获取任务信息数据
		// 这里获取任务信息数据
		List<SysTask> jobList = SystemCache.sysTaskList;
		if (null!=jobList) {
			for (SysTask sysTask : jobList) {
				if ("1".equals(sysTask.getStatus())) {
					ScheduleJob job = ScheduleJobUtils.entityToData(sysTask);
					quartzManager.addJob(job);
				}
			}
		}
	}
}
