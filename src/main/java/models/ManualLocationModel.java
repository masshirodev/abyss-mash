package models;

import abyss.plugin.api.world.WorldTile;
import com.fasterxml.jackson.annotation.JsonProperty;
import enums.LocationType;
import kraken.plugin.api.Actions;
import kraken.plugin.api.Area3di;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.ArrayList;
import java.util.Arrays;

public class ManualLocationModel {

    public ManualLocationModel(
            int id,
            String execute,
            String name,
            ArrayList<LocationType> type,
            String teleport,
            int teleportDistance,
            ArrayList<RequirementModel> requirements,
            WorldTile areaTopLeft,
            WorldTile areaBottomRight,
            ArrayList<PathModel> path,
            ArrayList<CustomPathModel> customPath,
            ArrayList<ExitPathModel> exitPath
    ) {
        this.Id = id;
        this.Execute = execute;
        this.Name = name;
        this.Type = type;
        this.Teleport = teleport;
        this.TeleportDistance = teleportDistance;
        this.Requirements = requirements;
        this.AreaTopLeft = areaTopLeft;
        this.AreaBottomRight = areaBottomRight;
        this.Path = path;
        this.CustomPath = customPath;
        this.ExitPath = exitPath;

        if (this.AreaBottomRight != null && this.AreaTopLeft != null)
            this.Area = new Area3di(this.AreaTopLeft, this.AreaBottomRight);
    }

    @BsonProperty(value="Id")
    @JsonProperty("Id")
    public int Id;
    @BsonProperty(value="Execute")
    @JsonProperty("Execute")
    public String Execute;
    @BsonProperty(value="Name")
    @JsonProperty("Name")
    public String Name;
    @BsonProperty(value="Type")
    @JsonProperty("Type")
    public ArrayList<LocationType> Type;
    @BsonProperty(value="Teleport")
    @JsonProperty("Teleport")
    public String Teleport;
    @BsonProperty(value="TeleportDistance")
    @JsonProperty("TeleportDistance")
    public int TeleportDistance;
    @BsonProperty(value="AreaTopLeft")
    @JsonProperty("AreaTopLeft")
    public WorldTile AreaTopLeft;
    @BsonProperty(value="AreaBottomRight")
    @JsonProperty("AreaBottomRight")
    public WorldTile AreaBottomRight;
    public Area3di Area;
    @BsonProperty(value="Path")
    @JsonProperty("Path")
    public ArrayList<PathModel> Path;
    @BsonProperty(value="Requirements")
    @JsonProperty("Requirements")
    public ArrayList<RequirementModel> Requirements;
    @BsonProperty(value="CustomPath")
    @JsonProperty("CustomPath")
    public ArrayList<CustomPathModel> CustomPath;
    @BsonProperty(value="ExitPath")
    @JsonProperty("ExitPath")
    public ArrayList<ExitPathModel> ExitPath;


    public static ManualLocationModel GetLocationByName(String name) {
        ArrayList<ManualLocationModel> all = GetLocationList();
        for (ManualLocationModel current : all) {
            if (current.Name.equals(name))
                return current;
        }

        return null;
    }

    public static ArrayList<ManualLocationModel> GetLocationListByType(LocationType type) {
        ArrayList<ManualLocationModel> all = GetLocationList();
        ArrayList<ManualLocationModel> result = new ArrayList<>();
        for (ManualLocationModel current : all) {
            if (current.Type.contains(type))
                result.add(current);
        }

        return result;
    }

    public static String[] GetLocationListByTypeToCombo(LocationType type) {
        ArrayList<ManualLocationModel> all = GetLocationList();
        ArrayList<String> result = new ArrayList<String>();
        for (ManualLocationModel current : all) {
            if (current.Type.contains(type))
                result.add(current.Name);
        }

        return result.toArray(new String[0]);
    }

    public static ArrayList<ManualLocationModel> GetLocationList() {
        return new ArrayList<ManualLocationModel>(){
            {
                add(new ManualLocationModel(
                        1,
                        "Default",
                        "Burthorpe Mine",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.Mine);
                                add(LocationType.Disconnected);
                            }
                        },
                        "Burthorpe",
                        100,
                        new ArrayList<RequirementModel>(),
                        new WorldTile(2243,4541,0),
                        new WorldTile(2300,4484,0),
                        new ArrayList<PathModel>() {
                            {
                                add(new PathModel(
                                        1,
                                        new ArrayList<WorldTile>() {
                                            {
                                                add(new WorldTile(2899, 3544, 0));
                                                add(new WorldTile(2897, 3513, 0));
                                                add(new WorldTile(2876, 3503, 0));
                                                add(new WorldTile(2292,  4516, 0));
                                            }},
                                        new ArrayList<RequirementModel>()
                                ));
                            }
                        },
                        new ArrayList<CustomPathModel>() {
                            {
                                // Entrance
                                add(new CustomPathModel(
                                        1,
                                        2,
                                        "ObjectInteract",
                                        Actions.MENU_EXECUTE_OBJECT1,
                                        new ArrayList<Integer>(Arrays.asList(66876)),
                                        50,
                                        3
                                ));
                            }
                        },
                        new ArrayList<ExitPathModel>() {
                            {
                                // Exit
                                add(new ExitPathModel(
                                        0,
                                        "ObjectInteract",
                                        Actions.MENU_EXECUTE_OBJECT1,
                                        67002
                                ));
                            }
                        }
                ));

