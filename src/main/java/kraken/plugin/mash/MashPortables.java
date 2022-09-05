package kraken.plugin.mash;

import abyss.plugin.api.input.InputHelper;
import abyss.plugin.api.world.WorldTile;
import com.google.common.base.Stopwatch;
import enums.LocationType;
import helpers.Helper;
import helpers.Log;
import kraken.plugin.api.*;
import models.ManualLocationModel;
import models.PortableModel;
import modules.BankHandler;
import modules.InventoryHandler;
import modules.ManualMovementHandler;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class MashPortables extends Plugin {
    public static boolean startRoutine = false;
    private static String[] comboOptions = PortableModel.GetPortableListToCombo();
    private static int comboSelected = 0;
    private static PortableModel portableChosen = PortableModel.GetPortableByName("Portable brazier");
    private static int presetSelected = 1;
    private static final ManualLocationModel bankLocation = ManualLocationModel.GetLocationByName("Grand Exchange");
    private static Stopwatch idleTimer = Stopwatch.createStarted();
    public static boolean currentlyCrafting = false;
    public static ArrayList<Integer> presetItems = new ArrayList<>();
    private static ArrayList<Boolean> selectedItems = new ArrayList<>(Arrays.asList(false, false, false, false, false, false, false));

    public MashPortables() {
    }

    @Override
    public boolean onLoaded(PluginContext pluginContext) {
        pluginContext.setName("MashPortables v1.05092022a");
        return true;
    }

    @Override
    public int onLoop() {
        if (Players.self() == null) return 1000;

        if (startRoutine)
            Routine();

        return 1000;
    }

    private void Routine() {
        Player player = Players.self();

        if (presetItems.size() == 0) {
            for (int i = 0; i < InventoryHandler.GetDistinctWidgetItems().length; i++) {
                WidgetItem item = InventoryHandler.GetDistinctWidgetItems()[i];
                boolean enabled = selectedItems.get(i);
                if (enabled)
                    presetItems.add(item.getId());
            }
        }

        if (Inventory.countFreeSlots() >= 12 || !InventoryHandler.HaveAll(presetItems)) {
            if (!InventoryHandler.HaveAll(presetItems))
                Log.Information("Not all preset items are in the inventory.");
            if (Inventory.countFreeSlots() >= 12)
                Log.Information("Inventory is half empty.");

            currentlyCrafting = false;
            if (BankHandler.BankNearby(LocationType.Bank)) {
                Log.Information("A bank was found nearby.");
                BankHandler.WithdrawPreset(presetSelected);
                ManualMovementHandler.movementIndex = 1;
                return;
            }

            Log.Information("Going to a nearby bank.");
            ManualMovementHandler.GoTo(bankLocation);
            return;
        }

        if (player.isAnimationPlaying() && currentlyCrafting) {
            Log.Information("Idle timer reset.");
            idleTimer.reset().start();
        }

        if (!currentlyCrafting || idleTimer.elapsed(TimeUnit.SECONDS) > 8) {
            Log.Information("Starting a craft.");
            SceneObject portable = SceneObjects.closest(x -> portableChosen.ObjectIds.contains(x.getId()));

            if (portable != null){
                if (!Widgets.isOpen(1371)) {
                    portable.interact(Actions.MENU_EXECUTE_OBJECT1);
                    Helper.Wait(1000);
                    return;
                }

                Helper.Wait(500);
                InputHelper.typeKey(0x20);
                currentlyCrafting = true;
                idleTimer.reset().start();
                return;
            }

            ArrayList<WorldTile> coords = bankLocation.Path.get(0).Coordinates;
            if (coords.get(coords.size()-1).distance(Players.self().getGlobalPosition()) > 30) {
                Log.Information("Cant find a portable, going to the Grand Exchange.");
                ManualMovementHandler.GoTo(bankLocation);
                return;
            }

            Log.Information("Cant find a portable.");
        }
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
        if (presetSelected == 0 || presetSelected > 9) presetSelected = 1;
        ImGui.columns("##PortableMainColumns", 2, false);
        ImGui.beginChild("##PortableMainFrame", true);

        if (!startRoutine) {
            comboSelected = ImGui.combo("Portable##PortableSelectCombo", comboOptions, comboSelected);
            portableChosen = PortableModel.GetPortableByName(comboOptions[comboSelected]);
            ImGui.newLine();
            ImGui.label(MessageFormat.format("Skill: {0}", portableChosen.Skill));
            ImGui.newLine();

            ImGui.label("Inventory:");
            for (int i = 0; i < InventoryHandler.GetDistinctWidgetItems().length; i++) {
                WidgetItem item = InventoryHandler.GetDistinctWidgetItems()[i];
                selectedItems.set(i, ImGui.checkbox(MessageFormat.format("{0} ({1})##SelectItem{2}", item.getName(), item.getId(), item.getId()), selectedItems.get(i)));
            }
        } else {
            ImGui.label(MessageFormat.format("Using {0} ({1}).", portableChosen.Name, portableChosen.Skill));
        }

        ImGui.newLine();

        String toggleRoutineText = startRoutine ? "Stop Routine" : "Start Routine";
        if (ImGui.button(toggleRoutineText)) {
            if (startRoutine)
                presetItems = new ArrayList<>();
            startRoutine = !startRoutine;
            currentlyCrafting = false;
        }

        ImGui.newLine();

        ImGui.endChild();
        ImGui.nextColumn();
        ImGui.beginChild("##PortableSecondaryFrame", true);
        presetSelected = ImGui.intInput("Preset##InputDesiredPreset", presetSelected);
        ImGui.newLine();
        ImGui.label("1 - Select a portable.");
        ImGui.label("2 - Set up a bank preset.");
        ImGui.label("3 - Withdraw the preset.");
        ImGui.label("4 - Select the items to the left.");
        ImGui.label("4 - Configure the portable.");
        ImGui.label("6 - Start.");
        ImGui.newLine();
        ImGui.label("That's it. :B");
        ImGui.label("For now it only supports already");
        ImGui.label("placed portables, so go to like, ");
        ImGui.label("world 84 or something.");

        ImGui.endChild();
        ImGui.columns("", 1, false);
    }
}
