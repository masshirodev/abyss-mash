package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import enums.LocationType;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;

public class OreModel {

    public OreModel(int id, ArrayList<Integer> objectId, String name, ArrayList<RequirementModel> requirements, String[] mineList, String closestDeposit, LocationType depositType) {
        this.Id = id;
        this.ObjectId = objectId;
        this.Name = name;
        this.Requirements = requirements;
        this.MineList = mineList;
        this.ClosestDeposit = closestDeposit;
        this.DepositType = depositType;
    }

    @JsonProperty("Id")
    public int Id;
    @JsonProperty("ObjectId")
    public ArrayList<Integer> ObjectId;
    @JsonProperty("Name")
    public String Name;
    @JsonProperty("Requirements")
    public ArrayList<RequirementModel> Requirements;
    @JsonProperty("ClosestDeposit")
    public String ClosestDeposit;
    @JsonProperty("DepositType")
    public LocationType DepositType;
    @JsonProperty("MineList")
    public String[] MineList;

    public static String[] GetOreListToCombo() {
        return new String[] {
                "Copper Ore",
                "Tin Ore",
                "Iron Ore",
                "Clay",
                "Silver Ore",
//                "Coal",
                "Mithril Ore",
                "Adamantite Ore",
                "Luminite",
                "Runite Ore",
                "Orichalcite Ore",
                "Drakolith",
                "Phasmatite",
                "Necrite Ore",
                "Precious Gem",
                "Banite Ore",
                "Light Animica",
                "Dark Animica"
        };
    }

