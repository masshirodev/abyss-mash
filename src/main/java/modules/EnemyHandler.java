package modules;

import helpers.Log;
import kraken.plugin.api.*;
import models.EnemyModel;

import java.text.MessageFormat;
import java.util.Arrays;

public class EnemyHandler {
    private static Npc currentEnemy;
    private static EnemyModel enemyChosen;
    private static Npc[] enemies;

    public static void Execute(EnemyModel enemy, Npc[] enemyEntities) {
        enemyChosen = enemy;
        enemies = enemyEntities;
        ManualMovementHandler.movementIndex = 0;

        if (currentEnemy != null && !enemy.enemyId.contains(currentEnemy.getId()))
            currentEnemy = null;

        Log.Information(MessageFormat.format("Hunting {0}s.", enemyChosen.name));
        switch(enemyChosen.name) {
            default:
                Kill();
        }
    }

    private static void Kill() {
        Player player = Players.self();
        Npc closest = Npcs.closest(x -> enemyChosen.enemyId.contains(x.getId()));
        GroundItem drop = GroundItems.closest(x -> Arrays.stream(enemyChosen.drops).anyMatch(y -> x.getId() == y.getValue()));

        if (drop != null){
            Log.Information("Picking up drops.");
//            GroundItemHandler.PickItem(drop);
            drop.interact(Actions.MENU_EXECUTE_GROUND_ITEM3);
            return;
        }

        if (currentEnemy == null || !currentEnemy.isStatusBarActive(0)) {
            Log.Information("Setting new enemy as the target.");
            currentEnemy = closest;
            currentEnemy.interact(Actions.MENU_EXECUTE_NPC2);
        }

        if (player.getHealth() < 1000) {
            Log.Information("Healing.");
        }
    }
}
