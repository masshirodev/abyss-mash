package helpers;

import abyss.plugin.api.actions.attributes.PluginAttributes;
import kraken.plugin.api.Kraken;
import kraken.plugin.api.PluginContext;

import java.util.ArrayList;

public class System {
    public static ArrayList<String> GetInstalledPlugins() {
        ArrayList<String> plugins = new ArrayList<>();
        for (PluginContext ctx : Kraken.getAllPlugins())
            plugins.add(ctx.getName());

        return plugins;
    }

    public static boolean IsPluginInstalled(String name) {
        ArrayList<String> all = GetInstalledPlugins();
        for (String addon : all)
            if (addon.equals(name))
                return true;

        return false;
    }
}
