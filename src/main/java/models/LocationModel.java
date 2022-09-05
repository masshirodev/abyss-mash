package models;

import abyss.plugin.api.world.WorldTile;
import com.fasterxml.jackson.annotation.JsonProperty;
import enums.LocationType;
import kraken.plugin.api.Area3di;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class LocationModel {
    public LocationModel(
            int id,
            String name,
            ArrayList<LocationType> type,
            String teleport,
            int teleportDistance,
            ArrayList<RequirementModel> requirements,
            @Nullable WorldTile areaTopLeft,
            @Nullable WorldTile areaBottomRight,
            @Nullable WorldTile coordinate
    ) {
        this.Id = id;
        this.Name = name;
        this.Type = type;
        this.Teleport = teleport;
        this.TeleportDistance = teleportDistance;
        this.Requirements = requirements;
        this.AreaTopLeft = areaTopLeft;
        this.AreaBottomRight = areaBottomRight;

        if (this.AreaBottomRight != null && this.AreaTopLeft != null)
            this.Area = new Area3di(this.AreaTopLeft, this.AreaBottomRight);

        if (coordinate != null)
            this.Coordinate = coordinate;
        else if (this.Area != null)
            this.Coordinate = new WorldTile(this.Area.center().getX(), this.Area.center().getY(), this.Area.center().getZ());
    }

    @BsonProperty(value="Id")
    @JsonProperty("Id")
    public int Id;
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
    @BsonProperty(value="Coordinate")
    @JsonProperty("Coordinate")
    public WorldTile Coordinate;
    @BsonProperty(value="Requirements")
    @JsonProperty("Requirements")
    public ArrayList<RequirementModel> Requirements;

    public static LocationModel GetLocationByName(String name) {
        ArrayList<LocationModel> all = GetLocationList();
        for (LocationModel current : all) {
            if (current.Name.equals(name))
                return current;
        }

        return null;
    }

    public static ArrayList<LocationModel> GetLocationListByType(LocationType type) {
        ArrayList<LocationModel> all = GetLocationList();
        ArrayList<LocationModel> result = new ArrayList<>();
        for (LocationModel current : all) {
            if (current.Type.contains(type))
                result.add(current);
        }

        return result;
    }

    public static String[] GetLocationListByTypeToCombo(LocationType type) {
        ArrayList<LocationModel> all = GetLocationList();
        ArrayList<String> result = new ArrayList<String>();
        for (LocationModel current : all) {
            if (current.Type.contains(type))
                result.add(current.Name);
        }

        return result.toArray(new String[0]);
    }

    public static ArrayList<LocationModel> GetLocationList() {
        return new ArrayList<LocationModel>() {
            {
                add(new LocationModel(
                        1,
                        "Burthorpe Mine",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.Mine);
                                add(LocationType.Disconnected);
                            }
                        },
                        "Burthorpe",
                        300,
                        new ArrayList<RequirementModel>(),
                        new WorldTile(2243,4541,0),
                        new WorldTile(2300,4484,0),
                        null
                ));

                add(new LocationModel(
                        2,
                        "Burthorpe Furnace",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.MetalBank);
                                add(LocationType.OpenWorld);
                            }
                        },
                        "Burthorpe",
                        300,
                        new ArrayList<RequirementModel>(),
                        new WorldTile(2875, 3510, 0),
                        new WorldTile(2890, 3494, 0),
                        null
                ));

                // Untested
                add(new LocationModel(
                        3,
                        "Falador Mining Site",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.Mine);
                                add(LocationType.OpenWorld);
                            }
                        },
                        "Falador",
                        300,
                        new ArrayList<RequirementModel>(),
                        new WorldTile(2934, 3340, 0),
                        new WorldTile(2922, 3329, 0),
                        null
                ));

                // Untested
                add(new LocationModel(
                        4,
                        "Varrock Southwest Mine",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.Mine);
                                add(LocationType.OpenWorld);
                            }
                        },
                        "Varrock",
                        300,
                        new ArrayList<RequirementModel>(),
                        new WorldTile(3172, 3383, 0),
                        new WorldTile(3185, 3364, 0),
                        null
                ));

                // Untested
                add(new LocationModel(
                        5,
                        "Varrock Southeast Mine",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.Mine);
                                add(LocationType.OpenWorld);
                            }
                        },
                        "Varrock",
                        300,
                        new ArrayList<RequirementModel>(),
                        new WorldTile(3279, 3373, 0),
                        new WorldTile(3296, 3354, 0),
                        null
                ));

                // Untested
                add(new LocationModel(
                        6,
                        "Ourania Battlefield Mine",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.Mine);
                                add(LocationType.OpenWorld);
                            }
                        },
                        "Ardougne",
                        300,
                        new ArrayList<RequirementModel>(),
                        new WorldTile(2468, 3257, 0),
                        new WorldTile(2479, 3247, 0),
                        null
                ));

                // Untested
                add(new LocationModel(
                        7,
                        "Dwarven Mine",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.Mine);
                                add(LocationType.Disconnected);
                            }
                        },
                        "Falador",
                        300,
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
                        new WorldTile(3041,9740, 0)
                ));

                // Untested
                add(new LocationModel(
                        8,
                        "Mining Guild Resource Dungeon",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.Mine);
                                add(LocationType.Disconnected);
                            }
                        },
                        "Falador",
                        300,
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
                        null
                ));

                // Untested
                add(new LocationModel(
                        9,
                        "Port Phasmatys South Mine",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.Mine);
                                add(LocationType.OpenWorld);
                            }
                        },
                        "Canifis",
                        300,
                        new ArrayList<RequirementModel>(),
                        new WorldTile(3683, 3410, 0),
                        new WorldTile(3694, 3388, 0),
                        null
                ));

                // Untested
                add(new LocationModel(
                        10,
                        "Al Kharid Resource Dungeon",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.Mine);
                                add(LocationType.Disconnected);
                            }
                        },
                        "Canifis",
                        300,
                        new ArrayList<RequirementModel>() {
                            {
                                add(new RequirementModel(
                                        "Level",
                                        "DUNGEONEERING",
                                        75,
                                        true,
                                        ""
                                ));
                            }
                        },
                        new WorldTile(1162, 4523, 0),
                        new WorldTile(1210, 4495, 0),
                        null
                ));

                // Untested
                add(new LocationModel(
                        11,
                        "Arctic Habitat Mine",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.Mine);
                                add(LocationType.OpenWorld);
                            }
                        },
                        "Fremennik Province",
                        300,
                        new ArrayList<RequirementModel>(),
                        new WorldTile(2708, 3880, 0),
                        new WorldTile(2748, 3869, 0),
                        null
                ));

                // Untested
                add(new LocationModel(
                        12,
                        "Anachronia Southwest Mine",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.Mine);
                                add(LocationType.OpenWorld);
                            }
                        },
                        "Anachronia",
                        300,
                        new ArrayList<RequirementModel>(),
                        new WorldTile(5335, 2263, 0),
                        new WorldTile(5347, 2247, 0),
                        null
                ));

                // Untested
                add(new LocationModel(
                        13,
                        "Empty Throne Room Mine",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.Mine);
                                add(LocationType.Disconnected);
                            }
                        },
                        "Varrock",
                        300,
                        new ArrayList<RequirementModel>(),
                        new WorldTile(2871, 12650, 0),
                        new WorldTile(2883, 12629, 0),
                        null
                ));

                // Untested
                add(new LocationModel(
                        14,
                        "Artisan's Workshop",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.MetalBank);
                                add(LocationType.Bank);
                                add(LocationType.OpenWorld);
                            }
                        },
                        "Falador",
                        300,
                        new ArrayList<RequirementModel>(),
                        new WorldTile(3032, 3350, 0),
                        new WorldTile(3066, 3330, 0),
                        null
                ));

                // Untested
                add(new LocationModel(
                        15,
                        "Grand Exchange",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.Bank);
                                add(LocationType.GrandExchange);
                                add(LocationType.OpenWorld);
                            }
                        },
                        "Varrock",
                        300,
                        new ArrayList<RequirementModel>(),
                        new WorldTile(3032, 3350, 0),
                        new WorldTile(3066, 3330, 0),
                        null
                ));

                // Untested
                add(new LocationModel(
                        16,
                        "Varrock Metal Bank",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.MetalBank);
                                add(LocationType.OpenWorld);
                            }
                        },
                        "Varrock",
                        300,
                        new ArrayList<RequirementModel>(),
                        new WorldTile(3153, 3502, 0),
                        new WorldTile(3176, 3480, 0),
                        null
                ));

                // Untested
                add(new LocationModel(
                        17,
                        "Burthorpe Bank",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.Bank);
                                add(LocationType.OpenWorld);
                            }
                        },
                        "Burthorpe",
                        300,
                        new ArrayList<RequirementModel>(),
                        new WorldTile(2886, 3539, 0),
                        new WorldTile(2892, 3533, 0),
                        null
                ));

                // Untested
                add(new LocationModel(
                        18,
                        "Clan Portal",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.Clan);
                                add(LocationType.Bank);
                                add(LocationType.OpenWorld);
                            }
                        },
                        "Falador",
                        300,
                        new ArrayList<RequirementModel>(),
                        new WorldTile(2948, 3307, 0),
                        new WorldTile(2988, 3274, 0),
                        null
                ));

                // Untested
                add(new LocationModel(
                        19,
                        "Al Kharid Mining Site",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.Mine);
                                add(LocationType.OpenWorld);
                            }
                        },
                        "Al Kharid",
                        300,
                        new ArrayList<RequirementModel>(),
                        new WorldTile(3290, 3322, 0),
                        new WorldTile(3309, 3282, 0),
                        null
                ));

                // Untested
                add(new LocationModel(
                        20,
                        "Falador Bank",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.Bank);
                                add(LocationType.OpenWorld);
                            }
                        },
                        "Falador",
                        300,
                        new ArrayList<RequirementModel>(),
                        new WorldTile(3007, 3362, 0),
                        new WorldTile(3023, 3351, 0),
                        null
                ));

                // Untested
                add(new LocationModel(
                        21,
                        "Al Kharid Bank",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.Bank);
                                add(LocationType.OpenWorld);
                            }
                        },
                        "Al Kharid",
                        300,
                        new ArrayList<RequirementModel>(),
                        new WorldTile(3262, 3189, 0),
                        new WorldTile(3277, 3162, 0),
                        null
                ));

                // Untested
                add(new LocationModel(
                        22,
                        "Al Kharid Metal Bank",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.MetalBank);
                                add(LocationType.OpenWorld);
                            }
                        },
                        "Al Kharid",
                        300,
                        new ArrayList<RequirementModel>(),
                        new WorldTile(3281, 3194, 0),
                        new WorldTile(3290, 3184, 0),
                        null
                ));

                // Untested
                add(new LocationModel(
                        23,
                        "Anachronia Bank Chest",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.Bank);
                                add(LocationType.OpenWorld);
                            }
                        },
                        "Anachronia",
                        300,
                        new ArrayList<RequirementModel>(),
                        new WorldTile(5461, 2352, 0),
                        new WorldTile(5470, 2333, 0),
                        null
                ));

                // Untested
                add(new LocationModel(
                        24,
                        "Elder God Wars Dungeon",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.Battle);
                                add(LocationType.Disconnected);
                            }
                        },
                        "Varrock",
                        300,
                        new ArrayList<RequirementModel>(),
                        null,
                        null,
                        new WorldTile(1755, 1341, 0)
                ));

                // Untested
                add(new LocationModel(
                        25,
                        "Croesus Front",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.Battle);
                                add(LocationType.Bossing);
                                add(LocationType.Disconnected);
                            }
                        },
                        "Varrock",
                        300,
                        new ArrayList<RequirementModel>(),
                        null,
                        null,
                        new WorldTile(1916, 1248, 0)
                ));

                // Untested
                add(new LocationModel(
                        26,
                        "Burthorpe Forest",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.Woodcutting);
                                add(LocationType.OpenWorld);
                            }
                        },
                        "Burthorpe",
                        300,
                        new ArrayList<RequirementModel>(),
                        null,
                        null,
                        new WorldTile(2911, 3495, 0)
                ));

                // Untested
                add(new LocationModel(
                        27,
                        "Tai Bwo Wannai",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.Woodcutting);
                                add(LocationType.OpenWorld);
                            }
                        },
                        "Karamja",
                        300,
                        new ArrayList<RequirementModel>(),
                        null,
                        null,
                        new WorldTile(2801, 3085, 0)
                ));

                // Untested
                add(new LocationModel(
                        28,
                        "Tai Bwo Wannai Mine",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.Mine);
                                add(LocationType.OpenWorld);
                            }
                        },
                        "Karamja",
                        300,
                        new ArrayList<RequirementModel>(),
                        null,
                        null,
                        new WorldTile(2844, 3038, 0)
                ));

                // Untested
                add(new LocationModel(
                        29,
                        "Shilo Village",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.Bank);
                                add(LocationType.OpenWorld);
                            }
                        },
                        "Karamja",
                        300,
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
                        null,
                        null,
                        new WorldTile(2849, 2959, 0)
                ));

                // Untested
                add(new LocationModel(
                        30,
                        "Shilo Village Mine",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.Mine);
                                add(LocationType.OpenWorld);
                            }
                        },
                        "Karamja",
                        300,
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
                        null,
                        null,
                        new WorldTile(2827, 2995, 0)
                ));

                // Untested
                add(new LocationModel(
                        31,
                        "Lumbridge",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.Bank);
                                add(LocationType.MetalBank);
                                add(LocationType.OpenWorld);
                            }
                        },
                        "Lumbridge",
                        300,
                        new ArrayList<RequirementModel>(),
                        null,
                        null,
                        new WorldTile(3219, 3253, 0)
                ));

                // Untested
                add(new LocationModel(
                        32,
                        "Edgeville Bank",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.Bank);
                                add(LocationType.OpenWorld);
                            }
                        },
                        "Edgeville",
                        300,
                        new ArrayList<RequirementModel>(),
                        null,
                        null,
                        new WorldTile(3095, 3497, 0)
                ));

                // Untested
                add(new LocationModel(
                        33,
                        "Abyss Inner Circle",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.Runecrafting);
                                add(LocationType.Disconnected);
                            }
                        },
                        "Edgeville",
                        300,
                        new ArrayList<RequirementModel>(),
                        new WorldTile(3025, 4846, 0),
                        new WorldTile(3055, 4817, 0),
                        null
                ));

                // Untested
                add(new LocationModel(
                        34,
                        "Burthorpe Mine (Center)",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.Mine);
                                add(LocationType.Disconnected);
                            }
                        },
                        "Edgeville",
                        300,
                        new ArrayList<RequirementModel>(),
                        new WorldTile(3028, 9781, 0),
                        new WorldTile(3048, 9757, 0),
                        null
                ));

                // Untested
                add(new LocationModel(
                        35,
                        "Fremennik Slayer Dungeon",
                        new ArrayList<LocationType>() {
                            {
                                add(LocationType.Battle);
                                add(LocationType.Slayer);
                                add(LocationType.Disconnected);
                            }
                        },
                        "Fremennik Province",
                        300,
                        new ArrayList<RequirementModel>(),
                        null,
                        null,
                        new WorldTile(2726, 9973, 0)
                ));

//                add(new LocationModel(
//                        000000000000,
//                        "aaaaaaaaaaaaaaaaaaa",
//                        new ArrayList<LocationType>() {
//                            {
//                                add(LocationType.aaaaaaaaaaaaaaaaaaa);
//                                add(LocationType.aaaaaaaaaaaaaaaaaaa);
//                            }
//                        },
//                        "aaaaaaaaaaaaaaaaaaa",
//                        100,
//                        new ArrayList<RequirementModel>(),
//                        new WorldTile(0000, 0000, 0),
//                        new WorldTile(0000, 0000, 0),
//                        null
//                ));
            }
        };
    }

}
