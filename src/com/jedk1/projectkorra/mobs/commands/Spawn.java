package com.jedk1.projectkorra.mobs.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.jedk1.projectkorra.mobs.MobMethods;
import com.jedk1.projectkorra.mobs.ProjectKorraMobs;
import com.jedk1.projectkorra.mobs.object.Element;
import com.jedk1.projectkorra.mobs.object.SubElement;
import com.projectkorra.projectkorra.command.PKCommand;

public class Spawn extends PKCommand implements CommandExecutor {

	private String[] allSubs = new String[] { "ice", "lava", "metal", "combustion", "lightning" };

	public Spawn() {
		super("spawn", "/pkm spawn <entity type> [element] [x] [y] [z] [subelements...]",
				"Use this command to spawn a bending mob with a specified element. A random element will be picked if the element "
						+ "argument is empty",
				new String[] { "projectkorramobs", "pkmobs", "bm", "bmods", "bendingmobs" });
		PKCommand.instances.remove("spawn");
		ProjectKorraMobs.plugin.getCommand("pkm").setExecutor(this);
	}

	private String getRandomElement() {
		switch (new Random().nextInt(5)) {
		case 0:
			return "Water";
		case 1:
			return "Earth";
		case 2:
			return "Fire";
		case 3:
			return "Air";
		case 4:
			return "Avatar";
		default:
			return "Water";
		}
	}

