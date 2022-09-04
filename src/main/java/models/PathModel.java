package models;

import abyss.plugin.api.world.WorldTile;

import java.util.ArrayList;

public class PathModel {
    public PathModel(
            int id,
            ArrayList<WorldTile> poordinates,
            ArrayList<RequirementModel> requirements
    ) {
        this.Id = id;
        this.Coordinates = poordinates;
        this.Requirements = requirements;
    }

    public int Id;
    public ArrayList<WorldTile> Coordinates;
    public ArrayList<RequirementModel> Requirements;
}
