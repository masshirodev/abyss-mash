package kraken.plugin.mash;

import kraken.plugin.api.ImGui;
import kraken.plugin.api.Players;
import kraken.plugin.api.Plugin;
import kraken.plugin.api.PluginContext;
import models.ArchaeologyModel;
import models.ManualLocationModel;

import java.text.MessageFormat;
import java.util.List;

public class MashArchaeology extends Plugin {
    private static boolean startRoutine = false;
    private static int wispSelected = 0;
//    private static List<ArchaeologyModel> archAvailable = ArchaeologyModel.GetArchList();
//    private static ArchaeologyModel archChosen = archAvailable.get(wispSelected);
//    private static ManualLocationModel archLocation = ManualLocationModel.GetLocationByName(archChosen.Location);
//    private static String[] comboOptions = ArchaeologyModel.GetArchListToCombo();

    public MashArchaeology() {
    }

    @Override
    public boolean onLoaded(PluginContext pluginContext) {
        pluginContext.setName("MashArchaeology v1.05092022a");
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
//        if (!startRoutine) {
//            wispSelected = ImGui.combo("Wisps##WispSelectCombo", comboOptions, wispSelected);
//            wispChosen = wispAvailable.get(wispSelected);
//            wispLocation = ManualLocationModel.GetLocationByName(wispChosen.Location);
//        } else {
//            ImGui.label(MessageFormat.format("Harvesting {0}s.", wispChosen.Name));
//        }
//
//        ImGui.newLine();
//
//        ImGui.label(MessageFormat.format("Level: {0}", wispChosen.Level));
//        ImGui.label(MessageFormat.format("Location: {0}", wispChosen.Location));
//
//        ImGui.newLine();
//        doYouEvenUwU = ImGui.checkbox("uwu?", doYouEvenUwU);
//
//        if (wispLocation == null) {
//            ImGui.newLine();
//            ImGui.label("Location not found in the database.");
//            ImGui.label("The user has to move to a pool manually.");
//        }
//
//        ImGui.newLine();
//
//        String toggleRoutineText = startRoutine ? "Stop Routine" : "Start Routine";
//        if (ImGui.button(toggleRoutineText)) {
//            startRoutine = !startRoutine;
//        }
    }

    private void Routine() {

    }
}
