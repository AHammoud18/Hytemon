/*    */ package com.hypixel.hytale.server.npc.commands;
/*    */ 
/*    */ import com.hypixel.hytale.component.ArchetypeChunk;
/*    */ import com.hypixel.hytale.component.CommandBuffer;
/*    */ import com.hypixel.hytale.component.Ref;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.component.query.Query;
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.system.FlagArg;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
/*    */ import com.hypixel.hytale.server.core.command.system.arguments.types.EntityWrappedArg;
/*    */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractWorldCommand;
/*    */ import com.hypixel.hytale.server.core.entity.Frozen;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*    */ import it.unimi.dsi.fastutil.Pair;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class NPCThawCommand extends AbstractWorldCommand {
/*    */   @Nonnull
/* 23 */   private static final Message MESSAGE_COMMANDS_NPC_THAW_ALL = Message.translation("server.commands.npc.thaw.all");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 29 */   private final FlagArg allArg = withFlagArg("all", "server.commands.npc.thaw.arg.all");
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 34 */   private final EntityWrappedArg entityArg = (EntityWrappedArg)
/* 35 */     withOptionalArg("entity", "server.commands.entity.entity.desc", ArgTypes.ENTITY_ID);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public NPCThawCommand() {
/* 41 */     super("thaw", "server.commands.npc.thaw.desc");
/* 42 */     addAliases(new String[] { "unfreeze" });
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 48 */     if (((Boolean)this.allArg.get(context)).booleanValue()) {
/* 49 */       store.forEachEntityParallel((Query)NPCEntity.getComponentType(), (index, archetypeChunk, commandBuffer) -> commandBuffer.tryRemoveComponent(archetypeChunk.getReferenceTo(index), Frozen.getComponentType()));
/*    */       
/* 51 */       context.sendMessage(MESSAGE_COMMANDS_NPC_THAW_ALL);
/*    */       
/*    */       return;
/*    */     } 
/* 55 */     Pair<Ref<EntityStore>, NPCEntity> targetNpcPair = NPCCommandUtils.getTargetNpc(context, this.entityArg, store);
/* 56 */     if (targetNpcPair == null) {
/*    */       return;
/*    */     }
/*    */     
/* 60 */     Ref<EntityStore> targetNpcRef = (Ref<EntityStore>)targetNpcPair.first();
/* 61 */     NPCEntity targetNpcComponent = (NPCEntity)targetNpcPair.second();
/* 62 */     store.tryRemoveComponent(targetNpcRef, Frozen.getComponentType());
/* 63 */     context.sendMessage(Message.translation("server.commands.npc.thaw.npc")
/* 64 */         .param("role", targetNpcComponent.getRoleName()));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\commands\NPCThawCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */