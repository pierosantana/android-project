package es.upgrade.entidad;

import es.upgrade.R;

public enum Schedule {
    COMPLETE(R.drawable.ic_dia_y_noche,
            "El objetivo principal es proteger tu piel. Durante la noche, tu piel se recupera y produce células nuevas. " +
                    "Por la mañana, necesitas limpiar la piel para eliminar los residuos de esa renovación nocturna y aplicar " +
                    "protector solar para evitar los daños del sol. Asegúrate de usar productos ligeros y hidratantes."),
    NIGHT(R.drawable.ic_rutina_noche,"Es el momento ideal para enfocarte en la reparación de la piel. La noche es cuando la " +
            "piel se regenera, por lo que los tratamientos más intensivos como sueros, cremas " +
            "antiarrugas o productos con ácidos son más efectivos. La limpieza profunda también es clave para eliminar el maquillaje, " +
            "el polvo y las impurezas acumuladas durante el día.");

    private final int iconResId;
    private final String description;

    Schedule(int iconResId, String description) {
        this.iconResId = iconResId;
        this.description = description;
    }

    public int getImageResId() {
        return iconResId;
    }

    public String getDescription() {
        return description;
    }
}
