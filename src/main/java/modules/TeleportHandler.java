package modules;

import abyss.plugin.api.actions.ActionHelper;
import abyss.plugin.api.actions.MenuAction;
import abyss.plugin.api.input.InputHelper;
import abyss.plugin.api.teleport.Lodestones;
import enums.World;
import helpers.Helper;
import helpers.Log;
import kraken.plugin.api.*;
import kraken.plugin.mash.MashToolbox;
import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;

public class TeleportHandler {
    public static int LODESTONE_ID = 1092;
    public static int LODESTONE_NETWORK_ID = 1465;

    public static void Execute(Lodestones lodestone) {
        if (Players.self().getAnimationId() != 0) return;
        if (!Widgets.isOpen(LODESTONE_ID)) {
            ActionHelper.INSTANCE.menu(
                    MenuAction.WIDGET,
                    1,
                    -1,
                    96010258
            );
            Helper.Wait(2000);
            return;
        }

        if (World.IsF2P(Client.getWorld()) && lodestone.isMembers() || !lodestone.isAvailable()) {
            Log.Information(MessageFormat.format("Can't teleport to {0}.", lodestone.name()));
            return;
        }

        if (lodestone.getKey() != ' ') {
            InputHelper.typeCharLiteral(lodestone.getKey());
        }

        Helper.Wait(3000);

        if (Widgets.isOpen(LODESTONE_ID)) {
            ActionHelper.INSTANCE.menu(
                    MenuAction.WIDGET,
                    lodestone.getParam1(), lodestone.getParam2(), lodestone.getParam3()
            );
        }
    }

    public static Lodestones Get(@NotNull String lodestone) {
        switch (lodestone) {
            case "Lumbridge":
                return Lodestones.LUMBRIDGE;
            case "Al Kharid":
                return Lodestones.KHARID;
            case "Draynor":
                return Lodestones.DRAYNOR;
            case "Port Sarim":
                return Lodestones.PORT_SARIM;
            case "Varrock":
                return Lodestones.VARROCK;
            case "Falador":
                return Lodestones.FALADOR;
            case "Edgeville":
                return Lodestones.EDGEVILLE;
            case "Burthorpe":
                return Lodestones.BURTHORPE;
            case "Taverly":
                return Lodestones.TAVERLY;
            case "Catherby":
                return Lodestones.CATHERBY;
            case "Seers":
                return Lodestones.SEERS;
            case "Canifis":
                return Lodestones.CANIFIS;
            case "Yanille":
                return Lodestones.YANILLE;
            case "Oo'glog":
                return Lodestones.OOGLOG;
            case "Ardougne":
                return Lodestones.ARDOUGNE;
            case "Wilderness":
                return Lodestones.WILDY;
            case "Fremennik Province":
                return Lodestones.FREMENNIK_PROVINCE;
            case "Eagle's Peak":
                return Lodestones.EAGLES_PEAK;
            case "Karamja":
                return Lodestones.KARAMAJA;
            case "Bandit Camp":
                return Lodestones.BANDIT_CAMP;
            case "Lunar Isle":
                return Lodestones.LUNAR_ISLE;
            case "Prifddinas":
                return Lodestones.PRIFDDINAS;
            case "Anachronia":
                return Lodestones.ANACHRONIA;
            case "Tirannwn":
                return Lodestones.TIRANNWIN;
        }

        return null;
    }
}
