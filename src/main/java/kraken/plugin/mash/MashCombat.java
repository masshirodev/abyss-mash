package kraken.plugin.mash;

import abyss.plugin.api.input.InputHelper;
import abyss.plugin.api.variables.Variables;
import com.google.common.base.Stopwatch;
import enums.KeyboardKey;
import helpers.Helper;
import helpers.Log;
import javafx.util.Pair;
import kraken.plugin.api.*;
import models.EnemyModel;
import models.LocationModel;
import models.RequirementModel;
import modules.*;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public final class MashCombat extends Plugin {
    private static PluginContext context;
    private static boolean settingsLoaded = false;
    private static boolean startRoutine = false;
    private static int loopDelay = 1000;
    private static EnemyModel enemyChosen;
    private static List<EnemyModel> enemiesAvailable = EnemyModel.GetEnemyList();
    private static String[] comboOptions = EnemyModel.GetEnemyListToCombo();
    private static int comboSelected = 0;
    private static int newComboSelected = 0;
    private static ArrayList<Boolean> dropsEnabled;
    public static boolean enableOverloads = false;
    public static boolean enableAggression = false;
    public static boolean enablePrayerRenew = false;
    private static int usePrayerRenewValue = 20;
    public static boolean enableFood = false;
    public static int useFoodPercentage = 20;
    public static boolean enablePrayerUsage = false;
    public static int maxHealth = Variables.MAX_HEALTH_AMOUNT.getValue();

    private static int consumableCooldown = 2;
    private static Stopwatch consumableTimer = Stopwatch.createStarted();
    private static int foodKeybind = 1;
    private static int overloadKeybind = 2;
    private static int prayerKeybind = 3;
    private static int aggressionKeybind = 4;
    private static int customDropIdInput = 0;
    public static ArrayList<Integer> customDropIds = new ArrayList<Integer>();

    public MashCombat() { }

    @Override
    public boolean onLoaded(PluginContext pluginContext) {
        if (context == null)
            context = pluginContext;

        pluginContext.setName("MashCombat");
//        pluginContext.category = "Mash";

        load(context);
        return true;
    }

    private void HandlePersistentData() {
        Log.Information("Loading settings from last session.");

        startRoutine = getBoolean("startRoutine") | startRoutine;

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
        if (Client.getState() != Client.IN_GAME && !Client.isLoading()) return 1000;
        if (!settingsLoaded) HandlePersistentData();
        UpdatePersistentData();

        if (startRoutine)
            Routine();

        return loopDelay;
    }

    public void Routine() {
        ExecuteConsumables();

        if (Inventory.isFull()) {
            MovementHandler.GoTo(LocationModel.GetLocationByName(enemyChosen.closestDeposit));
        } else {
            Npc[] enemies = Npcs.all(x -> enemyChosen.enemyId.contains(x.getId()));

            if (enemies.length > 0)
                EnemyHandler.Execute(enemyChosen, enemies);
            else
                MovementHandler.GoTo(LocationModel.GetLocationByName(enemyChosen.location));
        }
    }

    private void ExecuteConsumables() {
        if (consumableTimer.elapsed(TimeUnit.SECONDS) < consumableCooldown) return;

        Player player = Players.self();
        if (enableFood && maxHealth < Helper.PercentOf(useFoodPercentage, maxHealth)) {
            InputHelper.pressKey(KeyboardKey.GetByIndex(foodKeybind));
            consumableTimer.reset().start();
            return;

//            WidgetItem food = ConsumableModel.GetFirstInInventoryOfType(ConsumableType.Health);
//            if (food != null) {
//                InventoryHandler.Interact(food, 1);
//                consumableTimer.reset().start();
//                return;
//            }
        }

//        if (enableOverloads && ) {
//            WidgetItem overload = ConsumableModel.GetFirstInInventoryOfType(ConsumableType.Overload);
//            if (overload != null) {
//                InventoryHandler.Interact(overload, 1);
//                consumableTimer.reset().start();
//                return;
//            }
//        }
//
//        if (enableAggression && ) {
//            WidgetItem aggression = ConsumableModel.GetFirstInInventoryOfType(ConsumableType.Aggression);
//            if (aggression != null) {
//                consumableTimer.reset().start();
//                InventoryHandler.Interact(aggression, 1);
//                return;
//            }
//        }

        if (enablePrayerRenew && Variables.MAX_HEALTH_AMOUNT.getValue() < usePrayerRenewValue) {
            InputHelper.pressKey(KeyboardKey.GetByIndex(prayerKeybind));
            consumableTimer.reset().start();
            return;

//            WidgetItem prayer = ConsumableModel.GetFirstInInventoryOfType(ConsumableType.Prayer);
//            if (prayer != null) {
//                InventoryHandler.Interact(prayer, 1);
//                consumableTimer.reset().start();
//                return;
//            }
        }
    }

    @Override
    public void onPaint() {
        if (ImGui.beginTabBar("MainTabs")) {
            if (ImGui.beginTabItem("Main")) {
                PaintMain();
                ImGui.endTabItem();
            }

            if (ImGui.beginTabItem("Debug")) {
                PaintDebug();
                ImGui.endTabItem();
            }

            ImGui.endTabBar();
        }
    }

    private void PaintMain() {
        ImGui.columns("##CombatMainColumns", 2, false);
        ImGui.beginChild("##CombatMainFrame", true);

        if (!startRoutine) {
            comboSelected = ImGui.combo("Enemies##EnemySelectCombo", comboOptions, comboSelected);
            if (comboSelected != newComboSelected || dropsEnabled == null) {
                enemyChosen = enemiesAvailable.get(comboSelected);
                dropsEnabled = new ArrayList<Boolean>();
                for (int i = 0; i < enemyChosen.drops.length; i++) {
                    dropsEnabled.add(false);
                }
                newComboSelected = comboSelected;
            }

        } else
            ImGui.label(MessageFormat.format("Currently hunting: {0}", enemyChosen.name));

        ImGui.newLine();
        ImGui.label(MessageFormat.format("Location: {0}", enemyChosen.location));
        ImGui.label(MessageFormat.format("Deposit: {0}", enemyChosen.closestDeposit));
        if (TeleportHandler.Get(enemyChosen.teleport) != null) {
            ImGui.label(MessageFormat.format("Teleport unlocked: {0} ({1})", enemyChosen.teleport, TeleportHandler.Get(enemyChosen.teleport).isAvailable() ));
        }

        if (enemyChosen.requirements.size() > 0) {
            ImGui.newLine();
            ImGui.label("Requirements:");
            for (RequirementModel req : enemyChosen.requirements){
                if (req.Type.equals("Level"))
                    ImGui.label(MessageFormat.format("{0} Lv{1}", req.Param1, req.ThresholdInt));
            }
        }

        ImGui.newLine();

        if (enemyChosen.requirements != null && RequirementHandler.LevelsMet(enemyChosen.requirements) || startRoutine) {
            String toggleRoutineText = startRoutine ? "Stop Routine" : "Start Routine";
            if (ImGui.button(toggleRoutineText))
                startRoutine = !startRoutine;
        } else
            ImGui.label("Your level is too low to get to this enemy.");

        ImGui.endChild();
        ImGui.nextColumn();
        ImGui.beginChild("##CombatSecondaryFrame", true);

        // Food
        enableFood = ImGui.checkbox("##CombatEnableFoodCheckbox", enableFood);
        ImGui.sameLine();
        ImGui.beginChild("##FoodComboExtremeWorkaround", 150, 19, false);
        foodKeybind = ImGui.combo("##FoodKeybindCombo", KeyboardKey.GetKeyListToCombo(), foodKeybind);
        ImGui.endChild();
        ImGui.sameLine();
        ImGui.label("Use food when HP is below % ");
        ImGui.sameLine();
        ImGui.beginChild("##FoodInputExtremeWorkaround", 120, 19, false);
        useFoodPercentage = ImGui.intInput("##UseFoodPercentageValue", useFoodPercentage);
        ImGui.endChild();

        // Overloads
//        enableOverloads = ImGui.checkbox("##CombatEnableOverloadsCheckbox", enableOverloads);
//        ImGui.sameLine();
//        ImGui.beginChild("##OverloadsComboExtremeWorkaround", 150, 19, false);
//        overloadKeybind = ImGui.combo("##OverloadsKeybindCombo", KeyboardKey.GetKeyListToCombo(), overloadKeybind);
//        ImGui.endChild();
//        ImGui.sameLine();
//        ImGui.label("Overloads");

        // Prayer
//        enablePrayerUsage = ImGui.checkbox("Prayer##CombatEnablePrayerCheckbox", enablePrayerRenew);
        enablePrayerRenew = ImGui.checkbox("##CombatEnablePrayerRenewCheckbox", enablePrayerRenew);
        ImGui.sameLine();
        ImGui.beginChild("##PrayerRenewComboExtremeWorkaround", 150, 19, false);
        prayerKeybind = ImGui.combo("##PrayerRenewKeybindCombo", KeyboardKey.GetKeyListToCombo(), prayerKeybind);
        ImGui.endChild();
        ImGui.sameLine();
        ImGui.label("Renew Prayer when below ");
        ImGui.sameLine();
        ImGui.beginChild("##PrayerRenewInputExtremeWorkaround", 120, 19, false);
        usePrayerRenewValue = ImGui.intInput("##PrayerRenewPercentageValue", usePrayerRenewValue);
        ImGui.endChild();

        // Aggression
//        enableAggression = ImGui.checkbox("##CombatEnableAggressionCheckbox", enableAggression);
//        ImGui.sameLine();
//        ImGui.beginChild("##AggressionComboExtremeWorkaround", 150, 19, false);
//        aggressionKeybind = ImGui.combo("##AggressionKeybindCombo", KeyboardKey.GetKeyListToCombo(), aggressionKeybind);
//        ImGui.endChild();
//        ImGui.sameLine();
//        ImGui.label("Aggression");

        // Drops
        if (enemyChosen.drops.length > 0) {
            ImGui.newLine();
            ImGui.label("Drops");
            int index = 0;
            for (Pair drop : enemyChosen.drops) {
                dropsEnabled.set(index, ImGui.checkbox(MessageFormat.format("{0}##ToggleDrop{1}", drop.getKey().toString(), drop.getValue()), dropsEnabled.get(index)));
                index++;
            }

            ImGui.newLine();
        }

        ImGui.label("Custom Ids");
        ImGui.beginChild("##CustomDropIdOneInputArea", 150, 19, false);
        customDropIdInput = ImGui.intInput("##CustomDropIdOneInput", customDropIdInput);
        ImGui.endChild();
        ImGui.sameLine();
        if (ImGui.button("Add##AddCustomDropId")) {
            if (customDropIdInput != 0)
                customDropIds.add(customDropIdInput);
        }

        if (customDropIds.size() > 0) {
            for (int i = 0; i < customDropIds.size(); i++) {
                int val = customDropIds.get(i);
                ImGui.label(String.valueOf(val));
                ImGui.sameLine();
                if (ImGui.button(MessageFormat.format("Remove##RemoveCustomDropId{0}{1}", i, val))) {
                    customDropIds.remove(i);
                }
            }
        }


        ImGui.endChild();
        ImGui.columns("", 1, false);
    }

    private void PaintDebug() {
        if (ImGui.button("Debug1")) {
            GroundItem drop = GroundItems.closest(x -> Arrays.stream(enemyChosen.drops).anyMatch(y -> x.getId() == y.getValue()));
            GroundItemHandler.PickItem(drop);
        }
        if (ImGui.button("Debug2")) {
            Inventory.first(x -> true).interact(Actions.MENU_EXECUTE_OBJECT2);
        }
        if (ImGui.button("Debug3")) {
            Inventory.first(x -> true).interact(Actions.MENU_EXECUTE_PLAYER2);
        }
        if (ImGui.button("Debug4")) {
            Inventory.first(x -> true).interact(Actions.MENU_EXECUTE_GROUND_ITEM2);
        }
        if (ImGui.button("Debug5")) {
            Inventory.first(x -> true).interact(Actions.MENU_EXECUTE_NPC2);
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
            ImGui.newLine();
            ImGui.label(MessageFormat.format("AnimationId: {0}", Players.self().getAnimationId()));
            ImGui.label(MessageFormat.format("Adrenaline: {0} (Threshold: {1})", Players.self().getAdrenaline(), Players.self().getAdrenaline() < 0.2));
            ImGui.label(MessageFormat.format("Inventory is full: {0}", Inventory.isFull()));
            ImGui.label(MessageFormat.format("Is Bank open: {0}", Widgets.isOpen(517)));
            if (SceneObjects.all(x -> x.getId() == 6226).length > 0)
                ImGui.label(MessageFormat.format("Distance to Inside Stair: {0}", SceneObjects.all(x -> x.getId() == 6226)[0].getScenePosition().distance(Players.self().getScenePosition())));
            if (SceneObjects.all(x -> x.getId() == 2113).length > 0)
                ImGui.label(MessageFormat.format("Distance to Outside Stair: {0}", SceneObjects.all(x -> x.getId() == 2113)[0].getScenePosition().distance(Players.self().getScenePosition())));
            ImGui.newLine();
            ImGui.label("Ores detected:");

            SceneObject[] ores = SceneObjects.all(x -> enemyChosen.enemyId.contains(x.getId()));

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