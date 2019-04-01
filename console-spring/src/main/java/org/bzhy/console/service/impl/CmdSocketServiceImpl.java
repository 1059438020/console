package org.bzhy.console.service.impl;

import org.bzhy.console.comm.CmdSocketCommon;
import org.bzhy.console.service.TaskService;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @Author: 白振宇
 * @Date： 2019/4/1 17:22
 */
@Service
public class CmdSocketServiceImpl implements TaskService {
    @Override
    public void cmdSocket(String portStr) {
        int port = Integer.parseInt(portStr);
        try
        {
            Thread t = new CmdSocketCommon(port);
            t.run();
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
