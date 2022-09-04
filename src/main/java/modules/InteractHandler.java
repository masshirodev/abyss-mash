package modules;

import helpers.Helper;
import helpers.Log;
import kraken.plugin.api.*;

import java.text.MessageFormat;
import java.util.ArrayList;

public class InteractHandler {
    public static boolean WithObject(ArrayList<Integer> ids, int action) {
        SceneObject object = SceneObjects.closest(x -> ids.contains(x.getId()));
        return WithObject(object.getId(), action);
    }

    public static boolean WithObject(int id, int action) {
        Player player = Players.self();
        SceneObject object = SceneObjects.closest(x -> x.getId() == id);

        if (object != null) {
            Log.Information(MessageFormat.format("{0} found, walking to it.", object.getName()));
            Vector3 position = object.getScenePosition();
            if (position.distance(player.getScenePosition()) > 5000) {
                Move.to(position.toTile());
            } else {
                object.interact(action);
                return true;
            }

            Helper.Wait(2000);
        }

        return false;
    }

    public static boolean WithNpc(ArrayList<Integer> ids, int action) {
        Npc npc = Npcs.closest(x -> ids.contains(x.getId()));
        return WithObject(npc.getId(), action);
    }

    public static boolean WithNpc(int id, int action) {
        Player player = Players.self();
        Npc npc = Npcs.closest(x -> x.getId() == id);

        if (npc != null) {
            Log.Information(MessageFormat.format("{0} found, walking to it.", npc.getName()));
            Vector3 position = npc.getScenePosition();
            if (position.distance(player.getScenePosition()) > 5000) {
                Move.to(position.toTile());
            } else {
                npc.interact(action);
                Helper.Wait(2000);
                return true;
            }

            Helper.Wait(2000);
        }

        return false;
    }
}
