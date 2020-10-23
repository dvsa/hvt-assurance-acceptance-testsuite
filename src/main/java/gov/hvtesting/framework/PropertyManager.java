package gov.hvtesting.framework;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyManager {
    private static final Object lock = new Object();
    private static final String LOCAL_PROPERTIES_FILE_PATH = "src/test/resources/test.properties";
    private static final String REMOTE_PROPERTIES_FILE_PATH = "src/test/resources/remote.properties";
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

    public static PropertyManager getInstance(Boolean isLocal) {
        String propertiesFilePath = isLocal ? LOCAL_PROPERTIES_FILE_PATH : REMOTE_PROPERTIES_FILE_PATH;
        if (instance == null) {
            synchronized (lock) {
                instance = new PropertyManager();
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
        isLocal = Boolean.getBoolean(prop.getProperty("test.isLocal"));
        url = prop.getProperty("test.baseUrl");
        secret = prop.getProperty("test.secret");
        updateAvailabilityUrl = prop.getProperty("test.updateAvailabilityUrl");
        jenkinsUrl = prop.getProperty("test.jenkinsUrl");
        sendReminderEmailJobName = prop.getProperty("test.SendReminderEmailJobName");
        searchNearestAtfUrl = prop.getProperty("test.searchNearestAtfUrl");
        readApiHost = prop.getProperty("test.readApiHost");
        readApiPort = prop.getProperty("test.readApiPort");
        atfId = prop.getProperty("test.atfId");
    }

    public String getURL() {
        return url;
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
}