	@Override
	public void execute(CommandSender sender, List<String> args) {
		if (!hasPermission(sender))
			return;

		if (!isPlayer(sender))
			return;

		Player player = (Player) sender;
		if (args.isEmpty()) {
			help(sender, true);
			return;
		}

		if (args.size() == 1) { // random element
			Element element = Element.valueOf(getRandomElement());
			if (!ProjectKorraMobs.plugin.getConfig().getStringList("Properties.EntityTypes")
					.contains(args.get(0).toUpperCase())) {
				sender.sendMessage(ChatColor.RED + "You can't spawn that entity!");
				return;
			}
			EntityType type = EntityType.valueOf(args.get(0).toUpperCase());
			Entity entity = player.getWorld().spawnEntity(player.getLocation(), type);
			MobMethods.assignElement(entity, element, allSubs);
			player.sendMessage(entity.getType() + ", " + element);
		} else if (args.size() == 2) { // element
			Element element = null;
			String elementString = args.get(1).toLowerCase();
			Character c = Character.toUpperCase(elementString.charAt(0));
			elementString = c + elementString.substring(1);
			try {
				element = Element.valueOf(elementString);
			} catch (IllegalArgumentException e) {
				player.sendMessage(ChatColor.RED + "That is not an element!");
				return;
			}
			String entityString = args.get(0).toUpperCase();
			if (!ProjectKorraMobs.plugin.getConfig().getStringList("Properties.EntityTypes")
					.contains(entityString.toUpperCase())) {
				sender.sendMessage(ChatColor.RED + "You can't spawn that entity!");
				return;
			}
			EntityType type = EntityType.valueOf(entityString);
			Entity entity = player.getWorld().spawnEntity(player.getLocation(), type);
			MobMethods.assignElement(entity, element, allSubs);
			player.sendMessage(entity.getType() + ", " + element);
			// <mob 0> <element 1> <x 2> <y 3> <z 4> <subs 5>

		} else if (args.size() >= 3 && args.size() <= 5) { // spawning with
															// coordinates
			double x = player.getLocation().getX();
			double y = player.getLocation().getY();
			double z = player.getLocation().getZ();
			if (args.get(2) != null && !args.get(2).equalsIgnoreCase("~")) // x
				try {
					x = Double.parseDouble(args.get(2));
				} catch (IllegalArgumentException e) {
					player.sendMessage("Please put a number as a coordinate.");
					return;
				}
			if (args.size() >= 4 && args.get(3) != null && !args.get(3).equalsIgnoreCase("~")) // y
				try {
					y = Double.parseDouble(args.get(3));
				} catch (IllegalArgumentException e) {
					player.sendMessage("Please put a number as a coordinate.");
					return;
				}
			if (args.size() >= 5 && args.get(4) != null && !args.get(4).equalsIgnoreCase("~")) // z
				try {
					z = Double.parseDouble(args.get(4));
				} catch (IllegalArgumentException e) {
					player.sendMessage("Please put a number as a coordinate.");
					return;
				}
			Location loc = player.getLocation().clone();
			loc.setX(x);
			loc.setY(y);
			loc.setZ(z);

			Element element = null;
			String elementString = args.get(1).toLowerCase();
			Character c = Character.toUpperCase(elementString.charAt(0));
			elementString = c + elementString.substring(1);
			try {
				element = Element.valueOf(elementString);
			} catch (IllegalArgumentException e) {
				player.sendMessage(ChatColor.RED + "That is not an element!");
				return;
			}
			String entityString = args.get(0).toUpperCase();
			if (!ProjectKorraMobs.plugin.getConfig().getStringList("Properties.EntityTypes").contains(entityString)) {
				sender.sendMessage(ChatColor.RED + "You can't spawn that entity!");
				return;
			}
			EntityType type = EntityType.valueOf(entityString);
			Entity entity = player.getWorld().spawnEntity(loc, type);
			MobMethods.assignElement(entity, element, allSubs);
			player.sendMessage(entity.getType() + ", " + element + ", " + x + ", " + y + ", " + z);
		} else if (args.size() > 5) { // spawning with coordinates AND subs T^T
			double x = player.getLocation().getX();
			double y = player.getLocation().getY();
			double z = player.getLocation().getZ();
			if (args.get(2) != null && !args.get(2).equalsIgnoreCase("~")) // x
				try {
					x = Double.parseDouble(args.get(2));
				} catch (IllegalArgumentException e) {
					player.sendMessage("Please put a number as a coordinate.");
					return;
				}
			if (args.get(3) != null && !args.get(3).equalsIgnoreCase("~")) // y
				try {
					y = Double.parseDouble(args.get(3));
				} catch (IllegalArgumentException e) {
					player.sendMessage("Please put a number as a coordinate.");
					return;
				}
			if (args.get(4) != null && !args.get(4).equalsIgnoreCase("~")) // z
				try {
					z = Double.parseDouble(args.get(4));
				} catch (IllegalArgumentException e) {
					player.sendMessage("Please put a number as a coordinate.");
					return;
				}
			Location loc = player.getLocation().clone();
			loc.setX(x);
			loc.setY(y);
			loc.setZ(z);

			Element element = null;
			String elementString = args.get(1).toLowerCase();
			Character c = Character.toUpperCase(elementString.charAt(0));
			elementString = c + elementString.substring(1);
			try {
				element = Element.valueOf(elementString);
			} catch (IllegalArgumentException e) {
				player.sendMessage(ChatColor.RED + "That is not an element!");
				return;
			}
			SubElement sub = null;
			String subString = args.get(5).toLowerCase();
			c = Character.toUpperCase(subString.charAt(0));
			subString = c + subString.substring(1);
			try {
				sub = SubElement.valueOf(subString);
			} catch (IllegalArgumentException e) {
				player.sendMessage(ChatColor.RED + "Mobs cant have that sub element!");
				return;
			}
			if (sub.getElement() != element) {
				player.sendMessage(ChatColor.RED + subString + " can't go with " + elementString + "bending!");
				return;
			}
			String entityString = args.get(0).toUpperCase();
			if (!ProjectKorraMobs.plugin.getConfig().getStringList("Properties.EntityTypes")
					.contains(entityString.toUpperCase())) {
				sender.sendMessage(ChatColor.RED + "You can't spawn that entity!");
				return;
			}
			EntityType type = EntityType.valueOf(entityString);
			Entity entity = player.getWorld().spawnEntity(loc, type);
			String[] subs = new String[] { sub.name() };
			MobMethods.assignElement(entity, element, subs);
			player.sendMessage(entity.getType() + ", " + element + ", " + x + ", " + y + ", " + z);
			player.sendMessage(sub.name());
		}

	}

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		List<String> list = new ArrayList<String>();
		for (int i = 1; i < arg3.length; i++)
			list.add(arg3[i]);
		execute(arg0, list);
		return true;
	}

}