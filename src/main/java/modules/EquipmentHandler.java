package modules;

import kraken.plugin.api.*;

import java.text.MessageFormat;
import java.util.ArrayList;

public class EquipmentHandler {
    private static int equipmentWidgetId = 95944719;
    public static Integer GetSlot(EquipmentSlot slot) {
        switch (slot) {
            case HELMET:
                return 0;
            case AMULET:
                return 2;
            case BODY:
                return 4;
            case LEGS:
                return 7;
            case GLOVES:
                return 9;
            case BOOTS:
                return 10;
            case WEAPON:
                return 3;
            case RING:
                return 12;
            case CAPE:
            case AURA:
            case SHIELD:
            default:
                return null;
        }
    }

    public static void Interact (EquipmentSlot slot, int action) {
        Integer slotId = GetSlot(slot);
        if (slotId == null)
            return;

        Actions.menu(Actions.MENU_EXECUTE_WIDGET, action, slotId, equipmentWidgetId, 0);
    }

    public static boolean IsEquipped(int id) {
        Item[] all = ItemContainers.byId(94).getItems();

        for (Item item : all) {
            if (id == item.getId())
                return true;
        }

        return false;
    }

    public static boolean IsAnyEquipped(ArrayList<Integer> ids) {
        Item[] all = ItemContainers.byId(94).getItems();

        for (Item item : all) {
            if (ids.contains(item.getId()))
                return true;
        }

        return false;
    }
}
