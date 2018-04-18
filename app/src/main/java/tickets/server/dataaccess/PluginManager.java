
package tickets.server.dataaccess;

import java.net.MalformedURLException;
import java.net.URLClassLoader;
import java.net.URL;
import java.util.ArrayList;
import java.io.File;

import tickets.server.dataaccess.interfaces.DAOFactory;


public class PluginManager {

    private ArrayList<URL> allJars;
    private ClassLoader loader;

    public PluginManager() {
        allJars = new ArrayList<>();

        //Get a file object of the directory holding all persistence plugins
        File libs = new File("tickets");

        //iterate through all of the files in the tickets/ directory
        //  for each .jar file, create a new URL for it, and add it
        //  to in allJars
        try {
            for (File file : libs.listFiles()) {
                if (isJar(file.getName())) {
                    allJars.add(new URL("jar:File:" + file.getAbsolutePath() + "!/"));
                }
            }
        }
        catch (MalformedURLException ex) {
            System.out.println(ex.toString());
        }
        // create a class loader that loads from the given .jar files
        loader = new URLClassLoader(allJars.toArray(new URL[allJars.size()]), PluginManager.class.getClassLoader());
        return;
    }

    // for a plugin to be correctly loaded,
    public DAOFactory getPlugin(String name) {
        System.out.println("getting plugin");
        DAOFactory daoFactory = null;
        name = "tickets." + name.toLowerCase() + "_dao." + name + "Factory";
        try {
            Class<? extends DAOFactory> plugin = Class.forName(name, true, loader)
                    .asSubclass(DAOFactory.class);
            daoFactory = plugin.getDeclaredConstructor().newInstance();
        }
        catch (NoClassDefFoundError defFoundError) {
            // I'm not positive that this is covered by the
            //   general Exception catch-all, and since it's
            //   a runtime exception it's better to be safe
            return null;
        }
        catch (ClassNotFoundException classNotFound) {
            return null;
        }
        catch (Exception ex) {
            System.out.println(ex);
        }

        return daoFactory;
    }

    // helper function to check whether or not the
    //   final extension of a file is '.jar'
    private boolean isJar(String pathname) {
        String extension = "";
        int i = pathname.lastIndexOf(".");
        if (i > 0) {
            extension = pathname.substring(i + 1);
        }
        if (extension.equals("jar")) {
            return true;
        }
        return false;
    }
}