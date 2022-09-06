package helpers;

import enums.World;
import kraken.plugin.api.Client;
import kraken.plugin.api.Kraken;
import kraken.plugin.api.PluginContext;

import java.text.MessageFormat;
import java.util.ArrayList;

public class Engine {
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

    public static void SwapWorld(World world) {
        Integer targetWorld = Integer.valueOf(Helper.ReplaceString(world.name(), "WORLD", ""));
        if (targetWorld != 0) {
            Log.Information(MessageFormat.format("Changing worlds to {0}.", targetWorld));
            Kraken.setRandomizeWorldLoginType(null);
            Kraken.setLoginWorld(targetWorld);
            Kraken.toggleAutoLogin(true);
            Client.exitToLobby();
        }
    }
}
