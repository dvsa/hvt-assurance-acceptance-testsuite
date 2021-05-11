package gov.hvtesting.framework;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyManager {
    private static final Object lock = new Object();
    private static PropertyManager instance;
    private static Boolean isLocal;
    private static String url;
    private static String secret;
    private static String updateAvailabilityUrl;
    private static String jenkinsUrl;
    private static String sendReminderEmailJobName;
    private static String searchNearestAtfUrl;
    private static String readApiPort;
    private static String readApiHost;
    private static String atfId;
    private static String tokenGeneratorHost;
    private static String tokenGeneratorPort;

    public static PropertyManager getInstance() {
        if (instance == null) {
            synchronized (lock) {
                instance = new PropertyManager();
                String env = instance.getEnvironmentFile();
                String propertiesFilePath = String.format("src/test/resources/%s.properties", env);
                instance.loadData(propertiesFilePath);
            }
        }
        return instance;
    }

    private void loadData(String defaultPropertiesFilePath) {
        Properties prop = new Properties();
        String path = defaultPropertiesFilePath;
        try {
            prop.load(new FileInputStream(new File(path).getPath()));
        } catch (IOException e) {
            System.out.println("Configuration properties file cannot be found in the given path: " + path);
        }
        url = prop.getProperty("test.baseUrl");
        secret = prop.getProperty("test.secret");
        updateAvailabilityUrl = prop.getProperty("test.updateAvailabilityUrl");
        jenkinsUrl = prop.getProperty("test.jenkinsUrl");
        sendReminderEmailJobName = prop.getProperty("test.SendReminderEmailJobName");
        searchNearestAtfUrl = prop.getProperty("test.searchNearestAtfUrl");
        readApiHost = prop.getProperty("test.readApiHost");
        readApiPort = prop.getProperty("test.readApiPort");
        atfId = prop.getProperty("test.atfId");
        tokenGeneratorHost = prop.getProperty("test.tokenGeneratorHost");
        tokenGeneratorPort = prop.getProperty("test.tokenGeneratorPort");
    }

    public String getURL() {
        return url;
    }

    private String getEnvironmentFile() {
        String propValue = "test";
        if (System.getProperty("env") != null) {
            propValue = System.getProperty("env");
        }
        return propValue;
    }

    public Boolean isLocal() {
        return isLocal;
    }

    public String getSecret() {
        return secret;
    }

    public String getUpdateAvailabilityUrl() {
        return updateAvailabilityUrl;
    }

    public String getJenkinsUrl() {
        return jenkinsUrl;
    }

    public String getSendReminderEmailJobName() {
        return sendReminderEmailJobName;
    }

    public String getSearchNearestAtfUrl() {
        return searchNearestAtfUrl;
    }

    public String getReadApiPort() {
        return readApiPort;
    }

    public String getReadApiHost() {
        return readApiHost;
    }

    public String getAtfId() {
        return atfId;
    }

    public void setAtfId(String id) {
        atfId = id;
    }

    public String getTokenGeneratorHost() {
        return tokenGeneratorHost;
    }

    public String getTokenGeneratorPort() {
        return tokenGeneratorPort;
    }
}
