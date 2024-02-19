package rionte.bliss;

import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import rionte.bliss.commands.AirJumpCommand;
import rionte.bliss.commands.AutoBucketCommand;
import rionte.bliss.commands.JumpResetCommand;
import rionte.bliss.commands.KillauraCommand;

@Mod(modid = reference.MODID, name = reference.NAME, version = reference.VERSION)
public class bliss {
	
	@Mod.Instance("bls")
	public static bliss instance;
	
	@EventHandler
	public static void preInit(FMLPreInitializationEvent event) {
		
	}
	
	@EventHandler
	public static void init(FMLInitializationEvent event) {
		ClientCommandHandler.instance.registerCommand(new JumpResetCommand());
		ClientCommandHandler.instance.registerCommand(new AirJumpCommand());
		ClientCommandHandler.instance.registerCommand(new AutoBucketCommand());
		ClientCommandHandler.instance.registerCommand(new KillauraCommand());
		MinecraftForge.EVENT_BUS.register(new listeners());
	}

	@EventHandler
	public static void postInit(FMLPostInitializationEvent event) {
		
	}

}