package kraken.plugin.mash;

import enums.World;
import helpers.Engine;
import helpers.Helper;
import helpers.Log;
import kraken.plugin.api.*;
import modules.InventoryHandler;
import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Map;

public class MashDebug extends Plugin {
    private static int isWidOpenId = 0;
     static boolean isWidOpen = false;
    public static boolean debugRoutine = false;
    private static boolean drawingAreaEnabled = false;
    private static boolean drawingPathEnabled = false;
    private static int getObjectId = 0;
    private static SceneObject getObject;
    private static int getNpcId = 0;
    private static Npc getNpc;
    private static String npcAction = "";
    private static ArrayList<Vector3i> globalCoordinates = new ArrayList<Vector3i>();
    private static ArrayList<Vector3> sceneCoordinates = new ArrayList<Vector3>();
    private static ArrayList<Vector3i> globalPath = new ArrayList<Vector3i>();
    private static Vector3i areaTopLeft = Vector3i.ZERO;
    private static Vector3i areaBottomRight = Vector3i.ZERO;
    private static byte[] objectInteractInput = "Action".getBytes();
    private static int widgetItemId = 0;
    private static WidgetItem widgetItem;

    public MashDebug() {
    }

    @Override
    public boolean onLoaded(@NotNull PluginContext pluginContext) {
        pluginContext.setName("MashDebug");
//        pluginContext.category = "Mash";
        return true;
    }

    @Override
    public int onLoop() {
        if (Players.self() == null) return 1000;

        if (debugRoutine) {
            WidgetItem toDrop = Inventory.first(x ->x.getName().contains("ore") && !x.getName().contains("ore box"));
            if (toDrop != null) {
                InventoryHandler.DropItem(toDrop);
                return 10;
            }

            debugRoutine = false;
        }

        return 1000;
    }
    
    @Override
    public void onPaint() {
        if (Players.self() != null) {
            if (ImGui.beginTabBar("MainTabs")) {
                if (ImGui.beginTabItem("Main")) {
                    ImGui.label("");
                    PaintMain();
                    ImGui.endTabItem();
                }
                if (ImGui.beginTabItem("Object")) {
                    ImGui.label("");
                    PaintObject();
                    ImGui.endTabItem();
                }

                if (ImGui.beginTabItem("Npc")) {
                    ImGui.label("");
                    PaintNpc();
                    ImGui.endTabItem();
                }

                if (ImGui.beginTabItem("Player")) {
                    ImGui.label("");
                    if (Players.self() != null)
                        PaintPlayer();
                    ImGui.endTabItem();
                }

                if (ImGui.beginTabItem("StatusBar")) {
                    ImGui.label("");
                    PaintStatusBar();
                    ImGui.endTabBar();
                }

                if (ImGui.beginTabItem("Equipment")) {
                    ImGui.label("");
                    PaintEquipment();
                    ImGui.endTabBar();
                }

                if (ImGui.beginTabItem("Area")) {
                    ImGui.label("");
                    PaintArea();
                    ImGui.endTabItem();
                }

                if (ImGui.beginTabItem("Path")) {
                    ImGui.label("");
                    PaintPath();
                    ImGui.endTabItem();
                }

                if (ImGui.beginTabItem("Widget")) {
                    ImGui.label("");
                    PaintWidget();
                    ImGui.endTabItem();
                }

                if (ImGui.beginTabItem("WidgetItem")) {
                    ImGui.label("");
                    PaintWidgetItem();
                    ImGui.endTabItem();
                }

                ImGui.endTabBar();
            }
        }
    }

    private void PaintStatusBar() {
        Player player = Players.self();

        ImGui.beginChild("##StatusBarFrame", true);

        ImGui.endChild();
    }

