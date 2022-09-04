package modules;

import kraken.plugin.api.Actions;
import kraken.plugin.api.Inventory;
import kraken.plugin.api.WidgetItem;

import java.util.ArrayList;

public class InventoryHandler {
    public static void DropItem (WidgetItem item) {
        Actions.menu(Actions.MENU_EXECUTE_WIDGET, 8, item.getSlot(), item.getWidgetId(), 0);
    }

    public static void Interact (WidgetItem item, int action) {
        Actions.menu(Actions.MENU_EXECUTE_WIDGET, action, item.getSlot(), item.getWidgetId(), 0);
    }

    public static String[] GetTeleportationEquipment() {
        return new String[]{
            "Ring of Fortune",
            "Luck of the Dwarvens"
        };
    }

    public static WidgetItem[] GetDistinctWidgetItems() {
        WidgetItem[] all = Inventory.getItems();
        ArrayList<String> already = new ArrayList<String>();
        ArrayList<WidgetItem> distinct = new ArrayList<WidgetItem>();

        for (WidgetItem item : all) {
            if (!already.contains(item.getName())) {
                already.add(item.getName());
                distinct.add(item);
            }
        }

        return distinct.toArray(new WidgetItem[0]);
    }

    public static int GetInventoryWidgetItemQuantity(int id) {
        WidgetItem[] all = Inventory.getItems();
        int quantity = 0;

        for (WidgetItem item : all) {
            if (item.getId() == id)
                quantity = quantity + item.getAmount();
        }

        return quantity;
    }
}
