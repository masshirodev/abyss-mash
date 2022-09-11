package modules;

import helpers.Helper;
import kraken.plugin.api.Actions;
import kraken.plugin.api.Npc;
import kraken.plugin.api.Npcs;

import java.util.ArrayList;
import java.util.Arrays;

public class SkillingHandler {
    private static ArrayList<Integer> chronicleIds = new ArrayList<Integer>(Arrays.asList(18205, 18204));
    private static ArrayList<Integer> serenSpiritIds = new ArrayList<Integer>(Arrays.asList(26022));
    private static ArrayList<Integer> divineBlessingIds = new ArrayList<Integer>(Arrays.asList(27228));
    private static ArrayList<Integer> catalystAlterationIds = new ArrayList<Integer>(Arrays.asList(28411));
    private static ArrayList<Integer> divineOMaticIds = new ArrayList<Integer>(Arrays.asList());

    public static boolean HandleChronicles() {
        Npc chronicle = Npcs.closest(x -> chronicleIds.contains(x.getId()));

        if (chronicle != null) {
            Helper.Wait(2000);
            chronicle.interact(Actions.MENU_EXECUTE_NPC1);
            return true;
        }

        return false;
    }

    public static boolean HandleSerenSpirits() {
        Npc seren = Npcs.closest(x -> serenSpiritIds.contains(x.getId()));

        if (seren != null) {
            Helper.Wait(2000);
            seren.interact(Actions.MENU_EXECUTE_NPC1);
            return true;
        }

        return false;
    }

    public static boolean HandleDivineBlessings() {
        Npc seren = Npcs.closest(x -> divineBlessingIds.contains(x.getId()));

        if (seren != null) {
            Helper.Wait(2000);
            seren.interact(Actions.MENU_EXECUTE_NPC1);
            return true;
        }

        return false;
    }

    public static boolean HandleCatalystOfAlteration() {
        Npc seren = Npcs.closest(x -> catalystAlterationIds.contains(x.getId()));

        if (seren != null) {
            Helper.Wait(2000);
            seren.interact(Actions.MENU_EXECUTE_NPC1);
            return true;
        }

        return false;
    }

    public static void HandleDivineOMatic() {

    }
}
