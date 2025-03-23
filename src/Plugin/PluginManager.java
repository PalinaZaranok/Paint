package Plugin;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

public class PluginManager {
    private static final String PLUGIN_DIR = "plugins";
    private final List<ShapePlugin> plugins = new ArrayList<>();
    public void loadPlugins() {
        File pluginDir = new File(PLUGIN_DIR);
        URL[] urls = Arrays.stream(pluginDir.listFiles())
                .filter(f -> f.getName().endsWith(".jar"))
                .map(f -> {
                    try {
                        return f.toURI().toURL();
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toArray(URL[]::new);

        URLClassLoader classLoader = new URLClassLoader(urls);
        ServiceLoader<ShapePlugin> serviceLoader = ServiceLoader.load(
                ShapePlugin.class, classLoader);
        for (ShapePlugin plugin : serviceLoader) {
            plugins.add(plugin);
        }
    }

    public List<ShapePlugin> getPlugins() {
        return Collections.unmodifiableList(plugins);
    }

}
