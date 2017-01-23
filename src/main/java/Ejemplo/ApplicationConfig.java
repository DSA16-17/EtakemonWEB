package Ejemplo;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@ApplicationPath("/service")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {

        Set<Class<?>> resources = new java.util.HashSet<>();

        System.out.println("REST configuration starting: getClasses()");

        //features
        //this will register MOXy JSON providers
        resources.add(org.glassfish.jersey.moxy.json.MoxyJsonFeature.class);
        //we could also use this
        //resources.add(org.glassfish.jersey.moxy.xml.MoxyXmlFeature.class);


        System.out.println("REST configuration ended successfully.");
        return resources;
    }

    @Override
    public Set<Object> getSingletons() {
        return Collections.emptySet();
    }

    @Override
    public Map<String, Object> getProperties() {
        Map<String, Object> properties = new HashMap<>();

        //in Jersey WADL generation is enabled by default, but we don't
        //want to expose too much information about our apis.
        //therefore we want to disable wadl (http://localhost:8080/service/application.wadl should return http 404)
        //see https://jersey.java.net/nonav/documentation/latest/user-guide.html#d0e9020 for details
        properties.put("jersey.config.server.wadl.disableWadl", true);

        //we could also use something like this instead of adding each of our resources
        //explicitely in getClasses():
        //properties.put("jersey.config.server.provider.packages", "com.nabisoft.tutorials.mavenstruts.service");


        return properties;
    }
}