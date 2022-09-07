package models;

import java.util.ArrayList;
import java.util.Arrays;

public class RunePouchModel {
    public RunePouchModel(
            int id,
            String name,
            ArrayList<Integer> itemIds,
            int insideConVar,
            int durabilityConVar,
            int cap,
            boolean enabled
    ) {
        this.Id = id;
        this.Name = name;
        this.ItemIds = itemIds;
        this.InsideConVar = insideConVar;
        this.DurabilityConVar = durabilityConVar;
        this.Cap = cap;
        this.Enabled = enabled;
    }

    public int Id;
    public String Name;
    public ArrayList<Integer> ItemIds;
    public int InsideConVar;
    public int DurabilityConVar;
    public int Cap;
    public boolean Enabled;

    public static ArrayList<RunePouchModel> GetRunecraftingPouches() {
        return new ArrayList<RunePouchModel>() {
            {
                add(new RunePouchModel(
                        1,
                        "Small Pouch",
                        new ArrayList<Integer>(Arrays.asList(5509)),
                        16497,
                        0,
                        3,
                        false
                ));
                add(new RunePouchModel(
                        2,
                        "Medium Pouch",
                        new ArrayList<Integer>(Arrays.asList(5510, 5511)),
                        16498,
                        0,
                        6,
                        false
                ));
                add(new RunePouchModel(
                        3,
                        "Large Pouch",
                        new ArrayList<Integer>(Arrays.asList(5512, 5513)),
                        16499,
                        0,
                        9,
                        false
                ));
                add(new RunePouchModel(
                        4,
                        "Giant Pouch",
                        new ArrayList<Integer>(Arrays.asList(5514, 5515)),
                        16500,
                        0,
                        12,
                        false
                ));
                add(new RunePouchModel(
                        5,
                        "Massive Pouch",
                        new ArrayList<Integer>(Arrays.asList(24204, 24205)),
                        16521,
                        0,
                        18,
                        false
                ));
            }
        };
    }
}
