package es.upgrade.entidad;

import es.upgrade.R;

public enum SkinType {
    DRY(R.drawable.img_dry, "Sensación de tirantez, piel áspera, picazón, descamación."),
    NORMAL(R.drawable.img_normal, "Piel brillante, poros dilatados, exceso de grasa."),
    COMBINATION(R.drawable.img_combination, "Zona T grasa, otras áreas secas, equilibrio de tipos.");


    private final int imageResId;
    private final String description;

    SkinType(int imageResId, String description) {
        this.imageResId = imageResId;
        this.description = description;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getDescription() {
        return description;
    }
}

