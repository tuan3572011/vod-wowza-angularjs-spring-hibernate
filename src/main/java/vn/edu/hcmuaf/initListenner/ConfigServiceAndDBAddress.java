package vn.edu.hcmuaf.initListenner;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vn.edu.hcmuaf.controller.PhimLeController;

/**
 * Application Lifecycle Listener implementation class ConfigServiceAndDBAddress
 *
 */
public class ConfigServiceAndDBAddress implements ServletContextListener {
    public static String streamingServerAddress = "";
    public static String resfulServerAddress = "";
    public static String imageServerAddress = "";

    private Logger logger = LoggerFactory
            .getLogger(ConfigServiceAndDBAddress.class);

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        try {
            Properties properties = new Properties();
            System.out.println(properties);
            properties.load(PhimLeController.class
                    .getResourceAsStream("/serverAddressConfig.properties"));
            streamingServerAddress = properties
                    .getProperty("StreamingServerAddress");
            resfulServerAddress = properties.getProperty("ResfulServerAddress");
            imageServerAddress = properties.getProperty("ImageServerAddress");

            logger.info("IP ADDRESS OF ALL SERVER"+streamingServerAddress + resfulServerAddress
                    + imageServerAddress);

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
    }

}
