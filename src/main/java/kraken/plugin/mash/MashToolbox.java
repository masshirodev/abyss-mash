package kraken.plugin.mash;

import abyss.plugin.api.input.InputHelper;
import com.google.common.base.Stopwatch;
import helpers.Helper;
import kraken.plugin.api.*;

import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;

public final class MashToolbox extends Plugin {
    public static boolean antiAfk = true;
    public static Stopwatch afkTimer = Stopwatch.createUnstarted();

    public MashToolbox() {
    }

    @Override
    public boolean onLoaded(PluginContext pluginContext) {
        pluginContext.setName("MashToolbox");
//        pluginContext.category = "Mash";
        return true;
    }

    @Override
    public int onLoop() {
        if (Players.self() == null) return 1000;

        if (antiAfk)
            AntiAfk();

        return 1000;
    }

    private void AntiAfk() {
        if (!afkTimer.isRunning())
            afkTimer.start();

        if (afkTimer.elapsed(TimeUnit.MINUTES) > 2) {
            ExecuteRandomAction();
            afkTimer.reset();
        }
    }

    private void ExecuteRandomAction() {
        int value = Helper.RandomInt(1, 2);

        switch (value) {
            // B - Inventario
            case 1:
                InputHelper.typeKey(0x42);
                Helper.Wait(500);
                InputHelper.typeKey(0x42);
                return;
            // H - Gear
            case 2:
            default:
                InputHelper.typeKey(0x48);
                Helper.Wait(500);
                InputHelper.typeKey(0x48);
                return;
        }

    }

    @Override
    public void onPaint() {
        ImGui.label("");
        ImGui.label("MashToolbox");
        ImGui.label("");
        if (Players.self() != null) {
            if (ImGui.beginTabBar("MainTabs")) {
                if (ImGui.beginTabItem("Main")) {
                    PaintMain();
                    ImGui.endTabItem();
                }
                if (ImGui.beginTabItem("Debug")) {
                    PaintDebug();
                    ImGui.endTabItem();
                }

                ImGui.endTabBar();
                ImGui.label("");
            }
        } else {
            ImGui.label("Log into the game first!");
        }
    }

    private void PaintMain() {
        antiAfk = ImGui.checkbox("Anti AFK", antiAfk);
        if (afkTimer != null) {
            ImGui.label(MessageFormat.format("afkTimer: {0}", afkTimer.elapsed(TimeUnit.MILLISECONDS)));
        }
    }

    private void PaintDebug() {
        ImGui.label("");
    }
}