package es.upgrade.entidad;

import java.io.Serializable;
import java.util.List;

public class Step implements Serializable {

    private int id;
    private String title;
    private String description;
    private String duration;
    private Product product;
    private List<String> proTips;

    public Step(int id, String title, String description, String duration, Product product, List<String> proTips) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.proTips = proTips;
    }

    public int getId() {
        return id;
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


    public List<String> getProTips() {
        return proTips;
    }

    /**
     * The toString method is overridden to provide a string representation of a Step object's
     * attributes.
     * 
     * @return The `toString()` method is being overridden to return a string representation of the
     * `Step` object, including its `id`, `title`, `description`, `duration`, `product`, and `proTips`
     * properties.
     */
    @Override
    public String toString() {
        return "Step{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", duration='" + duration + '\'' +
                ", product='" + product + '\'' +
                ", proTips=" + proTips +
                '}';
    }
}
