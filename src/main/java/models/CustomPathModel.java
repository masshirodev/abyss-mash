package models;

import java.util.ArrayList;

public class CustomPathModel {
    public CustomPathModel(
        int pathId,
        int index,
        String type,
        int action,
        ArrayList<Integer> entityId,
        int distanceToNext,
        int goToIndex
    ) {
        this.PathId = pathId;
        this.Index = index;
        this.Type = type;
        this.Action = action;
        this.EntityId = entityId;
        this.DistanceToNext = distanceToNext;
        this.GoToIndex = goToIndex;
    }

    public int PathId;
    public int Index;
    public String Type;
    public int Action;
    public ArrayList<Integer> EntityId;
    public int DistanceToNext;
    public int GoToIndex;
}
