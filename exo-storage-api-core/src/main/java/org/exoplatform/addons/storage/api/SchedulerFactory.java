package org.exoplatform.addons.storage.api;

import org.exoplatform.addons.storage.utils.PropertyManager;

/**
 * Created by menzli on 05/06/14.
 */
public class SchedulerFactory {

    private Class concreteClass ;

    public SchedulerFactory() {

        try {

            // Taking the name of class from the resource file
            String className = PropertyManager.getProperty(PropertyManager.PROPERTY_SCHEDULER_ClASSNAME);

            // Class.forName() method will dynamically load the class
            // passed to it as a parameter
            concreteClass = Class.forName(className);


        } catch(Exception ex){



        } finally {

        }
    }

    // This method will act as a method that will work as a factory
    // and will return the object asked based on the configuration
    // files entry.
    public IScheduler createScheduler(){

        try {

            // Returns concrete implementation of Scheduler by
            // calling newInstance() method on the Class instance
            // Here in our case properties file points to Dog object
            // so based on the resource file the object will be created .
            return (IScheduler)concreteClass.newInstance();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
