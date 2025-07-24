package com.tttsaurus.antiachieve;

import com.tttsaurus.antiachieve.gui.mvvm.AchievementPageViewModel;
import com.tttsaurus.ingameinfo.common.core.forgeevent.IgiRuntimeEntryPointEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = Tags.MODID,
        version = Tags.VERSION,
        name = Tags.MODNAME,
        acceptedMinecraftVersions = "[1.12.2]",
        dependencies = "required-after:ingameinfo")
public class AntimatterAchievements
{
    public static final Logger LOGGER = LogManager.getLogger(Tags.MODNAME);

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {

    }

    @SubscribeEvent
    public void onIgiRuntimeEntryPoint(IgiRuntimeEntryPointEvent event)
    {
        event.runtime.initPhase.registerMvvm("achievement_page", AchievementPageViewModel.class);
    }
}