    private void PaintEquipment() {
        ImGui.columns("##EquipmentColumns", 3, true);
        ImGui.setColumnWidth(1, 300);
        ImGui.label("Players.self().getEquipment()");
        ImGui.nextColumn();
        ImGui.setColumnWidth(2, 400);
        ImGui.label("Equipment.getItems()");
        ImGui.nextColumn();
        ImGui.setColumnWidth(3, 400);
        ImGui.label("ItemContainers.byId(94).getItems()");
        ImGui.nextColumn();

        ImGui.separator();
        ImGui.label("");

        Map<EquipmentSlot, Item> equip = Players.self().getEquipment();
        for (var entry : equip.entrySet()) {
            ImGui.label(MessageFormat.format("{0} - {1}", entry.getValue().getId(), entry.getValue().getName()));
        }

        ImGui.nextColumn();

        ImGui.label("");

        WidgetItem[] equipBag = Equipment.getItems();
        for (WidgetItem entry : equipBag) {
            ImGui.label(MessageFormat.format("Slot: {0} - Id: {1} - {2}", entry.getSlot(), entry.getId(), entry.getName()));
        }
        ImGui.nextColumn();

        ImGui.label("");

        Item[] itemCont = ItemContainers.byId(94).getItems();
        for (Item entry : itemCont) {
            ImGui.label(MessageFormat.format("{0} - {1}", entry.getId(), entry.getName()));
        }

        ImGui.columns("", 1, false);
    }

    private void PaintWidgetItem() {
        widgetItemId = ImGui.intInput("WidgetItem Id##WidgetItemIdSet", widgetItemId);
        ImGui.sameLine();
        if (ImGui.button("Set##SetWidgetItem")) {
            Log.Information("Setting widgetItem.");
            widgetItem = Inventory.first(x -> x.getId() == isWidOpenId);
        }

        WidgetItem[] inventory = InventoryHandler.GetDistinctWidgetItems();

        if (inventory.length > 0) {
            for (int i = 0; i < inventory.length; i++) {
                ImGui.label(MessageFormat.format("{0} - {1}", inventory[i].getId(), inventory[i].getName()));
                ImGui.sameLine();
                if (ImGui.button(MessageFormat.format("Set##SetWidgetItemFromList{0}{1}", i, inventory[i].getId()))) {
                    widgetItem = inventory[i];
                }
            }
        }

        if (widgetItem != null) {
            ImGui.label(MessageFormat.format("getId: {0}", widgetItem.getId()));
            ImGui.label(MessageFormat.format("getName: {0}", widgetItem.getName()));
            ImGui.label(MessageFormat.format("getAmount: {0} ({1})", widgetItem.getAmount(), InventoryHandler.GetInventoryWidgetItemQuantity(widgetItem.getId())));
            ImGui.label(MessageFormat.format("getSlot: {0}", widgetItem.getSlot()));
            ImGui.label(MessageFormat.format("getWidgetId: {0}", widgetItem.getWidgetId()));
            ImGui.label(MessageFormat.format("getContainer: {0}", widgetItem.getContainer()));
            ImGui.label(MessageFormat.format("getClass: {0}", widgetItem.getClass()));
            String[] options = widgetItem.getOptionNames();
            if (options.length > 0) {
                ImGui.label("");
                ImGui.label("Interact:");
                for (int i = 0; i < options.length; i++) {
                    if (ImGui.button(MessageFormat.format("{0} - [{1}]", i+1, options[i]))) {
                        widgetItem.interact(options[i+1]);
                    }
                }
            }
        }
    }

    @Override
    public void onPaintOverlay() {
        Vector3[] vectors = sceneCoordinates.toArray(new Vector3[0]);
        if (drawingAreaEnabled) {
            Log.Information("Drawing Area.");
            ImGui.freePoly4(Client.worldToScreen(vectors[0]), Client.worldToScreen(vectors[1]), Client.worldToScreen(vectors[2]), Client.worldToScreen(vectors[3]), 0x39fa86cc);
        }

        if (drawingPathEnabled && globalPath.size() > 1) {
            Log.Information("Drawing Path.");
            for (int i = 0; i < globalPath.size()-1; i++) {
                Vector3 drawLine = globalPath.get(i).toScene();
                Vector3 drawLineNext = globalPath.get(i+1).toScene();
                if (drawLineNext != null) {
                    ImGui.freeLine(Client.worldToScreen(drawLine), Client.worldToScreen(drawLineNext), 0xff0000ff);
                }
            }
        }
    }

