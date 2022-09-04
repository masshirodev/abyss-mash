package modules;

import enums.LocationType;
import helpers.Log;
import kraken.plugin.api.*;
import org.jetbrains.annotations.Nullable;

import java.text.MessageFormat;
import java.util.ArrayList;

public class BankHandler {
    public static void DecideDeposit(LocationType option, @Nullable Filter<WidgetItem> predicate) {
        switch (option) {
            case Bank:
                DepositBank(predicate);
                break;
            case MetalBank:
                DepositMetalBank();
                break;
        }
    }

    public static boolean BankNearby(LocationType option) {
        switch (option) {
            case Bank:
                return Npcs
                    .closest(x ->
                        GetBankIds().contains(x.getId()) &&
                        x.getGlobalPosition().distance(Players.self().getGlobalPosition()) < 30)
                    != null;
            case MetalBank:
                return SceneObjects
                    .closest(x ->
                        GetMetalBankIds().contains(x.getId()) &&
                        x.getGlobalPosition().distance(Players.self().getGlobalPosition()) < 30)
                    != null;
        }

        return false;
    }

    public static ArrayList<String> GetBankNames() {
        return new ArrayList<>() {
            {
                add("Banker");
                add("Bank booth");
                add("Bank chest");
                add("Gnome Banker");
            }
        };
    }

    public static ArrayList<Integer> GetBankIds() {
        return new ArrayList<>() {
            {
                add(42192);
                add(100248);
                add(11758);
                add(100247);
                add(19086);
                add(76274);
                add(79036);
                add(113739);
                add(25688);
                add(499);
                add(2759);
                add(553);
                add(42377);
                add(42378);
                add(496);
                add(76274);
            }
        };
    }

    public static ArrayList<Integer> GetMetalBankIds() {
        return new ArrayList<>() {
            {
                add(120050);
                add(113262);
                add(120048);
                add(67467);
                add(67465);
                add(113261);
                add(113259);
                add(113258);
                add(25688);
                add(76293);
            }
        };
    }

    public static ArrayList<String> GetMetalBankNames() {
        return new ArrayList<>() {
            {
                add("Furnace");
                add("Anvil");
                add("Forge");
            }
        };
    }

    private static boolean OpenBank() {
        Player player = Players.self();
        SceneObject bank = SceneObjects.closest(x ->
                (GetBankIds().contains(x.getId()) || GetBankNames().contains(x.getName())) &&
                        x.getGlobalPosition().distance(player.getGlobalPosition()) < 30
        );
        Npc npcBank = Npcs.closest(x ->
                (GetBankIds().contains(x.getId()) || GetBankNames().contains(x.getName())) &&
                        x.getGlobalPosition().distance(player.getGlobalPosition()) < 30
        );

        if (bank != null || npcBank != null) {
            if (!Widgets.isOpen(517)) {
                Log.Information("Opening bank window.");

                if (npcBank != null) {
                    Vector3i npcBankPos = npcBank.getGlobalPosition();
                    if (npcBankPos.distance(player.getGlobalPosition()) > 10)
                        Move.to(npcBankPos);
                    else
                        npcBank.interact(Actions.MENU_EXECUTE_NPC1);
                    return false;
                }

                Vector3i bankPos = bank.getGlobalPosition();
                if (bankPos.distance(player.getGlobalPosition()) > 10)
                    Move.to(bankPos);
                else
                    bank.interact(Actions.MENU_EXECUTE_OBJECT1);

                return false;
            }

            return true;
        }

        ManualMovementHandler.movementIndex = 0;
        return false;
    }

    private static void DepositBank(Filter<WidgetItem> predicate) {
        boolean ready = OpenBank();

        if (ready) {
            Log.Information("Depositing items.");
            Bank.deposit(predicate, 7);
        }
    }

    private static void DepositMetalBank() {
        SceneObject metalBank = SceneObjects.closest(x ->
                GetMetalBankIds().contains(x.getId()) || GetMetalBankNames().contains(x.getName())
        );

        if (metalBank != null) {
            Log.Information("Metal Bank found, interacting with it.");
            metalBank.interact(Actions.MENU_EXECUTE_OBJECT2);
        }

        ManualMovementHandler.movementIndex = 0;
    }

    private static void Withdraw(WidgetItem item, int option) {
        Actions.menu(Actions.MENU_EXECUTE_WIDGET, option, item.getSlot(), 33882307, 0);
    }

    public static void WithdrawAll(Filter<WidgetItem> predicate, int option) {
        boolean ready = OpenBank();

        if (ready) {
            Log.Information("Withdrawing items.");
            Bank.withdraw(predicate, option);
        }
    }

    public static void WithdrawFirst(Filter<WidgetItem> predicate, int option) {
        boolean ready = OpenBank();

        if (ready) {
            Log.Information("Withdrawing item.");
            WidgetItem item = Bank.first(predicate);

            if (item != null) {
                Withdraw(item, option);
                Log.Information(MessageFormat.format("Actions.menu({0}, {1}, {2}, 33882307, 0);", Actions.MENU_EXECUTE_WIDGET, option, item.getSlot(), item.getWidgetId()));
            }
        }
    }

    public static void WithdrawItem(int id) {

    }

    public static void WithdrawPreset(int number) {
        Player player = Players.self();
        SceneObject bank = SceneObjects.closest(x ->
                (GetBankIds().contains(x.getId()) || GetBankNames().contains(x.getName())) &&
                        x.getGlobalPosition().distance(player.getGlobalPosition()) < 30
        );
        Npc npcBank = Npcs.closest(x ->
                (GetBankIds().contains(x.getId()) || GetBankNames().contains(x.getName())) &&
                        x.getGlobalPosition().distance(player.getGlobalPosition()) < 30
        );

        if (bank != null || npcBank != null) {
            if (!Widgets.isOpen(517)) {
                Log.Information("Opening bank window.");

                if (npcBank != null) {
                    Vector3i npcBankPos = npcBank.getGlobalPosition();
                    if (npcBankPos.distance(player.getGlobalPosition()) > 10)
                        Move.to(npcBankPos);
                    else
                        npcBank.interact(Actions.MENU_EXECUTE_NPC1);
                    return;
                }

                Vector3i bankPos = bank.getGlobalPosition();
                if (bankPos.distance(player.getGlobalPosition()) > 10)
                    Move.to(bankPos);
                else
                    bank.interact(Actions.MENU_EXECUTE_OBJECT1);

                return;
            }

            Log.Information(MessageFormat.format("Withdrawing preset {0}.", number));
            Actions.menu(Actions.MENU_EXECUTE_WIDGET, 1, number, 33882231, 0);
        }

        ManualMovementHandler.movementIndex = 0;
    }
}
