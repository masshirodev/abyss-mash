package modules;

import abyss.plugin.api.teleport.Lodestones;
import abyss.plugin.api.world.WorldTile;
import enums.LocationType;
import helpers.Log;
import kraken.plugin.api.Move;
import kraken.plugin.api.Player;
import kraken.plugin.api.Players;
import kraken.plugin.api.TraverseContext;
import models.LocationModel;
import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;
import java.util.ArrayList;

public class MovementHandler {
    public static void GoTo(@NotNull LocationModel location) {
        Player player = Players.self();
//        Lodestones teleport = TeleportHandler.Get(location.Teleport);
//        boolean tooFar = location.Coordinate.distance(player.getGlobalPosition()) > location.TeleportDistance;
//        LocationModel currentInside = GetCurrentAreaIfType(LocationType.Disconnected);
//
//        if (tooFar && currentInside == null) {
//            Log.Information(MessageFormat.format("Teleporting to {0}.", teleport.name()));
//            if (teleport.isAvailable())
//                TeleportHandler.Execute(teleport);
//            return;
//        }

        // Move
        Log.Information(MessageFormat.format("Currently moving to {0}.", location.Coordinate));
        if (location.Coordinate.distance(player.getGlobalPosition()) > 5 || location.Area.contains(player.getGlobalPosition())) {
            TraverseContext context = Move.startTraverse(location.Coordinate);

            if (!player.isMoving())
                Move.traverse(context);
        }
    }

    public static LocationModel GetCurrentArea() {
        ArrayList<LocationModel> list = LocationModel.GetLocationList();
        Player player = Players.self();
        for (LocationModel local : list) {
            if (local.Area == null) continue;
            if (local.Area.contains(player))
                return local;
        }

        return null;
    }

    public static LocationModel GetCurrentAreaIfType(LocationType type) {
        ArrayList<LocationModel> list = LocationModel.GetLocationListByType(type);
        Player player = Players.self();
        for (LocationModel local : list) {
            if (local.Area == null) continue;
            if (local.Area.contains(player))
                return local;
        }

        return null;
    }

    public static void MoveAwayFrom(WorldTile position) {

    }
}