    private void PaintPath() {
        if (ImGui.button("Save##SavPath")) {
            Log.Information("Saving path coordinate.");
            Vector3i path = Players.self().getGlobalPosition();
            globalPath.add(path);
        }
        ImGui.sameLine();
        if (ImGui.button("Reset##ResetPath")) {
            Log.Information("Reseting path coordinates.");
            globalPath = new ArrayList<Vector3i>();
        }
        ImGui.sameLine();
        drawingPathEnabled = ImGui.checkbox("Draw", drawingPathEnabled);

        for (int i = 0; i < globalPath.size(); i++) {
            Vector3i currentCoord = globalPath.get(i);
            ImGui.label(MessageFormat.format("I: {0} - C: {1} - D: {2}", i, globalPath.get(i), globalPath.get(i).distance(Players.self().getGlobalPosition())));
            ImGui.sameLine();
            if (ImGui.button(MessageFormat.format("x##RemovePathCoord{0}", i))) {
                globalPath.remove(i);
            }
            ImGui.sameLine();
            if (ImGui.button(MessageFormat.format("Go##GoToCoord{0}", i))) {
                Move.to(globalPath.get(i));
            }

            byte[] pathValue = (
                    MessageFormat.format(
                            "new WorldTile({0}, {1}, 0);",
                            String.valueOf(currentCoord.getX()).replace(".", ""),
                            String.valueOf(currentCoord.getY()).replace(".", "")
                    )).getBytes();

            ImGui.input(MessageFormat.format("##CreatePathVector3diInput{0}", i), pathValue);
        }
    }

    private void PaintArea() {
        if (ImGui.button("Save##SaveCoordinate")) {
            Log.Information("Saving coordinate.");
            globalCoordinates.add(Players.self().getGlobalPosition());
            sceneCoordinates.add(Players.self().getScenePosition());
        }
        ImGui.sameLine();
        if (ImGui.button("Reset##ResetCoordinates")) {
            Log.Information("Reseting coordinates.");
            globalCoordinates = new ArrayList<Vector3i>();
            sceneCoordinates = new ArrayList<Vector3>();
        }
        ImGui.sameLine();
        drawingAreaEnabled = ImGui.checkbox("Draw", drawingAreaEnabled);

        ImGui.label("");
        ImGui.label("Global Coordinates -> Scene Coordinates");
        for (int i = 0; i < globalCoordinates.size(); i++) {
            ImGui.label(MessageFormat.format("[{0}] -> [{1}]", globalCoordinates.get(i), sceneCoordinates.get(i)));
            Vector3i globalCoord = globalCoordinates.get(i);
            Vector3 sceneCoord = sceneCoordinates.get(i);
            Vector3 newScene = globalCoord.toScene();

            if (ImGui.button(MessageFormat.format("X +1##SetXPlus1{0}Id{1}", globalCoord.getX(), i))) {
                globalCoord.setX(globalCoord.getX() + 1);
            }
            ImGui.sameLine();
            if (ImGui.button(MessageFormat.format("Y +1##SetYPlus1{0}Id{1}", globalCoord.getY(), i))) {
                globalCoord.setY(globalCoord.getY() + 1);
            }
            ImGui.sameLine();
            if (ImGui.button(MessageFormat.format("Z +1##SetZPlus1{0}Id{1}", globalCoord.getZ(), i))) {
                globalCoord.setZ(globalCoord.getZ() + 1);
            }
            ImGui.sameLine();
            if (ImGui.button(MessageFormat.format("X -1##SetXMinus1{0}Id{1}", globalCoord.getX(), i))) {
                globalCoord.setX(globalCoord.getX() - 1);
            }
            ImGui.sameLine();
            if (ImGui.button(MessageFormat.format("Y -1##SetYMinus11{0}Id{1}", globalCoord.getY(), i))) {
                globalCoord.setY(globalCoord.getY() - 1);
            }
            ImGui.sameLine();
            if (ImGui.button(MessageFormat.format("Z -1##SetZMinus1{0}Id{1}", globalCoord.getZ(), i))) {
                globalCoord.setZ(globalCoord.getZ() - 1);
            }
            ImGui.sameLine();
            if (ImGui.button(MessageFormat.format("Set Top Left##SetTopLeftId{0}", i))) {
                areaTopLeft = globalCoord;
            }
            ImGui.sameLine();
            if (ImGui.button(MessageFormat.format("Set Bottom Right##SetBottomRightId{0}", i))) {
                areaBottomRight = globalCoord;
            }

            sceneCoord.setX(newScene.getX());
            sceneCoord.setY(newScene.getY());
            sceneCoord.setZ(newScene.getZ());

            ImGui.sameLine();
            if (ImGui.button(MessageFormat.format("x##DeleteAreaCoord{0}", i))) {
                globalCoordinates.remove(i);
                sceneCoordinates.remove(i);
            }
        }

        ImGui.label("");
        ImGui.label(MessageFormat.format("Top Left: {0}", areaTopLeft));
        ImGui.label(MessageFormat.format("Bottom Right: {0}", areaBottomRight));

        if (areaTopLeft != Vector3i.ZERO && areaBottomRight != Vector3i.ZERO)
        {
            Area3di area = new Area3di(areaTopLeft, areaBottomRight);
            ImGui.label(MessageFormat.format("Is Player inside area: {0}", area.contains(Players.self())));

            byte[] areaCreate = (
                    MessageFormat.format(
                            "new Area3di(new WorldTile({0}, {1}, 0), new WorldTile({2}, {3}, 0));",
                            String.valueOf(areaTopLeft.getX()).replace(".", ""),
                            String.valueOf(areaTopLeft.getY()).replace(".", ""),
                            String.valueOf(areaBottomRight.getX()).replace(".", ""),
                            String.valueOf(areaBottomRight.getY()).replace(".", "")
            )).getBytes();


            ImGui.input("##CreateArea3diInput", areaCreate);
        }
    }

