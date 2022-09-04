package kraken.plugin.mash;

import enums.LocationType;
import helpers.Helper;
import helpers.Log;
import kraken.plugin.api.*;
import models.ManualLocationModel;
import models.OreModel;
import models.RequirementModel;
import modules.*;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;

public final class MashMining extends Plugin {
    private static boolean oreBoxIsFull;
    private static WidgetItem oreBox;
    private static ArrayList<Integer> oreBoxIds = new ArrayList<>(Arrays.asList(
            44797, 44795, 44793, 44791, 44789, 44787, 44785, 44783, 44781, 44779
    ));
    public static boolean withdrawOreBox = false;
    public static ManualLocationModel defaultBank = ManualLocationModel.GetLocationByName("Burthorpe Bank");
    public static boolean startRoutine = false;
    private static OreModel oreChosen;
    private static ManualLocationModel mineChosen;
    private static ManualLocationModel depositChosen;
    private static int oreSelected = 0;
    private static int mineSelected = 0;
    private static int oldOreSelected = 0;
    private static int depositSelected = 0;
    private static boolean powerMining = false;
    private static boolean currentlyDroppingItems = false;
    private static boolean drawMineChosen = false;
    private static boolean drawDepositChosen = false;

    public MashMining() {
    }

    @Override
    public boolean onLoaded(PluginContext pluginContext) {
        pluginContext.setName("MashMining v3");
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
        if (withdrawOreBox && Inventory.first(x -> oreBoxIds.contains(x.getId())) == null) {
            WithdrawOreBox();
            return;
        }

        if (Inventory.isFull() || currentlyDroppingItems) {
            if (powerMining && !currentlyDroppingItems) currentlyDroppingItems = true;
            oreBox = Inventory.first(x -> oreBoxIds.contains(x.getId()));

            if (powerMining && currentlyDroppingItems) {
                WidgetItem toDrop = Inventory.first(x -> Arrays.stream(OreModel.GetOreListToCombo()).anyMatch(y -> x.getName().equalsIgnoreCase(y)));
                if (toDrop != null) {
                    InventoryHandler.DropItem(toDrop);
                    Log.Information(MessageFormat.format("Dropping [{0}]", toDrop.getName()));
                    return;
                }

                currentlyDroppingItems = false;
                return;
            }

            if (oreBox != null && !oreBoxIsFull) {
                FillOreBox();
                Helper.Wait(4000);
            }

            if (Inventory.isFull()) {
                Log.Information("Inventory is still full.");
                oreBoxIsFull = true;
            }

            if (oreBoxIsFull || oreBox == null) {
                if (BankHandler.BankNearby(oreChosen.DepositType)) {
                    BankHandler.DecideDeposit(oreChosen.DepositType, (x -> !oreBoxIds.contains(x.getId())));
                    return;
                }

                ManualMovementHandler.GoTo(depositChosen);
            }
        } else {
            oreBoxIsFull = false;
            SceneObject[] ores = SceneObjects.all(x -> oreChosen.ObjectId.contains(x.getId()));
            boolean insideMine = mineChosen.Area.contains(Players.self().getGlobalPosition());

            if (ores.length > 0 && insideMine)
                MiningHandler.Execute(oreChosen, ores);
            else
                ManualMovementHandler.GoTo(mineChosen);
        }
    }

    private void WithdrawOreBox() {
        if (BankHandler.BankNearby(LocationType.Bank)) {
            BankHandler.WithdrawFirst(x -> oreBoxIds.contains(x.getId()), 1);
            return;
        }

        ManualMovementHandler.GoTo(defaultBank);
    }

    public void FillOreBox() {
        Log.Information("Filling ore box.");

        if (oreBox != null)
            oreBox.interact(1);
    }

