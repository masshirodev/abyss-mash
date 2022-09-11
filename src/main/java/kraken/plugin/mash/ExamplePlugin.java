package kraken.plugin.mash;

import helpers.Log;
import kraken.plugin.api.ImGui;
import kraken.plugin.api.Players;
import kraken.plugin.api.Plugin;
import kraken.plugin.api.PluginContext;

public class ExamplePlugin extends Plugin {
    private static PluginContext context;
    private static boolean startRoutine = false;
    private static boolean settingsLoaded = false;
    private static int loopDelay = 1000;

    public ExamplePlugin() {
    }

    @Override
    public boolean onLoaded(PluginContext pluginContext) {
        if (context == null)
            context = pluginContext;

        pluginContext.setName("ExamplePlugin v1.05092022a");
//        pluginContext.category = "Mash";

        load(context);
        return true;
    }

    private void HandlePersistentData() {
        Log.Information("Loading settings from last session.");

        startRoutine = getBoolean("startRoutine");

        settingsLoaded = true;
    }

    private void UpdatePersistentData() {
        boolean any = false;

        if (getBoolean("startRoutine") != startRoutine) {
            setAttribute("startRoutine", startRoutine);
            any = true;
        }

        if (any) {
            Log.Information("Saving settings.");
            save(context);
        }
    }

    @Override
    public int onLoop() {
        if (Players.self() == null) return 1000;
        if (!settingsLoaded) HandlePersistentData();
        UpdatePersistentData();

        if (startRoutine)
            Routine();

        return loopDelay;
    }

    @Override
    public void onPaint() {
        if (ImGui.beginTabBar("MainTabs")) {
            if (ImGui.beginTabItem("Main")) {
                PaintMain();
                ImGui.endTabItem();
            }

            ImGui.endTabBar();
        }
    }

    private void PaintMain() {

    }

    private void Routine() {

    }
}
