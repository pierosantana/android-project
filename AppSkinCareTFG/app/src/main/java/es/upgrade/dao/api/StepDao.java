package es.upgrade.dao.api;

import java.util.ArrayList;
import java.util.List;

import es.upgrade.entidad.Step;

/**
 * The `StepDao` class in Java provides methods to manage a list of `Step` objects, including getting,
 * setting, and clearing the steps list.
 */
public class StepDao {
    private List<Step> steps;
    private static StepDao instance;

    // The `private StepDao()` constructor is a private constructor of the `StepDao` class in Java. It
    // initializes the `steps` variable with a new instance of `ArrayList`. This constructor is
    // private, meaning it can only be accessed within the `StepDao` class itself.
    private StepDao(){steps = new ArrayList<>();}

    /**
     * The function `getInstance` returns an instance of `StepDao` and creates a new instance if it
     * doesn't already exist.
     * 
     * @return An instance of the StepDao class is being returned.
     */
    public static StepDao getInstance(){
        if(instance == null){
            instance = new StepDao();
        }
        return instance;
    }

    /**
     * The function `getSteps` returns a list of `Step` objects, initializing it if necessary.
     * 
     * @return The method `getSteps()` returns a List of Step objects. If the `steps` variable is null,
     * it initializes a new ArrayList and returns it.
     */
    public List<Step> getSteps(){
        if(steps == null){
            steps = new ArrayList<>();
        }
        return steps;
    }

    /**
     * The function sets a list of Step objects to a class variable.
     * 
     * @param steps The `setSteps` method is used to set the list of `Step` objects in the class. The
     * `steps` parameter is a List of Step objects that will be assigned to the `steps` instance
     * variable of the class.
     */
    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

   /**
    * The clearSteps function clears all elements from the steps list.
    */
    public void clearSteps(){this.steps.clear();}
}
