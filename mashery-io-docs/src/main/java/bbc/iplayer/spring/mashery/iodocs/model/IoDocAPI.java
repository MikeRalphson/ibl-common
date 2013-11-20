package bbc.iplayer.spring.mashery.iodocs.model;

public class IoDocAPI {

    private String name;

    private String version;

    private String title;

    private String description;

    private String basePath;

    private String protocol;

    private Auth auth;

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getBasePath() {
        return basePath;
    }

    public String getProtocol() {
        return protocol;
    }

    public Auth getAuth() {
        return auth;
    }

    public String getEnvBasePath() {
        return envBasePath;
    }

    private final String envBasePath;

    public IoDocAPI(Auth auth, String protocol, String basePath, String title, String description,
                    String version, String name, String envBasePath) {
        this.auth = auth;
        this.protocol = protocol;
        this.basePath = basePath;
        this.description = description;
        this.title = title;
        this.version = version;
        this.name = name;
        this.envBasePath = envBasePath;
    }

}
