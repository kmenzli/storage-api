package org.exoplatform.addons.storage.api;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;

import java.util.logging.Logger;

/**
 * Created by menzli on 05/06/14.
 */
public interface IScheduler {

    void shutdown() throws SchedulerException;
}