                add(new ManualLocationModel(
                        2,
                        "Default",
                        "Falador Mining Site",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.Mine);
                                add(LocationType.OpenWorld);
                            }
                        },
                        "Falador",
                        100,
                        new ArrayList<RequirementModel>(),
                        new WorldTile(2934, 3340, 0),
                        new WorldTile(2922, 3329, 0),
                        new ArrayList<PathModel>() {
                            {
                                add(new PathModel(
                                        1,
                                        new ArrayList<WorldTile>() {
                                            {
                                                add(new WorldTile(2967, 3403, 0));
                                                add(new WorldTile(2941, 3360, 0));
                                                add(new WorldTile(2934, 3355, 0));
                                                add(new WorldTile(2927, 3336, 0));
                                            }
                                        },
                                        new ArrayList<RequirementModel>() {
                                            {
                                                add(new RequirementModel(
                                                        "Level",
                                                        "AGILITY",
                                                        5,
                                                        true,
                                                        ""
                                                ));
                                            }
                                        }
                                ));
                                add(new PathModel(
                                        2,
                                        new ArrayList<WorldTile>() {
                                            {
                                                add(new WorldTile(2967, 3402, 0));
                                                add(new WorldTile(2969, 3380, 0));
                                                add(new WorldTile(3006, 3360, 0));
                                                add(new WorldTile(3006, 3315, 0));
                                                add(new WorldTile(2974, 3302, 0));
                                                add(new WorldTile(2938, 3302, 0));
                                                add(new WorldTile(2926, 3338, 0));
                                            }
                                        },
                                        new ArrayList<RequirementModel>()
                                ));
                            }
                        },
                        new ArrayList<CustomPathModel>() {
                            {
                                // Outside Stairs
                                add(new CustomPathModel(
                                        1,
                                        1,
                                        "ObjectInteract",
                                        Actions.MENU_EXECUTE_OBJECT1,
                                        new ArrayList<Integer>(Arrays.asList(11844)),
                                        1,
                                        2
                                ));
                            }
                        },
                        new ArrayList<ExitPathModel>()
                ));

                add(new ManualLocationModel(
                        3,
                        "Default",
                        "Varrock Southwest Mine",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.Mine);
                                add(LocationType.OpenWorld);
                            }
                        },
                        "Varrock",
                        100,
                        new ArrayList<RequirementModel>(),
                        new WorldTile(3172, 3383, 0),
                        new WorldTile(3185, 3364, 0),
                        new ArrayList<PathModel>() {
                            {
                                add(new PathModel(
                                        1,
                                        new ArrayList<WorldTile>() {
                                            {
                                                add(new WorldTile(3214, 3376, 0));
                                                add(new WorldTile(3187, 3371, 0));
                                            }
                                        },
                                        new ArrayList<RequirementModel>()
                                ));
                            }
                        },
                        new ArrayList<CustomPathModel>(),
                        new ArrayList<ExitPathModel>()
                ));

                add(new ManualLocationModel(
                        4,
                        "Default",
                        "Varrock Southeast Mine",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.Mine);
                                add(LocationType.OpenWorld);
                            }
                        },
                        "Varrock",
                        100,
                        new ArrayList<RequirementModel>(),
                        new WorldTile(3279, 3373, 0),
                        new WorldTile(3296, 3354, 0),
                        new ArrayList<PathModel>() {
                            {
                                add(new PathModel(
                                        1,
                                        new ArrayList<WorldTile>() {
                                            {
                                                add(new WorldTile(3214, 3376, 0));
                                                add(new WorldTile(3255, 3373, 0));
                                                add(new WorldTile(3284, 3367, 0));
                                            }
                                        },
                                        new ArrayList<RequirementModel>()
                                ));
                            }
                        },
                        new ArrayList<CustomPathModel>(),
                        new ArrayList<ExitPathModel>()
                ));

                add(new ManualLocationModel(
                        5,
                        "Default",
                        "Ourania Battlefield Mine",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.Mine);
                                add(LocationType.OpenWorld);
                            }
                        },
                        "Ardougne",
                        100,
                        new ArrayList<RequirementModel>(),
                        new WorldTile(2468, 3257, 0),
                        new WorldTile(2479, 3247, 0),
                        new ArrayList<PathModel>() {
                            {
                                add(new PathModel(
                                        1,
                                        new ArrayList<WorldTile>() {
                                            {
                                                add(new WorldTile(2614, 3351, 0));
                                                add(new WorldTile(2607, 3333, 0));
                                                add(new WorldTile(2607, 3297, 0));
                                                add(new WorldTile(2562, 3279, 0));
                                                add(new WorldTile(2529, 3261, 0));
                                                add(new WorldTile(2492, 3260, 0));
                                                add(new WorldTile(2474, 3254, 0));
                                            }
                                        },
                                        new ArrayList<RequirementModel>()
                                ));
                            }
                        },
                        new ArrayList<CustomPathModel>(),
                        new ArrayList<ExitPathModel>()
                ));

                add(new ManualLocationModel(
                        6,
                        "Default",
                        "Dwarven Mine",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.Mine);
                                add(LocationType.Disconnected);
                            }
                        },
                        "Falador",
                        100,
                        new ArrayList<RequirementModel>() {
                            {
                                add(new RequirementModel(
                                        "Level",
                                        "MINING",
                                        60,
                                        true,
                                        ""
                                ));
                            }
                        },
                        new WorldTile(3013,9760,0),
                        new WorldTile(3061,9729,0),
                        new ArrayList<PathModel>() {
                            {
                                add(new PathModel(
                                        1,
                                        new ArrayList<WorldTile>() {
                                            {
                                                add(new WorldTile(2967, 3403, 0));
                                                add(new WorldTile(2990, 3371, 0));
                                                add(new WorldTile(3025, 3357, 0));
                                                add(new WorldTile(3022, 3338, 0));
                                                add(new WorldTile(3021, 9739, 0));
                                                add(new WorldTile(3039, 9739, 0));
                                            }
                                        },
                                        new ArrayList<RequirementModel>()
                                ));
                            }
                        },
                        new ArrayList<CustomPathModel>() {
                            {
                                // Outside Stairs
                                add(new CustomPathModel(
                                        1,
                                        3,
                                        "ObjectInteract",
                                        Actions.MENU_EXECUTE_OBJECT1,
                                        new ArrayList<Integer>(Arrays.asList(2113)),
                                        1,
                                        4
                                ));
                            }
                        },
                        new ArrayList<ExitPathModel>() {
                            {
                                // Inside Stairs - Exit
                                add(new ExitPathModel(
                                        1,
                                        "ObjectInteract",
                                        Actions.MENU_EXECUTE_OBJECT1,
                                        6226
                                ));
                            }
                        }
                ));

                add(new ManualLocationModel(
                        7,
                        "Default",
                        "Mining Guild Resource Dungeon",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.Mine);
                                add(LocationType.Disconnected);
                            }
                        },
                        "Falador",
                        100,
                        new ArrayList<RequirementModel>() {
                            {
                                add(new RequirementModel(
                                        "Level",
                                        "MINING",
                                        60,
                                        true,
                                        ""
                                ));
                                add(new RequirementModel(
                                        "Level",
                                        "DUNGEONEERING",
                                        45,
                                        true,
                                        ""
                                ));
                            }
                        },
                        new WorldTile(1044, 4528, 0),
                        new WorldTile(1067, 4504, 0),
                        new ArrayList<PathModel>() {
                            {
                                add(new PathModel(
                                        1,
                                        new ArrayList<WorldTile>() {
                                            {
                                                add(new WorldTile(2967, 3403, 0));
                                                add(new WorldTile(2990, 3371, 0));
                                                add(new WorldTile(3025, 3357, 0));
                                                add(new WorldTile(3022, 3338, 0));
                                                add(new WorldTile(3021, 9739, 0));
                                                add(new WorldTile(1053, 4521, 0));
                                            }
                                        },
                                        new ArrayList<RequirementModel>()
                                ));
                            }
                        },
                        new ArrayList<CustomPathModel>() {
                            {
                                // Outside Stairs
                                add(new CustomPathModel(
                                        1,
                                        3,
                                        "ObjectInteract",
                                        Actions.MENU_EXECUTE_OBJECT1,
                                        new ArrayList<Integer>(Arrays.asList(2113)),
                                        1,
                                        4
                                ));
                                // Dungeon Entrance
                                add(new CustomPathModel(
                                        1,
                                        4,
                                        "ObjectInteract",
                                        Actions.MENU_EXECUTE_OBJECT1,
                                        new ArrayList<Integer>(Arrays.asList(52856)),
                                        1,
                                        5
                                ));
                            }
                        },
                        new ArrayList<ExitPathModel>() {
                            {
                                // Dungeon Exit
                                add(new ExitPathModel(
                                        0,
                                        "ObjectInteract",
                                        Actions.MENU_EXECUTE_OBJECT1,
                                        52866
                                ));
                            }
                        }
                ));

                add(new ManualLocationModel(
                        8,
                        "Default",
                        "Port Phasmatys South Mine",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.Mine);
                                add(LocationType.OpenWorld);
                            }
                        },
                        "Canifis",
                        100,
                        new ArrayList<RequirementModel>(),
                        new WorldTile(3683, 3410, 0),
                        new WorldTile(3694, 3388, 0),
                        new ArrayList<PathModel>() {
                            {
                                add(new PathModel(
                                        1,
                                        new ArrayList<WorldTile>() {
                                            {
                                                add(new WorldTile(3517, 3515, 0));
                                                add(new WorldTile(3539, 3497, 0));
                                                add(new WorldTile(3573, 3473, 0));
                                                add(new WorldTile(3618, 3447, 0));
                                                add(new WorldTile(3650, 3430, 0));
                                                add(new WorldTile(3683, 3407, 0));
                                            }
                                        },
                                        new ArrayList<RequirementModel>()
                                ));
                            }
                        },
                        new ArrayList<CustomPathModel>(),
                        new ArrayList<ExitPathModel>()
                ));

                add(new ManualLocationModel(
                        9,
                        "Default",
                        "Al Kharid Resource Dungeon",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.Mine);
                                add(LocationType.Disconnected);
                            }
                        },
                        "Al Kharid",
                        100,
                        new ArrayList<RequirementModel>(),
                        new WorldTile(1162, 4523, 0),
                        new WorldTile(1210, 4495, 0),
                        new ArrayList<PathModel>() {
                            {
                                add(new PathModel(
                                        1,
                                        new ArrayList<WorldTile>() {
                                            {
                                                add(new WorldTile(3297, 3184, 0));
                                                add(new WorldTile(3307, 3215, 0));
                                                add(new WorldTile(3300, 3237, 0));
                                                add(new WorldTile(3294, 3271, 0));
                                                add(new WorldTile(3297, 3298, 0));
                                                add(new WorldTile(1182, 4515, 0));
                                            }
                                        },
                                        new ArrayList<RequirementModel>()
                                ));
                            }
                        },
                        new ArrayList<CustomPathModel>() {
                            {
                                // Dungeon Entrance
                                add(new CustomPathModel(
                                        1,
                                        4,
                                        "ObjectInteract",
                                        Actions.MENU_EXECUTE_OBJECT1,
                                        new ArrayList<Integer>(Arrays.asList(52860)),
                                        1,
                                        3
                                ));
                            }
                        },
                        new ArrayList<ExitPathModel>() {
                            {
                                // Dungeon Exit
                                add(new ExitPathModel(
                                        0,
                                        "ObjectInteract",
                                        Actions.MENU_EXECUTE_OBJECT1,
                                        52872
                                ));
                            }
                        }
                ));

                add(new ManualLocationModel(
                        10,
                        "Default",
                        "Arctic Habitat Mine",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.Mine);
                                add(LocationType.OpenWorld);
                            }
                        },
                        "Fremennik Province",
                        100,
                        new ArrayList<RequirementModel>(),
                        new WorldTile(2708, 3880, 0),
                        new WorldTile(2748, 3869, 0),
                        new ArrayList<PathModel>() {
                            {
                                add(new PathModel(
                                        1,
                                        new ArrayList<WorldTile>() {
                                            {
                                                add(new WorldTile(2712, 3677, 0));
                                                add(new WorldTile(2716, 3717, 0));
                                                add(new WorldTile(2726, 3764, 0));
                                                add(new WorldTile(2727, 3793, 0));
                                                add(new WorldTile(2727, 3798, 0));
                                                add(new WorldTile(2727, 3805, 0));
                                                add(new WorldTile(2740, 3834, 0));
                                                add(new WorldTile(2740, 3840, 0));
                                                add(new WorldTile(2725, 3877, 0));
                                            }
                                        },
                                        new ArrayList<RequirementModel>()
                                ));
                            }
                        },
                        new ArrayList<CustomPathModel>() {
                            {
                                // Steps
                                add(new CustomPathModel(
                                        1,
                                        4,
                                        "ObjectInteract",
                                        Actions.MENU_EXECUTE_OBJECT1,
                                        new ArrayList<Integer>(Arrays.asList(19690)),
                                        2,
                                        5
                                ));
                                // Stairs
                                add(new CustomPathModel(
                                        1,
                                        6,
                                        "ObjectInteract",
                                        Actions.MENU_EXECUTE_OBJECT1,
                                        new ArrayList<Integer>(Arrays.asList(91222)),
                                        1,
                                        7
                                ));
                            }
                        },
                        new ArrayList<ExitPathModel>()
                ));

                add(new ManualLocationModel(
                        11,
                        "Default",
                        "Anachronia Southwest Mine",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.Mine);
                                add(LocationType.OpenWorld);
                            }
                        },
                        "Anachronia",
                        100,
                        new ArrayList<RequirementModel>(),
                        new WorldTile(5335, 2263, 0),
                        new WorldTile(5347, 2247, 0),
                        new ArrayList<PathModel>() {
                            {
                                add(new PathModel(
                                        1,
                                        new ArrayList<WorldTile>() {
                                            {
                                                add(new WorldTile(5431, 2338, 0));
                                                add(new WorldTile(5386, 2336, 0));
                                                add(new WorldTile(5368, 2311, 0));
                                                add(new WorldTile(5354, 2277, 0));
                                                add(new WorldTile(5319, 2290, 0));
                                                add(new WorldTile(5340, 2255, 0));
                                            }
                                        },
                                        new ArrayList<RequirementModel>()
                                ));
                            }
                        },
                        new ArrayList<CustomPathModel>(),
                        new ArrayList<ExitPathModel>()
                ));

                add(new ManualLocationModel(
                        12,
                        "Default",
                        "Empty Throne Room Mine",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.Mine);
                                add(LocationType.Disconnected);
                            }
                        },
                        "Varrock",
                        100,
                        new ArrayList<RequirementModel>(),
                        new WorldTile(2871, 12650, 0),
                        new WorldTile(2883, 12629, 0),
                        new ArrayList<PathModel>() {
                            {
                                add(new PathModel(
                                        1,
                                        new ArrayList<WorldTile>() {
                                            {
                                                add(new WorldTile(3214, 3376, 0));
                                                add(new WorldTile(3263, 3374, 0));
                                                add(new WorldTile(3306, 3387, 0));
                                                add(new WorldTile(3356, 3396, 0));
                                                add(new WorldTile(3378, 3411, 0));
                                                add(new WorldTile(3377, 3402, 0));
                                                add(new WorldTile(2828, 12627, 0));
                                                add(new WorldTile(2861, 12620, 0));
                                                add(new WorldTile(2874, 12637, 0));
                                            }
                                        },
                                        new ArrayList<RequirementModel>()
                                ));
                            }
                        },
                        new ArrayList<CustomPathModel>() {
                            {
                                // Door
                                add(new CustomPathModel(
                                        1,
                                        5,
                                        "ObjectInteract",
                                        Actions.MENU_EXECUTE_OBJECT1,
                                        new ArrayList<Integer>(Arrays.asList(105579)),
                                        2,
                                        6

                                ));
                            }
                        },
                        new ArrayList<ExitPathModel>() {
                            {
                                add(new ExitPathModel(
                                        0,
                                        "ObjectInteract",
                                        Actions.MENU_EXECUTE_OBJECT1,
                                        105580
                                ));
                            }
                        }
                ));

                add(new ManualLocationModel(
                        13,
                        "Default",
                        "Artisan's Workshop",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.MetalBank);
                                add(LocationType.Bank);
                                add(LocationType.OpenWorld);
                            }
                        },
                        "Falador",
                        100,
                        new ArrayList<RequirementModel>(),
                        new WorldTile(3032, 3350, 0),
                        new WorldTile(3066, 3330, 0),
                        new ArrayList<PathModel>() {
                            {
                                add(new PathModel(
                                        1,
                                        new ArrayList<WorldTile>() {
                                            {
                                                add(new WorldTile(2967, 3403, 0));
                                                add(new WorldTile(2989, 3371, 0));
                                                add(new WorldTile(3027, 3358, 0));
                                                add(new WorldTile(3037, 3339, 0));
                                                add(new WorldTile(3030, 3339, 0));
                                            }
                                        },
                                        new ArrayList<RequirementModel>()
                                ));
                            }
                        },
                        new ArrayList<CustomPathModel>(),
                        new ArrayList<ExitPathModel>()
                ));

                add(new ManualLocationModel(
                        14,
                        "Default",
                        "Grand Exchange",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.Bank);
                                add(LocationType.GrandExchange);
                                add(LocationType.OpenWorld);
                            }
                        },
                        "Varrock",
                        100,
                        new ArrayList<RequirementModel>(),
                        new WorldTile(3032, 3350, 0),
                        new WorldTile(3066, 3330, 0),
                        new ArrayList<PathModel>() {
                            {
                                add(new PathModel(
                                        1,
                                        new ArrayList<WorldTile>() {
                                            {
                                                add(new WorldTile(3214, 3376, 0));
                                                add(new WorldTile(3211, 3402, 0));
                                                add(new WorldTile(3177, 3428, 0));
                                                add(new WorldTile(3165, 3468, 0));
                                                add(new WorldTile(3164, 3483, 0));
                                            }
                                        },
                                        new ArrayList<RequirementModel>()
                                ));
                            }
                        },
                        new ArrayList<CustomPathModel>(),
                        new ArrayList<ExitPathModel>()
                ));

                add(new ManualLocationModel(
                        15,
                        "Default",
                        "Varrock Metal Bank",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.MetalBank);
                                add(LocationType.OpenWorld);
                            }
                        },
                        "Varrock",
                        100,
                        new ArrayList<RequirementModel>(),
                        new WorldTile(3153, 3502, 0),
                        new WorldTile(3176, 3480, 0),
                        new ArrayList<PathModel>() {
                            {
                                add(new PathModel(
                                        1,
                                        new ArrayList<WorldTile>() {
                                            {
                                                add(new WorldTile(3214, 3376, 0));
                                                add(new WorldTile(3211, 3402, 0));
                                                add(new WorldTile(3187, 3425, 0));
                                            }
                                        },
                                        new ArrayList<RequirementModel>()
                                ));
                            }
                        },
                        new ArrayList<CustomPathModel>(),
                        new ArrayList<ExitPathModel>()
                ));

                add(new ManualLocationModel(
                        16,
                        "Default",
                        "Burthorpe Bank",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.Bank);
                                add(LocationType.OpenWorld);
                            }
                        },
                        "Burthorpe",
                        100,
                        new ArrayList<RequirementModel>(),
                        new WorldTile(2886, 3539, 0),
                        new WorldTile(2892, 3533, 0),
                        new ArrayList<PathModel>() {
                            {
                                add(new PathModel(
                                        1,
                                        new ArrayList<WorldTile>() {
                                            {
                                                add(new WorldTile(2899, 3544, 0));
                                                add(new WorldTile(2890, 3537, 0));
                                            }
                                        },
                                        new ArrayList<RequirementModel>()
                                ));
                            }
                        },
                        new ArrayList<CustomPathModel>(),
                        new ArrayList<ExitPathModel>()
                ));

                add(new ManualLocationModel(
                        17,
                        "Default",
                        "Burthorpe Furnace",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.MetalBank);
                                add(LocationType.OpenWorld);
                            }
                        },
                        "Burthorpe",
                        100,
                        new ArrayList<RequirementModel>(),
                        new WorldTile(2875, 3510, 0),
                        new WorldTile(2890, 3494, 0),
                        new ArrayList<PathModel>() {
                            {
                                add(new PathModel(
                                        1,
                                        new ArrayList<WorldTile>() {
                                            {
                                                add(new WorldTile(2899, 3544, 0));
                                                add(new WorldTile(2887, 3503, 0));
                                            }
                                        },
                                        new ArrayList<RequirementModel>()
                                ));
                            }
                        },
                        new ArrayList<CustomPathModel>(),
                        new ArrayList<ExitPathModel>()
                ));

                add(new ManualLocationModel(
                        18,
                        "Default",
                        "Clan Portal",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.Clan);
                                add(LocationType.Bank);
                                add(LocationType.OpenWorld);
                            }
                        },
                        "Falador",
                        100,
                        new ArrayList<RequirementModel>(),
                        new WorldTile(2948, 3307, 0),
                        new WorldTile(2988, 3274, 0),
                        new ArrayList<PathModel>() {
                            {
                                add(new PathModel(
                                        1,
                                        new ArrayList<WorldTile>() {
                                            {
                                                add(new WorldTile(2926, 3327, 0));
                                                add(new WorldTile(2946, 3301, 0));
                                                add(new WorldTile(2957, 3296, 0));
                                            }
                                        },
                                        new ArrayList<RequirementModel>() {
                                            {
                                                add(new RequirementModel(
                                                        "InArea",
                                                        "Falador Mining Site",
                                                        0,
                                                        true,
                                                        ""
                                                ));
                                            }
                                        }
                                ));
                                add(new PathModel(
                                        2,
                                        new ArrayList<WorldTile>() {
                                            {
                                                add(new WorldTile(2967, 3403, 0));
                                                add(new WorldTile(2941, 3360, 0));
                                                add(new WorldTile(2934, 3355, 0));
                                                add(new WorldTile(2927, 3336, 0));
                                                add(new WorldTile(2946, 3301, 0));
                                                add(new WorldTile(2957, 3296, 0));
                                            }
                                        },
                                        new ArrayList<RequirementModel>() {
                                            {
                                                add(new RequirementModel(
                                                        "Level",
                                                        "AGILITY",
                                                        5,
                                                        true,
                                                        ""
                                                ));
                                            }
                                        }
                                ));
                                add(new PathModel(
                                        3,
                                        new ArrayList<WorldTile>() {
                                            {
                                                add(new WorldTile(2967, 3403, 0));
                                                add(new WorldTile(3003, 3362, 0));
                                                add(new WorldTile(3006, 3317, 0));
                                                add(new WorldTile(2965, 3295, 0));
                                            }
                                        },
                                        new ArrayList<RequirementModel>()
                                ));
                            }
                        },
                        new ArrayList<CustomPathModel>(),
                        new ArrayList<ExitPathModel>()
                ));

                add(new ManualLocationModel(
                        19,
                        "Default",
                        "Al Kharid Mining Site",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.Mine);
                                add(LocationType.OpenWorld);
                            }
                        },
                        "Al Kharid",
                        100,
                        new ArrayList<RequirementModel>(),
                        new WorldTile(3290, 3322, 0),
                        new WorldTile(3309, 3282, 0),
                        new ArrayList<PathModel>() {
                            {
                                add(new PathModel(
                                        1,
                                        new ArrayList<WorldTile>() {
                                            {
                                                add(new WorldTile(3297, 3184, 0));
                                                add(new WorldTile(3307, 3215, 0));
                                                add(new WorldTile(3300, 3237, 0));
                                                add(new WorldTile(3294, 3271, 0));
                                                add(new WorldTile(3297, 3298, 0));
                                            }
                                        },
                                        new ArrayList<RequirementModel>()
                                ));
                            }
                        },
                        new ArrayList<CustomPathModel>(),
                        new ArrayList<ExitPathModel>()
                ));

                add(new ManualLocationModel(
                        20,
                        "Default",
                        "Falador Bank",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.Bank);
                                add(LocationType.OpenWorld);
                            }
                        },
                        "Falador",
                        100,
                        new ArrayList<RequirementModel>(),
                        new WorldTile(3007, 3362, 0),
                        new WorldTile(3023, 3351, 0),
                        new ArrayList<PathModel>() {
                            {
                                add(new PathModel(
                                        1,
                                        new ArrayList<WorldTile>() {
                                            {
                                                add(new WorldTile(2967, 3403, 0));
                                                add(new WorldTile(2992, 3370, 0));
                                                add(new WorldTile(3012, 3361, 0));
                                            }
                                        },
                                        new ArrayList<RequirementModel>()
                                ));
                            }
                        },
                        new ArrayList<CustomPathModel>(),
                        new ArrayList<ExitPathModel>()
                ));

                add(new ManualLocationModel(
                        21,
                        "Default",
                        "Al Kharid Bank",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.Bank);
                                add(LocationType.OpenWorld);
                            }
                        },
                        "Al Kharid",
                        100,
                        new ArrayList<RequirementModel>(),
                        new WorldTile(3262, 3189, 0),
                        new WorldTile(3277, 3162, 0),
                        new ArrayList<PathModel>() {
                            {
                                add(new PathModel(
                                        1,
                                        new ArrayList<WorldTile>() {
                                            {
                                                add(new WorldTile(3297, 3184, 0));
                                                add(new WorldTile(3275, 3179, 0));
                                            }
                                        },
                                        new ArrayList<RequirementModel>()
                                ));
                            }
                        },
                        new ArrayList<CustomPathModel>(),
                        new ArrayList<ExitPathModel>()
                ));

                add(new ManualLocationModel(
                        22,
                        "Default",
                        "Al Kharid Metal Bank",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.MetalBank);
                                add(LocationType.OpenWorld);
                            }
                        },
                        "Al Kharid",
                        100,
                        new ArrayList<RequirementModel>(),
                        new WorldTile(3281, 3194, 0),
                        new WorldTile(3290, 3184, 0),
                        new ArrayList<PathModel>() {
                            {
                                add(new PathModel(
                                        1,
                                        new ArrayList<WorldTile>() {
                                            {
                                                add(new WorldTile(3297, 3184, 0));
                                                add(new WorldTile(3288, 3189, 0));
                                            }
                                        },
                                        new ArrayList<RequirementModel>()
                                ));
                            }
                        },
                        new ArrayList<CustomPathModel>(),
                        new ArrayList<ExitPathModel>()
                ));

                add(new ManualLocationModel(
                        23,
                        "Default",
                        "Anachronia Bank Chest",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.Bank);
                                add(LocationType.OpenWorld);
                            }
                        },
                        "Anachronia",
                        100,
                        new ArrayList<RequirementModel>(),
                        new WorldTile(5461, 2352, 0),
                        new WorldTile(5470, 2333, 0),
                        new ArrayList<PathModel>() {
                            {
                                add(new PathModel(
                                        1,
                                        new ArrayList<WorldTile>() {
                                            {
                                                add(new WorldTile(5431, 2338, 0));
                                                add(new WorldTile(5455, 2338, 0));
                                                add(new WorldTile(5465, 2340, 0));
                                            }
                                        },
                                        new ArrayList<RequirementModel>()
                                ));
                            }
                        },
                        new ArrayList<CustomPathModel>(),
                        new ArrayList<ExitPathModel>()
                ));

                add(new ManualLocationModel(
                        24,
                        "Default",
                        "Elder God Wars Dungeon",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.Battle);
                                add(LocationType.Disconnected);
                            }
                        },
                        "Varrock",
                        100,
                        new ArrayList<RequirementModel>(),
                        new WorldTile(0, 0, 0),
                        new WorldTile(0, 0, 0),
                        new ArrayList<PathModel>() {
                            {
                                add(new PathModel(
                                        1,
                                        new ArrayList<WorldTile>() {
                                            {
                                                add(new WorldTile(3214, 3376, 0));
                                                add(new WorldTile(3242, 3374, 0));
                                                add(new WorldTile(3287, 3374, 0));
                                                add(new WorldTile(3297, 3413, 0));
                                                add(new WorldTile(3329, 3450, 0));
                                                add(new WorldTile(1755, 1341, 0));
                                            }
                                        },
                                        new ArrayList<RequirementModel>()
                                ));
                            }
                        },
                        new ArrayList<CustomPathModel>() {
                            {
                                // Ancient Door
                                add(new CustomPathModel(
                                        1,
                                        4,
                                        "ObjectInteract",
                                        Actions.MENU_EXECUTE_OBJECT1,
                                        new ArrayList<Integer>(Arrays.asList(119483)),
                                        2,
                                        5

                                ));
                            }
                        },
                        new ArrayList<ExitPathModel>()
                ));

                add(new ManualLocationModel(
                        25,
                        "Default",
                        "Croesus Front",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.Battle);
                                add(LocationType.Bossing);
                                add(LocationType.Disconnected);
                            }
                        },
                        "Varrock",
                        100,
                        new ArrayList<RequirementModel>(),
                        new WorldTile(0, 0, 0),
                        new WorldTile(0, 0, 0),
                        new ArrayList<PathModel>() {
                            {
                                add(new PathModel(
                                        1,
                                        new ArrayList<WorldTile>() {
                                            {
                                                add(new WorldTile(3214, 3376, 0));
                                                add(new WorldTile(3242, 3374, 0));
                                                add(new WorldTile(3287, 3374, 0));
                                                add(new WorldTile(3297, 3413, 0));
                                                add(new WorldTile(3329, 3450, 0));
                                                add(new WorldTile(1755, 1341, 0));
                                                add(new WorldTile(1771, 1317, 0));
                                                add(new WorldTile(1759, 1292, 0));
                                                add(new WorldTile(1759, 1264, 0));
                                                add(new WorldTile(1795, 1248, 0));
                                                add(new WorldTile(1832, 1248, 0));
                                                add(new WorldTile(1877, 1247, 0));
                                                add(new WorldTile(1916, 1248, 0));
                                            }
                                        },
                                        new ArrayList<RequirementModel>()
                                ));
                            }
                        },
                        new ArrayList<CustomPathModel>() {
                            {
                                // Ancient Door
                                add(new CustomPathModel(
                                        1,
                                        4,
                                        "ObjectInteract",
                                        Actions.MENU_EXECUTE_OBJECT1,
                                        new ArrayList<Integer>(Arrays.asList(119483)),
                                        2,
                                        5

                                ));

                                // Gateway
                                add(new CustomPathModel(
                                        1,
                                        12,
                                        "ObjectInteract",
                                        Actions.MENU_EXECUTE_OBJECT1,
                                        new ArrayList<Integer>(Arrays.asList(121693)),
                                        2,
                                        0

                                ));
                            }
                        },
                        new ArrayList<ExitPathModel>()
                ));

                add(new ManualLocationModel(
                        26,
                        "Default",
                        "Burthorpe Forest",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.Woodcutting);
                                add(LocationType.OpenWorld);
                            }
                        },
                        "Burthorpe",
                        100,
                        new ArrayList<RequirementModel>(),
                        new WorldTile(0, 0, 0),
                        new WorldTile(0, 0, 0),
                        new ArrayList<PathModel>() {
                            {
                                add(new PathModel(
                                        1,
                                        new ArrayList<WorldTile>() {
                                            {
                                                add(new WorldTile(2899, 3544, 0));
                                                add(new WorldTile(2899, 3518, 0));
                                                add(new WorldTile(2911, 3495, 0));
                                            }
                                        },
                                        new ArrayList<RequirementModel>()
                                ));
                            }
                        },
                        new ArrayList<CustomPathModel>(),
                        new ArrayList<ExitPathModel>()
                ));

                add(new ManualLocationModel(
                        27,
                        "Default",
                        "Tai Bwo Wannai",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.Woodcutting);
                                add(LocationType.OpenWorld);
                            }
                        },
                        "Karamja",
                        100,
                        new ArrayList<RequirementModel>(),
                        new WorldTile(0, 0, 0),
                        new WorldTile(0, 0, 0),
                        new ArrayList<PathModel>() {
                            {
                                add(new PathModel(
                                        1,
                                        new ArrayList<WorldTile>() {
                                            {
                                                add(new WorldTile(2761, 3147, 0));
                                                add(new WorldTile(2785, 3117, 0));
                                                add(new WorldTile(2801, 3085, 0));
                                            }
                                        },
                                        new ArrayList<RequirementModel>()
                                ));
                            }
                        },
                        new ArrayList<CustomPathModel>(),
                        new ArrayList<ExitPathModel>()
                ));

                add(new ManualLocationModel(
                        28,
                        "Default",
                        "Tai Bwo Wannai Mine",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.Mine);
                                add(LocationType.OpenWorld);
                            }
                        },
                        "Karamja",
                        100,
                        new ArrayList<RequirementModel>(),
                        new WorldTile(0, 0, 0),
                        new WorldTile(0, 0, 0),
                        new ArrayList<PathModel>() {
                            {
                                add(new PathModel(
                                        1,
                                        new ArrayList<WorldTile>() {
                                            {
                                                add(new WorldTile(2761, 3147, 0));
                                                add(new WorldTile(2785, 3117, 0));
                                                add(new WorldTile(2801, 3085, 0));
                                                add(new WorldTile(2826, 3063, 0));
                                                add(new WorldTile(2844, 3038, 0));
                                            }
                                        },
                                        new ArrayList<RequirementModel>()
                                ));
                            }
                        },
                        new ArrayList<CustomPathModel>(),
                        new ArrayList<ExitPathModel>()
                ));

                add(new ManualLocationModel(
                        29,
                        "Default",
                        "Shilo Village",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.Bank);
                                add(LocationType.OpenWorld);
                            }
                        },
                        "Karamja",
                        100,
                        new ArrayList<RequirementModel>() {
                            {
                                add(new RequirementModel(
                                        "Quest",
                                        "Shilo Village",
                                        0,
                                        true,
                                        ""
                                ));
                            }
                        },
                        new WorldTile(0, 0, 0),
                        new WorldTile(0, 0, 0),
                        new ArrayList<PathModel>() {
                            {
                                add(new PathModel(
                                        1,
                                        new ArrayList<WorldTile>() {
                                            {
                                                add(new WorldTile(2761, 3147, 0));
                                                add(new WorldTile(2758, 3184, 0));
                                                add(new WorldTile(2773, 3210, 0));
                                                add(new WorldTile(2776, 3211, 0));
                                                add(new WorldTile(2831, 2961, 0));
                                                add(new WorldTile(2849, 2959, 0));
                                            }
                                        },
                                        new ArrayList<RequirementModel>()
                                ));
                            }
                        },
                        new ArrayList<CustomPathModel>() {
                            {
                                // Travel cart
                                add(new CustomPathModel(
                                        1,
                                        3,
                                        "ObjectInteract",
                                        Actions.MENU_EXECUTE_OBJECT2,
                                        new ArrayList<Integer>(Arrays.asList(2230)),
                                        15,
                                        4

                                ));
                            }
                        },
                        new ArrayList<ExitPathModel>()
                ));

                add(new ManualLocationModel(
                        30,
                        "Default",
                        "Shilo Village Mine",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.Mine);
                                add(LocationType.OpenWorld);
                            }
                        },
                        "Karamja",
                        100,
                        new ArrayList<RequirementModel>() {
                            {
                                add(new RequirementModel(
                                        "Quest",
                                        "Shilo Village",
                                        0,
                                        true,
                                        ""
                                ));
                            }
                        },
                        new WorldTile(0, 0, 0),
                        new WorldTile(0, 0, 0),
                        new ArrayList<PathModel>() {
                            {
                                add(new PathModel(
                                        1,
                                        new ArrayList<WorldTile>() {
                                            {
                                                add(new WorldTile(2761, 3147, 0));
                                                add(new WorldTile(2758, 3184, 0));
                                                add(new WorldTile(2773, 3210, 0));
                                                add(new WorldTile(2776, 3211, 0));
                                                add(new WorldTile(2831, 2961, 0));
                                                add(new WorldTile(2827, 2995, 0));
                                            }
                                        },
                                        new ArrayList<RequirementModel>()
                                ));
                            }
                        },
                        new ArrayList<CustomPathModel>() {
                            {
                                // Travel cart
                                add(new CustomPathModel(
                                        1,
                                        3,
                                        "ObjectInteract",
                                        Actions.MENU_EXECUTE_OBJECT3,
                                        new ArrayList<Integer>(Arrays.asList(2230)),
                                        15,
                                        4

                                ));
                            }
                        },
                        new ArrayList<ExitPathModel>()
                ));

                add(new ManualLocationModel(
                        31,
                        "Default",
                        "Lumbridge",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.Bank);
                                add(LocationType.MetalBank);
                                add(LocationType.OpenWorld);
                            }
                        },
                        "Lumbridge",
                        100,
                        new ArrayList<RequirementModel>(),
                        new WorldTile(0, 0, 0),
                        new WorldTile(0, 0, 0),
                        new ArrayList<PathModel>() {
                            {
                                add(new PathModel(
                                        1,
                                        new ArrayList<WorldTile>() {
                                            {
                                                add(new WorldTile(3233, 3221, 0));
                                                add(new WorldTile(3219, 3253, 0));
                                            }
                                        },
                                        new ArrayList<RequirementModel>()
                                ));
                            }
                        },
                        new ArrayList<CustomPathModel>(),
                        new ArrayList<ExitPathModel>()
                ));

                add(new ManualLocationModel(
                        32,
                        "Default",
                        "Edgeville Bank",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.Bank);
                                add(LocationType.OpenWorld);
                            }
                        },
                        "Edgeville",
                        100,
                        new ArrayList<RequirementModel>(),
                        new WorldTile(0, 0, 0),
                        new WorldTile(0, 0, 0),
                        new ArrayList<PathModel>() {
                            {
                                add(new PathModel(
                                        1,
                                        new ArrayList<WorldTile>() {
                                            {
                                                add(new WorldTile(3067, 3505, 0));
                                                add(new WorldTile(3095, 3497, 0));
                                            }
                                        },
                                        new ArrayList<RequirementModel>()
                                ));
                            }
                        },
                        new ArrayList<CustomPathModel>(),
                        new ArrayList<ExitPathModel>()
                ));

                add(new ManualLocationModel(
                        33,
                        "Default",
                        "Abyss Inner Circle",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.Runecrafting);
                                add(LocationType.Disconnected);
                            }
                        },
                        "Edgeville",
                        100,
                        new ArrayList<RequirementModel>(),
                        new WorldTile(3025, 4846, 0),
                        new WorldTile(3055, 4817, 0),
                        new ArrayList<PathModel>() {
                            {
                                add(new PathModel(
                                        1,
                                        new ArrayList<WorldTile>() {
                                            {
                                                add(new WorldTile(3067, 3505, 0));
                                                add(new WorldTile(3102, 3501, 0));
                                                add(new WorldTile(3103, 3520, 0));
                                                add(new WorldTile(3103, 3523, 0));
                                                add(new WorldTile(3103, 3552, 0));
                                                add(new WorldTile(3038, 4810, 0));
                                            }
                                        },
                                        new ArrayList<RequirementModel>()
                                ));
                            }
                        },
                        new ArrayList<CustomPathModel>() {
                            {
                                // Go through wilderness wall
                                add(new CustomPathModel(
                                        1,
                                        2,
                                        "ObjectInteract",
                                        Actions.MENU_EXECUTE_OBJECT1,
                                        new ArrayList<Integer>(Arrays.asList(65084)),
                                        1,
                                        3
                                ));
                            }
                        },
                        new ArrayList<ExitPathModel>()
                ));

                add(new ManualLocationModel(
                        34,
                        "Default",
                        "Fremennik Slayer Dungeon",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.Runecrafting);
                                add(LocationType.Disconnected);
                            }
                        },
                        "Fremennik Province",
                        100,
                        new ArrayList<RequirementModel>(),
                        new WorldTile(0, 0, 0),
                        new WorldTile(0, 0, 0),
                        new ArrayList<PathModel>() {
                            {
                                add(new PathModel(
                                        1,
                                        new ArrayList<WorldTile>() {
                                            {
                                                add(new WorldTile(2712, 3677, 0));
                                                add(new WorldTile(2753, 3639, 0));
                                                add(new WorldTile(2791, 3616, 0));
                                                add(new WorldTile(2808, 10002, 0));
                                                add(new WorldTile(2791, 10018, 0));
                                                add(new WorldTile(2762, 10022, 0));
                                                add(new WorldTile(2741, 10009, 0));
                                                add(new WorldTile(2719, 10004, 0));
                                                add(new WorldTile(2726, 9973, 0));
                                            }
                                        },
                                        new ArrayList<RequirementModel>()
                                ));
                            }
                        },
                        new ArrayList<CustomPathModel>() {
                            {
                                // Entrance
                                add(new CustomPathModel(
                                        1,
                                        2,
                                        "ObjectInteract",
                                        Actions.MENU_EXECUTE_OBJECT1,
                                        new ArrayList<Integer>(Arrays.asList(34395)),
                                        1,
                                        3
                                ));
                            }
                        },
                        new ArrayList<ExitPathModel>()
                ));

                add(new ManualLocationModel(
                        35,
                        "Default",
                        "Draynor Divination Pool",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.Divination);
                                add(LocationType.OpenWorld);
                            }
                        },
                        "Draynor",
                        100,
                        new ArrayList<RequirementModel>(),
                        new WorldTile(0, 0, 0),
                        new WorldTile(0, 0, 0),
                        new ArrayList<PathModel>() {
                            {
                                add(new PathModel(
                                        1,
                                        new ArrayList<WorldTile>() {
                                            {
                                                add(new WorldTile(3105, 3298, 0));
                                                add(new WorldTile(3106, 3259, 0));
                                                add(new WorldTile(3106, 3259, 0));
                                                add(new WorldTile(3122, 3217, 0));
                                            }
                                        },
                                        new ArrayList<RequirementModel>()
                                ));
                            }
                        },
                        new ArrayList<CustomPathModel>(),
                        new ArrayList<ExitPathModel>()
                ));

                add(new ManualLocationModel(
                        36,
                        "Default",
                        "Falador Northeast Divination Pool",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.Divination);
                                add(LocationType.OpenWorld);
                            }
                        },
                        "Falador",
                        100,
                        new ArrayList<RequirementModel>(),
                        new WorldTile(0, 0, 0),
                        new WorldTile(0, 0, 0),
                        new ArrayList<PathModel>() {
                            {
                                add(new PathModel(
                                        1,
                                        new ArrayList<WorldTile>() {
                                            {
                                                add(new WorldTile(2967, 3403, 0));
                                                add(new WorldTile(2989, 3403, 0));
                                                add(new WorldTile(2989, 3403, 0));
                                            }
                                        },
                                        new ArrayList<RequirementModel>()
                                ));
                            }
                        },
                        new ArrayList<CustomPathModel>(),
                        new ArrayList<ExitPathModel>()
                ));

                add(new ManualLocationModel(
                        37,
                        "Default",
                        "Varrock Southeast Divination Pool",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.Divination);
                                add(LocationType.OpenWorld);
                            }
                        },
                        "Varrock",
                        100,
                        new ArrayList<RequirementModel>(),
                        new WorldTile(0, 0, 0),
                        new WorldTile(0, 0, 0),
                        new ArrayList<PathModel>() {
                            {
                                add(new PathModel(
                                        1,
                                        new ArrayList<WorldTile>() {
                                            {
                                                add(new WorldTile(3214, 3376, 0));
                                                add(new WorldTile(3239, 3376, 0));
                                                add(new WorldTile(3262, 3373, 0));
                                                add(new WorldTile(3285, 3373, 0));
                                                add(new WorldTile(3298, 3393, 0));
                                            }
                                        },
                                        new ArrayList<RequirementModel>()
                                ));
                            }
                        },
                        new ArrayList<CustomPathModel>(),
                        new ArrayList<ExitPathModel>()
                ));

                add(new ManualLocationModel(
                        38,
                        "Default",
                        "aaaaaaaaaaaaaaaaaa",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.Divination);
                                add(LocationType.OpenWorld);
                            }
                        },
                        "aaaaaaaaaaaaaaa",
                        100,
                        new ArrayList<RequirementModel>(),
                        new WorldTile(0, 0, 0),
                        new WorldTile(0, 0, 0),
                        new ArrayList<PathModel>() {
                            {
                                add(new PathModel(
                                        1,
                                        new ArrayList<WorldTile>() {
                                            {
                                            }
                                        },
                                        new ArrayList<RequirementModel>()
                                ));
                            }
                        },
                        new ArrayList<CustomPathModel>(),
                        new ArrayList<ExitPathModel>()
                ));

                add(new ManualLocationModel(
                        39,
                        "Default",
                        "Sorcerer's Tower Divination Pool",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.Divination);
                                add(LocationType.OpenWorld);
                            }
                        },
                        "Catherby",
                        100,
                        new ArrayList<RequirementModel>(),
                        new WorldTile(0, 0, 0),
                        new WorldTile(0, 0, 0),
                        new ArrayList<PathModel>() {
                            {
                                add(new PathModel(
                                        1,
                                        new ArrayList<WorldTile>() {
                                            {
                                                add(new WorldTile(2811, 3449, 0));
                                                add(new WorldTile(2779, 3440, 0));
                                                add(new WorldTile(2748, 3431, 0));
                                                add(new WorldTile(2735, 3417, 0));
                                            }
                                        },
                                        new ArrayList<RequirementModel>()
                                ));
                            }
                        },
                        new ArrayList<CustomPathModel>(),
                        new ArrayList<ExitPathModel>()
                ));

                add(new ManualLocationModel(
                        40,
                        "Default",
                        "Rellekka Southeast Divination Pool",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.Divination);
                                add(LocationType.OpenWorld);
                            }
                        },
                        "Fremennik Province",
                        100,
                        new ArrayList<RequirementModel>(),
                        new WorldTile(0, 0, 0),
                        new WorldTile(0, 0, 0),
                        new ArrayList<PathModel>() {
                            {
                                add(new PathModel(
                                        1,
                                        new ArrayList<WorldTile>() {
                                            {
                                                add(new WorldTile(2712, 3677, 0));
                                                add(new WorldTile(2736, 3654, 0));
                                                add(new WorldTile(2752, 3624, 0));
                                                add(new WorldTile(2765, 3599, 0));
                                            }
                                        },
                                        new ArrayList<RequirementModel>()
                                ));
                            }
                        },
                        new ArrayList<CustomPathModel>(),
                        new ArrayList<ExitPathModel>()
                ));

                add(new ManualLocationModel(
                        41,
                        "Default",
                        "Karamja Divination Pool",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.Divination);
                                add(LocationType.OpenWorld);
                            }
                        },
                        "Karamja",
                        100,
                        new ArrayList<RequirementModel>(),
                        new WorldTile(0, 0, 0),
                        new WorldTile(0, 0, 0),
                        new ArrayList<PathModel>() {
                            {
                                add(new PathModel(
                                        1,
                                        new ArrayList<WorldTile>() {
                                            {
                                                add(new WorldTile(2762, 3148, 0));
                                                add(new WorldTile(2787, 3122, 0));
                                                add(new WorldTile(2816, 3106, 0));
                                                add(new WorldTile(2844, 3085, 0));
                                                add(new WorldTile(2844, 3085, 0));
                                                add(new WorldTile(2887, 3052, 0));
                                            }
                                        },
                                        new ArrayList<RequirementModel>()
                                ));
                            }
                        },
                        new ArrayList<CustomPathModel>(),
                        new ArrayList<ExitPathModel>()
                ));

                add(new ManualLocationModel(
                        42,
                        "Default",
                        "Oo'glog Divination Pool",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.Divination);
                                add(LocationType.OpenWorld);
                            }
                        },
                        "Oo'glog",
                        100,
                        new ArrayList<RequirementModel>(),
                        new WorldTile(0, 0, 0),
                        new WorldTile(0, 0, 0),
                        new ArrayList<PathModel>() {
                            {
                                add(new PathModel(
                                        1,
                                        new ArrayList<WorldTile>() {
                                            {
                                                add(new WorldTile(2532, 2871, 0));
                                                add(new WorldTile(2504, 2872, 0));
                                                add(new WorldTile(2475, 2873, 0));
                                                add(new WorldTile(2448, 2873, 0));
                                                add(new WorldTile(2426, 2865, 0));
                                            }
                                        },
                                        new ArrayList<RequirementModel>()
                                ));
                            }
                        },
                        new ArrayList<CustomPathModel>(),
                        new ArrayList<ExitPathModel>()
                ));

                add(new ManualLocationModel(
                        43,
                        "Default",
                        "Canifis Divination Pool",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.Divination);
                                add(LocationType.OpenWorld);
                            }
                        },
                        "Canifis",
                        100,
                        new ArrayList<RequirementModel>(),
                        new WorldTile(0, 0, 0),
                        new WorldTile(0, 0, 0),
                        new ArrayList<PathModel>() {
                            {
                                add(new PathModel(
                                        1,
                                        new ArrayList<WorldTile>() {
                                            {
                                                add(new WorldTile(3517, 3515, 0));
                                                add(new WorldTile(3502, 3534, 0));
                                                add(new WorldTile(3472, 3540, 0));
                                            }
                                        },
                                        new ArrayList<RequirementModel>()
                                ));
                            }
                        },
                        new ArrayList<CustomPathModel>(),
                        new ArrayList<ExitPathModel>()
                ));

                add(new ManualLocationModel(
                        44,
                        "Default",
                        "Mage Training Area's Divination Pool",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.Divination);
                                add(LocationType.OpenWorld);
                            }
                        },
                        "Varrock",
                        100,
                        new ArrayList<RequirementModel>(),
                        new WorldTile(0, 0, 0),
                        new WorldTile(0, 0, 0),
                        new ArrayList<PathModel>() {
                            {
                                add(new PathModel(
                                        1,
                                        new ArrayList<WorldTile>() {
                                            {
                                                add(new WorldTile(3214, 3376, 0));
                                                add(new WorldTile(3240, 3375, 0));
                                                add(new WorldTile(3240, 3375, 0));
                                                add(new WorldTile(3300, 3359, 0));
                                                add(new WorldTile(3329, 3354, 0));
                                                add(new WorldTile(3349, 3333, 0));
                                                add(new WorldTile(3383, 3330, 0));
                                                add(new WorldTile(3399, 3310, 0));
                                                add(new WorldTile(3403, 3297, 0));
                                            }
                                        },
                                        new ArrayList<RequirementModel>()
                                ));
                            }
                        },
                        new ArrayList<CustomPathModel>(),
                        new ArrayList<ExitPathModel>()
                ));

                add(new ManualLocationModel(
                        45,
                        "Default",
                        "Menaphos Divination Pool",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.Divination);
                                add(LocationType.OpenWorld);
                            }
                        },
                        "Menaphos",
                        100,
                        new ArrayList<RequirementModel>(),
                        new WorldTile(0, 0, 0),
                        new WorldTile(0, 0, 0),
                        new ArrayList<PathModel>() {
                            {
                                add(new PathModel(
                                        1,
                                        new ArrayList<WorldTile>() {
                                            {
                                                add(new WorldTile(3216, 2716, 0));
                                                add(new WorldTile(3241, 2729, 0));
                                                add(new WorldTile(3266, 2729, 0));
                                                add(new WorldTile(3278, 2713, 0));
                                                add(new WorldTile(3301, 2693, 0));
                                                add(new WorldTile(3306, 2666, 0));

                                            }
                                        },
                                        new ArrayList<RequirementModel>()
                                ));
                            }
                        },
                        new ArrayList<CustomPathModel>() {
                            {
                                // Npc Door
                                add(new CustomPathModel(
                                        1,
                                        1,
                                        "NpcInteract",
                                        Actions.MENU_EXECUTE_NPC2,
                                        new ArrayList<Integer>(Arrays.asList(24666)),
                                        2,
                                        2

                                ));
                            }
                        },
                        new ArrayList<ExitPathModel>()
                ));

                add(new ManualLocationModel(
                        46,
                        "Default",
                        "Tirannwn Lodestone",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.OpenWorld);
                            }
                        },
                        "Tirannwn",
                        100,
                        new ArrayList<RequirementModel>(),
                        new WorldTile(0, 0, 0),
                        new WorldTile(0, 0, 0),
                        new ArrayList<PathModel>() {
                            {
                                add(new PathModel(
                                        1,
                                        new ArrayList<WorldTile>() {
                                            {
                                                add(new WorldTile(2254, 3149, 0));
                                            }
                                        },
                                        new ArrayList<RequirementModel>()
                                ));
                            }
                        },
                        new ArrayList<CustomPathModel>(),
                        new ArrayList<ExitPathModel>()
                ));

                add(new ManualLocationModel(
                        47,
                        "Default",
                        "Tirannwn Divination Pool",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.Divination);
                                add(LocationType.OpenWorld);
                            }
                        },
                        "Yanille",
                        100,
                        new ArrayList<RequirementModel>(),
                        new WorldTile(0, 0, 0),
                        new WorldTile(0, 0, 0),
                        new ArrayList<PathModel>() {
                            {
                                add(new PathModel(
                                        1,
                                        new ArrayList<WorldTile>() {
                                            {
                                                add(new WorldTile(2529, 3094, 0));
                                                add(new WorldTile(2500, 3094, 0));
                                                add(new WorldTile(2474, 3081, 0));
                                                add(new WorldTile(2450, 3062, 0));
                                                add(new WorldTile(2423, 3052, 0));
                                                add(new WorldTile(2394, 3038, 0));
                                                add(new WorldTile(2365, 3051, 0));
                                                add(new WorldTile(2339, 3065, 0));
                                                add(new WorldTile(2309, 3066, 0));
                                                add(new WorldTile(2286, 3059, 0));
                                                add(new WorldTile(2283, 3052, 0));
                                            }
                                        },
                                        new ArrayList<RequirementModel>()
                                ));
                            }
                        },
                        new ArrayList<CustomPathModel>(),
                        new ArrayList<ExitPathModel>()
                ));

