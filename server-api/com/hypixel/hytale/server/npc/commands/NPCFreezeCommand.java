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
/*    */ import com.hypixel.hytale.server.core.modules.entity.component.Interactable;
/*    */ import com.hypixel.hytale.server.core.modules.entity.item.ItemComponent;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
/*    */ import it.unimi.dsi.fastutil.Pair;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class NPCFreezeCommand
/*    */   extends AbstractWorldCommand
/*    */ {
/*    */   @Nonnull
/* 28 */   private final FlagArg allArg = withFlagArg("all", "server.commands.npc.freeze.all");
/*    */   
/*    */   @Nonnull
/* 31 */   private final FlagArg toggleArg = withFlagArg("toggle", "server.commands.npc.freeze.toggle");
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/* 36 */   private final EntityWrappedArg entityArg = (EntityWrappedArg)
/* 37 */     withOptionalArg("entity", "server.commands.entity.entity.desc", ArgTypes.ENTITY_ID);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public NPCFreezeCommand() {
/* 43 */     super("freeze", "server.commands.npc.freeze.desc");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void execute(@Nonnull CommandContext context, @Nonnull World world, @Nonnull Store<EntityStore> store) {
/* 49 */     if (((Boolean)this.allArg.get(context)).booleanValue()) {
/*    */ 
/*    */       
/* 52 */       store.forEachEntityParallel((Query)NPCEntity.getComponentType(), (index, archetypeChunk, commandBuffer) -> commandBuffer.ensureComponent(archetypeChunk.getReferenceTo(index), Frozen.getComponentType()));
/*    */ 
/*    */ 
/*    */       
/* 56 */       store.forEachEntityParallel((Query)ItemComponent.getComponentType(), (index, archetypeChunk, commandBuffer) -> {
/*    */             Ref<EntityStore> ref = archetypeChunk.getReferenceTo(index);
/*    */             
/*    */             commandBuffer.ensureComponent(ref, Frozen.getComponentType());
/*    */             commandBuffer.ensureComponent(ref, Interactable.getComponentType());
/*    */           });
/*    */       return;
/*    */     } 
/* 64 */     Pair<Ref<EntityStore>, NPCEntity> targetNpcPair = NPCCommandUtils.getTargetNpc(context, this.entityArg, store);
/* 65 */     if (targetNpcPair == null) {
/*    */       return;
/*    */     }
/*    */     
/* 69 */     Ref<EntityStore> targetNpcRef = (Ref<EntityStore>)targetNpcPair.first();
/* 70 */     String roleName = ((NPCEntity)targetNpcPair.second()).getRoleName();
/*    */     
/* 72 */     if (((Boolean)this.toggleArg.get(context)).booleanValue()) {
/* 73 */       boolean wasFrozen = store.getArchetype(targetNpcRef).contains(Frozen.getComponentType());
/* 74 */       if (wasFrozen) {
/* 75 */         store.tryRemoveComponent(targetNpcRef, Frozen.getComponentType());
/* 76 */         context.sendMessage(Message.translation("server.commands.npc.thaw.npc")
/* 77 */             .param("role", roleName));
/*    */       } else {
/* 79 */         store.ensureComponent(targetNpcRef, Frozen.getComponentType());
/* 80 */         context.sendMessage(Message.translation("server.commands.npc.freeze.npc").param("role", roleName));
/*    */       } 
/*    */     } else {
/* 83 */       store.ensureComponent(targetNpcRef, Frozen.getComponentType());
/* 84 */       context.sendMessage(Message.translation("server.commands.npc.freeze.npc").param("role", roleName));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\commands\NPCFreezeCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */