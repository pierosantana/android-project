package es.upgrade.entidad;

import es.upgrade.R;

public enum SkinType {
    DRY(R.drawable.ic_piel_seca,
            "If your skin feels tight, rough, or tends to peel, especially " +
                    "after washing it, you probably have dry skin. This type of skin " +
                    "produces less natural oil than needed to maintain hydration, making " +
                    "it more prone to irritation, redness, and early signs of fine lines. " +
                    "It may have areas with rough texture, lack of radiance, and a " +
                    "constant feeling of dryness. If you notice your skin cracking easily " +
                    "in winter or that it needs constant hydration to feel comfortable, " +
                    "then you have dry skin."),
    NORMAL(R.drawable.ic_piel_normal, "If your skin doesn’t usually feel too oily " +
            "or too dry, you don’t experience peeling or areas with excess shine, and you " +
            "rarely have imperfections, you probably have normal skin. This type of skin has " +
            "a proper balance of oil and hydration, giving it a smooth, even appearance with " +
            "a healthy glow. The pores are small or medium-sized and don’t get clogged easily. " +
            "Although this skin type doesn’t typically present specific issues, maintaining a " +
            "basic routine of cleansing, moisturizing, and sun protection is key to keeping its " +
            "natural balance.."),
    COMBINATION(R.drawable.ic_piel_mixta, "If you notice that your forehead, nose, and " +
            "chin tend to be oilier and shinier, while your cheeks and other areas of your face " +
            "are drier or normal, then you have combination skin. This skin type is very common " +
            "and may have enlarged pores and a tendency to blackheads or pimples in the T-zone, " +
            "while the cheeks may feel tighter or even peel in certain seasons. Identifying which " +
            "parts of your face are oilier and which are drier will help you choose specific " +
            "products to balance your skin without dehydrating it or increasing oil production."),


    SENSITIVE(R.drawable.ic_piel_sensible,
    "If you often experience redness, burning, itching, " +
            "or a feeling of tightness after using certain products, exposing your skin to the sun, wind, or even temperature changes, " +
            "you likely have sensitive skin. This skin type reacts easily to external factors and may appear dry, " +
            "irritated, or flushed without much warning. " +
            "Sensitive skin can be naturally delicate or become sensitized over time due to environmental stressors or harsh skincare routines. " +
            "Choosing gentle, fragrance-free products and maintaining a soothing routine is key to keeping sensitive skin calm and protected."
    );


    private final int iconResId;
    private final String description;

    SkinType(int iconResId, String description) {
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

