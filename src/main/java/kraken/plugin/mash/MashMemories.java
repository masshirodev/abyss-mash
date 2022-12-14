package kraken.plugin.mash;

import kraken.plugin.api.ImGui;
import kraken.plugin.api.Players;
import kraken.plugin.api.Plugin;
import kraken.plugin.api.PluginContext;

import java.util.ArrayList;
import java.util.Arrays;

public class MashMemories extends Plugin {
    private static boolean startRoutine = false;
    private static ArrayList<Integer> memoryJar = new ArrayList<Integer>(Arrays.asList(42898, 42899));

    public MashMemories() {
    }

    @Override
    public boolean onLoaded(PluginContext pluginContext) {
        pluginContext.setName("MashMemories v1.05092022a");
//        pluginContext.category = "Mash";
        return true;
    }

    @Override
    public int onLoop() {
        if (Players.self() == null) return 1000;

        if (startRoutine)
            Routine();

        return 1000;
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
