package kraken.plugin.mash;

import com.google.common.base.Stopwatch;
import helpers.Log;
import helpers.Welp;
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
    private static PluginContext context;
    private static boolean settingsLoaded = false;
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
    private static ArrayList<Integer> riftIds = new ArrayList<Integer>(Arrays.asList(2342, 66523, 87306, 89975)); // 110307 // 119345
    private static ArrayList<Integer> minigameRiftIds = new ArrayList<Integer>(Arrays.asList(93489, 93495));
    private static boolean doYouEvenUwU = false;

    public MashDivination() {
    }

    @Override
    public boolean onLoaded(PluginContext pluginContext) {
        if (context == null)
            context = pluginContext;

        pluginContext.setName("MashDivination v1.08092022a");
//        pluginContext.category = "Mash";

        load(context);
        return true;
    }

    private void HandlePersistentData() {
        Log.Information("Loading settings from last session.");

        startRoutine = getBoolean("startRoutine");
        wispSelected = getInt("wispSelected");
        doYouEvenUwU = getBoolean("doYouEvenUwU");
        wispChosen = wispAvailable.get(wispSelected);
        wispLocation = ManualLocationModel.GetLocationByName(wispChosen.Location);

        settingsLoaded = true;
    }

    private void UpdatePersistentData() {
        boolean any = false;

        if (getBoolean("startRoutine") != startRoutine) {
            setAttribute("startRoutine", startRoutine);
            any = true;
        }

        if (getInt("wispSelected") != wispSelected) {
            setAttribute("wispSelected", wispSelected);
            any = true;
        }

        if (getBoolean("doYouEvenUwU") != doYouEvenUwU) {
            setAttribute("doYouEvenUwU", doYouEvenUwU);
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
            ImGui.label(MessageFormat.format("Hawvesting {0}s.", Welp.Uwufy(wispChosen.Name)));
        }

        ImGui.newLine();

        ImGui.label(MessageFormat.format("Wevew: {0} :O", wispChosen.Level));
        ImGui.label(MessageFormat.format("Wocation: {0} >///<", Welp.Uwufy(wispChosen.Location)));

        ImGui.newLine();
        doYouEvenUwU = ImGui.checkbox("UwU!!!", doYouEvenUwU);

        if (wispLocation == null) {
            ImGui.newLine();
            ImGui.label("Whaaaa, the wocation was not found Dx");
            ImGui.label("Pwease move thewe by uwself owo!!");
        }

        ImGui.newLine();

        String toggleRoutineText = startRoutine ? "Stawp Woutine >:C" : "Stawt Woutine uwu!";
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
        SkillingHandler.HandleDivineOMatic();

        if (Inventory.isFull() || isConverting) {
            if (!convertTimeout.isRunning())
                convertTimeout.start();

            isConverting = Inventory.contains(x -> wispChosen.ItemIds.contains(x.getId()));
            if (isConverting) {
                Log.Information("Memories found, trying to convert.");

                if (player.getAnimationId() != 0)
                    convertTimeout.reset().start();

                SceneObject minigameRift = SceneObjects.closest(x -> minigameRiftIds.contains(x.getId()));

                if (minigameRift != null && convertTimeout.elapsed(TimeUnit.SECONDS) > 5) {
                    Log.Information("Minigame rift found, ugh.");
                    Vector3i riftPos = minigameRift.getGlobalPosition();
                    Actions.menu(Actions.MENU_EXECUTE_OBJECT1, minigameRift.getInteractId(), riftPos.getX(), riftPos.getY(), 0);
                    return;
                }

                SceneObject rift = SceneObjects.closest(x -> riftIds.contains(x.getId()));

                if (rift != null && convertTimeout.elapsed(TimeUnit.SECONDS) > 5) {
                    Log.Information("Normal rift found.");
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
