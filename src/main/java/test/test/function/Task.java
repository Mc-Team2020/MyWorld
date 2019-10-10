package test.test.function;


import test.test.api.ProjectGuildAPI;

import java.util.TimerTask;

public class Task extends TimerTask {
    @Override
    public void run() {
        ProjectGuildAPI projectGuildAPI = new ProjectGuildAPI();
        projectGuildAPI.UpDataRockingSize();
        projectGuildAPI.UpDataOnLineTime();
    }
}
