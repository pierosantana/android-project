package es.upgrade.entidad;

import es.upgrade.R;

public enum SkinType {
    DRY(R.drawable.ic_piel_seca,
            "Si tu piel se siente tirante, áspera o con tendencia a descamarse, " +
                    "especialmente después de lavarla, es probable que tengas piel seca. " +
                    "Este tipo de piel produce menos grasa natural de lo necesario para mantener " +
                    "la hidratación, lo que la hace más propensa a la irritación, enrojecimiento y " +
                    "la aparición temprana de líneas de expresión. Puede presentar zonas con textura rugosa, " +
                    "falta de luminosidad y una sensación constante de sequedad. Si notas que tu piel se agrieta " +
                    "fácilmente en invierno o que necesita hidratación constante para sentirse cómoda, " +
                    "entonces tienes piel seca."),
    NORMAL(R.drawable.ic_piel_normal, "Si tu piel no suele sentirse ni demasiado grasosa ni demasiado seca, " +
            "no presentas descamación ni zonas con exceso de brillo y rara vez tienes imperfecciones, probablemente " +
            "tengas piel normal. Este tipo de piel tiene un equilibrio adecuado de grasa e hidratación, " +
            "lo que le da una apariencia suave, uniforme y con un brillo saludable. Los poros son pequeños o medianos " +
            "y no se obstruyen con facilidad. Aunque este tipo de piel no suele presentar problemas específicos, " +
            "mantener una rutina básica de limpieza, hidratación y protección solar es clave para conservar su equilibrio natural."),
    COMBINATION(R.drawable.ic_piel_mixta, "Si notas que tu frente, nariz y mentón tienden a ser más grasosos " +
            "y brillantes, mientras que tus mejillas y otras áreas del rostro son más secas o normales, entonces " +
            "tienes piel mixta. Este tipo de piel es muy común y puede presentar poros dilatados y propensión " +
            "a puntos negros o espinillas en la zona T, mientras que las mejillas pueden sentirse más " +
            "tirantes o incluso descamarse en algunas épocas del año. Identificar qué partes de tu rostro " +
            "son más grasas y cuáles más secas te ayudará a elegir productos específicos para equilibrar " +
            "tu piel sin deshidratarla ni aumentar la producción de grasa.");


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

