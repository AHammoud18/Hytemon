/*    */ package com.hypixel.hytale.builtin.instances.removal;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.Store;
/*    */ import com.hypixel.hytale.server.core.modules.time.TimeResource;
/*    */ import com.hypixel.hytale.server.core.universe.world.World;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
/*    */ import java.util.concurrent.TimeUnit;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TimeoutCondition
/*    */   implements RemovalCondition
/*    */ {
/*    */   public static final BuilderCodec<TimeoutCondition> CODEC;
/*    */   
/*    */   static {
/* 25 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(TimeoutCondition.class, TimeoutCondition::new).documentation("A condition that triggers after a set time limit.")).append(new KeyedCodec("TimeoutSeconds", (Codec)Codec.DOUBLE), (o, i) -> o.timeoutSeconds = i.doubleValue(), o -> Double.valueOf(o.timeoutSeconds)).documentation("How long to wait (in seconds) before closing the world.").add()).build();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 30 */   private double timeoutSeconds = TimeUnit.MINUTES.toSeconds(5L);
/*    */ 
/*    */ 
/*    */   
/*    */   public TimeoutCondition(double timeoutSeconds) {
/* 35 */     this.timeoutSeconds = timeoutSeconds;
/*    */   }
/*    */   
/*    */   public double getTimeoutSeconds() {
/* 39 */     return this.timeoutSeconds;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldRemoveWorld(@Nonnull Store<ChunkStore> store) {
/* 44 */     InstanceDataResource data = (InstanceDataResource)store.getResource(InstanceDataResource.getResourceType());
/* 45 */     World world = ((ChunkStore)store.getExternalData()).getWorld();
/* 46 */     TimeResource timeResource = (TimeResource)world.getEntityStore().getStore().getResource(TimeResource.getResourceType());
/*    */     
/* 48 */     if (data.getTimeoutTimer() == null) {
/* 49 */       data.setTimeoutTimer(timeResource.getNow().plusNanos((long)(this.timeoutSeconds * 1.0E9D)));
/*    */     }
/*    */     
/* 52 */     return timeResource.getNow().isAfter(data.getTimeoutTimer());
/*    */   }
/*    */   
/*    */   public TimeoutCondition() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\instances\removal\TimeoutCondition.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */