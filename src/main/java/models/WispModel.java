package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import kraken.plugin.api.Vector3i;

import java.util.ArrayList;

public class WispModel {

    public WispModel(int id, ArrayList<Integer> itemId, int npcId, int energyId, String name, Vector3i location, String teleport, int level, String type) {
        this.id = id;
        this.itemId = itemId;
        this.npcId = npcId;
        this.energyId = energyId;
        this.name = name;
        this.location = location;
        this.teleport = teleport;
        this.level = level;
        this.type = type;
    }

    @JsonProperty("Id")
    public int id;
    @JsonProperty("ItemId")
    public ArrayList<Integer> itemId;
    @JsonProperty("NpcId")
    public int npcId;
    @JsonProperty("EnergyId")
    public int energyId;
    @JsonProperty("Name")
    public String name;
    @JsonProperty("Location")
    public Vector3i location;
    @JsonProperty("Teleport")
    public String teleport;
    @JsonProperty("Level")
    public int level;
    @JsonProperty("type")
    public String type;
}
