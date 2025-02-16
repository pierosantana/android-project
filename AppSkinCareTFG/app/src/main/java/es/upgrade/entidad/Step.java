package es.upgrade.entidad;

import java.io.Serializable;

public class Step implements Serializable {
    private String title;
    private String description;
    private String duration;
    private String proTip;

    public Step(String title, String description, String duration, String proTip) {
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.proTip = proTip;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDuration() {
        return duration;
    }

    public String getProTip() {
        return proTip;
    }
}