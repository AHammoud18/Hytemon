/*    */ package com.hypixel.hytale.builtin.adventure.memories.commands;
/*    */ 
/*    */ import com.hypixel.hytale.builtin.adventure.memories.component.PlayerMemories;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.protocol.Packet;
/*    */ import com.hypixel.hytale.protocol.packets.player.UpdateMemoriesFeatureStatus;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.io.PacketHandler;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MemoriesCapacityCommand
/*    */   extends AbstractPlayerCommand
/*    */ {
/*    */   @Nonnull
/* 28 */   private final RequiredArg<Integer> capacityArg = withRequiredArg("capacity", "server.commands.memories.capacity.capacity.desc", (ArgumentType)ArgTypes.INTEGER);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MemoriesCapacityCommand() {
/* 34 */     super("capacity", "server.commands.memories.capacity.desc");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 39 */     Integer capacity = (Integer)this.capacityArg.get(context);
/* 40 */     PacketHandler playerConnection = playerRef.getPacketHandler();
/*    */     
/* 42 */     if (capacity.intValue() <= 0) {
/* 43 */       store.tryRemoveComponent(ref, PlayerMemories.getComponentType());
/* 44 */       context.sendMessage(Message.translation("server.commands.memories.capacity.success")
/* 45 */           .param("capacity", 0));
/* 46 */       playerConnection.writeNoCache((Packet)new UpdateMemoriesFeatureStatus(false));
/*    */       
/*    */       return;
/*    */     } 
/* 50 */     PlayerMemories playerMemories = (PlayerMemories)store.ensureAndGetComponent(ref, PlayerMemories.getComponentType());
/* 51 */     playerMemories.setMemoriesCapacity(capacity.intValue());
/* 52 */     context.sendMessage(Message.translation("server.commands.memories.capacity.success")
/* 53 */         .param("capacity", capacity.intValue()));
/* 54 */     playerConnection.writeNoCache((Packet)new UpdateMemoriesFeatureStatus(true));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\memories\commands\MemoriesCapacityCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */