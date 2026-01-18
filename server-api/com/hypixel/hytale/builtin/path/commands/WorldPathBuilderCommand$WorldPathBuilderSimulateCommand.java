/*     */ package com.hypixel.hytale.builtin.path.commands;
/*     */ 
/*     */ import com.hypixel.hytale.builtin.path.WorldPathBuilder;
/*     */ import com.hypixel.hytale.component.Component;
/*     */ import com.hypixel.hytale.component.Ref;
/*     */ import com.hypixel.hytale.component.Store;
/*     */ import com.hypixel.hytale.math.vector.Transform;
/*     */ import com.hypixel.hytale.server.core.HytaleServer;
/*     */ import com.hypixel.hytale.server.core.command.system.CommandContext;
/*     */ import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
/*     */ import com.hypixel.hytale.server.core.modules.entity.teleport.Teleport;
/*     */ import com.hypixel.hytale.server.core.universe.PlayerRef;
/*     */ import com.hypixel.hytale.server.core.universe.world.World;
/*     */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.ScheduledFuture;
/*     */ import java.util.concurrent.TimeUnit;
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
/*     */ class WorldPathBuilderSimulateCommand
/*     */   extends AbstractPlayerCommand
/*     */ {
/*     */   public WorldPathBuilderSimulateCommand() {
/* 120 */     super("simulate", "server.commands.worldpath.builder.simulate.desc");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void execute(@Nonnull CommandContext context, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
/* 129 */     WorldPathBuilder builder = WorldPathBuilderCommand.getBuilder(ref, store);
/* 130 */     if (builder == null)
/*     */       return; 
/* 132 */     ObjectArrayList<Transform> waypoints = new ObjectArrayList(builder.getPath().getWaypoints());
/* 133 */     CompletableFuture<Void> future = new CompletableFuture<>();
/* 134 */     ScheduledFuture[] arrayOfScheduledFuture = new ScheduledFuture[1];
/* 135 */     arrayOfScheduledFuture[0] = HytaleServer.SCHEDULED_EXECUTOR.scheduleWithFixedDelay(() -> { Transform transform = (Transform)waypoints.removeFirst(); if (transform == null) { future.complete(null); scheduledFuture[0].cancel(false); } else { world.execute(()); }  }1L, 1L, TimeUnit.SECONDS);
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\path\commands\WorldPathBuilderCommand$WorldPathBuilderSimulateCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */