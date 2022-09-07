package models;

import java.util.ArrayList;
import java.util.Arrays;

public class RuneModel {

    public RuneModel(int id, String name, ArrayList<Integer> itemIds, ArrayList<Integer> altarIds, ArrayList<Integer> riftId, ArrayList<RequirementModel> requirements) {
        this.Id = id;
        this.Name = name;
        this.ItemIds = itemIds;
        this.AltarIds = altarIds;
        this.RiftIds = riftId;
        this.Requirements = requirements;
    }

    public int Id;
    public String Name;
    public ArrayList<Integer> ItemIds;
    public ArrayList<Integer> AltarIds;
    public ArrayList<Integer> RiftIds;
    public ArrayList<RequirementModel> Requirements;

    public static String[] GetRuneListToCombo() {
        return new String[] {
                "Air Rune",
                "Water Rune",
                "Body Rune",
                "Earth Rune",
                "Fire Rune",
                "Cosmic Rune",
                "Nature Rune",
                "Chaos Rune",
                "Mind Rune",
                "Law Rune",
                "Death Rune",
                "Soul Rune",
                "Blood Rune"
        };
    }

    public static ArrayList<Integer> GetItemIds() {
        ArrayList<Integer> result = new ArrayList<>();
        for (RuneModel rune : GetRuneList()) {
            result.addAll(rune.ItemIds);
        }

        return result;
    }

    public static ArrayList<RuneModel> GetRuneList() {
        return new ArrayList<RuneModel>(){
            {
                add(new RuneModel(
                        1,
                        "Air Rune",
                        new ArrayList<Integer>(Arrays.asList(556)),
                        new ArrayList<Integer>(Arrays.asList(2478)),
                        new ArrayList<Integer>(Arrays.asList(7139)),
                        new ArrayList<RequirementModel>()
                ));
                add(new RuneModel(
                        2,
                        "Water Rune",
                        new ArrayList<Integer>(Arrays.asList(555)),
                        new ArrayList<Integer>(Arrays.asList(2480)),
                        new ArrayList<Integer>(Arrays.asList(7137)),
                        new ArrayList<RequirementModel>()
                ));
                add(new RuneModel(
                        3,
                        "Body Rune",
                        new ArrayList<Integer>(Arrays.asList(559)),
                        new ArrayList<Integer>(Arrays.asList(2483)),
                        new ArrayList<Integer>(Arrays.asList(7131)),
                        new ArrayList<RequirementModel>()
                ));
                add(new RuneModel(
                        4,
                        "Earth Rune",
                        new ArrayList<Integer>(Arrays.asList(557)),
                        new ArrayList<Integer>(Arrays.asList(2481)),
                        new ArrayList<Integer>(Arrays.asList(7130)),
                        new ArrayList<RequirementModel>()
                ));
                add(new RuneModel(
                        5,
                        "Fire Rune",
                        new ArrayList<Integer>(Arrays.asList(554)),
                        new ArrayList<Integer>(Arrays.asList(2482)),
                        new ArrayList<Integer>(Arrays.asList(7129)),
                        new ArrayList<RequirementModel>()
                ));
                add(new RuneModel(
                        6,
                        "Cosmic Rune",
                        new ArrayList<Integer>(Arrays.asList(564)),
                        new ArrayList<Integer>(Arrays.asList(2484)),
                        new ArrayList<Integer>(Arrays.asList(7132)),
                        new ArrayList<RequirementModel>()
                ));
                add(new RuneModel(
                        7,
                        "Nature Rune",
                        new ArrayList<Integer>(Arrays.asList(561)),
                        new ArrayList<Integer>(Arrays.asList(2486)),
                        new ArrayList<Integer>(Arrays.asList(7133)),
                        new ArrayList<RequirementModel>()
                ));
                add(new RuneModel(
                        8,
                        "Chaos Rune",
                        new ArrayList<Integer>(Arrays.asList(562)),
                        new ArrayList<Integer>(Arrays.asList(2487)),
                        new ArrayList<Integer>(Arrays.asList(7134)),
                        new ArrayList<RequirementModel>()
                ));
                add(new RuneModel(
                        9,
                        "Mind Rune",
                        new ArrayList<Integer>(Arrays.asList(558)),
                        new ArrayList<Integer>(Arrays.asList(2479)),
                        new ArrayList<Integer>(Arrays.asList(7140)),
                        new ArrayList<RequirementModel>()
                ));
                add(new RuneModel(
                        10,
                        "Law Rune",
                        new ArrayList<Integer>(Arrays.asList(563)),
                        new ArrayList<Integer>(Arrays.asList(2485)),
                        new ArrayList<Integer>(Arrays.asList(7135)),
                        new ArrayList<RequirementModel>()
                ));
                add(new RuneModel(
                        11,
                        "Death Rune",
                        new ArrayList<Integer>(Arrays.asList(560)),
                        new ArrayList<Integer>(Arrays.asList(2488)),
                        new ArrayList<Integer>(Arrays.asList(7136)),
                        new ArrayList<RequirementModel>()
                ));
                add(new RuneModel(
                        12,
                        "Soul Rune",
                        new ArrayList<Integer>(Arrays.asList(566)),
                        new ArrayList<Integer>(Arrays.asList(109429)),
                        new ArrayList<Integer>(Arrays.asList(7138)),
                        new ArrayList<RequirementModel>()
                ));
                add(new RuneModel(
                        13,
                        "Blood Rune",
                        new ArrayList<Integer>(Arrays.asList(565)),
                        new ArrayList<Integer>(Arrays.asList(30624)),
                        new ArrayList<Integer>(Arrays.asList(7141)),
                        new ArrayList<RequirementModel>()
                ));
            }
        };
    }
}
