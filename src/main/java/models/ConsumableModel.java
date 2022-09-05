package models;

import enums.ConsumableType;
import enums.LocationType;
import kraken.plugin.api.Inventory;
import kraken.plugin.api.WidgetItem;

import java.util.ArrayList;
import java.util.Arrays;

public class ConsumableModel {
    public ConsumableModel(
            int id,
            String name,
            ArrayList<ConsumableType> type,
            ArrayList<Integer> itemIds
    ) {
        this.Id = id;
        this.Name = name;
        this.Type = type;
        this.ItemIds = itemIds;
    }

    public int Id;
    public String Name;
    public ArrayList<ConsumableType> Type;
    public ArrayList<Integer> ItemIds;

    public static ConsumableModel GetConsumableByName(String name) {
        ArrayList<ConsumableModel> all = GetConsumableList();
        for (ConsumableModel current : all) {
            if (current.Name.equals(name))
                return current;
        }

        return null;
    }

    public static ArrayList<ConsumableModel> GetConsumableByType(ConsumableType type) {
        ArrayList<ConsumableModel> all = GetConsumableList();
        ArrayList<ConsumableModel> result = new ArrayList<>();
        for (ConsumableModel current : all) {
            if (current.Type.contains(type))
                result.add(current);
        }

        return result;
    }


    public static WidgetItem GetFirstInInventoryOfType(ConsumableType type) {
        ArrayList<ConsumableModel> all = GetConsumableByType(type);

        for (ConsumableModel item : all) {
            WidgetItem inv = Inventory.first(x -> item.ItemIds.contains(x.getId()));
            if (inv != null)
                return inv;
        }

        return null;
    }

