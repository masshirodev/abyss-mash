package modules;

import helpers.Helper;
import helpers.Log;
import kraken.plugin.api.*;
import models.WispModel;

import java.util.ArrayList;

public class ConvertionHandler {
    private static int convertionAnimation = 21234;
    private static ArrayList<Integer> riftIds = new ArrayList<Integer>() { { add(87306); }};

    public static boolean Execute(WispModel wisp) {
        Log.Information("Converting wisps.");
        if (Inventory.contains(x -> wisp.itemId.contains(x.getId()))) {
            SceneObject[] rift = SceneObjects.all(x -> riftIds.contains(x.getId()));

            if (rift.length > 0) {
                if (Players.self().getAnimationId() != convertionAnimation)
                {
                    rift[0].interact(Actions.MENU_EXECUTE_OBJECT1);
                    Helper.Wait(1000);
                }
            }

            return true;
        } else {
            return false;
        }
    }
}
