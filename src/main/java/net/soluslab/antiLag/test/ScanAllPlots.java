package net.soluslab.antiLag.test;


import com.plotsquared.core.PlotAPI;
import com.plotsquared.core.location.BlockLoc;
import com.plotsquared.core.plot.Plot;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.soluslab.antiLag.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class ScanAllPlots implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Set<Plot> plots = new PlotAPI().getAllPlots();
            List<Component> High = new ArrayList<>();
            List<Component> Middle = new ArrayList<>();
            List<Component> Low = new ArrayList<>();
            for (Plot plot : plots) {

                int entityCount = Arrays.stream(plot.countEntities()).sum();
                Main.getInstance().getLogger().info("Plot ID: " + plot.getId());
                Main.getInstance().getLogger().info("Entity Count: " + Arrays.toString(plot.countEntities()));
                BlockLoc PlotPosition = plot.getPosition();
                World PlotWorld = Bukkit.getWorld(Objects.requireNonNull(plot.getWorldName()));

                String OwnerName = Bukkit.getOfflinePlayer(plot.getOwner()).getName();
                if (OwnerName == null || OwnerName.isEmpty()) {
                    OwnerName = "User not found";
                }
                String message = "§7- Plot ID: " + plot.getId() + " | Number of Entities: §c" + entityCount + " §7| Plot Owner: " + OwnerName;
                Component textComponent = Component.text(message)
                        .clickEvent(ClickEvent.runCommand("/p visit " + plot.getId()))
                        .hoverEvent(HoverEvent.showText(Component.text("Teleport to the Plot")));
                if (entityCount > 500) {
                    High.add(textComponent);
                } else if (entityCount > 100) {
                    Middle.add(textComponent);
                } else {
                    Low.add(textComponent);
                }

            }

            Component message = Component.text()
                    .append(Component.text("§cHigh Entity Count Plots: " + High.size() + "\n"))
                    .append(High.isEmpty() ? Component.text("§aNo Plots\n") : Component.join(Component.newline(), High))
                    .append(Component.text("\n§bMiddle Entity Count Plots: " + Middle.size() + "\n"))
                    .append(Middle.isEmpty() ? Component.text("§aNo Plots\n") : Component.join(Component.newline(), Middle))
                    .append(Component.text("\n§aLow Entity Count Plots: " + Low.size() + "\n"))
                    .append(Low.isEmpty() ? Component.text("§aNo Plots") : Component.join(Component.newline(), Low))
                    .build();
            
            sender.sendMessage(message);

            /*
            High Entity Count Plots:
            - Plot Coords: X: 12, Y:13, Z: 12 | Number of Entity's: 500 | Plot Owner
             */

        }
        return true;
    }

    private ClickEvent createTeleportClickEvent(Player player, World plotWorld, BlockLoc coords) {
        return ClickEvent.callback(audience -> {
            Teleport(player, plotWorld, coords);
        });
    }

    private static void Teleport(Player p, World plotWorld, BlockLoc coords) {
        Location loc = new Location(plotWorld, coords.getX(), 0, coords.getZ());
        p.teleport(loc);
    }
}