//                add(new LocationModel(
//                        000000000000,
//                        "Default",
//                        "aaaaaaaaaaaaaaaaaa",
//                        new ArrayList<LocationType>() {
//                            {
//                                add(LocationType.Mine);
//                                add(LocationType.OpenWorld);
//                              }
//                          },
//                        "aaaaaaaaaaaaaaaaaa",
//                        100,
//                        new ArrayList<RequirementModel>(),
//                        new WorldTile(0, 0, 0),
//                        new WorldTile(0, 0, 0),
//                        new ArrayList<PathModel>() {
//                            {
//                                add(new PathModel(
//                                        1,
//                                        new ArrayList<WorldTile>() {
//                                            {
//                                                add(new WorldTile(0, 0, 0);
//                                                add(new WorldTile(0, 0, 0);
//                                            }
//                                        },
//                                        new ArrayList<RequirementModel>(),
//                                );
//                            }
//                        },
//                        new ArrayList<CustomPath>(),
//                        new ArrayList<ExitPathModel>()
//                );
            }
        };
    }

//    public static LocationModel MapFromDocument(Document doc) {
//        return new LocationModel(
//            doc.getInteger("Id"),
//            doc.getString("Execute"),
//            doc.getString("Name"),
//            doc.get("Type"),
//            doc.getString("Teleport"),
//            doc.getInteger("TeleportDistance"),
//            doc.get("Requirements"),
//            doc.get("AreaTopLeft"),
//            doc.get("AreaBottomRight"),
//            doc.get("Path"),
//            doc.get("CustomPath"),
//            doc.get("ExitPath")
//        );
//        int id,
//        String execute,
//        String name,
//        ArrayList<LocationType> type,
//        String teleport,
//        int teleportDistance,
//        ArrayList<RequirementModel> requirements,
//        WorldTile areaTopLeft,
//        WorldTile areaBottomRight,
//        ArrayList<PathModel> path,
//        ArrayList<CustomPathModel> customPath,
//        ArrayList<ExitPathModel> exitPath
//    }
}