    private void PaintMain() {
        String dRoutText = MessageFormat.format("{0} debug routine", debugRoutine ? "Stop" : "Start");
        if (ImGui.button(dRoutText)) {
            debugRoutine = ! debugRoutine;
        }

        if (ImGui.button("Debug - swap world")) {
            boolean isF2P = World.IsF2P(Client.getWorld());
            Engine.SwapWorld(World.GetRandomWorld(isF2P ? WorldType.F2P : WorldType.P2P));
        }


        if (ImGui.button("Debug - interact")) {
            SceneObject[] forge = SceneObjects.all(x -> x.getId() == 120050);
            Vector3 position = forge[0].getScenePosition();
            if (position.distance(Players.self().getScenePosition()) > 5000)
                Move.to(position.toTile());
            else
                forge[0].interact(Actions.MENU_EXECUTE_OBJECT2);
        }

        if (ImGui.button("Debug - deposit")) {
            Bank.deposit((item) -> true, 7);
        }
    }

    private void PaintObject() {
        getObjectId = ImGui.intInput("##ObjectInfo", getObjectId);
        ImGui.sameLine();
        if (ImGui.button("Set##GetObjectInfo")) {
            Log.Information("Setting getObject.");
            getObject = SceneObjects.closest(x -> x.getId() == getObjectId);
        }
        ImGui.sameLine();
        if (ImGui.button("Null##SetNullObjectInfo")) {
            Log.Information("Setting getObject.");
            getObject = null;
        }

        SceneObject closestObject = SceneObjects.closest(x -> x.getGlobalPosition().distance(Players.self().getGlobalPosition()) < 5000);

        ImGui.label(MessageFormat.format("{0} - {1}", closestObject.getId(), closestObject.getName()));
        ImGui.sameLine();
        if (ImGui.button("Set##NearbyObjects")) {
            Log.Information("Setting getObject.");
            getObject = closestObject;
        }

//        SceneObject[] allObjects = SceneObjects.all(x -> x.getGlobalPosition().distance(Players.self().getGlobalPosition()) < 5000);
//
//        for (int i = 0; i < 5; i++) {
//            ImGui.label(MessageFormat.format("{0} - {1}", allObjects[i].getId(), allObjects[i].getName()));
//            ImGui.sameLine();
//            if (ImGui.button("Get##NearbyObjects")) {
//                Log.Information("Setting getObject.");
//                getObject = allObjects[i];
//            }
//        }

        if (getObject != null)
            PaintObjectDebug();
    }

