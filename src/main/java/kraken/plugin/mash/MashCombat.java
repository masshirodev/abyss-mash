package kraken.plugin.mash;

import javafx.util.Pair;
import kraken.plugin.api.*;
import models.EnemyModel;
import models.ManualLocationModel;
import models.RequirementModel;
import modules.*;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class MashCombat extends Plugin {
    public static boolean startRoutine = false;
    private static EnemyModel enemyChosen;
    private static List<EnemyModel> enemiesAvailable = EnemyModel.GetEnemyList();
    private static String[] comboOptions = EnemyModel.GetEnemyListToCombo();
    private static int comboSelected = 0;
    private static int newComboSelected = 0;
    private static ArrayList<Boolean> dropsEnabled;

    public MashCombat() {
    }

    @Override
    public boolean onLoaded(PluginContext pluginContext) {
        pluginContext.setName("MashCombat");
        return true;
    }

    @Override
    public int onLoop() {
        if (Players.self() == null) return 1000;


        if (startRoutine)
            CombatRoutine();

        return 1000;
    }

    public void CombatRoutine() {
        if (Inventory.isFull()) {
            ManualMovementHandler.GoTo(ManualLocationModel.GetLocationByName(enemyChosen.closestDeposit));
        } else {
            Npc[] enemies = Npcs.all(x -> enemyChosen.enemyId.contains(x.getId()));

            if (enemies.length > 0)
                EnemyHandler.Execute(enemyChosen, enemies);
            else
                ManualMovementHandler.GoTo(ManualLocationModel.GetLocationByName(enemyChosen.location));
        }
    }

    @Override
    public void onPaint() {
        ImGui.label("");
        ImGui.label("MashCombat");
        ImGui.label("");

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

        ImGui.label("");
        ImGui.label(MessageFormat.format("Location: {0}", enemyChosen.location));
        ImGui.label(MessageFormat.format("Deposit: {0}", enemyChosen.closestDeposit));
        if (TeleportHandler.Get(enemyChosen.teleport) != null) {
            ImGui.label(MessageFormat.format("Teleport unlocked: {0} ({1})", enemyChosen.teleport, TeleportHandler.Get(enemyChosen.teleport).isAvailable() ));
        }


        if (enemyChosen.requirements.size() > 0) {
            ImGui.label("");
            ImGui.label("Requirements:");
            for (RequirementModel req : enemyChosen.requirements){
                if (req.Type == "Level")
                    ImGui.label(MessageFormat.format("{0} Lv{1}", req.Param1, req.ThresholdInt));
            }
        }

        if (enemyChosen.drops.length > 0) {
            ImGui.label("Drops");
            int index = 0;
            for (Pair drop : enemyChosen.drops) {
                dropsEnabled.set(index, ImGui.checkbox(MessageFormat.format("{0}##ToggleDrop{1}", drop.getKey().toString(), drop.getValue()), dropsEnabled.get(index)));
                index++;
            }
        }

        ImGui.label("");

        if (enemyChosen.requirements != null && RequirementHandler.LevelsMet(enemyChosen.requirements) || startRoutine) {
            String toggleRoutineText = startRoutine ? "Stop Routine" : "Start Routine";
            if (ImGui.button(toggleRoutineText))
                startRoutine = !startRoutine;
        } else
            ImGui.label("Your level is too low to get to this enemy.");
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