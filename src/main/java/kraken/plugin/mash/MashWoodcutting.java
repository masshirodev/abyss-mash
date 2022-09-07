package kraken.plugin.mash;

import helpers.Helper;
import helpers.Log;
import kraken.plugin.api.*;
import models.ManualLocationModel;
import models.LogModel;
import models.RequirementModel;
import modules.*;

import java.text.MessageFormat;
import java.util.Arrays;

public final class MashWoodcutting extends Plugin {
    public static boolean startRoutine = false;
    private static LogModel logChosen;
    private static ManualLocationModel locationChosen;
    private static ManualLocationModel depositChosen;
    private static int logSelected = 0;
    private static int locationSelected = 0;
    private static int oldLogSelected = 0;
    private static int depositSelected = 0;
    private static boolean powerWoodcutting = false;
    private static boolean currentlyDroppingItems = false;
    private static boolean drawLocationChosen = false;
    private static boolean drawDepositChosen = false;

    public MashWoodcutting() {
    }

    @Override
    public boolean onLoaded(PluginContext pluginContext) {
        pluginContext.setName("MashWoodcutting");
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

    public void Routine() {
        if (Inventory.isFull() || currentlyDroppingItems) {
            if (powerWoodcutting && !currentlyDroppingItems) currentlyDroppingItems = true;
            if (powerWoodcutting && currentlyDroppingItems) {
                WidgetItem toDrop = Inventory.first(x -> Arrays.stream(LogModel.GetLogListToCombo()).anyMatch(y -> x.getName().equalsIgnoreCase(y)));
                if (toDrop != null) {
                    InventoryHandler.DropItem(toDrop);
                    Log.Information(MessageFormat.format("Dropping [{0}]", toDrop.getName()));
                    return;
                }

                currentlyDroppingItems = false;
                return;
            }

            if (BankHandler.BankNearby(logChosen.DepositType)) {
//                BankHandler.Decide(logChosen.DepositType, (x -> true));
                return;
            }

            ManualMovementHandler.GoTo(depositChosen);
        } else {
            SceneObject logNearby = SceneObjects.closest(x -> logChosen.ObjectId.contains(x.getId()) && !x.hidden());

            if (logNearby != null)
                WoodcuttingHandler.Execute(logChosen);
            else
                ManualMovementHandler.GoTo(locationChosen);
        }
    }

    @Override
    public void onPaint() {
        ImGui.label("");
        ImGui.label("MashWoodcutting");
        ImGui.label("");
        if (Players.self() != null) {
            if (ImGui.beginTabBar("MainTabs")) {
                if (ImGui.beginTabItem("Main")) {
                    PaintMain();
                    ImGui.endTabItem();
                }

                ImGui.endTabBar();
            }
        } else {
            ImGui.label("Log into the game first!");
        }
    }

    private void PaintMain() {
        if (!startRoutine) {
            logSelected = ImGui.combo("Logs##LogSelectCombo", LogModel.GetLogListToCombo(), logSelected);
            if (logChosen == null || !logChosen.Name.equals(LogModel.GetLogListToCombo()[logSelected]))
                logChosen = LogModel.GetLogList().get(logSelected);

            // @TODO! Make this related to the mine and not the log.
            String[] bankOptions = ManualLocationModel.GetLocationListByTypeToCombo(logChosen.DepositType);
            if (logSelected != oldLogSelected) {
                depositSelected = Helper.GetIndexOf(bankOptions, logChosen.ClosestDeposit);
                oldLogSelected = logSelected;
            }

            locationSelected = ImGui.combo("Location##LocationSelectCombo", logChosen.LocationList, locationSelected);
            if (locationChosen == null || !locationChosen.Name.equals(logChosen.LocationList[locationSelected]))
                locationChosen = ManualLocationModel.GetLocationByName(logChosen.LocationList[locationSelected]);

            if (bankOptions.length > 0) {
                depositSelected = ImGui.combo("Deposit##DepositSelectCombo", bankOptions, depositSelected);
                depositChosen = ManualLocationModel.GetLocationListByType(logChosen.DepositType).get(depositSelected);
            }
        } else {
            ImGui.label(MessageFormat.format("Currently mining: {0}", logChosen.Name));
        }

        if (TeleportHandler.Get(locationChosen.Teleport) != null) {
            ImGui.label(MessageFormat.format("Teleport unlocked: {0} ({1})", locationChosen.Teleport, TeleportHandler.Get(locationChosen.Teleport).isAvailable() ));
        }

        if (logChosen.Requirements != null && logChosen.Requirements.size() > 0) {
            ImGui.label("Log Requirements:");
            for (RequirementModel req : logChosen.Requirements){
                if (req.Type.equals("Level"))
                    ImGui.label(MessageFormat.format("{0} Lv{1}", req.Param1, req.ThresholdInt));
            }
            ImGui.label("");
        }

        if (locationChosen.Requirements != null && locationChosen.Requirements.size() > 0) {
            ImGui.label("Location Requirements:");
            for (RequirementModel req : locationChosen.Requirements){
                if (req.Type.equals("Level"))
                    ImGui.label(MessageFormat.format("{0} Lv{1}", req.Param1, req.ThresholdInt));
            }
        }


        // @TODO!
        // Change log requirements to reflect their own requirements and not the mine's.
        ImGui.label("");
        powerWoodcutting = ImGui.checkbox("Drop items instead of depositing.", powerWoodcutting);
//        levelMode = ImGui.checkbox("Leveling mode.", levelMode);
        ImGui.label("");

        if (logChosen.Requirements != null && RequirementHandler.LevelsMet(logChosen.Requirements) || startRoutine) {
            String toggleRoutineText = startRoutine ? "Stop Routine" : "Start Routine";
            if (ImGui.button(toggleRoutineText)) {
                startRoutine = !startRoutine;
                ManualMovementHandler.movementIndex = 0;
            }
        } else {
            ImGui.label("Your level is too low for this log.");
        }
    }

    @Override
    public void onPaintOverlay() {
        if (drawLocationChosen && locationChosen != null && locationChosen.Path.size() > 1) {
            for (int i = 0; i < locationChosen.Path.get(0).Coordinates.size()-1; i++) {
                Vector3 drawLine = locationChosen.Path.get(0).Coordinates.get(i).toScene();
                Vector3 drawLineNext = locationChosen.Path.get(0).Coordinates.get(i+1).toScene();
                if (drawLineNext != null) {
                    ImGui.freeLine(Client.worldToScreen(drawLine), Client.worldToScreen(drawLineNext), 0xff0000ff);
                }
            }
        }

        if (drawDepositChosen && depositChosen != null) {
            for (int i = 0; i < depositChosen.Path.get(0).Coordinates.size()-1; i++) {
                Vector3 drawLine = depositChosen.Path.get(0).Coordinates.get(i).toScene();
                Vector3 drawLineNext = depositChosen.Path.get(0).Coordinates.get(i+1).toScene();
                if (drawLineNext != null) {
                    ImGui.freeLine(Client.worldToScreen(drawLine), Client.worldToScreen(drawLineNext), 0xff0000ff);
                }
            }
        }
    }
}