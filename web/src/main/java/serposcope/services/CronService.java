/*
 * Serposcope - SEO rank checker https://serposcope.serphacker.com/
 * 
 * Copyright (c) 2016 SERP Hacker
 * @author Pierre Nogues <support@serphacker.com>
 * @license https://opensource.org/licenses/MIT MIT License
 */

package serposcope.services;

import com.serphacker.serposcope.db.base.ConfigDB;
import com.serphacker.serposcope.models.base.Config;
import com.serphacker.serposcope.models.base.Run;
import com.serphacker.serposcope.task.TaskManager;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import javax.inject.Singleton;
import ninja.lifecycle.Dispose;
import ninja.lifecycle.Start;
import ninja.scheduler.Schedule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class CronService implements Runnable {
    
    private static final Logger LOG = LoggerFactory.getLogger(CronService.class);
    
    LocalTime previousCheck = null;
    ScheduledExecutorService executor;

    
    @Inject
    TaskManager manager;
    
    @Inject
    ConfigDB db;
    
    @Start(order = 90)
    public void startService() {
        LOG.info("startService");
        executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(this,0, 30, TimeUnit.SECONDS);
    }

    @Dispose(order = 90)
    public void stopService() {
       LOG.info("stopService");
       try{executor.shutdownNow();}catch(Exception ex){}
    }    
    
    @Override
    public void run() {
        LocalTime now = LocalTime.now();
        if(previousCheck != null && now.getMinute() == previousCheck.getMinute()){
            return;
        }
        
        previousCheck = now;
        
        Config config = db.getConfig();
        if(config.getCronTime() == null){
            return;
        }
        
        if(config.getCronTime().getHour() != now.getHour() || config.getCronTime().getMinute() != now.getMinute()){
            return;
        }
        
        
        if(manager.startGoogleTask(Run.Mode.CRON, LocalDateTime.now())){
            LOG.debug("starting google task via cron");
        } else {
            LOG.debug("failed to start google task via cron, this task is already running");
        }
    }


}
