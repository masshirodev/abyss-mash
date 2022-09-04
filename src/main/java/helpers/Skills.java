package helpers;

import enums.XP;
import kraken.plugin.api.Client;
import kraken.plugin.api.Stat;

import java.text.MessageFormat;
import java.util.Objects;

public class Skills {
    public static Stat GetStats(String skill) {
        switch (skill){
            case "ATTACK":
                return Client.getStatById(Client.ATTACK);
            case "DEFENSE":
                return Client.getStatById(Client.DEFENSE);
            case "STRENGTH":
                return Client.getStatById(Client.STRENGTH);
            case "HITPOINTS":
                return Client.getStatById(Client.HITPOINTS);
            case "RANGE":
                return Client.getStatById(Client.RANGE);
            case "PRAYER":
                return Client.getStatById(Client.PRAYER);
            case "MAGIC":
                return Client.getStatById(Client.MAGIC);
            case "COOKING":
                return Client.getStatById(Client.COOKING);
            case "WOODCUTTING":
                return Client.getStatById(Client.WOODCUTTING);
            case "FLETCHING":
                return Client.getStatById(Client.FLETCHING);
            case "FISHING":
                return Client.getStatById(Client.FISHING);
            case "FIREMAKING":
                return Client.getStatById(Client.FIREMAKING);
            case "CRAFTING":
                return Client.getStatById(Client.CRAFTING);
            case "SMITHING":
                return Client.getStatById(Client.SMITHING);
            case "MINING":
                return Client.getStatById(Client.MINING);
            case "HERBLORE":
                return Client.getStatById(Client.HERBLORE);
            case "AGILITY":
                return Client.getStatById(Client.AGILITY);
            case "THIEVING":
                return Client.getStatById(Client.THIEVING);
            case "SLAYER":
                return Client.getStatById(Client.SLAYER);
            case "FARMING":
                return Client.getStatById(Client.FARMING);
            case "RUNECRAFTING":
                return Client.getStatById(Client.RUNECRAFTING);
            case "HUNTER":
                return Client.getStatById(Client.HUNTER);
            case "CONSTRUCTION":
                return Client.getStatById(Client.CONSTRUCTION);
            case "SUMMONING":
                return Client.getStatById(Client.SUMMONING);
            case "DUNGEONEERING":
                return Client.getStatById(Client.DUNGEONEERING);
            case "DIVINATION":
                return Client.getStatById(Client.DIVINATION);
            case "INVENTION":
                return Client.getStatById(Client.INVENTION);
            case "ARCHAEOLOGY":
                return Client.getStatById(Client.ARCHAEOLOGY);
            default:
                return null;
        }
    }

    public static int GetLevel(String skill) {
        int level = 1;
        if (!skill.equals("INVENTION")) {
            int xp = GetStats(skill).getXp();

            for (int i = 1; i < 100; i++) {
                if (XP.valueOf(MessageFormat.format("LEVEL{0}", i)).getValue() < xp)
                    level = i;
                else
                    break;
            }
        }

        return level;
    }
}
