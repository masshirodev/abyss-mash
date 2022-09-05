package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;

public class EnemyModel {
    public EnemyModel(int id, ArrayList<Integer> objectId, String name, String location, String teleport, ArrayList<RequirementModel> requirements, String closestDeposit, Pair<String, Integer>[] drops) {
        this.id = id;
        this.enemyId = objectId;
        this.name = name;
        this.location = location;
        this.teleport = teleport;
        this.requirements = requirements;
        this.closestDeposit = closestDeposit;
        this.drops = drops;
    }

    @JsonProperty("Id")
    public int id;
    @JsonProperty("ObjectId")
    public ArrayList<Integer> enemyId;
    @JsonProperty("Name")
    public String name;
    @JsonProperty("Location")
    public String location;
    @JsonProperty("Teleport")
    public String teleport;
    @JsonProperty("Requirements")
    public ArrayList<RequirementModel> requirements;
    @JsonProperty("ClosestDeposit")
    public String closestDeposit;
    @JsonProperty("Drops")
    public Pair<String, Integer>[] drops;

    public static String[] GetEnemyListToCombo() {
        return new String[] {
                "Cow",
                "Turoth"
        };
    }

    public static ArrayList<EnemyModel> GetEnemyList() {
        return new ArrayList<EnemyModel>(){
            {
                add(new EnemyModel(
                        1,
                        new ArrayList<Integer>(Arrays.asList(14997, 14998, 14999)),
                        "Cow",
                        "Burthorpe",
                        "Burthorpe",
                        new ArrayList<RequirementModel>(),
                        "Burthorpe Bank",
                        new Pair[] {
                                new Pair<String, Integer>("Raw Beef", 2132),
                                new Pair<String, Integer>("Bones", 526),
                                new Pair<String, Integer>("Cow Hide", 1739)
                        }
                ));
                add(new EnemyModel(
                        1,
                        new ArrayList<Integer>(Arrays.asList(1626, 1627, 1623)),
                        "Turoth",
                        "Fremennik Slayer Dungeon",
                        "Fremennik Province",
                        new ArrayList<RequirementModel>(),
                        "Burthorpe Bank",
                        new Pair[] {
//                                new Pair<String, Integer>("Raw Beef", 2132),
//                                new Pair<String, Integer>("Bones", 526),
//                                new Pair<String, Integer>("Cow Hide", 1739)
                        }
                ));
            }
        };
    }
}
