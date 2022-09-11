package modules;

import abyss.plugin.api.teleport.Lodestones;
import abyss.plugin.api.world.WorldTile;
import enums.LocationType;
import helpers.Log;
import kraken.plugin.api.*;
import models.CustomPathModel;
import models.ExitPathModel;
import models.ManualLocationModel;
import models.PathModel;
import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Random;

public class ManualMovementHandler {
    public static int movementIndex = 0;
    public static boolean ignoreExits = false;
    public static Stopwatch exitTimeout = new Stopwatch();

    public static void GoTo(@NotNull ManualLocationModel location) {
        switch (location.Execute) {
            case "Traverse":
                Traverse(location);
                break;
            default:
                ExecutePath(location);
                break;
        }
    }

    private static void MoveTo(WorldTile coord) {
        Random rand = new Random();
        int x = rand.nextInt(coord.getX()-1, coord.getX()+1);
        int y = rand.nextInt(coord.getY()-1, coord.getY()+1);

        WorldTile newCoord = new WorldTile(x, y, 0);
        Move.to(newCoord);
    }

    private static void Traverse(ManualLocationModel location) {
        PathModel pathInfo = ManualMovementHandler.DecideBestPath(location.Path);
        ArrayList<WorldTile> path = pathInfo.Coordinates;
        WorldTile coord = path.get(path.size()-1);
        Player player = Players.self();

        // Move
        Log.Information(MessageFormat.format("Currently moving to index {0}.", coord));
        if (coord.distance(player.getGlobalPosition()) > 5) {
            if (!player.isMoving())
                MoveTo(coord);
        }
    }

    private static void ExecutePath(ManualLocationModel location) {
        PathModel pathInfo = ManualMovementHandler.DecideBestPath(location.Path);
        ArrayList<WorldTile> path = pathInfo.Coordinates;
        WorldTile coord = path.get(movementIndex);
        Lodestones teleport = TeleportHandler.Get(location.Teleport);
        Player player = Players.self();
        ManualLocationModel currentInside = GetCurrentAreaIfType(LocationType.Disconnected);

        if (movementIndex == 0) {
            if (exitTimeout.elapsed() == 0)
                exitTimeout.start();

            if (exitTimeout.elapsed() > 30000)
                ignoreExits = true;

            // Iterate through exits
            if (currentInside != null && currentInside.ExitPath.size() > 0) {
                for (ExitPathModel exit : currentInside.ExitPath) {
                    ExecuteExitPath(exit);
                }

                return;
            }

            // Get the closest coordinate if index == 0
            boolean isFar = path.get(GetClosestIndex(path)).distance(Players.self().getGlobalPosition()) > location.TeleportDistance;
            if (isFar) {
                Log.Information(MessageFormat.format("Teleporting to {0}.", teleport.name()));
                if (teleport.isAvailable())
                    TeleportHandler.Execute(teleport);
                return;
            }

            if (exitTimeout.elapsed() > 0)
                exitTimeout = new Stopwatch();

            movementIndex = GetClosestIndex(path);
            Log.Information(MessageFormat.format("Changing movement index to {0}.", movementIndex));
        }

        // Check for custom path
        if (location.CustomPath.size() > 0) {
            for (CustomPathModel custom : location.CustomPath) {
                if (custom.PathId == 0) continue; // PathId == 0 means an exit.
                if (custom.Index == movementIndex && custom.PathId == pathInfo.Id) {
                    int distanceToNext = path.get(movementIndex+1).distance(Players.self().getGlobalPosition());
                    ExecuteCustomIndex(custom, distanceToNext);
                    return;
                }
            }
        }

        // Move
        Log.Information(MessageFormat.format("Currently moving to index {0}.", movementIndex));
        if (coord.distance(player.getGlobalPosition()) > 5) {
            if (!player.isMoving()) {

                MoveTo(coord);
            }

            return;
        }

        movementIndex = GetClosestNextIndex(path);
        coord = path.get(movementIndex);
        MoveTo(coord);
        Log.Information(MessageFormat.format("Changing movement index to {0}.", movementIndex));

        // Return 0 on path end
        if (movementIndex+1 > pathInfo.Coordinates.size())
            movementIndex = 0;
    }

    private static void ExecuteCustomIndex(CustomPathModel custom, int distanceToNext) {
        // Change movement index if distance is lower than threshold
        if (distanceToNext <= custom.DistanceToNext) {
            movementIndex++;
            return;
        }

        switch (custom.Type) {
            case "ObjectInteract":
                InteractHandler.WithObject(custom.EntityId, custom.Action);
                return;
            case "NpcInteract":
                InteractHandler.WithNpc(custom.EntityId, custom.Action);
        }
    }

    private static void ExecuteExitPath(ExitPathModel exit) {
        switch (exit.Type) {
            case "ObjectInteract":
                InteractHandler.WithObject(exit.EntityId, exit.Action);
                return;
            case "NpcInteract":
                InteractHandler.WithNpc(exit.EntityId, exit.Action);
        }
    }

    public static int GetClosestIndex(@NotNull ArrayList<WorldTile> array) {
        Player player = Players.self();
        int closest = 0;
        int distance = Integer.MAX_VALUE;
        for (int i = 0; i < array.size(); i++) {
            int currentDistance = array.get(i).distance(player.getGlobalPosition());
            if (currentDistance < distance) {
                closest = i;
                distance = currentDistance;
            }
        }

        return closest;
    }

    public static int GetClosestNextIndex(@NotNull ArrayList<WorldTile> array) {
        Player player = Players.self();
        int current = movementIndex;
        int closest = movementIndex;
        int distance = Integer.MAX_VALUE;
        for (int i = 0; i < array.size(); i++) {
            int currentDistance = array.get(i).distance(player.getGlobalPosition());
            if (currentDistance < distance && i > current) {
                closest = i;
                distance = currentDistance;
            }
        }

        return closest;
    }

    public static PathModel DecideBestPath(ArrayList<PathModel> paths) {
        for (PathModel path : paths) {
            boolean levelCheck = RequirementHandler.LevelsMet(path.Requirements);
            boolean areaCheck = RequirementHandler.InsideArea(path.Requirements);
            if (levelCheck && areaCheck)
                return path;
        }

        return paths.get(paths.size()-1);
    }

    public static ManualLocationModel GetCurrentArea() {
        ArrayList<ManualLocationModel> list = ManualLocationModel.GetLocationList();
        Player player = Players.self();
        for (ManualLocationModel local : list) {
            if (local.Area.contains(player))
                return local;
        }

        return null;
    }

    public static ManualLocationModel GetCurrentAreaIfType(LocationType type) {
        ArrayList<ManualLocationModel> list = ManualLocationModel.GetLocationListByType(type);
        Player player = Players.self();
        for (ManualLocationModel local : list) {
            if (local.Area.contains(player))
                return local;
        }

        return null;
    }
}