    private void PaintObjectDebug() {
        ImGui.beginChild("##ObjectInfoSection");
        ImGui.label("---------- Main ----------");
        ImGui.label(MessageFormat.format("getId: {0}", getObject.getId()));
        ImGui.label(MessageFormat.format("getName: {0}", getObject.getName()));
        ImGui.label(MessageFormat.format("hidden: {0}", getObject.hidden()));
        ImGui.label(MessageFormat.format("getGlobalPosition: {0}", getObject.getGlobalPosition()));
        ImGui.label(MessageFormat.format("distance (global): {0}", getObject.getGlobalPosition().distance(Players.self().getGlobalPosition())));
        ImGui.label(MessageFormat.format("distance (scene): {0}", getObject.getScenePosition().distance(Players.self().getScenePosition())));
        ImGui.label("---------- Secondary ----------");
        ImGui.label(MessageFormat.format("getClass: {0}", getObject.getClass()));
        ImGui.label(MessageFormat.format("getInteractId: {0}", getObject.getInteractId()));
        ImGui.label(MessageFormat.format("getSize: {0}", getObject.getSize()));
        ImGui.label(MessageFormat.format("getScenePosition: {0}", getObject.getScenePosition()));
        ImGui.label(MessageFormat.format("hashCode: {0}", getObject.hashCode()));
        ImGui.label(MessageFormat.format("string: {0}", getObject.toString()));
        ImGui.label("---------- Interactions ----------");
        if (ImGui.button("1##InteractObject1")) {
            Log.Information(MessageFormat.format("Interaction with {0} - {1} on index MENU_EXECUTE_OBJECT1", getObject.getId(), getObject.getName()));
            getObject.interact(Actions.MENU_EXECUTE_OBJECT1);
        }
        ImGui.sameLine();
        if (ImGui.button("2##InteractObject2")) {
            Log.Information(MessageFormat.format("Interaction with {0} - {1} on index MENU_EXECUTE_OBJECT2", getObject.getId(), getObject.getName()));
            getObject.interact(Actions.MENU_EXECUTE_OBJECT2);
        }
        ImGui.sameLine();
        if (ImGui.button("3##InteractObject3")) {
            Log.Information(MessageFormat.format("Interaction with {0} - {1} on index MENU_EXECUTE_OBJECT3", getObject.getId(), getObject.getName()));
            getObject.interact(Actions.MENU_EXECUTE_OBJECT3);
        }
        ImGui.sameLine();
        if (ImGui.button("4##InteractObject4")) {
            Log.Information(MessageFormat.format("Interaction with {0} - {1} on index MENU_EXECUTE_OBJECT4", getObject.getId(), getObject.getName()));
            getObject.interact(Actions.MENU_EXECUTE_OBJECT4);
        }
        ImGui.sameLine();
        if (ImGui.button("5##InteractObject5")) {
            Log.Information(MessageFormat.format("Interaction with {0} - {1} on index MENU_EXECUTE_OBJECT5", getObject.getId(), getObject.getName()));
            getObject.interact(Actions.MENU_EXECUTE_OBJECT5);
        }
        ImGui.sameLine();
        if (ImGui.button("6##InteractObject6")) {
            Log.Information(MessageFormat.format("Interaction with {0} - {1} on index MENU_EXECUTE_OBJECT6", getObject.getId(), getObject.getName()));
            getObject.interact(Actions.MENU_EXECUTE_OBJECT6);
        }

        String before = new String(objectInteractInput);
        ImGui.input("##ObjectCustomAction", objectInteractInput);
        String after = new String(objectInteractInput);
        if (!before.equals(after))
            objectInteractInput = after.getBytes();

        ImGui.sameLine();
        if (ImGui.button("Custom##InteractObjectCustom")) {
            Log.Information(MessageFormat.format("Interaction with {0} - {1} with {2}", getObject.getId(), getObject.getName(), new String(objectInteractInput)));
            getObject.interact(new String(objectInteractInput));
        }
        ImGui.endChild();
    }

    private void PaintPlayer() {
        ImGui.beginChild("PlayerInfoSection");
        Player player = Players.self();
        ImGui.label("---------- Main ----------");
        ImGui.label(MessageFormat.format("getName: {0}", player.getName()));
        ImGui.label(MessageFormat.format("getHealth: {0}", player.getHealth()));
        ImGui.label(MessageFormat.format("getAdrenaline: {0}", player.getAdrenaline()));
        ImGui.label(MessageFormat.format("getAnimationId: {0}", player.getAnimationId()));
        ImGui.label(MessageFormat.format("getEquipment: {0}", player.getEquipment()));
        ImGui.label(MessageFormat.format("getTotalLevel: {0}", player.getTotalLevel()));
        ImGui.label(MessageFormat.format("getCombatLevel: {0}", player.getCombatLevel()));
        ImGui.label(MessageFormat.format("isMoving: {0}", player.isMoving()));
        ImGui.label(MessageFormat.format("isUnderAttack: {0}", player.isUnderAttack()));
        ImGui.label(MessageFormat.format("getActiveStatusBars: {0}", player.getActiveStatusBars()));
        ImGui.label(MessageFormat.format("getDirectionOffset: {0}", player.getDirectionOffset()));
        ImGui.label(MessageFormat.format("getGlobalPosition: {0}", player.getGlobalPosition()));
        ImGui.label(MessageFormat.format("getInteracting: {0}", player.getInteracting()));
        ImGui.label(MessageFormat.format("getInteractingIndex: {0}", player.getInteractingIndex()));
        ImGui.label(MessageFormat.format("getScenePosition: {0}", player.getScenePosition()));
        ImGui.label(MessageFormat.format("getServerIndex: {0}", player.getServerIndex()));
        ImGui.label(MessageFormat.format("getStatusBarFill: {0}", player.getStatusBarFill()));
        ImGui.label(MessageFormat.format("hashCode: {0}", player.hashCode()));
        ImGui.label(MessageFormat.format("isAnimationPlaying: {0}", player.isAnimationPlaying()));
        ImGui.label(MessageFormat.format("getClass: {0}", player.getClass()));
        ImGui.label(MessageFormat.format("toString: {0}", player.toString()));
        ImGui.endChild();
    }

    private void PaintNpc() {
        ImGui.label("Npc Info");
        getNpcId = ImGui.intInput("##NpcInfo", getNpcId);
        ImGui.sameLine();
        if (ImGui.button("Set##SetNpcInfo")) {
            Log.Information("Setting getNpc.");
            getNpc = Npcs.closest(x -> x.getId() == getNpcId);
        }
        ImGui.sameLine();
        if (ImGui.button("Null##SetNullNpcInfo")) {
            Log.Information("Setting getObject.");
            getNpc = null;
        }

        Npc closestNpc = Npcs.closest(x -> x.getGlobalPosition().distance(Players.self().getGlobalPosition()) < 5000);

        ImGui.label(MessageFormat.format("{0} - {1}", closestNpc.getId(), closestNpc.getName()));
        ImGui.sameLine();
        if (ImGui.button("Set##SetNearbyNpc")) {
            Log.Information("Setting getNpc.");
            getNpc = closestNpc;
        }

//        Npc[] allNpcs = Npcs.all(x -> x.getGlobalPosition().distance(Players.self().getGlobalPosition()) < 5000);
//
//        for (int i = 0; i < 5; i++) {
//            ImGui.label(MessageFormat.format("{0} - {1}", allNpcs[i].getId(), allNpcs[i].getName()));
//            ImGui.sameLine();
//            if (ImGui.button("Get##NearbyNpcs")) {
//                Log.Information("Setting getNpc.");
//                getNpc = allNpcs[i];
//            }
//        }

        if (getNpc != null)
            PaintNpcDebug();
    }

