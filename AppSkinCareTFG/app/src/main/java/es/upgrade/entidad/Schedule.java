package es.upgrade.entidad;

import es.upgrade.R;

public enum Schedule {
    COMPLETE(R.drawable.ic_dia_y_noche,
            "The main goal is to protect your skin. During the night, your skin recovers " +
                    "and produces new cells. In the morning, you need to cleanse your skin to " +
                    "remove the residue from that overnight renewal and apply sunscreen to prevent " +
                    "sun damage. Make sure to use light, moisturizing products."),
    NIGHT(R.drawable.ic_rutina_noche,"It’s the perfect time to focus on skin repair. " +
            "Nighttime is when the skin regenerates, so more intensive treatments like serums, " +
            "anti-wrinkle creams, or products with acids are more effective. Deep cleansing is " +
            "also key to remove makeup, dust, and impurities accumulated throughout the day.");

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
