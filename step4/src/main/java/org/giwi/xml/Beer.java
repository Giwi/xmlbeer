package org.giwi.xml;

/**
 * Created by b3605 on 22/02/17.
 *
 * @author Xavier MARIN (b3605)
 */
public class Beer {
    private String id;
    private String name;
    private String description;
    private String img;
    private double alcohol;

    @Override
    public String toString() {
        return "Beer{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", img='" + img + '\'' +
                ", alcohol=" + alcohol +
                '}';
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets img.
     *
     * @return the img
     */
    public String getImg() {
        return img;
    }

    /**
     * Sets img.
     *
     * @param img the img
     */
    public void setImg(String img) {
        this.img = img;
    }

    /**
     * Gets alcohol.
     *
     * @return the alcohol
     */
    public double getAlcohol() {
        return alcohol;
    }

    /**
     * Sets alcohol.
     *
     * @param alcohol the alcohol
     */
    public void setAlcohol(double alcohol) {
        this.alcohol = alcohol;
    }
}
