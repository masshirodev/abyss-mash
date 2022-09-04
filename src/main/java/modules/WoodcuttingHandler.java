package modules;

import helpers.Helper;
import helpers.Log;
import kraken.plugin.api.*;
import models.LogModel;

import java.text.MessageFormat;

public class WoodcuttingHandler {
    private static LogModel treeChosen;
    private static SceneObject current;

    public static void Execute(LogModel tree) {
        treeChosen = tree;
        ManualMovementHandler.movementIndex = 0;

        Log.Information(MessageFormat.format("Woodcutting >> {0}.", treeChosen.Name));
        switch(treeChosen.Name) {
            default:
                Cut();
        }
    }

    private static void Cut() {
        Player player = Players.self();
        current = SceneObjects.closest(x -> !x.hidden() && treeChosen.ObjectId.contains(x.getId()));

        if (!player.isAnimationPlaying() && current != null) {
            current.interact(Actions.MENU_EXECUTE_OBJECT1);
            Helper.Wait(2000);
        }
    }
}
