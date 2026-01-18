/*    */ package com.hypixel.hytale.builtin.parkour;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ public class ParkourCheckpoint
/*    */   implements Component<EntityStore>
/*    */ {
/*    */   public static final BuilderCodec<ParkourCheckpoint> CODEC;
/*    */   protected int index;
/*    */   
/*    */   static {
/* 20 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(ParkourCheckpoint.class, ParkourCheckpoint::new).append(new KeyedCodec("CheckpointIndex", (Codec)Codec.INTEGER), (parkourCheckpoint, integer) -> parkourCheckpoint.index = integer.intValue(), parkourCheckpoint -> Integer.valueOf(parkourCheckpoint.index)).add()).build();
/*    */   }
/*    */   public static ComponentType<EntityStore, ParkourCheckpoint> getComponentType() {
/* 23 */     return ParkourPlugin.get().getParkourCheckpointComponentType();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ParkourCheckpoint(int index) {
/* 29 */     this.index = index;
/*    */   }
/*    */ 
/*    */   
/*    */   protected ParkourCheckpoint() {}
/*    */   
/*    */   public int getIndex() {
/* 36 */     return this.index;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Component<EntityStore> clone() {
/* 42 */     ParkourCheckpoint parkourCheckpoint = new ParkourCheckpoint();
/* 43 */     parkourCheckpoint.index = this.index;
/* 44 */     return parkourCheckpoint;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\parkour\ParkourCheckpoint.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */