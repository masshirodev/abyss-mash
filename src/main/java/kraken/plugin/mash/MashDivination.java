package kraken.plugin.mash;

import helpers.Log;
import kraken.plugin.api.*;
import models.WispModel;
import modules.ConvertionHandler;
import modules.HarvestHandler;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MashDivination extends Plugin {
    private static boolean startRoutine = false;
    private static WispModel wispChosen;
    private static List<WispModel> wispAvailable = new ArrayList<WispModel>(){
        {
            add(new WispModel(
                    1,
                    new ArrayList<Integer>(),
                    18150,
                    29313,
                    "Pale Wisps",
                    new Vector3i(3118, 3219, 0),
                    "Draynor",
                    0,
                    "Wisp"
            ));
            add(new WispModel(
                    2,
                    new ArrayList<Integer>(),
                    18151,
                    29314,
                    "Flickering Wisps",
                    new Vector3i(3005, 3404, 0),
                    "Falador",
                    10,
                    "Wisp"
            ));
            add(new WispModel(
                    3,
                    new ArrayList<Integer>(),
                    18153,
                    29315,
                    "Bright Wisps",
                    new Vector3i(3302, 3393, 0),
                    "Varrock",
                    20,
                    "Wisp"
            ));
            add(new WispModel(
                    4,
                    new ArrayList<Integer>() { { add(29387); add(29398);}},
                    18155,
                    29316,
                    "Glowing Wisps",
                    new Vector3i(2733, 3419, 0),
                    "Varrock",
                    30,
                    "Wisp"
            ));
            add(new WispModel(
                    5,
                    new ArrayList<Integer>() { { add(29388); add(29399);}},
                    18157,
                    29317,
                    "Sparkling Wisps",
                    new Vector3i(2771, 3598, 0),
                    "Fremmenik",
                    40,
                    "Wisp"
            ));
            add(new WispModel(
                    6,
                    new ArrayList<Integer>() { { add(29389); add(29400);}},
                    18159,
                    29318,
                    "Gleaming Wisps",
                    new Vector3i(2888, 3051, 0),
                    "Karamja",
                    50,
                    "Wisp"
            ));
            add(new WispModel(
                    7,
                    new ArrayList<Integer>() { { add(29390); add(29401);}},
                    18161,
                    29319,
                    "Vibrant Wisps",
                    new Vector3i(2426, 2863, 0),
                    "Ooglog",
                    60,
                    "Wisp"
            ));
            add(new WispModel(
                    8,
                    new ArrayList<Integer>() { { add(29391); add(29402);}},
                    18163,
                    29320,
                    "Lustrous Wisps",
                    new Vector3i(3471, 3538, 0),
                    "Canifis",
                    70,
                    "Wisp"
            ));
            add(new WispModel(
                    9,
                    new ArrayList<Integer>() { { add(29392); add(29403);}},
                    18165,
                    29321,
                    "Brilliant Wisps",
                    new Vector3i(3403, 3297, 0),
                    "Varrock",
                    80,
                    "Wisp"
            ));
            add(new WispModel(
                    10,
                    new ArrayList<Integer>() { { add(29393); add(29404);}},
                    18167,
                    29322,
                    "Radiant Wisps",
                    new Vector3i(3804, 3548, 0),
                    "None",
                    85,
                    "Wisp"
            ));
//            add(new WispModel(
//                    10,
//                    new ArrayList<Integer>() { { add(0); add(0);}},
//                    0,
//                    0,
//                    "Luminous Wisps",
//                    new Vector3i(),
//                    "aaaaaaa",
//                    60,
//                    "Wisp"
//            ));
//            add(new WispModel(
//                    11,
//                    new ArrayList<Integer>() { { add(0); add(0);}},
//                    0,
//                    0,
//                    "Incandescent Wisps",
//                    new Vector3i(),
//                    "aaaaaaa",
//                    60,
//                    "Wisp"
//            ));
        }
    };
    private static String[] comboOptions = new String[] {
            "Pale Wisps",
            "Flickering Wisps",
            "Bright Wisps",
            "Glowing Wisps",
            "Sparkling Wisps",
            "Gleaming Wisps",
            "Vibrant Wisps",
            "Lustrous Wisps",
            "Brilliant Wisps",
            "Radiant Wisps",
//            "Luminous Wisps",
//            "Incandescent Wisps",
    };
    private static int comboSelected = 9;
    private static boolean isConverting = false;
    private static int[] memoryJar = new int[] { 42898, 42899 };

    public MashDivination() {
    }

    @Override
    public boolean onLoaded(PluginContext pluginContext) {
        pluginContext.setName("MashDivination");
        return true;
    }

    @Override
    public int onLoop() {
        if (Players.self() == null) return 1000;

        if (startRoutine)
            divinationRoutine();

        return 1000;
    }

    @Override
    public void onPaint() {
        ImGui.label("");
        ImGui.label("MashDivination");
        ImGui.label("");

        if (ImGui.beginTabBar("MainTabs")) {
            if (ImGui.beginTabItem("Main")) {
                PaintMain();
                ImGui.endTabItem();
            }

            if (ImGui.beginTabItem("Debug")) {
                PaintDebug();
                ImGui.endTabItem();
            }

            ImGui.endTabBar();
        }
    }

    private void PaintMain() {
        comboSelected = ImGui.combo("Wisps##WispSelectCombo", comboOptions, comboSelected);
        wispChosen = wispAvailable.get(comboSelected);

        String toggleRoutineText = startRoutine ? "Stop Routine" : "Start Routine";
        if (ImGui.button(toggleRoutineText)) {
            startRoutine = !startRoutine;
        }
    }

    private void PaintDebug() {
        if (ImGui.button("Debug - isopen")) {
            //Log.Information("Teleporting to [Varrock].");
            //Lodestones.VARROCK.interact();
            Log.Information(MessageFormat.format("Forge Is Open: {0}", Widgets.isOpen(37)));
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

        if (Players.self() != null) {
            ImGui.label("");
            ImGui.label(MessageFormat.format("AnimationId: {0}", Players.self().getAnimationId()));
            ImGui.label(MessageFormat.format("Inventory is full: {0}", Inventory.isFull()));
            ImGui.label("");
            ImGui.label("Wisps detected:");

            Npc[] wisps = Npcs.all(x -> x.getId() == 18150);

            if (wisps.length > 0) {
                ImGui.beginChild("WispsFoundFrame");
                for (Npc wisp : wisps) {
                    ImGui.label(MessageFormat.format("Id: {0}", wisp.getId()));
                    ImGui.label(MessageFormat.format("Name: {0}", wisp.getName()));
                    ImGui.label(MessageFormat.format("Health: {0}", wisp.getHealth()));
                    ImGui.label(MessageFormat.format("Location: {0}", wisp.getGlobalPosition()));
                    ImGui.label("========================");
                }
                ImGui.endChild();
            }
        }
    }

    private void divinationRoutine() {
        if (wispChosen.type == "Wisp") {
            if (Inventory.isFull() || isConverting) {
                Log.Information("Inventory is full.");
                isConverting = ConvertionHandler.Execute(wispChosen);
            } else {
                HarvestHandler.Execute(wispChosen);
            }
        } else if (wispChosen.type == "HallOfMemory") {
            if (Inventory.contains(x -> Arrays.stream(memoryJar).anyMatch(y -> x.getId() == y))) {
                // Harvest
            } else {
                if (Inventory.isFull()) {
                    // Convert
                } else {
                    // Grab jars
                }
            }
        }
    }
}
