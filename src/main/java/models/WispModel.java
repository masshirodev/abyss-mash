package models;

import kraken.plugin.api.Vector3i;

import java.util.ArrayList;
import java.util.Arrays;

public class WispModel {

    public WispModel(int id, ArrayList<Integer> itemId, int npcId, int energyId, String name, String location, int level) {
        this.Id = id;
        this.ItemIds = itemId;
        this.NpcId = npcId;
        this.EnergyId = energyId;
        this.Name = name;
        this.Location = location;
        this.Level = level;
    }

    public int Id;
    public ArrayList<Integer> ItemIds;
    public int NpcId;
    public int EnergyId;
    public String Name;
    public String Location;
    public int Level;

    public static String[] GetWispListToCombo() {
        return new String[] {
                "Pale Wisp",
                "Flickering Wisp",
                "Bright Wisp",
                "Glowing Wisp",
                "Sparkling Wisp",
                "Gleaming Wisp",
                "Vibrant Wisp",
                "Lustrous Wisp",
                "Brilliant Wisp",
                "Radiant Wisp",
                "Luminous Wisp",
                "Incandescent Wisp",
        };
    }

    public static ArrayList<WispModel> GetWispList() {
        return new ArrayList<WispModel>() {
            {
                add(new WispModel(
                        1,
                        new ArrayList<Integer>(Arrays.asList(29384)),
                        18150,
                        29313,
                        "Pale Wisp",
                        "Draynor Divination Pool",
                        0
                ));
                add(new WispModel(
                        2,
                        new ArrayList<Integer>(Arrays.asList(29396, 29385)),
                        18151,
                        29314,
                        "Flickering Wisp",
                        "Falador Northeast Divination Pool",
                        10
                ));
                add(new WispModel(
                        3,
                        new ArrayList<Integer>(Arrays.asList(29386, 29397)),
                        18153,
                        29315,
                        "Bright Wisp",
                        "Varrock Southeast Divination Pool",
                        20
                ));
                add(new WispModel(
                        4,
                        new ArrayList<Integer>(Arrays.asList(29387, 29398)),
                        18155,
                        29316,
                        "Glowing Wisp",
                        "Sorcerer's Tower Divination Pool",
                        30
                ));
                add(new WispModel(
                        5,
                        new ArrayList<Integer>(Arrays.asList(29388, 29399)),
                        18157,
                        29317,
                        "Sparkling Wisp",
                        "Rellekka Southeast Divination Pool",
                        40
                ));
                add(new WispModel(
                        6,
                        new ArrayList<Integer>(Arrays.asList(29389, 29400)),
                        18159,
                        29318,
                        "Gleaming Wisp",
                        "Karamja Divination Pool",
                        50
                ));
                add(new WispModel(
                        7,
                        new ArrayList<Integer>(Arrays.asList(29390, 29401)),
                        18161,
                        29319,
                        "Vibrant Wisp",
                        "Oo'glog Divination Pool",
                        60
                ));
                add(new WispModel(
                        8,
                        new ArrayList<Integer>(Arrays.asList(29391, 29402)),
                        18163,
                        29320,
                        "Lustrous Wisp",
                        "Canifis Divination Pool",
                        70
                ));
                add(new WispModel(
                        9,
                        new ArrayList<Integer>(Arrays.asList(29392, 29403)),
                        18165,
                        29321,
                        "Brilliant Wisp",
                        "Mage Training Area's Divination Pool",
                        80
                ));
                add(new WispModel(
                        10,
                        new ArrayList<Integer>(Arrays.asList(29393, 29404)),
                        18167,
                        29322,
                        "Radiant Wisp",
                        "Dragontooth Island",
                        85
                ));
                add(new WispModel(
                        11,
                        new ArrayList<Integer>(Arrays.asList(29405, 29394)),
                        18192,
                        29323,
                        "Luminous Wisp",
                        "Menaphos Divination Pool",
                        90
                ));
                add(new WispModel(
                        12,
                        new ArrayList<Integer>(Arrays.asList(29406, 29395)),
                        18171,
                        29324,
                        "Incandescent Wisp",
                        "Tirannwn Divination Pool",
                        95
                ));
            }
        };
    }
}
