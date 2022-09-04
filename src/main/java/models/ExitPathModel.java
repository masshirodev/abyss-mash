package models;

public class ExitPathModel {
    public ExitPathModel(
            int index,
            String type,
            int action,
            int entityId
    ) {
        this.Index = index;
        this.Type = type;
        this.Action = action;
        this.EntityId = entityId;
    }

    public int Index;
    public String Type;
    public int Action;
    public int EntityId;
}