    @Override
    public void onPaint() {
        ImGui.label("");
        ImGui.label("MashMining v3");
        ImGui.label("");
        if (Players.self() != null) {
            if (ImGui.beginTabBar("MainTabs")) {
                if (ImGui.beginTabItem("Main")) {
                    PaintMain();
                    ImGui.endTabItem();
                }

//                if (ImGui.beginTabItem("Debug")) {
//                    PaintDebug();
//                    ImGui.endTabItem();
//                }

                ImGui.endTabBar();
            }
        } else {
            ImGui.label("Log into the game first!");
        }
    }

    private void PaintMain() {
        if (!startRoutine) {
            oreSelected = ImGui.combo("Ores##OreSelectCombo", OreModel.GetOreListToCombo(), oreSelected);
            if (oreChosen == null || !oreChosen.Name.equals(OreModel.GetOreListToCombo()[oreSelected]))
                oreChosen = OreModel.GetOreList().get(oreSelected);

            String[] bankOptions = ManualLocationModel.GetLocationListByTypeToCombo(oreChosen.DepositType);
            // @TODO! Make this related to the mine and not the ore.
            if (oreSelected != oldOreSelected) {
                depositSelected = Helper.GetIndexOf(bankOptions, oreChosen.ClosestDeposit);
                oldOreSelected = oreSelected;
            }

            mineSelected = ImGui.combo("Mine##MineSelectCombo", oreChosen.MineList, mineSelected);
            if (mineChosen == null || !mineChosen.Name.equals(oreChosen.MineList[mineSelected]))
                mineChosen = ManualLocationModel.GetLocationByName(oreChosen.MineList[mineSelected]);

            if (bankOptions.length > 0) {
                depositSelected = ImGui.combo("Deposit##DepositSelectCombo", bankOptions, depositSelected);
                depositChosen = ManualLocationModel.GetLocationListByType(oreChosen.DepositType).get(depositSelected);
            }
        } else {
            ImGui.label(MessageFormat.format("Currently mining: {0}", oreChosen.Name));
        }

        ImGui.label("");
        ImGui.label(MessageFormat.format("Recommended Deposit: {0}", oreChosen.ClosestDeposit));

        if (TeleportHandler.Get(mineChosen.Teleport) != null) {
            ImGui.label(MessageFormat.format("Teleport unlocked: {0} ({1})", mineChosen.Teleport, TeleportHandler.Get(mineChosen.Teleport).isAvailable() ));
        }

        if (oreChosen.Requirements != null && oreChosen.Requirements.size() > 0) {
            ImGui.label("Ore Requirements:");
            for (RequirementModel req : oreChosen.Requirements){
                if (req.Type.equals("Level"))
                    ImGui.label(MessageFormat.format("{0} Lv{1}", req.Param1, req.ThresholdInt));
            }
            ImGui.label("");
        }

        if (mineChosen.Requirements != null && mineChosen.Requirements.size() > 0) {
            ImGui.label("Mine Requirements:");
            for (RequirementModel req : mineChosen.Requirements){
                if (req.Type.equals("Level"))
                    ImGui.label(MessageFormat.format("{0} Lv{1}", req.Param1, req.ThresholdInt));
                if (req.Type.equals("Quest"))
                    ImGui.label(MessageFormat.format("Quest: {0}", req.Param1));
            }
        }


        // @TODO!
        // Change ore requirements to reflect their own requirements and not the mine's.
        ImGui.label("");
        withdrawOreBox = ImGui.checkbox("Withdraw ore box.", withdrawOreBox);
        powerMining = ImGui.checkbox("Drop items instead of depositing.", powerMining);
//        levelMode = ImGui.checkbox("Leveling mode.", levelMode);
        ImGui.label("");

        if (oreChosen.Requirements != null && RequirementHandler.LevelsMet(oreChosen.Requirements) || startRoutine) {
            String toggleRoutineText = startRoutine ? "Stop Routine" : "Start Routine";
            if (ImGui.button(toggleRoutineText)) {
                startRoutine = !startRoutine;
            }
        } else {
            ImGui.label("Your level is too low for this ore.");
        }
    }

