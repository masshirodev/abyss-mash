package modules;

import helpers.Helper;
import helpers.Log;
import kraken.plugin.api.Actions;
import kraken.plugin.api.Npc;
import kraken.plugin.api.Npcs;
import kraken.plugin.api.Players;
import models.WispModel;

public class HarvestHandler {
    private static int harvestAnimation = 21228;
    private static Npc currentWisp;
    private static WispModel wispChosen;
    private static int chronicleId = 18205;
    public static void Execute(WispModel wisp) {
        wispChosen = wisp;
        if (wispChosen.type == "Wisp") {
            HarvestWisp();
        } else if (wispChosen.type == "HallOfMemories") {
            HarvestHallOfMemories();
        }
    }

    private static void HarvestHallOfMemories() {

    }

    private static void HarvestWisp() {
        Npc[] wisps = Npcs.all(x -> x.getId() == wispChosen.npcId);
        Npc chronicle = Npcs.closest(x -> x.getId() == chronicleId);

        if (chronicle != null) {
            chronicle.interact(Actions.MENU_EXECUTE_NPC1);
            Helper.Wait(1000);
        }

        if (wisps.length > 0) {
            if (currentWisp == null || Players.self().getAnimationId() != harvestAnimation) {
                Log.Information("Interacting with a wisp.");
                currentWisp = wisps[0];
                currentWisp.interact(Actions.MENU_EXECUTE_NPC1);
            }

            Helper.Wait(5000);
        }
    }
}
