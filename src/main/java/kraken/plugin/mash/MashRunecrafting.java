package kraken.plugin.mash;

import abyss.plugin.api.input.InputHelper;
import abyss.plugin.api.variables.VariableManager;
import com.google.common.base.Stopwatch;
import enums.LocationType;
import enums.World;
import helpers.Engine;
import helpers.Helper;
import helpers.Log;
import helpers.Skills;
import kotlin.Triple;
import kraken.plugin.api.*;
import models.ManualLocationModel;
import models.RuneModel;
import modules.BankHandler;
import modules.EquipmentHandler;
import modules.InventoryHandler;
import modules.ManualMovementHandler;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public final class MashRunecrafting extends Plugin {
    public static boolean startRoutine = false;
    public static boolean useWildSword = false;
    private static RuneModel runeChosen;
    private static int runeSelected = 0;
    private static int presetSelected = 1;
    private static boolean enableWildSwordUsage = true;
    private static Vector3i swordUsagePosition;
    private static Stopwatch swordUsageTimeout = Stopwatch.createUnstarted();
    private static final int repairNpcId = 2262;
    private static final ArrayList<Integer> mageIds = new ArrayList<Integer>(Arrays.asList(24241, 24242));
    private static final ArrayList<Integer> creatureIds = new ArrayList<Integer>(Arrays.asList(2264, 2263));
    private static final ManualLocationModel bankLocation = ManualLocationModel.GetLocationByName("Edgeville Bank");
    private static final ManualLocationModel innerCircleArea = ManualLocationModel.GetLocationByName("Abyss Inner Circle");
    private static final ArrayList<Integer> degradedPouchIds = new ArrayList<Integer>(Arrays.asList(5511, 5513, 5515, 24204));
    private static final ArrayList<Integer> wildSwords = new ArrayList<Integer>(Arrays.asList(37904, 37905, 37906, 37907));
    private static final ArrayList<Triple<Integer, Integer, Integer>> pouchs = new ArrayList<Triple<Integer, Integer, Integer>>() {
        {
            // Item Id - Items inside ConVar - Cap
            add(new Triple<Integer, Integer, Integer>(5509, 16497, 3));
            add(new Triple<Integer, Integer, Integer>(5510, 16498, 6));
            add(new Triple<Integer, Integer, Integer>(5512, 16499, 9));
            add(new Triple<Integer, Integer, Integer>(5514, 16500, 12));
            add(new Triple<Integer, Integer, Integer>(24205, 16521, 18));
        }
    };
    private static SceneObject innerEntrance;
    private static ArrayList<Integer> innerEntranceBlackList = new ArrayList<>();
    private static Stopwatch innerEntranceTimeout = Stopwatch.createUnstarted();
    private static boolean swapWorlds = false;

    public MashRunecrafting() {
    }

    @Override
    public boolean onLoaded(PluginContext pluginContext) {
        pluginContext.setName("MashRunecrafting v1.05092022b");
        return true;
    }

    @Override
    public int onLoop() {
        if (Players.self() == null) return 1000;

        if (startRoutine)
            Routine();

        return 1000;
    }

    public static void UseWildernessSword() {
        InputHelper.typeKey(0x52);
        Helper.Wait(5000);
    }

    public static boolean CheckForWildernessSword() {
        if (EquipmentHandler.IsAnyEquipped(wildSwords))
            return true;

        WidgetItem inInventory = Inventory.first(x -> wildSwords.contains(x.getId()));
        return inInventory != null;
    }

    public void Routine() {
        Player player = Players.self();

        // RIP
        SceneObject deathHourglass = SceneObjects.closest(x -> x.getId() == 96782);

        if (deathHourglass != null) {
            Log.Information("Am I... Dead!?!? This is so sad, despacito, play Country Roads.");
            deathHourglass.interact(Actions.MENU_EXECUTE_OBJECT1);
            swapWorlds = true;
            return;
        }

        if (swapWorlds) {
            boolean isF2P = World.IsF2P(Client.getWorld());
            Engine.SwapWorld(World.GetRandomWorld(isF2P ? WorldType.F2P : WorldType.P2P));
            swapWorlds = false;
        }

        if (!Inventory.isFull()) {
            Log.Information("Inventory is not full.");
            if (BankHandler.BankNearby(LocationType.Bank)) {
                Log.Information("A bank was found nearby.");
                enableWildSwordUsage = true;
                swordUsageTimeout = Stopwatch.createUnstarted();
                swordUsagePosition = null;

//                BankHandler.WithdrawFirst(x -> oreBoxIds.contains(x.getId()), 1);
                BankHandler.WithdrawPreset(presetSelected);
                ManualMovementHandler.movementIndex = 1;
                return;
            }

            if (useWildSword) {
                Log.Information("Checking if I can use the wilderness sword.");
                SceneObject altar = SceneObjects.closest(x -> runeChosen.AltarId.contains(x.getId()));
                Log.Information(MessageFormat.format("Altar found: {0}", altar != null));
                Log.Information(MessageFormat.format("Has Sword: {0}", CheckForWildernessSword()));

                if (altar != null && CheckForWildernessSword()) {
                    if (swordUsageTimeout.elapsed(TimeUnit.SECONDS) > 5 && swordUsagePosition.distance(Players.self().getGlobalPosition()) < 20) {
                        Log.Information("[FAILSAFE] Player has not teleported away in 10 seconds, re-enabling the wilderness sword.");
                        enableWildSwordUsage = true;
                        swordUsageTimeout = Stopwatch.createUnstarted();
                        swordUsagePosition = null;
                    }

                    if (enableWildSwordUsage && !Players.self().isAnimationPlaying()) {
                        Log.Information("Requirements met, using the wilderness sword.");
                        if (!swordUsageTimeout.isRunning())
                            swordUsageTimeout.start();

                        swordUsagePosition = Players.self().getGlobalPosition();
                        enableWildSwordUsage = false;
                        Helper.Wait(1000);
                        UseWildernessSword();
                    }

                    return;
                }

                return;
            }

            ManualMovementHandler.GoTo(bankLocation);
            return;
        }

        if (BankHandler.BankNearby(LocationType.Bank)) {
            Log.Information("Trying to fill pouches.");
            FillPouches();
            Helper.Wait(2000);

            if (!Inventory.isFull())
                return;
        }

        if (Widgets.isOpen(382)) {
            Log.Information("Bypassing wilderness PvP warning.");
            Actions.menu(Actions.MENU_EXECUTE_DIALOGUE, 0, -1, 25034765, 0);
            Helper.Wait(1000);
            Actions.menu(Actions.MENU_EXECUTE_DIALOGUE, 0, -1, 25034760, 0);
        }

        Npc mage = Npcs.closest(x -> mageIds.contains(x.getId()) && x.getGlobalPosition().distance(Players.self().getGlobalPosition()) < 15);

        if (mage != null) {
            Log.Information("Mage found, interacting.");
            mage.interact(Actions.MENU_EXECUTE_NPC1);
            mage.interact("Teleport");
            return;
        }

        Npc abyssCreature = Npcs.closest(x -> creatureIds.contains(x.getId()));

        if (abyssCreature != null) {
            Log.Information("Player is inside the abyss.");

            if (innerCircleArea.Area.contains(Players.self())) {
                Log.Information("Player is inside inner circle.");

                if (!player.isUnderAttack()) {
                    Log.Information("Resetting timeouts.");
                    innerEntrance = null;
                    innerEntranceBlackList = new ArrayList<>();
                    innerEntranceTimeout = Stopwatch.createUnstarted();

                    Log.Information("Checking for degraded pouches.");
                    WidgetItem anyDegraded = Inventory.first(x -> degradedPouchIds.contains(x.getId()));

                    if (anyDegraded != null) {
                        Log.Information("Degraded pouches found, repairing.");
                        Npc repairNpc = Npcs.closest(x -> x.getId() == repairNpcId);

                        if (repairNpc != null)
                            repairNpc.interact(Actions.MENU_EXECUTE_NPC2);

                        return;
                    }

                    Log.Information(MessageFormat.format("Looking for a {0} rift.", runeChosen.Name));
                    SceneObject rift = SceneObjects.closest(x -> runeChosen.RiftId.contains(x.getId()));

                    if (rift != null) {
                        Log.Information("Rift found, interacting.");
                        rift.interact(Actions.MENU_EXECUTE_OBJECT1);
                    }

                    return;
                }

                Log.Information("[FAILSAFE] Player is under attack, considering Player inside the outer circle instead.");
            }

            if (!innerCircleArea.Area.contains(Players.self()) || player.isUnderAttack()) {
                Log.Information("Looking for an entrance to the inner circle.");

                if (innerEntrance == null) {
                    innerEntrance = SceneObjects.closest(x ->
                            BuildPassableObjectList().contains(x.getId()) &&
                            !innerEntranceBlackList.contains(x.getId()) &&
                            !x.hidden()
                    );
                }

                if (innerEntrance != null) {
                    if (!innerEntranceTimeout.isRunning()) {
                        Log.Information("Starting timeout timer.");
                        innerEntranceTimeout.start();
                    }

                    if (Players.self().isMoving())
                        innerEntranceTimeout.reset();

                    if (innerEntranceTimeout.elapsed(TimeUnit.SECONDS) > 4) {
                        Log.Information("Threshold met, blacklisting object and looking for another.");
                        if (!innerEntranceBlackList.contains(innerEntrance.getId()))
                            innerEntranceBlackList.add(innerEntrance.getId());
                        innerEntrance = null;
                        innerEntranceTimeout.reset();
                        return;

                    }

                    Log.Information(MessageFormat.format("Entrance found ({0}), interacting.", innerEntrance.getId()));
                    innerEntrance.interact(Actions.MENU_EXECUTE_OBJECT1);
                }

                return;
            }
        }

        Log.Information("Looking for an altar.");
        SceneObject altar = SceneObjects.closest(x -> runeChosen.AltarId.contains(x.getId()));

        if (altar != null) {
            Log.Information("Altar found, interacting.");
            altar.interact(Actions.MENU_EXECUTE_OBJECT1);
            ManualMovementHandler.movementIndex = 0;
            return;
        }

        ManualMovementHandler.GoTo(innerCircleArea);
    }

    private void FillPouches() {
        if (Inventory.isFull()) {
            for (Triple<Integer, Integer, Integer> pouchInfo : pouchs) {
                // Item Id - Items inside ConVar - Cap
                WidgetItem pouch = Inventory.first(x -> x.getId() == pouchInfo.component1());

                if (pouch != null) {
                    int inside = 0;
                    if (pouchInfo.component1() == 24205)
                        inside = Inventory.getVarbitValue(pouch.getSlot(), pouchInfo.component2());
                    else
                        inside = VariableManager.getVarbitById(pouchInfo.component2());

                    if (inside < pouchInfo.component3()) {
                        if (InventoryHandler.GetInventoryWidgetItemQuantity(7936) >= pouchInfo.component3()) {
                            InventoryHandler.Interact(pouch, 1);
                            Helper.Wait(1000);
                            continue;
                        }

                        return;
                    }
                }
            }
        }
    }

    @Override
    public void onPaint() {
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

    private void PaintDebug() {
        ImGui.label("");

        ImGui.label(MessageFormat.format("MINING Level {0} - >= 30 -> {1}", Skills.GetLevel("MINING"), Skills.GetLevel("MINING") >= 30));
        ImGui.label(MessageFormat.format("THIEVING Level {0} - >= 30 -> {1}", Skills.GetLevel("THIEVING"), Skills.GetLevel("THIEVING") >= 30));
        ImGui.label(MessageFormat.format("AGILITY Level {0} - >= 30 -> {1}", Skills.GetLevel("AGILITY"), Skills.GetLevel("AGILITY") >= 30));
        ImGui.label(MessageFormat.format("WOODCUTTING Level {0} - >= 30 -> {1}", Skills.GetLevel("WOODCUTTING"), Skills.GetLevel("WOODCUTTING") >= 30));
        ImGui.label(MessageFormat.format("FIREMAKING Level {0} - >= 30 -> {1}", Skills.GetLevel("FIREMAKING"), Skills.GetLevel("FIREMAKING") >= 30));

        StringBuilder passableValues = new StringBuilder();
        for (int i = 0; i < BuildPassableObjectList().size(); i++) {
            passableValues.append(", ").append(BuildPassableObjectList().get(i));
        }
        ImGui.label(MessageFormat.format("Passable: {0}", passableValues.toString()));

        ImGui.label("");
        ImGui.label("Players.self().getEquipment()");
        ImGui.label("");


        Map<EquipmentSlot, Item> equip = Players.self().getEquipment();
        for (var entry : equip.entrySet()) {
            ImGui.label(MessageFormat.format("{0} - {1}", entry.getValue().getId(), entry.getValue().getName()));
        }

        ImGui.label("");
        ImGui.label("Equipment.getItems()");
        ImGui.label("");

        WidgetItem[] equipBag = Equipment.getItems();
        for (WidgetItem entry : equipBag) {
            ImGui.label(MessageFormat.format("Slot: {0} - Id: {1} - {2}", entry.getSlot(), entry.getId(), entry.getName()));
        }
    }

    public static ArrayList<Integer> BuildPassableObjectList() {
        ArrayList<Integer> result = new ArrayList<Integer>();
//        if (Skills.GetLevel("MINING") >= 30) {
//            result.add(7153);
//            result.add(7143);
//        }
//
//        if (Skills.GetLevel("THIEVING") >= 30) {
//            result.add(7146);
//            result.add(7150);
//        }
//
//        if (Skills.GetLevel("AGILITY") >= 30) {
//            result.add(7149);
//            result.add(7147);
//        }
//
//        if (Skills.GetLevel("WOODCUTTING") >= 30) {
//            result.add(7144);
//            result.add(7152);
//        }
//
//        if (Skills.GetLevel("FIREMAKING") >= 30) {
//            result.add(7145);
//            result.add(7151);
//        }

        result.add(7153);
        result.add(7143);
        result.add(7146);
        result.add(7150);
        result.add(7149);
        result.add(7147);
        result.add(7144);
        result.add(7152);
        result.add(7145);
        result.add(7151);

        result.add(7148);

        Collections.shuffle(result);
        return result;
    }

    private void PaintMain() {
        if (presetSelected == 0 || presetSelected > 9) presetSelected = 1;
        if (!startRoutine) {
            runeSelected = ImGui.combo("Rune##RuneSelectCombo", RuneModel.GetRuneListToCombo(), runeSelected);
            if (runeChosen == null || !runeChosen.Name.equals(RuneModel.GetRuneListToCombo()[runeSelected]))
                runeChosen = RuneModel.GetRuneList().get(runeSelected);

            presetSelected = ImGui.intInput("Preset##InputDesiredPreset", presetSelected);
        } else {
            ImGui.label(MessageFormat.format("Currently crafting: {0}", runeChosen.Name));
        }
        ImGui.label("");

//        if (ImGui.button("Debug")) {
//            Actions.menu(Actions.MENU_EXECUTE_DIALOGUE, 0, -1, 25034760, 0);
//        }
//
//        if (ImGui.button("Debug2")) {
//            EquipmentHandler.Interact(EquipmentSlot.WEAPON, 10);
//        }

//        for (Triple<Integer, Integer, Integer> pouchInfo : pouchs) {
//            ImGui.label(MessageFormat.format("Inside {0}: {1} ({2})",
//                    pouchInfo.component1(),
//                    VariableManager.getVarbitById(pouchInfo.component2()),
//                    VariableManager.getVarbitById(pouchInfo.component2()) == pouchInfo.component3()
//            ));
//        }
//        ImGui.label(MessageFormat.format("Inside Inner: {0}", innerCircleArea.Area.contains(Players.self())));

        useWildSword = ImGui.checkbox("Use the wilderness sword.", useWildSword);
//        if (innerEntranceTimeout != null) {
//            ImGui.label(MessageFormat.format("innerEntranceTimeout: {0}", innerEntranceTimeout.elapsed(TimeUnit.MILLISECONDS)));
//        }

        ImGui.label("");
        ImGui.label("Wilderness Sword: For now, you need to have your sword in the action bar, set up as the R key.");
        ImGui.label("");
        ImGui.label("[IMPORTANT] Turn off the Anti AFK plugin as it presses keys to maintain you logged in,");
        ImGui.label("and it can cause the teleportation to go wrong.");
        ImGui.label("");
        ImGui.label("This plugin does not grab individual items from your bank, instead, ");
        ImGui.label("you gotta make a preset and select it above. Any preset 1-9,");
        ImGui.label("and make sure that the 1-9 buttons appears on screen when you open the bank.");
        ImGui.label("This will be fixed with time...");
        ImGui.label("");
        ImGui.label("Thank you for your support!");
        ImGui.label("");

        String toggleRoutineText = startRoutine ? "Stop Routine" : "Start Routine";
        if (ImGui.button(toggleRoutineText)) {
            startRoutine = !startRoutine;
            enableWildSwordUsage = true;

            if (Inventory.isFull())
                ManualMovementHandler.movementIndex = ManualMovementHandler.GetClosestIndex(innerCircleArea.Path.get(0).Coordinates);
        }
    }
}