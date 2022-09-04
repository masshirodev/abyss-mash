package models;

public class RequirementModel {
    public RequirementModel(
            String type,
            String param1,
            int thresholdInt,
            boolean thresholdBool,
            String thresholdString
    ) {
        this.Type = type;
        this.Param1 = param1;
        this.ThresholdInt = thresholdInt;
        this.ThresholdBool = thresholdBool;
        this.ThresholdString = thresholdString;
    }

    public String Type;
    public String Param1;
    public int ThresholdInt;
    public boolean ThresholdBool;
    public String ThresholdString;
}
