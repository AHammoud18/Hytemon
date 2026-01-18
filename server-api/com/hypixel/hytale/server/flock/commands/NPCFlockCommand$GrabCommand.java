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
/*     */ import com.hypixel.hytale.server.flock.FlockMembership;
/*     */ import com.hypixel.hytale.server.flock.FlockMembershipSystems;
/*     */ import com.hypixel.hytale.server.flock.FlockPlugin;
/*     */ import com.hypixel.hytale.server.npc.entities.NPCEntity;
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
/*     */ public class GrabCommand
/*     */   extends AbstractPlayerCommand
/*     */ {
/*     */   public GrabCommand() {
/*  95 */     super("grab", "server.commands.npc.flock.grab.desc");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 105 */     int count = NPCFlockCommand.forNpcEntitiesInViewCone(ref, store, (targetRef, targetNpcComponent) -> {
/*     */           FlockMembership membership = (FlockMembership)store.getComponent(targetRef, FlockMembership.getComponentType());
/*     */           
/*     */           if (membership == null) {
/*     */             Ref<EntityStore> flockReference = FlockPlugin.getFlockReference(ref, (ComponentAccessor)store);
/*     */             
/*     */             if (flockReference == null) {
/*     */               flockReference = FlockPlugin.createFlock(store, targetNpcComponent.getRole());
/*     */               
/*     */               FlockMembershipSystems.join(ref, flockReference, store);
/*     */             } 
/*     */             FlockMembershipSystems.join(targetRef, flockReference, store);
/*     */             return true;
/*     */           } 
/*     */           return false;
/*     */         });
/* 121 */     context.sendMessage(Message.translation("server.commands.npc.flock.addedToFlock")
/* 122 */         .param("count", count));
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\flock\commands\NPCFlockCommand$GrabCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */