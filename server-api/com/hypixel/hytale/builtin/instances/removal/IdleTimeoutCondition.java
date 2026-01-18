/*    */ package com.hypixel.hytale.builtin.instances.removal;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.modules.time.TimeResource;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.concurrent.TimeUnit;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IdleTimeoutCondition
/*    */   implements RemovalCondition
/*    */ {
/*    */   public static final BuilderCodec<IdleTimeoutCondition> CODEC;
/*    */   
/*    */   static {
/* 26 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(IdleTimeoutCondition.class, IdleTimeoutCondition::new).documentation("A condition that triggers after the world has be idle (without players) for a set time.")).append(new KeyedCodec("TimeoutSeconds", (Codec)Codec.DOUBLE), (o, i) -> o.timeoutSeconds = i.doubleValue(), o -> Double.valueOf(o.timeoutSeconds)).documentation("How long (in seconds) the world has to be idle (without players) for before triggering.").add()).build();
/*    */   }
/*    */   
/* 29 */   private double timeoutSeconds = TimeUnit.MINUTES.toSeconds(5L);
/*    */ 
/*    */   
/*    */   public boolean shouldRemoveWorld(@Nonnull Store<ChunkStore> store) {
/* 33 */     InstanceDataResource instanceDataResource = (InstanceDataResource)store.getResource(InstanceDataResource.getResourceType());
/* 34 */     World world = ((ChunkStore)store.getExternalData()).getWorld();
/*    */     
/* 36 */     Store<EntityStore> entityStore = world.getEntityStore().getStore();
/* 37 */     TimeResource timeResource = (TimeResource)entityStore.getResource(TimeResource.getResourceType());
/*    */     
/* 39 */     boolean hasPlayer = (world.getPlayerCount() > 0);
/* 40 */     if (!hasPlayer) {
/* 41 */       if (instanceDataResource.getIdleTimeoutTimer() == null) {
/* 42 */         instanceDataResource.setIdleTimeoutTimer(timeResource.getNow().plusNanos((long)(this.timeoutSeconds * 1.0E9D)));
/*    */       }
/*    */       
/* 45 */       return timeResource.getNow().isAfter(instanceDataResource.getIdleTimeoutTimer());
/*    */     } 
/* 47 */     instanceDataResource.setIdleTimeoutTimer(null);
/* 48 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\instances\removal\IdleTimeoutCondition.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */