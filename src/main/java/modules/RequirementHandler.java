package modules;

import helpers.Skills;
import models.ManualLocationModel;
import models.RequirementModel;

import java.util.ArrayList;

public class RequirementHandler {
    public static boolean LevelsMet(ArrayList<RequirementModel> requirements) {
        ArrayList<Boolean> checks = new ArrayList<>();
        for (RequirementModel req : requirements){
            if (!req.Type.equals("Level")) continue;
            boolean higher = Skills.GetLevel(req.Param1) >= req.ThresholdInt;
            boolean met = higher == req.ThresholdBool;
            checks.add(met);
        }

        return !checks.contains(false);
    }

    public static boolean InsideArea(ArrayList<RequirementModel> requirements) {
        ManualLocationModel current = ManualMovementHandler.GetCurrentArea();
        if (current == null)
            return false;

        for (RequirementModel req : requirements)
            if (req.Type.equals("InArea"))
                return (current.Name.equals(req.Param1)) == req.ThresholdBool;

        return false;
    }
}
