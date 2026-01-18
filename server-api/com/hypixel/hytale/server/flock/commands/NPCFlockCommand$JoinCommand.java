/*     */ package com.hypixel.hytale.server.flock.commands;
/*     */ 
/*     */ import com.hypixel.hytale.component.ComponentAccessor;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.server.core.Message;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import com.hypixel.hytale.server.flock.FlockMembershipSystems;
/*     */ import com.hypixel.hytale.server.flock.FlockPlugin;
/*     */ import javax.annotation.Nonnull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JoinCommand
/*     */   extends AbstractPlayerCommand
/*     */ {
/*     */   public JoinCommand() {
/* 134 */     super("join", "server.commands.npc.flock.join.desc");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 144 */     boolean success = NPCFlockCommand.anyEntityInViewCone(ref, store, npcReference -> {
/*     */           Ref<EntityStore> flockReference = FlockPlugin.getFlockReference(npcReference, (ComponentAccessor)store);
/*     */           
/*     */           if (flockReference != null) {
/*     */             FlockMembershipSystems.join(ref, flockReference, store);
/*     */             return true;
/*     */           } 
/*     */           return false;
/*     */         });
/* 153 */     if (!success) {
/* 154 */       context.sendMessage(Message.translation("server.commands.npc.flock.resultJoinFlock")
/* 155 */           .param("status", "Failed"));
/*     */     } else {
/*     */       
/* 158 */       world.execute(() -> {
/*     */             String status = FlockPlugin.isFlockMember(ref, store) ? "Succeeded" : "Failed";
/*     */             context.sendMessage(Message.translation("server.commands.npc.flock.resultJoinFlock").param("status", status));
/*     */           });
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\flock\commands\NPCFlockCommand$JoinCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */