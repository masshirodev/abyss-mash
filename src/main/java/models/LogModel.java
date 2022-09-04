package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import enums.LocationType;

import java.util.ArrayList;
import java.util.Arrays;

public class LogModel {

    public LogModel(int id, ArrayList<Integer> objectId, String name, ArrayList<RequirementModel> requirements, String[] locationList, String closestDeposit, LocationType depositType) {
        this.Id = id;
        this.ObjectId = objectId;
        this.Name = name;
        this.Requirements = requirements;
        this.LocationList = locationList;
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
    @JsonProperty("LocationList")
    public String[] LocationList;

    public static String[] GetLogListToCombo() {
        return new String[] {
                "Logs",
                "Achey log",
                "Oak log",
                "Willow log",
                "Teak log",
                "Maple log",
                "Mahogany log",
                "Yew log",
                "Magic log",
                "Ivy",
                "Overgrown idols"
        };
    }

    public static ArrayList<LogModel> GetLogList() {
        return new ArrayList<LogModel>(){
            {
                add(new LogModel(
                        1,
                        new ArrayList<Integer>(Arrays.asList(
                                38793, 38782, 39145, 38788, 39145, 39119, 38783, 39137,
                                38760, 38785, 38795, 38787, 39097, 39121, 38783, 38787, 38784
                        )),
                        "Logs",
                        new ArrayList<RequirementModel>() {
                            {
                                add(new RequirementModel(
                                        "Level",
                                        "WOODCUTTING",
                                        1,
                                        true,
                                        ""
                                ));
                            }
                        },
                        new String[] { "Lumbridge" },
                        "Lumbridge",
                        LocationType.Bank
                ));
            }
        };
    }
}
