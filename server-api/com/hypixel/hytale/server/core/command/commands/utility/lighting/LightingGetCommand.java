/*    */ package com.hypixel.hytale.server.core.command.commands.utility.lighting;
/*    */ 
/*    */ import com.hypixel.hytale.component.ComponentAccessor;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.math.util.ChunkUtil;
/*    */ import com.hypixel.hytale.math.vector.Vector3i;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.FlagArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.RelativeIntPosition;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractWorldCommand;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.BlockChunk;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.section.BlockSection;
/*    */ import com.hypixel.hytale.server.core.universe.world.chunk.section.ChunkLightData;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LightingGetCommand
/*    */   extends AbstractWorldCommand
/*    */ {
/*    */   @Nonnull
/* 30 */   private final RequiredArg<RelativeIntPosition> positionArg = withRequiredArg("x y z", "server.commands.light.get.position.desc", ArgTypes.RELATIVE_BLOCK_POSITION);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 36 */   private final FlagArg hexFlag = withFlagArg("hex", "server.commands.light.get.hex.desc");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public LightingGetCommand() {
/* 42 */     super("get", "server.commands.light.get.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 47 */     ChunkStore chunkStore = world.getChunkStore();
/*    */     
/* 49 */     Vector3i position = ((RelativeIntPosition)this.positionArg.get(context)).getBlockPosition(context, (ComponentAccessor)store);
/* 50 */     int x = position.x;
/* 51 */     int y = position.y;
/* 52 */     int z = position.z;
/*    */     
/* 54 */     long chunkIndex = ChunkUtil.indexChunkFromBlock(x, z);
/* 55 */     Ref<ChunkStore> chunkReference = chunkStore.getChunkReference(chunkIndex);
/* 56 */     if (chunkReference == null || !chunkReference.isValid()) {
/*    */ 
/*    */ 
/*    */       
/* 60 */       Message errorMessage = Message.translation("server.commands.errors.chunkNotLoaded").param("chunkX", ChunkUtil.chunkCoordinate(x)).param("chunkZ", ChunkUtil.chunkCoordinate(z)).param("world", world.getName());
/* 61 */       context.sendMessage(errorMessage);
/*    */       
/*    */       return;
/*    */     } 
/* 65 */     BlockChunk blockChunkComponent = (BlockChunk)chunkStore.getStore().getComponent(chunkReference, BlockChunk.getComponentType());
/* 66 */     assert blockChunkComponent != null;
/*    */     
/* 68 */     BlockSection section = blockChunkComponent.getSectionAtBlockY(y);
/* 69 */     short lightValue = section.getGlobalLight().getLightRaw(x, y, z);
/*    */     
/* 71 */     byte redLight = ChunkLightData.getLightValue(lightValue, 0);
/* 72 */     byte greenLight = ChunkLightData.getLightValue(lightValue, 1);
/* 73 */     byte blueLight = ChunkLightData.getLightValue(lightValue, 2);
/* 74 */     byte skyLight = ChunkLightData.getLightValue(lightValue, 3);
/*    */     
/* 76 */     boolean displayHex = ((Boolean)this.hexFlag.get(context)).booleanValue();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 82 */     Message messageToSend = Message.translation("server.commands.light.get").param("x", x).param("y", y).param("z", z).param("worldName", world.getName());
/*    */     
/* 84 */     if (displayHex) {
/* 85 */       String hexString = Integer.toHexString(lightValue);
/* 86 */       messageToSend.insert("#" + "0".repeat(8 - hexString.length()) + hexString);
/*    */     } else {
/* 88 */       messageToSend.insert(Message.translation("server.commands.light.value")
/* 89 */           .param("red", redLight)
/* 90 */           .param("green", greenLight)
/* 91 */           .param("blue", blueLight)
/* 92 */           .param("sky", skyLight));
/*    */     } 
/*    */     
/* 95 */     context.sendMessage(messageToSend);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\command\command\\utility\lighting\LightingGetCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */