/*    */ package com.hypixel.hytale.server.spawning.commands;
/*    */ 
/*    */ import com.hypixel.hytale.component.ArchetypeChunk;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.RemoveReason;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.asset.type.environment.config.Environment;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.OptionalArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgumentType;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractWorldCommand;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.WorldConfig;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SpawnPopulateCommand
/*    */   extends AbstractWorldCommand
/*    */ {
/*    */   @Nonnull
/* 27 */   private final OptionalArg<Environment> environmentArg = withOptionalArg("environment", "server.commands.spawning.populate.arg.environment.desc", (ArgumentType)ArgTypes.ENVIRONMENT_ASSET);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SpawnPopulateCommand() {
/* 33 */     super("populate", "server.commands.spawning.populate.desc");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 40 */     int environmentIndex = this.environmentArg.provided(context) ? Environment.getAssetMap().getIndex(((Environment)this.environmentArg.get(context)).getId()) : Integer.MIN_VALUE;
/*    */     
/* 42 */     store.forEachEntityParallel((Query)NPCEntity.getComponentType(), (index, archetypeChunk, commandBuffer) -> {
/*    */           NPCEntity npcComponent = (NPCEntity)archetypeChunk.getComponent(index, NPCEntity.getComponentType());
/*    */           
/*    */           assert npcComponent != null;
/*    */           
/*    */           int npcEnvironment = npcComponent.getEnvironment();
/*    */           
/*    */           if (npcEnvironment >= 0 && (environmentIndex == Integer.MIN_VALUE || environmentIndex == npcEnvironment)) {
/*    */             commandBuffer.removeEntity(archetypeChunk.getReferenceTo(index), RemoveReason.REMOVE);
/*    */           }
/*    */         });
/*    */     
/* 54 */     WorldConfig worldConfig = world.getWorldConfig();
/* 55 */     worldConfig.setSpawningNPC(true);
/* 56 */     worldConfig.markChanged();
/*    */     
/* 58 */     context.sendMessage(Message.translation("server.commands.spawning.populate.success")
/* 59 */         .param("worldName", world.getName()));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\spawning\commands\SpawnPopulateCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */