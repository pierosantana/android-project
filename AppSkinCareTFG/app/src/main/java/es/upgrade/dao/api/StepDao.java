package es.upgrade.dao.api;

import java.util.ArrayList;
import java.util.List;

import es.upgrade.entidad.Step;

public class StepDao {
    private List<Step> steps;
    private static StepDao instance;

    private StepDao(){steps = new ArrayList<>();}

    public static StepDao getInstance(){
        if(instance == null){
            instance = new StepDao();
        }
        return instance;
    }
    public List<Step> getSteps(){
        if(steps == null){
            steps = new ArrayList<>();
        }
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }
    public void clearSteps(){this.steps.clear();}
}
