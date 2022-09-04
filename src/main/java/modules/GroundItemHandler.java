package modules;

import kraken.plugin.api.Actions;
import kraken.plugin.api.GroundItem;
import kraken.plugin.api.Vector3i;

public class GroundItemHandler {
    public static void PickItem(GroundItem item) {
        Vector3i coords = item.getGlobalPosition();
        Actions.menu(Actions.MENU_EXECUTE_GROUND_ITEM3, item.getId(), coords.getX(), coords.getY(), 1);
    }
}