    public static ArrayList<OreModel> GetOreList() {
        return new ArrayList<OreModel>(){
            {
                add(new OreModel(
                        1,
                        new ArrayList<Integer>(Arrays.asList(113026, 113027, 113028)),
                        "Copper Ore",
                        new ArrayList<RequirementModel>() {
                            {
                                add(new RequirementModel(
                                        "Level",
                                        "MINING",
                                        1,
                                        true,
                                        ""
                                ));
                            }
                        },
                        new String[] { "Burthorpe Mine" },
                        "Burthorpe Furnace",
                        LocationType.MetalBank
                ));
                add(new OreModel(
                        2,
                        new ArrayList<Integer>(Arrays.asList(113030, 113031)),
                        "Tin Ore",
                        new ArrayList<RequirementModel>() {
                            {
                                add(new RequirementModel(
                                        "Level",
                                        "MINING",
                                        1,
                                        true,
                                        ""
                                ));
                            }
                        },
                        new String[] { "Burthorpe Mine" },
                        "Burthorpe Furnace",
                        LocationType.MetalBank
                ));
                add(new OreModel(
                        3,
                        new ArrayList<Integer>(Arrays.asList(113038, 113039, 113040)),
                        "Iron Ore",
                        new ArrayList<RequirementModel>() {
                            {
                                add(new RequirementModel(
                                        "Level",
                                        "MINING",
                                        1,
                                        true,
                                        ""
                                ));
                            }
                        },
                        new String[] { "Burthorpe Mine" },
                        "Burthorpe Furnace",
                        LocationType.MetalBank
                ));
                add(new OreModel(
                        4,
                        new ArrayList<Integer>(Arrays.asList(113032, 113033, 113034)),
                        "Clay",
                        new ArrayList<RequirementModel>() {
                            {
                                add(new RequirementModel(
                                        "Level",
                                        "MINING",
                                        1,
                                        true,
                                        ""
                                ));
                            }
                        },
                        new String[] { "Burthorpe Mine" },
                        "Burthorpe Furnace",
                        LocationType.MetalBank
                ));
                add(new OreModel(
                        5,
                        new ArrayList<Integer>(Arrays.asList(113044, 113045, 113046)),
                        "Silver Ore",
                        new ArrayList<RequirementModel>() {
                            {
                                add(new RequirementModel(
                                        "Level",
                                        "MINING",
                                        20,
                                        true,
                                        ""
                                ));
                            }
                        },
                        new String[] { "Burthorpe Mine" },
                        "Burthorpe Furnace",
                        LocationType.MetalBank
                ));
//                add(new OreModel(
//                        6,
//                        new ArrayList<Integer>(Arrays.asList(113101, 113102, 113103)),
//                        "Coal",
//                        new ArrayList<RequirementModel>() {
//                            {
//                                add(new RequirementModel(
//                                        "Level",
//                                        "MINING",
//                                        20,
//                                        true,
//                                        ""
//                                ));
//                            }
//                        },
//                        new String[] { "Falador Mining Site" },
//                        "Falador Clan Chest",
//                        LocationType.MetalBank
//                ));
                add(new OreModel(
                        7,
                        new ArrayList<Integer>(Arrays.asList(113050, 113051, 113052)),
                        "Mithril Ore",
                        new ArrayList<RequirementModel>() {
                            {
                                add(new RequirementModel(
                                        "Level",
                                        "MINING",
                                        30,
                                        true,
                                        ""
                                ));
                            }
                        },
                        new String[] { "Varrock Southwest Mine" },
                        "Varrock Metal Bank",
                        LocationType.MetalBank
                ));
                add(new OreModel(
                        8,
                        new ArrayList<Integer>(Arrays.asList(113055, 113053)),
                        "Adamantite Ore",
                        new ArrayList<RequirementModel>() {
                            {
                                add(new RequirementModel(
                                        "Level",
                                        "MINING",
                                        40,
                                        true,
                                        ""
                                ));
                            }
                        },
                        new String[] { "Varrock Southeast Mine" },
                        "Varrock Metal Bank",
                        LocationType.MetalBank
                ));
                add(new OreModel(
                        9,
                        new ArrayList<Integer>(Arrays.asList(113056, 113057, 113058)),
                        "Luminite",
                        new ArrayList<RequirementModel>() {
                            {
                                add(new RequirementModel(
                                        "Level",
                                        "MINING",
                                        40,
                                        true,
                                        ""
                                ));
                            }
                        },
                        new String[] { "Ourania Battlefield Mine" },
                        "Al Kharid Metal Bank",
                        LocationType.MetalBank
                ));
                add(new OreModel(
                        10,
                        new ArrayList<Integer>(Arrays.asList(113067, 113066, 113065)),
                        "Runite Ore",
                        new ArrayList<RequirementModel>() {
                            {
                                add(new RequirementModel(
                                        "Level",
                                        "MINING",
                                        50,
                                        true,
                                        ""
                                ));
                            }
                        },
                        new String[] { "Dwarven Mine" },
                        "Artisan's Workshop",
                        LocationType.MetalBank
                ));
                add(new OreModel(
                        11,
                        new ArrayList<Integer>(Arrays.asList(113070, 113069)),
                        "Orichalcite Ore",
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
                        new String[] { "Dwarven Mine" },
                        "Artisan's Workshop",
                        LocationType.MetalBank
                ));
                add(new OreModel(
                        12,
                        new ArrayList<Integer>(Arrays.asList(113071, 113072, 113073)),
                        "Drakolith",
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
                        new String[] { "Mining Guild Resource Dungeon" },
                        "Artisan's Workshop",
                        LocationType.MetalBank
                ));
                add(new OreModel(
                        13,
                        new ArrayList<Integer>(Arrays.asList(113137, 113138, 113139)),
                        "Phasmatite",
                        new ArrayList<RequirementModel>() {
                            {
                                add(new RequirementModel(
                                        "Level",
                                        "MINING",
                                        70,
                                        true,
                                        ""
                                ));
                            }
                        },
                        new String[] { "Port Phasmatys South Mine" },
                        "Burthorpe Furnace",
                        LocationType.MetalBank
                ));
                add(new OreModel(
                        14,
                        new ArrayList<Integer>(Arrays.asList(113206, 113207, 113208)),
                        "Necrite Ore",
                        new ArrayList<RequirementModel>() {
                            {
                                add(new RequirementModel(
                                        "Level",
                                        "MINING",
                                        70,
                                        true,
                                        ""
                                ));
                            }
                        },
                        new String[] { "Al Kharid Resource Dungeon" },
                        "Al Kharid Metal Bank",
                        LocationType.MetalBank
                ));
                add(new OreModel(
                        15,
                        new ArrayList<Integer>(Arrays.asList(113062, 113063, 113064)),
                        "Precious Gem",
                        new ArrayList<RequirementModel>() {
                            {
                                add(new RequirementModel(
                                        "Level",
                                        "MINING",
                                        70,
                                        true,
                                        ""
                                ));
                            }
                        },
                        new String[] { "Al Kharid Resource Dungeon" },
                        "Al Kharid Bank",
                        LocationType.Bank
                ));
                add(new OreModel(
                        16,
                        new ArrayList<Integer>(Arrays.asList(113203, 113204, 113205)),
                        "Banite Ore",
                        new ArrayList<RequirementModel>() {
                            {
                                add(new RequirementModel(
                                        "Level",
                                        "MINING",
                                        80,
                                        true,
                                        ""
                                ));
                            }
                        },
                        new String[] { "Arctic Habitat Mine" },
                        "Al Kharid Metal Bank",
                        LocationType.MetalBank
                ));
                add(new OreModel(
                        17,
                        new ArrayList<Integer>(Arrays.asList(113018)),
                        "Light Animica",
                        new ArrayList<RequirementModel>() {
                            {
                                add(new RequirementModel(
                                        "Level",
                                        "MINING",
                                        90,
                                        true,
                                        ""
                                ));
                            }
                        },
                        new String[] { "Anachronia Southwest Mine" },
                        "Al Kharid Metal Bank",
                        LocationType.MetalBank
                ));
                add(new OreModel(
                        18,
                        new ArrayList<Integer>(Arrays.asList(113020, 113021, 113022)),
                        "Dark Animica",
                        new ArrayList<RequirementModel>() {
                            {
                                add(new RequirementModel(
                                        "Level",
                                        "MINING",
                                        90,
                                        true,
                                        ""
                                ));
                            }
                        },
                        new String[] { "Empty Throne Room Mine" },
                        "Al Kharid Metal Bank",
                        LocationType.MetalBank
                ));
            }
        };
    }
}