    @Override
    public void onPaintOverlay() {
    }

    private void PaintDebug() {
        ImGui.label(MessageFormat.format("Current Inside Area: {0}", ManualMovementHandler.GetCurrentArea() != null));
        if (ManualMovementHandler.GetCurrentArea() != null)
            ImGui.label(MessageFormat.format("Current Inside Area Name: {0}", ManualMovementHandler.GetCurrentArea().Name));

        drawMineChosen = ImGui.checkbox("Draw mine path.", drawMineChosen);
        drawDepositChosen = ImGui.checkbox("Draw deposit path.", drawDepositChosen);

        if (ImGui.button("Debug1")) {

        }
        if (ImGui.button("Debug2")) {
            SceneObjects.closest(x -> x.getId() == 2230).interact(Actions.MENU_EXECUTE_OBJECT1);
        }
        if (ImGui.button("Debug3")) {
            SceneObjects.closest(x -> x.getId() == 2230).interact(Actions.MENU_EXECUTE_OBJECT2);
        }
        if (ImGui.button("Debug4")) {
            SceneObjects.closest(x -> x.getId() == 2230).interact(Actions.MENU_EXECUTE_OBJECT3);
        }
        if (ImGui.button("Debug5")) {
            SceneObjects.closest(x -> x.getId() == 2230).interact(Actions.MENU_EXECUTE_OBJECT4);
        }

        if (ImGui.button("Debug - interact")) {
            SceneObject[] forge = SceneObjects.all(x -> x.getId() == 120050);
            Vector3 position = forge[0].getScenePosition();
            if (position.distance(Players.self().getScenePosition()) > 5000)
                Move.to(position.toTile());
            else
                forge[0].interact(Actions.MENU_EXECUTE_OBJECT2);
        }

        if (ImGui.button("Debug - deposit")) {
            Bank.deposit((item) -> true, 7);
        }

        if (Players.self() != null) {
            ImGui.label("");
            ImGui.label(MessageFormat.format("AnimationId: {0}", Players.self().getAnimationId()));
            ImGui.label(MessageFormat.format("Adrenaline: {0} (Threshold: {1})", Players.self().getAdrenaline(), Players.self().getAdrenaline() < 0.2));
            ImGui.label(MessageFormat.format("Inventory is full: {0}", Inventory.isFull()));
            ImGui.label(MessageFormat.format("Is Bank open: {0}", Widgets.isOpen(517)));
            if (SceneObjects.all(x -> x.getId() == 6226).length > 0)
                ImGui.label(MessageFormat.format("Distance to Inside Stair: {0}", SceneObjects.all(x -> x.getId() == 6226)[0].getScenePosition().distance(Players.self().getScenePosition())));
            if (SceneObjects.all(x -> x.getId() == 2113).length > 0)
                ImGui.label(MessageFormat.format("Distance to Outside Stair: {0}", SceneObjects.all(x -> x.getId() == 2113)[0].getScenePosition().distance(Players.self().getScenePosition())));
            ImGui.label("");
            ImGui.label("Ores detected:");

            SceneObject[] ores = SceneObjects.all(x -> oreChosen.ObjectId.contains(x.getId()));

            if (ores.length > 0) {
                ImGui.beginChild("OresFoundFrame");
                for (SceneObject ore : ores) {
                    ImGui.label(MessageFormat.format("Id: {0}", ore.getId()));
                    ImGui.label(MessageFormat.format("Name: {0}", ore.getInteractId()));
                    ImGui.label(MessageFormat.format("Location: {0}", ore.getSize()));
                    ImGui.label(MessageFormat.format("Level: {0}", ore.getName()));
                    ImGui.label(MessageFormat.format("Coordinates: {0}", ore.toString()));
                    ImGui.label("========================");
                }
                ImGui.endChild();
            }
        }
    }
}