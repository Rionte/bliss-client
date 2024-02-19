package rionte.bliss;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Vector3d;
import net.minecraftforge.client.event.GuiScreenEvent.KeyboardInputEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import rionte.bliss.commands.AirJumpCommand;
import rionte.bliss.commands.JumpResetCommand;
import rionte.bliss.commands.KillauraCommand;
import rionte.bliss.commands.AutoBucketCommand;

public class listeners {
	
	public static String prefix = EnumChatFormatting.AQUA + "[BLI" +  EnumChatFormatting.WHITE + "SS] ";
	public static int totalTicks = 0;
	public static int totalSeconds = 0;
	boolean bucketBuffer = true;
	static Minecraft mc = Minecraft.getMinecraft();
	int bucketSlot = 10;
	int stopBucketTick = 0;
	Entity killauraTarget;

	public static void gameprint(String s) {
		mc.thePlayer.addChatMessage(new ChatComponentText(s));
	}
	
	@SubscribeEvent
	public void onTick(TickEvent.PlayerTickEvent event) {
		
		if (mc.theWorld == null || mc.thePlayer == null) {
			return;
		}
		
		if (totalTicks % 80 == 0) {
			totalSeconds += 1;
			/* if (AutoBucketCommand.active) {
				for (int i = 0; i < 9; i++) {
					try {
						if (mc.thePlayer.inventory.getStackInSlot(i).getItem() == Item.getByNameOrId("water_bucket")) {
							bucketSlot = i;
						}
					} catch (NullPointerException e) {
						continue;
					}
				}
			} */
			
			if (KillauraCommand.active) {
				killauraTarget = mc.theWorld.getEntitiesWithinAABB(mc.thePlayer.getClass(), 3);
			}
		}
		
		if (KillauraCommand.active) {
			mc.thePlayer.attackTargetEntityWithCurrentItem();
		}
		
		if (mc.thePlayer.onGround) {
			bucketBuffer = true;
			if (AutoBucketCommand.active) {
				stopBucketTick = totalTicks;
			}
		}
		
		if (totalTicks - stopBucketTick == 40) {
			KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), false);
		}
		
		if (AirJumpCommand.active) {
			if (AirJumpCommand.airstrafe) {
				if (!mc.thePlayer.onGround) {
					mc.thePlayer.onGround = true;
				}
			} else {
				if (mc.gameSettings.keyBindJump.isPressed()) {
					mc.thePlayer.jump();
				}
			}
		}
		
		if (AutoBucketCommand.active) {
			if (mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posZ)).getBlock() == Blocks.air && mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 2, mc.thePlayer.posZ)).getBlock() != Blocks.air) {
				if (bucketBuffer) {
					bucketBuffer = false;
					mc.thePlayer.inventory.currentItem = bucketSlot;
					mc.thePlayer.setItemInUse(mc.thePlayer.inventory.getStackInSlot(bucketSlot), 1);
					KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), true);
				}
			}
		}
		
		totalTicks += 1;
	}
	
	@SubscribeEvent
	public void onHurt(LivingHurtEvent event) {
		
		if (mc.theWorld == null || mc.thePlayer == null) {
			return;
		}
		
		if (event.entity.getName() == mc.thePlayer.getName()) {
			mc.thePlayer.jump();
		}
	}
}
