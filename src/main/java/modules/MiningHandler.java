package modules;

import helpers.Helper;
import helpers.Log;
import kraken.plugin.api.*;
import models.OreModel;

import java.text.MessageFormat;

public class MiningHandler {
    private static final int miningAnimation = 32566;
    private static SceneObject currentRock;
    private static OreModel oreChosen;
    private static SceneObject[] ores;

    public static void Execute(OreModel ore, SceneObject[] oreEntities) {
        oreChosen = ore;
        ores = oreEntities;
        ManualMovementHandler.movementIndex = 0;

        if (currentRock != null && !ore.ObjectId.contains(currentRock.getId()))
            currentRock = null;

        Log.Information(MessageFormat.format("Mining {0}s.", oreChosen.Name));
        switch(oreChosen.Name) {
            default:
                Mine();
        }
    }

    private static void Mine() {
        SceneObject closest = SceneObjects.closest(x -> oreChosen.ObjectId.contains(x.getId()));

        if (currentRock == null) {
            currentRock = closest;
            currentRock.interact("Mine");
            return;
        }

        Effect effect = Effects.closest(x ->
                Array.contains(new int[] {7164, 7165}, x.getId()) &&
                currentRock != closest
        );

        if (effect != null) {
            Log.Information("I smell a rockertunity o.o");

            for (SceneObject item : ores) {
                if (item.getScenePosition().distance(effect.getScenePosition()) <
                    currentRock.getScenePosition().distance(effect.getScenePosition())) {
                    currentRock = item;
                    Helper.Wait(3000);
                }
            }

            currentRock.interact("Mine");
            return;
        }

        if (Players.self().getAdrenaline() < 0.3) {
            currentRock.interact("Mine");
        }
    }
}
