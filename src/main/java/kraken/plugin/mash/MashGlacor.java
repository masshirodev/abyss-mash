package kraken.plugin.mash;

import abyss.plugin.api.world.WorldTile;
import helpers.Log;
import kraken.plugin.api.*;
import models.ManualLocationModel;
import models.OreModel;
import modules.ManualMovementHandler;

import java.util.ArrayList;
import java.util.Arrays;

public class MashGlacor extends Plugin {
    private static PluginContext context;
    private static boolean startRoutine = false;
    private static boolean settingsLoaded = false;
    private static int loopDelay = 1000;
    private static int aqueductPortalId = 121338;
    private static int rewardsChest = 111366;
    private static ManualLocationModel glacorFront = ManualLocationModel.GetLocationByName("Arch Glacor Front");
    private static ArrayList<Integer> glacorIds = new ArrayList<Integer>(Arrays.asList(28420, 28421));
    private static ArrayList<Integer> glacorActive = new ArrayList<Integer>(Arrays.asList(28421));
    private static ArrayList<Integer> glacorArms = new ArrayList<Integer>(Arrays.asList(28242, 28243));
    private static ArrayList<Integer> glacorCore = new ArrayList<Integer>(Arrays.asList(28244));
    private static ArrayList<Integer> glacorSheerCold = new ArrayList<Integer>(Arrays.asList(28245));
    private static ArrayList<Integer> glacorAdds = new ArrayList<Integer>(Arrays.asList(28246, 28248));
    private static ArrayList<Integer> glacorUnstableCore = new ArrayList<Integer>(Arrays.asList(28249));
    private static ArrayList<Integer> glacorSpikes = new ArrayList<Integer>(Arrays.asList(121360, 121361, 121362, 121363, 121364));
    private static ArrayList<Integer> glacorLaser = new ArrayList<Integer>(Arrays.asList(7528)); // Effect, tentative
    private static ArrayList<Integer> glacorProjectiles = new ArrayList<Integer>(Arrays.asList());
    private static SceneObject currentArm;

    public MashGlacor() { }

    @Override
    public boolean onLoaded(PluginContext pluginContext) {
        if (context == null)
            context = pluginContext;

        pluginContext.setName("MashGlacor v1.10092022a");
//        pluginContext.category = "Mash";

        load(context);
        return true;
    }

    private void HandlePersistentData() {
        Log.Information("Loading settings from last session.");

        startRoutine = getBoolean("startRoutine");

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
        if (Players.self() == null) return 1000;
        if (!settingsLoaded) HandlePersistentData();
        UpdatePersistentData();

        if (startRoutine)
            Routine();

        return loopDelay;
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

    }

    private void Routine() {
        Player player = Players.self();

        // glacorIds -> if id 28420 -> inside arena, if id 28421 -> fight started.
        // glacorArms -> choose an arm, break it, move away.
        // glacorCore -> ignore ig.
        // glacorSheerCold -> move away from.
        // glacorAdds -> priority kill.
        // glacorUnstableCore -> grab after killing adds.
        // glacorSpikes -> move away from.
        // glacorLaser -> cast resonance with a shield.
        // glacorProjectiles -> move vertically if player close to their Y position.

        SceneObject glacor = SceneObjects.closest(x -> glacorIds.contains(x.getId()));
        if (glacor != null) {
            // handle encounter.
            boolean skip;
            skip = HandleLaser();
            if (skip) return;
            skip = HandleArms();
            if (skip) return;
            skip = HandleAdds();
            if (skip) return;
            skip = HandleSheerCold();
            if (skip) return;
            skip = HandleSpikes();
            if (skip) return;
            skip = HandleProjectiles();
            if (skip) return;

            if (glacorActive.contains(glacor.getId()) && !player.isMoving() && player.getAnimationId() == 0)
                glacor.interact(Actions.MENU_EXECUTE_NPC1);

            return;
        }

        SceneObject portal = SceneObjects.closest(x -> x.getId() == aqueductPortalId);
        if (portal != null) {
            if (portal.getGlobalPosition().distance(player.getGlobalPosition()) > 15) {
                Move.to(portal.getGlobalPosition());
                return;
            }

            // check widget open, interact.
            if (!Widgets.isOpen(0)) {
                portal.interact(Actions.MENU_EXECUTE_OBJECT1);
                return;
            }

            // navigate menu.
            return;
        }

        ManualMovementHandler.GoTo(glacorFront);
    }

    private boolean HandleLaser() {

        return false;
    }

    private boolean HandleArms() {
        SceneObject arm = SceneObjects.closest(x -> glacorArms.contains(x.getId()));
//        if (currentArm == null)


        return false;
    }

    private boolean HandleAdds() {
        SceneObject glacor = SceneObjects.closest(x -> glacorIds.contains(x.getId()));

        return false;
    }

    private boolean HandleSheerCold() {
        SceneObject glacor = SceneObjects.closest(x -> glacorIds.contains(x.getId()));

        return false;
    }

    private boolean HandleProjectiles() {
        SceneObject glacor = SceneObjects.closest(x -> glacorIds.contains(x.getId()));

        return false;
    }

    private boolean HandleSpikes() {
        SceneObject glacor = SceneObjects.closest(x -> glacorIds.contains(x.getId()));

        return false;
    }
}
