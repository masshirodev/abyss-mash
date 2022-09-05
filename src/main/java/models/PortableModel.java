package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import enums.LocationType;
import javafx.util.Pair;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;

public class PortableModel {

    public PortableModel(
            int id,
            String name,
            String skill,
            @Nullable ArrayList<Integer> objectIds,
            @Nullable ArrayList<Integer> itemIds
    ) {
        this.Id = id;
        this.Name = name;
        this.Skill = skill;
        this.ObjectIds = objectIds;
        this.ItemIds = itemIds;
    }

    public int Id;
    public String Name;
    public String Skill;
    public ArrayList<Integer>  ObjectIds;
    public ArrayList<Integer>  ItemIds;

    public static PortableModel GetPortableByName(String name) {
        ArrayList<PortableModel> all = GetPortableList();
        for (PortableModel current : all) {
            if (current.Name.equals(name))
                return current;
        }

        return null;
    }

    public static ArrayList<PortableModel> GetLocationListBySkill(String skill) {
        ArrayList<PortableModel> all = GetPortableList();
        ArrayList<PortableModel> result = new ArrayList<>();
        for (PortableModel current : all) {
            if (current.Skill == skill)
                result.add(current);
        }

        return result;
    }

    public static String[] GetPortableSkillListToCombo() {
        return new String[] {
                "Firemaking",
                "Crafting",
                "Fletching",
                "Cooking",
                "Herblore",
                "Construction",
//                "Protean"
        };
    }
    public static String[] GetPortableListToCombo() {
        return new String[] {
                "Portable brazier",
                "Portable crafter",
                "Portable fletcher",
                "Portable range",
                "Portable well",
                "Portable workbench",
//                "Protean"
        };
    }

    public static ArrayList<PortableModel> GetPortableList() {
        return new ArrayList<PortableModel>() {
            {
                add(new PortableModel(
                        1,
                        "Portable brazier",
                        "Firemaking",
                        new ArrayList<Integer>(Arrays.asList(98286)),
                        null
                ));
                add(new PortableModel(
                        2,
                        "Portable crafter",
                        "Crafting",
                        new ArrayList<Integer>(Arrays.asList(98284)),
                        null
                ));
                add(new PortableModel(
                        3,
                        "Portable fletcher",
                        "Fletching",
                        new ArrayList<Integer>(Arrays.asList(98285)),
                        null
                ));
                add(new PortableModel(
                        4,
                        "Portable range",
                        "Cooking",
                        new ArrayList<Integer>(Arrays.asList(89768)),
                        null
                ));
                add(new PortableModel(
                        5,
                        "Portable well",
                        "Herblore",
                        new ArrayList<Integer>(Arrays.asList(89770)),
                        null
                ));
                add(new PortableModel(
                        6,
                        "Portable workbench",
                        "Construction",
                        new ArrayList<Integer>(Arrays.asList(117926)),
                        null
                ));
//                add(new PortableModel(
//                        7,
//                        "Protean",
//                        "Any",
//                        null,
//                        new ArrayList<Integer>(Arrays.asList())
//                ));
            }
        };
    }
}
