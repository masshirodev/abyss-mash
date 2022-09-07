package models;

import abyss.plugin.api.world.WorldTile;
import com.fasterxml.jackson.annotation.JsonProperty;
import enums.LocationType;

import java.util.ArrayList;
import java.util.Arrays;

public class ArchaeologyModel {
    public ArchaeologyModel(
            int id,
            String name,
            ArrayList<Integer> nodeIds,
            ArrayList<Integer> trashIds,
            ArrayList<RequirementModel> requirements,
            String location,
            WorldTile closestCacheDeposit,
            ArrayList<Integer> closestCacheDepositIds,
            String closestBankDeposit,
            LocationType depositType
    ) {
        this.Id = id;
        this.Name = name;
        this.NodeIds = nodeIds;
        this.TrashIds = trashIds;
        this.Requirements = requirements;
        this.Location = location;
        this.ClosestCacheDeposit = closestCacheDeposit;
        this.ClosestCacheDepositIds = closestCacheDepositIds;
        this.ClosestBankDeposit = closestBankDeposit;
        this.DepositType = depositType;
    }

    public int Id;
    public ArrayList<Integer> NodeIds;
    public ArrayList<Integer> TrashIds;
    public String Name;
    public ArrayList<RequirementModel> Requirements;
    public String Location;
    public WorldTile ClosestCacheDeposit;
    public ArrayList<Integer> ClosestCacheDepositIds;
    public String ClosestBankDeposit;
    public LocationType DepositType;

    public static String[] GetArchCacheListToCombo() {
        return new String[] {
                "Third Age iron",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                ""
        };
    }

    public static String[] GetArchArtefactListToCombo() {
        return new String[] {
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                ""
        };
    }

    public static ArrayList<ArchaeologyModel> GetArchList() {
        return new ArrayList<ArchaeologyModel>() {
            {
                add(new ArchaeologyModel(
                        1,
                    "Third Age iron",
                    new ArrayList<Integer>(Arrays.asList(115426)),
                    new ArrayList<Integer>(Arrays.asList()),
                    new ArrayList<RequirementModel>(),
                    "Varrock Material cache (Third Age iron)",
                    new WorldTile(3361, 3397, 0),
                    new ArrayList<Integer>(Arrays.asList(115422)),
                    "Archaeology Guild Bank Chest",
                    LocationType.ArchaeologyBank
                ));
            }
        };
    }
}
