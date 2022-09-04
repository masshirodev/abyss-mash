package kraken.plugin.mash;

import abyss.plugin.api.world.WorldTile;
import kraken.plugin.api.*;
import models.ManualLocationModel;
import modules.ManualMovementHandler;

import java.util.ArrayList;
import java.util.Arrays;

public final class MashCroesus extends Plugin {
    public static boolean startRoutine = false;
    public static String[] options = new String[] { "Hunting", "Fishing", "Mining", "Woodcutting" };
    public static ArrayList<Integer> orderSelected = new ArrayList<Integer>(Arrays.asList(0, 1, 2, 3));
    public static WorldTile startingPos;
    public static WorldTile huntingPos;
    public static WorldTile fishingPos;
    public static WorldTile miningPos;
    public static WorldTile woodcuttingPos;
    public static final ManualLocationModel pathToCroe = ManualLocationModel.GetLocationByName("Croesus Front");
    public static final int gatewayId = 121693;
    public static final int croesusId = 28393;
    public static final int gorvekId = 28394;
    public static final int rottenFungusId = 52312;
    public static final int huntingNode = 28398;
    public static final int huntingItem = 52308;
    public static final int fishingNode = 28399;
    public static final int fishingItem = 52310;
    public static final ArrayList<Integer> miningNodes = new ArrayList<Integer>(Arrays.asList(121445, 121438, 121443));
    public static final int miningItem = 52306;
    public static final ArrayList<Integer> woodcuttingNodes = new ArrayList<Integer>(Arrays.asList(121460, 121453, 121458));
    public static final int woodcuttingItem = 52304;
    public static final int dialogueWidgetId = 77856781;
    public static final int startFightWidgetId = 0000000;
    public static final int rewardChestId = 121745;
    public static final ArrayList<Integer> statueIds = new ArrayList<Integer>(Arrays.asList(121445, 121438, 121443));

    public MashCroesus() {
    }

    @Override
    public boolean onLoaded(PluginContext pluginContext) {
        pluginContext.setName("MashCroesus");
        return true;
    }

    @Override
    public int onLoop() {
        if (Players.self() == null) return 1000;

        if (startRoutine)
            croesusRoutine();

        return 1000;
    }

    public void setLocations() {
        if (startingPos != null) {
            startingPos = (WorldTile) Players.self().getGlobalPosition();

            huntingPos = new WorldTile(startingPos.getX() + 11, startingPos.getY() + 19, 0); // NW
            woodcuttingPos = new WorldTile(startingPos.getX() + 11, startingPos.getY() - 19, 0); // SW
            fishingPos = new WorldTile(startingPos.getX() + 49, startingPos.getY() + 19, 0); // NE
            miningPos = new WorldTile(startingPos.getX() + 49, startingPos.getY() - 19, 0); // SE
        }
    }

    public Npc GetCroesus() {
        return Npcs.closest(x -> x.getId() == croesusId);
    }

    public Npc GetGorvek() {
        return Npcs.closest(x -> x.getId() == gorvekId);
    }

    public SceneObject GetClosestStatue() {
        return SceneObjects.closest(x -> statueIds.contains(x.getId()));
    }

    public void croesusRoutine() {
        if (GetCroesus() == null) {
            ManualMovementHandler.GoTo(pathToCroe);
            return;
        }


    }

    @Override
    public void onPaint() {
        ImGui.label("");
        ImGui.label("MashCroesus");
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
    }

    @Override
    public void onPaintOverlay() {
//        if (drawMineChosen && mineChosen != null && mineChosen.Path.size() > 1) {
//            for (int i = 0; i < mineChosen.Path.get(0).Coordinates.size()-1; i++) {
//                Vector3 drawLine = mineChosen.Path.get(0).Coordinates.get(i).toScene();
//                Vector3 drawLineNext = mineChosen.Path.get(0).Coordinates.get(i+1).toScene();
//                if (drawLineNext != null) {
//                    ImGui.freeLine(Client.worldToScreen(drawLine), Client.worldToScreen(drawLineNext), 0xff0000ff);
//                }
//            }
//        }
//
//        if (drawDepositChosen && depositChosen != null) {
//            for (int i = 0; i < depositChosen.Path.get(0).Coordinates.size()-1; i++) {
//                Vector3 drawLine = depositChosen.Path.get(0).Coordinates.get(i).toScene();
//                Vector3 drawLineNext = depositChosen.Path.get(0).Coordinates.get(i+1).toScene();
//                if (drawLineNext != null) {
//                    ImGui.freeLine(Client.worldToScreen(drawLine), Client.worldToScreen(drawLineNext), 0xff0000ff);
//                }
//            }
//        }
    }
}