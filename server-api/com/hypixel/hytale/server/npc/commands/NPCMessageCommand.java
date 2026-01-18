/*    */ package com.hypixel.hytale.server.npc.commands;
/*    */ 
/*    */ import com.hypixel.hytale.component.ArchetypeChunk;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.FlagArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.EntityWrappedArg;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*    */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.components.messaging.BeaconSupport;
/*    */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*    */ import it.unimi.dsi.fastutil.Pair;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NPCMessageCommand
/*    */   extends AbstractPlayerCommand
/*    */ {
/*    */   @Nonnull
/* 34 */   private final RequiredArg<String> messageArg = withRequiredArg("message", "server.commands.npc.message.message.desc", (ArgumentType)ArgTypes.STRING);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 40 */   private final OptionalArg<Double> expirationTimeArg = withOptionalArg("expiration", "server.commands.npc.message.expiration", (ArgumentType)ArgTypes.DOUBLE);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 46 */   private final FlagArg allArg = withFlagArg("all", "server.commands.npc.message.all");
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 51 */   private final EntityWrappedArg entityArg = (EntityWrappedArg)
/* 52 */     withOptionalArg("entity", "server.commands.entity.entity.desc", ArgTypes.ENTITY_ID);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public NPCMessageCommand() {
/* 58 */     super("message", "server.commands.npc.message.desc");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 68 */     String msg = (String)this.messageArg.get(context);
/* 69 */     double expiration = this.expirationTimeArg.provided(context) ? ((Double)this.expirationTimeArg.get(context)).doubleValue() : 1.0D;
/*    */     
/* 71 */     if (((Boolean)this.allArg.get(context)).booleanValue()) {
/* 72 */       store.forEachEntityParallel((Query)NPCEntity.getComponentType(), (index, archetypeChunk, commandBuffer) -> {
/*    */             BeaconSupport beaconSupport = (BeaconSupport)archetypeChunk.getComponent(index, BeaconSupport.getComponentType());
/*    */             
/*    */             if (beaconSupport != null) {
/*    */               beaconSupport.postMessage(msg, ref, expiration);
/*    */             }
/*    */           });
/*    */       return;
/*    */     } 
/* 81 */     Pair<Ref<EntityStore>, NPCEntity> targetNpcPair = NPCCommandUtils.getTargetNpc(context, this.entityArg, store);
/* 82 */     if (targetNpcPair == null) {
/*    */       return;
/*    */     }
/*    */     
/* 86 */     Ref<EntityStore> targetNpcRef = (Ref<EntityStore>)targetNpcPair.first();
/* 87 */     BeaconSupport beaconSupportComponent = (BeaconSupport)store.getComponent(targetNpcRef, BeaconSupport.getComponentType());
/* 88 */     if (beaconSupportComponent != null)
/* 89 */       beaconSupportComponent.postMessage(msg, ref, expiration); 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\commands\NPCMessageCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */