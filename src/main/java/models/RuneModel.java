package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import enums.LocationType;

import java.util.ArrayList;
import java.util.Arrays;

public class RuneModel {

    public RuneModel(int id, ArrayList<Integer> altarId, ArrayList<Integer> riftId, String name, ArrayList<RequirementModel> requirements) {
        this.Id = id;
        this.AltarId = altarId;
        this.RiftId = riftId;
        this.Name = name;
        this.Requirements = requirements;
    }

    @JsonProperty("Id")
    public int Id;
    @JsonProperty("AltarId")
    public ArrayList<Integer> AltarId;
    @JsonProperty("RiftId")
    public ArrayList<Integer> RiftId;
    @JsonProperty("Name")
    public String Name;
    @JsonProperty("Requirements")
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

    public static ArrayList<RuneModel> GetRuneList() {
        return new ArrayList<RuneModel>(){
            {
                add(new RuneModel(
                        1,
                        new ArrayList<Integer>(Arrays.asList(2478)),
                        new ArrayList<Integer>(Arrays.asList(7139)),
                        "Air Rune",
                        new ArrayList<RequirementModel>()
                ));
                add(new RuneModel(
                        2,
                        new ArrayList<Integer>(Arrays.asList(2480)),
                        new ArrayList<Integer>(Arrays.asList(7137)),
                        "Water Rune",
                        new ArrayList<RequirementModel>()
                ));
                add(new RuneModel(
                        3,
                        new ArrayList<Integer>(Arrays.asList(2483)),
                        new ArrayList<Integer>(Arrays.asList(7131)),
                        "Body Rune",
                        new ArrayList<RequirementModel>()
                ));
                add(new RuneModel(
                        4,
                        new ArrayList<Integer>(Arrays.asList(2481)),
                        new ArrayList<Integer>(Arrays.asList(7130)),
                        "Earth Rune",
                        new ArrayList<RequirementModel>()
                ));
                add(new RuneModel(
                        5,
                        new ArrayList<Integer>(Arrays.asList(2482)),
                        new ArrayList<Integer>(Arrays.asList(7129)),
                        "Fire Rune",
                        new ArrayList<RequirementModel>()
                ));
                add(new RuneModel(
                        6,
                        new ArrayList<Integer>(Arrays.asList(2484)),
                        new ArrayList<Integer>(Arrays.asList(7132)),
                        "Cosmic Rune",
                        new ArrayList<RequirementModel>()
                ));
                add(new RuneModel(
                        7,
                        new ArrayList<Integer>(Arrays.asList(2486)),
                        new ArrayList<Integer>(Arrays.asList(7133)),
                        "Nature Rune",
                        new ArrayList<RequirementModel>()
                ));
                add(new RuneModel(
                        8,
                        new ArrayList<Integer>(Arrays.asList(2487)),
                        new ArrayList<Integer>(Arrays.asList(7134)),
                        "Chaos Rune",
                        new ArrayList<RequirementModel>()
                ));
                add(new RuneModel(
                        9,
                        new ArrayList<Integer>(Arrays.asList(2479)),
                        new ArrayList<Integer>(Arrays.asList(7140)),
                        "Mind Rune",
                        new ArrayList<RequirementModel>()
                ));
                add(new RuneModel(
                        10,
                        new ArrayList<Integer>(Arrays.asList(2485)),
                        new ArrayList<Integer>(Arrays.asList(7135)),
                        "Law Rune",
                        new ArrayList<RequirementModel>()
                ));
                add(new RuneModel(
                        11,
                        new ArrayList<Integer>(Arrays.asList(2488)),
                        new ArrayList<Integer>(Arrays.asList(7136)),
                        "Death Rune",
                        new ArrayList<RequirementModel>()
                ));
                add(new RuneModel(
                        12,
                        new ArrayList<Integer>(Arrays.asList(109429)),
                        new ArrayList<Integer>(Arrays.asList(7138)),
                        "Soul Rune",
                        new ArrayList<RequirementModel>()
                ));
                add(new RuneModel(
                        13,
                        new ArrayList<Integer>(Arrays.asList(30624)),
                        new ArrayList<Integer>(Arrays.asList(7141)),
                        "Blood Rune",
                        new ArrayList<RequirementModel>()
                ));
            }
        };
    }
}
