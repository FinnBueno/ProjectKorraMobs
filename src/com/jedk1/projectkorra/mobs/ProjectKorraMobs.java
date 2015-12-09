package com.jedk1.projectkorra.mobs;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

import com.jedk1.projectkorra.mobs.ability.air.AirAbility;
import com.jedk1.projectkorra.mobs.ability.earth.EarthAbility;
import com.jedk1.projectkorra.mobs.ability.fire.FireAbility;
import com.jedk1.projectkorra.mobs.ability.water.WaterAbility;
import com.jedk1.projectkorra.mobs.commands.Spawn;
import com.jedk1.projectkorra.mobs.config.ConfigManager;
import com.jedk1.projectkorra.mobs.listener.MobListener;
import com.jedk1.projectkorra.mobs.manager.AbilityManager;
import com.jedk1.projectkorra.mobs.manager.EntityManager;

public class ProjectKorraMobs extends JavaPlugin {

	public static ProjectKorraMobs plugin;
	public static Logger log;
	
	@Override
	public void onEnable(){
		plugin = this;
		ProjectKorraMobs.log = this.getLogger();
		
		new ConfigManager(this);
		MobMethods.registerDisabledWorlds();
		MobMethods.registerEntityTypes();
		getServer().getPluginManager().registerEvents(new MobListener(this), this);
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new AbilityManager(),0L, 1L);
		new Spawn();
	}
	
	@Override
	public void onDisable(){
		AirAbility.remove();
		EarthAbility.remove();
		FireAbility.remove();
		WaterAbility.remove();
		EntityManager.remove();
	}
}