    private static ArrayList<ConsumableModel> GetConsumableList() {
        return new ArrayList<ConsumableModel>() {
            {
                add(new ConsumableModel(
                        1,
                        "Overload",
                        new ArrayList<ConsumableType>(Arrays.asList(ConsumableType.Buff, ConsumableType.Overload)),
                        new ArrayList<Integer>(Arrays.asList(15332, 15333, 15334, 15335))
                ));
                add(new ConsumableModel(
                        2,
                        "Overload flask",
                        new ArrayList<ConsumableType>(Arrays.asList(ConsumableType.Buff, ConsumableType.Overload)),
                        new ArrayList<Integer>(Arrays.asList(23531, 23532, 23533, 23534, 23535, 23536))
                ));
                add(new ConsumableModel(
                        3,
                        "Overload salve",
                        new ArrayList<ConsumableType>(Arrays.asList(ConsumableType.Buff, ConsumableType.Overload)),
                        new ArrayList<Integer>(Arrays.asList(33188, 33190, 33192, 33194, 33196, 33198))
                ));
                add(new ConsumableModel(
                        4,
                        "Supreme overload potion",
                        new ArrayList<ConsumableType>(Arrays.asList(ConsumableType.Buff, ConsumableType.Overload)),
                        new ArrayList<Integer>(Arrays.asList(33200, 33202, 33204, 33206, 33208, 33210))
                ));
                add(new ConsumableModel(
                        5,
                        "Supreme overload salve",
                        new ArrayList<ConsumableType>(Arrays.asList(ConsumableType.Buff, ConsumableType.Overload)),
                        new ArrayList<Integer>(Arrays.asList(33212, 33214, 33216, 33218, 33220, 33222))
                ));
                add(new ConsumableModel(
                        6,
                        "Holy overload potion",
                        new ArrayList<ConsumableType>(Arrays.asList(ConsumableType.Buff, ConsumableType.Overload)),
                        new ArrayList<Integer>(Arrays.asList(33236, 33238, 33240, 33242, 33244, 33246))
                ));
                add(new ConsumableModel(
                        7,
                        "Searing overload potion",
                        new ArrayList<ConsumableType>(Arrays.asList(ConsumableType.Buff, ConsumableType.Overload)),
                        new ArrayList<Integer>(Arrays.asList(33248, 33250, 33252, 33254, 33256, 33258))
                ));
                add(new ConsumableModel(
                        8,
                        "Deathmatch supreme overload salve",
                        new ArrayList<ConsumableType>(Arrays.asList(ConsumableType.Buff, ConsumableType.Overload)),
                        new ArrayList<Integer>(Arrays.asList(38502, 38503, 38504, 38505, 38506, 38507))
                ));
                add(new ConsumableModel(
                        9,
                        "Aggroverload",
                        new ArrayList<ConsumableType>(Arrays.asList(ConsumableType.Buff, ConsumableType.Overload, ConsumableType.Aggression)),
                        new ArrayList<Integer>(Arrays.asList(48229, 48231, 48233, 48235, 48237, 48239))
                ));
                add(new ConsumableModel(
                        10,
                        "Elder overload potion",
                        new ArrayList<ConsumableType>(Arrays.asList(ConsumableType.Buff, ConsumableType.Overload)),
                        new ArrayList<Integer>(Arrays.asList(49029, 49031, 49033, 49035, 49037, 49039))
                ));
                add(new ConsumableModel(
                        11,
                        "Elder overload salve",
                        new ArrayList<ConsumableType>(Arrays.asList(ConsumableType.Buff, ConsumableType.Overload)),
                        new ArrayList<Integer>(Arrays.asList(49042, 49044, 49046, 49048, 49050, 49052))
                ));
                add(new ConsumableModel(
                        12,
                        "Holy aggroverload",
                        new ArrayList<ConsumableType>(Arrays.asList(ConsumableType.Buff, ConsumableType.Overload, ConsumableType.Aggression)),
                        new ArrayList<Integer>(Arrays.asList(50867, 50869, 50871, 50873, 50875, 50877, 50879, 50909))
                ));
                add(new ConsumableModel(
                        13,
                        "Prayer renewal",
                        new ArrayList<ConsumableType>(Arrays.asList(ConsumableType.Prayer)),
                        new ArrayList<Integer>(Arrays.asList(21630, 21632, 21634, 21636))
                ));
                add(new ConsumableModel(
                        14,
                        "Prayer flask",
                        new ArrayList<ConsumableType>(Arrays.asList(ConsumableType.Prayer)),
                        new ArrayList<Integer>(Arrays.asList(23243, 23245, 23247, 23249, 23251, 23253))
                ));
                add(new ConsumableModel(
                        15,
                        "Super prayer flask",
                        new ArrayList<ConsumableType>(Arrays.asList(ConsumableType.Prayer)),
                        new ArrayList<Integer>(Arrays.asList(23525, 23526, 23527, 23528, 23529, 23530))
                ));
                add(new ConsumableModel(
                        16,
                        "Prayer renewal flask",
                        new ArrayList<ConsumableType>(Arrays.asList(ConsumableType.Prayer)),
                        new ArrayList<Integer>(Arrays.asList(23609, 23611, 23613, 23615, 23617, 23619))
                ));
                add(new ConsumableModel(
                        17,
                        "Super prayer renewal potion",
                        new ArrayList<ConsumableType>(Arrays.asList(ConsumableType.Prayer)),
                        new ArrayList<Integer>(Arrays.asList(33176, 33178, 33180, 33182, 33184, 33186))
                ));
                add(new ConsumableModel(
                        18,
                        "Super warmaster's potion",
                        new ArrayList<ConsumableType>(Arrays.asList(ConsumableType.Buff)),
                        new ArrayList<Integer>(Arrays.asList(33020, 33022, 33024, 33026, 33028, 33030))
                ));
                add(new ConsumableModel(
                        19,
                        "Extreme warmaster's potion",
                        new ArrayList<ConsumableType>(Arrays.asList(ConsumableType.Buff)),
                        new ArrayList<Integer>(Arrays.asList(33092, 33094, 33096, 33098, 33100, 33102))
                ));
                add(new ConsumableModel(
                        20,
                        "Aggression flask",
                        new ArrayList<ConsumableType>(Arrays.asList(ConsumableType.Aggression)),
                        new ArrayList<Integer>(Arrays.asList(37929, 37931, 37933, 37935, 37937, 37939))
                ));
                add(new ConsumableModel(
                        21,
                        "Aggression potion",
                        new ArrayList<ConsumableType>(Arrays.asList(ConsumableType.Aggression)),
                        new ArrayList<Integer>(Arrays.asList(37965, 37967, 37969, 37971))
                ));
                add(new ConsumableModel(
                        22,
                        "Saradomin brew",
                        new ArrayList<ConsumableType>(Arrays.asList(ConsumableType.Health, ConsumableType.Buff)),
                        new ArrayList<Integer>(Arrays.asList(6685, 6687, 6689, 6691, 000000, 000000))
                ));
                add(new ConsumableModel(
                        23,
                        "Saradomin brew flask",
                        new ArrayList<ConsumableType>(Arrays.asList(ConsumableType.Health, ConsumableType.Buff)),
                        new ArrayList<Integer>(Arrays.asList(23351, 23353, 23355, 23357, 23359, 23361))
                ));
                add(new ConsumableModel(
                        24,
                        "Super Saradomin brew",
                        new ArrayList<ConsumableType>(Arrays.asList(ConsumableType.Health, ConsumableType.Buff)),
                        new ArrayList<Integer>(Arrays.asList(28191, 28193, 28195, 28197))
                ));
                add(new ConsumableModel(
                        25,
                        "Super Saradomin brew flask",
                        new ArrayList<ConsumableType>(Arrays.asList(ConsumableType.Health, ConsumableType.Buff)),
                        new ArrayList<Integer>(Arrays.asList(28227, 28229, 28231, 28233, 28235, 28237))
                ));
                add(new ConsumableModel(
                        26,
                        "Skewered kebab",
                        new ArrayList<ConsumableType>(Arrays.asList(ConsumableType.Health)),
                        new ArrayList<Integer>(Arrays.asList(15123))
                ));
                add(new ConsumableModel(
                        27,
                        "Tomato",
                        new ArrayList<ConsumableType>(Arrays.asList(ConsumableType.Health)),
                        new ArrayList<Integer>(Arrays.asList(43783))
                ));
                add(new ConsumableModel(
                        28,
                        "Papaya fruit",
                        new ArrayList<ConsumableType>(Arrays.asList(ConsumableType.Health)),
                        new ArrayList<Integer>(Arrays.asList(43800))
                ));
                add(new ConsumableModel(
                        29,
                        "Chopped onion",
                        new ArrayList<ConsumableType>(Arrays.asList(ConsumableType.Health)),
                        new ArrayList<Integer>(Arrays.asList(1871))
                ));
                add(new ConsumableModel(
                        30,
                        "Roll",
                        new ArrayList<ConsumableType>(Arrays.asList(ConsumableType.Health)),
                        new ArrayList<Integer>(Arrays.asList(6963))
                ));
                add(new ConsumableModel(
                        31,
                        "Doughnut",
                        new ArrayList<ConsumableType>(Arrays.asList(ConsumableType.Health)),
                        new ArrayList<Integer>(Arrays.asList(14665))
                ));
                add(new ConsumableModel(
                        32,
                        "Square sandwich",
                        new ArrayList<ConsumableType>(Arrays.asList(ConsumableType.Health)),
                        new ArrayList<Integer>(Arrays.asList(51176))
                ));
                add(new ConsumableModel(
                        33,
                        "Bread",
                        new ArrayList<ConsumableType>(Arrays.asList(ConsumableType.Health)),
                        new ArrayList<Integer>(Arrays.asList(2309))
                ));
                add(new ConsumableModel(
                        34,
                        "Kebab",
                        new ArrayList<ConsumableType>(Arrays.asList(ConsumableType.Health)),
                        new ArrayList<Integer>(Arrays.asList(1971))
                ));
                add(new ConsumableModel(
                        35,
                        "Crayfish",
                        new ArrayList<ConsumableType>(Arrays.asList(ConsumableType.Health)),
                        new ArrayList<Integer>(Arrays.asList(13433))
                ));
                add(new ConsumableModel(
                        36,
                        "Shrimps",
                        new ArrayList<ConsumableType>(Arrays.asList(ConsumableType.Health)),
                        new ArrayList<Integer>(Arrays.asList(315))
                ));
                add(new ConsumableModel(
                        37,
                        "Sardine",
                        new ArrayList<ConsumableType>(Arrays.asList(ConsumableType.Health)),
                        new ArrayList<Integer>(Arrays.asList(325))
                ));
                add(new ConsumableModel(
                        38,
                        "Cooked meat",
                        new ArrayList<ConsumableType>(Arrays.asList(ConsumableType.Health)),
                        new ArrayList<Integer>(Arrays.asList(2142))
                ));
                add(new ConsumableModel(
                        39,
                        "Swordfish",
                        new ArrayList<ConsumableType>(Arrays.asList(ConsumableType.Health)),
                        new ArrayList<Integer>(Arrays.asList(373))
                ));
                add(new ConsumableModel(
                        40,
                        "Desert sole",
                        new ArrayList<ConsumableType>(Arrays.asList(ConsumableType.Health)),
                        new ArrayList<Integer>(Arrays.asList(40293))
                ));
                add(new ConsumableModel(
                        41,
                        "Catfish",
                        new ArrayList<ConsumableType>(Arrays.asList(ConsumableType.Health)),
                        new ArrayList<Integer>(Arrays.asList(40295))
                ));
                add(new ConsumableModel(
                        42,
                        "Monkfish",
                        new ArrayList<ConsumableType>(Arrays.asList(ConsumableType.Health)),
                        new ArrayList<Integer>(Arrays.asList(7946))
                ));
                add(new ConsumableModel(
                        43,
                        "Beltfish",
                        new ArrayList<ConsumableType>(Arrays.asList(ConsumableType.Health)),
                        new ArrayList<Integer>(Arrays.asList(40297))
                ));
                add(new ConsumableModel(
                        44,
                        "Cooked eeligator",
                        new ArrayList<ConsumableType>(Arrays.asList(ConsumableType.Health)),
                        new ArrayList<Integer>(Arrays.asList(35199))
                ));
                add(new ConsumableModel(
                        45,
                        "Shark",
                        new ArrayList<ConsumableType>(Arrays.asList(ConsumableType.Health)),
                        new ArrayList<Integer>(Arrays.asList(385))
                ));
                add(new ConsumableModel(
                        46,
                        "Sailfish",
                        new ArrayList<ConsumableType>(Arrays.asList(ConsumableType.Health)),
                        new ArrayList<Integer>(Arrays.asList(42251))
                ));
                add(new ConsumableModel(
                        47,
                        "Rocktail",
                        new ArrayList<ConsumableType>(Arrays.asList(ConsumableType.Health)),
                        new ArrayList<Integer>(Arrays.asList(15272))
                ));
                add(new ConsumableModel(
                        48,
                        "Great white shark",
                        new ArrayList<ConsumableType>(Arrays.asList(ConsumableType.Health)),
                        new ArrayList<Integer>(Arrays.asList(34729))
                ));
            }
        };
    }
}
