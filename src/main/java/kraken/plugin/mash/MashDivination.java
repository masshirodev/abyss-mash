package kraken.plugin.mash;

import abyss.plugin.api.input.InputHelper;
import com.google.common.base.Stopwatch;
import helpers.Log;
import kraken.plugin.api.*;
import models.ManualLocationModel;
import models.WispModel;
import modules.ManualMovementHandler;
import modules.SkillingHandler;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MashDivination extends Plugin {
    private static boolean startRoutine = false;
    private static int wispSelected = 0;
    private static List<WispModel> wispAvailable = WispModel.GetWispList();
    private static WispModel wispChosen = wispAvailable.get(wispSelected);
    private static ManualLocationModel wispLocation = ManualLocationModel.GetLocationByName(wispChosen.Location);
    private static String[] comboOptions = WispModel.GetWispListToCombo();
    private static boolean isConverting = false;
    private static Npc currentWisp;
    private static Stopwatch harvestTimeout = Stopwatch.createUnstarted();
    private static Stopwatch convertTimeout = Stopwatch.createUnstarted();
    private static ArrayList<Integer> riftIds = new ArrayList<Integer>(Arrays.asList(87306, 93489, 93495));
    private static boolean doYouEvenUwU = false;

    public MashDivination() {
    }

    @Override
    public boolean onLoaded(PluginContext pluginContext) {
        pluginContext.setName("MashDivination v1.05092022a");
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

    private void PaintMainUwu() {
        if (!startRoutine) {
            wispSelected = ImGui.combo("Uwisps##WispSelectCombo", comboOptions, wispSelected);
            wispChosen = wispAvailable.get(wispSelected);
            wispLocation = ManualLocationModel.GetLocationByName(wispChosen.Location);
        } else {
            ImGui.label(MessageFormat.format("Hawvesting {0}s.", wispChosen.Name));
        }

        ImGui.newLine();

        ImGui.label(MessageFormat.format("Wevew: {0} :0", wispChosen.Level));
        ImGui.label(MessageFormat.format("Wocation: {0} >///<", wispChosen.Location));

        ImGui.newLine();
        doYouEvenUwU = ImGui.checkbox("UwU!!!", doYouEvenUwU);

        if (wispLocation == null) {
            ImGui.newLine();
            ImGui.label("Whaaaa, the wocation was not found Dx");
            ImGui.label("Pwease move thewe by uwself owo!!");
        }

        ImGui.newLine();

        String toggleRoutineText = startRoutine ? "Stawp Woutine" : "Stawt Woutine";
        if (ImGui.button(toggleRoutineText)) {
            startRoutine = !startRoutine;
        }
    }

    private void PaintMain() {
        if (doYouEvenUwU)
            PaintMainUwu();
        else {
            if (!startRoutine) {
                wispSelected = ImGui.combo("Wisps##WispSelectCombo", comboOptions, wispSelected);
                wispChosen = wispAvailable.get(wispSelected);
                wispLocation = ManualLocationModel.GetLocationByName(wispChosen.Location);
            } else {
                ImGui.label(MessageFormat.format("Harvesting {0}s.", wispChosen.Name));
            }

            ImGui.newLine();

            ImGui.label(MessageFormat.format("Level: {0}", wispChosen.Level));
            ImGui.label(MessageFormat.format("Location: {0}", wispChosen.Location));

            ImGui.newLine();
            doYouEvenUwU = ImGui.checkbox("uwu?", doYouEvenUwU);

            if (wispLocation == null) {
                ImGui.newLine();
                ImGui.label("Location not found in the database.");
                ImGui.label("The user has to move to a pool manually.");
            }

            ImGui.newLine();

            String toggleRoutineText = startRoutine ? "Stop Routine" : "Start Routine";
            if (ImGui.button(toggleRoutineText)) {
                startRoutine = !startRoutine;
            }
        }
    }

    private void Routine() {
        Player player = Players.self();
        SkillingHandler.HandleChronicles();
        SkillingHandler.HandleSerenSpirits();

        if (Inventory.isFull() || isConverting) {
            if (!convertTimeout.isRunning())
                convertTimeout.start();

            isConverting = Inventory.contains(x -> wispChosen.ItemIds.contains(x.getId()));
            if (isConverting) {
                Log.Information("Memories found, converting.");
                SceneObject rift = SceneObjects.closest(x -> riftIds.contains(x.getId()));

                if (rift != null && convertTimeout.elapsed(TimeUnit.SECONDS) > 5) {
                    if (rift.getId() == 93489) {
                        Vector3i riftPos = rift.getGlobalPosition();
                        Actions.menu(Actions.MENU_EXECUTE_OBJECT1, 93489, riftPos.getX(), riftPos.getY(), 0);
                        return;
                    }

                    rift.interact(Actions.MENU_EXECUTE_OBJECT1);
                }
            }

            return;
        }

        Npc wisps = Npcs.closest(x -> x.getId() == wispChosen.NpcId);

        if (wisps != null) {
            if (currentWisp == null) {
                Log.Information("Interacting with a wisp.");
                harvestTimeout.reset().start();

                currentWisp = wisps;
                currentWisp.interact(Actions.MENU_EXECUTE_NPC1);
                return;
            }

            if (player.getAnimationId() != 0)
                harvestTimeout.reset().start();

            if (harvestTimeout.elapsed(TimeUnit.SECONDS) > 5) {
                currentWisp.interact(Actions.MENU_EXECUTE_NPC1);
                currentWisp = null;
                harvestTimeout.reset().start();
            }

            return;
        }

        if (wispLocation != null) {
            ManualMovementHandler.GoTo(wispLocation);
        }
    }
}