    private void PaintNpcDebug() {
        ImGui.beginChild("##NpcInfoSection");
        ImGui.label("---------- Main ----------");
        ImGui.label(MessageFormat.format("getId: {0}", getNpc.getId()));
        ImGui.label(MessageFormat.format("getName: {0}", getNpc.getName()));
        ImGui.label(MessageFormat.format("getHealth: {0}", getNpc.getHealth()));
        ImGui.label(MessageFormat.format("getGlobalPosition: {0}", getNpc.getGlobalPosition()));
        ImGui.label(MessageFormat.format("getScenePosition: {0}", getNpc.getScenePosition()));
        ImGui.label("getOptionNames: ");
        for (String optionName : getNpc.getOptionNames())
            ImGui.label(MessageFormat.format("        - {0}", optionName));
        ImGui.label(MessageFormat.format("getTransformedId: {0}", getNpc.getTransformedId()));
        ImGui.label(MessageFormat.format("isTagged: {0}", getNpc.isTagged()));
        ImGui.label(MessageFormat.format("getActiveStatusBars: {0}", getNpc.getActiveStatusBars()));
        ImGui.label(MessageFormat.format("getAnimationId: {0}", getNpc.getAnimationId()));
        ImGui.label(MessageFormat.format("getDirectionOffset: {0}", getNpc.getDirectionOffset()));
        ImGui.label(MessageFormat.format("getInteracting: {0}", getNpc.getInteracting()));
        ImGui.label(MessageFormat.format("getInteractingIndex: {0}", getNpc.getInteractingIndex()));
        ImGui.label(MessageFormat.format("getServerIndex: {0}", getNpc.getServerIndex()));
        ImGui.label(MessageFormat.format("getStatusBarFill: {0}", getNpc.getStatusBarFill()));
        ImGui.label(MessageFormat.format("hashCode: {0}", getNpc.hashCode()));
        ImGui.label(MessageFormat.format("isAnimationPlaying: {0}", getNpc.isAnimationPlaying()));
        ImGui.label(MessageFormat.format("isMoving: {0}", getNpc.isMoving()));
        ImGui.label(MessageFormat.format("getClass: {0}", getNpc.getClass()));
        ImGui.label(MessageFormat.format("toString: {0}", getNpc.toString()));
        ImGui.label("---------- Interactions ----------");
        if (ImGui.button("1##InteractNpc1")) {
            Log.Information(MessageFormat.format("Interaction with {0} - {1} on index MENU_EXECUTE_NPC1", getNpc.getId(), getNpc.getName()));
            getNpc.interact(Actions.MENU_EXECUTE_NPC1);
        }
        ImGui.sameLine();
        if (ImGui.button("2##InteractNpc2")) {
            Log.Information(MessageFormat.format("Interaction with {0} - {1} on index MENU_EXECUTE_NPC2", getNpc.getId(), getNpc.getName()));
            getNpc.interact(Actions.MENU_EXECUTE_NPC2);
        }
        ImGui.sameLine();
        if (ImGui.button("3##InteractNpc3")) {
            Log.Information(MessageFormat.format("Interaction with {0} - {1} on index MENU_EXECUTE_NPC3", getNpc.getId(), getNpc.getName()));
            getNpc.interact(Actions.MENU_EXECUTE_NPC3);
        }
        ImGui.sameLine();
        if (ImGui.button("4##InteractNpc4")) {
            Log.Information(MessageFormat.format("Interaction with {0} - {1} on index MENU_EXECUTE_NPC4", getNpc.getId(), getNpc.getName()));
            getNpc.interact(Actions.MENU_EXECUTE_NPC4);
        }
        ImGui.sameLine();
        if (ImGui.button("5##InteractNpc5")) {
            Log.Information(MessageFormat.format("Interaction with {0} - {1} on index MENU_EXECUTE_NPC5", getNpc.getId(), getNpc.getName()));
            getNpc.interact(Actions.MENU_EXECUTE_NPC5);
        }
        ImGui.sameLine();
        if (ImGui.button("6##InteractNpc6")) {
            Log.Information(MessageFormat.format("Interaction with {0} - {1} on index MENU_EXECUTE_NPC6", getNpc.getId(), getNpc.getName()));
            getNpc.interact(Actions.MENU_EXECUTE_NPC6);
        }
        ImGui.input("##NpcCustomAction", npcAction.getBytes());
        ImGui.sameLine();
        if (ImGui.button("Custom##InteractNpcCustom")) {
            Log.Information(MessageFormat.format("Interaction with {0} - {1} on index MENU_EXECUTE_OBJECT6", getNpc.getId(), getNpc.getName()));
            getNpc.interact(npcAction);
        }
        ImGui.endChild();
    }

    private void PaintWidget() {
        ImGui.label("Is Widget Open");
        isWidOpenId = ImGui.intInput("##IsWidOpenInputId", isWidOpenId);
        ImGui.sameLine();
        if (ImGui.button("Get##GetWidgetOpen")) {
            Log.Information("Setting isWidOpen.");
            isWidOpen = Widgets.isOpen(isWidOpenId);
        }

        ImGui.label(MessageFormat.format("Is widget open: {0}", isWidOpen));
    }
}
