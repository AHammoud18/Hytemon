/*    */ package com.hypixel.hytale.server.flock;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.common.util.ArrayUtil;
/*    */ import com.hypixel.hytale.component.Component;
/*    */ import com.hypixel.hytale.component.ComponentType;
/*    */ import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
/*    */ import com.hypixel.hytale.server.flock.config.FlockAsset;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*    */ import java.util.Arrays;
/*    */ import java.util.Collection;
/*    */ import java.util.Collections;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PersistentFlockData
/*    */   implements Component<EntityStore>
/*    */ {
/*    */   public static final BuilderCodec<PersistentFlockData> CODEC;
/*    */   
/*    */   static {
/* 39 */     CODEC = ((BuilderCodec.Builder)((BuilderCodec.Builder)((BuilderCodec.Builder)BuilderCodec.builder(PersistentFlockData.class, PersistentFlockData::new).append(new KeyedCodec("MaxGrowSize", (Codec)Codec.INTEGER), (flock, i) -> flock.maxGrowSize = i.intValue(), flock -> Integer.valueOf(flock.maxGrowSize)).add()).append(new KeyedCodec("AllowedRoles", (Codec)Codec.STRING_ARRAY), (flock, o) -> flock.flockAllowedRoles = o, flock -> flock.flockAllowedRoles).add()).append(new KeyedCodec("Size", (Codec)Codec.INTEGER), (flock, i) -> flock.size = i.intValue(), flock -> Integer.valueOf(flock.size)).add()).build();
/*    */   }
/*    */   public static ComponentType<EntityStore, PersistentFlockData> getComponentType() {
/* 42 */     return FlockPlugin.get().getPersistentFlockDataComponentType();
/*    */   }
/*    */   
/* 45 */   private int maxGrowSize = Integer.MAX_VALUE;
/* 46 */   private String[] flockAllowedRoles = ArrayUtil.EMPTY_STRING_ARRAY;
/*    */   
/*    */   private int size;
/*    */   
/*    */   public PersistentFlockData(@Nullable FlockAsset flockDefinition, @Nonnull String[] allowedRoles) {
/* 51 */     this.flockAllowedRoles = allowedRoles;
/* 52 */     Arrays.sort((Object[])this.flockAllowedRoles);
/* 53 */     if (flockDefinition == null)
/*    */       return; 
/* 55 */     this.maxGrowSize = flockDefinition.getMaxGrowSize();
/* 56 */     String[] blockedRoles = flockDefinition.getBlockedRoles();
/* 57 */     if (blockedRoles.length <= 0 || this.flockAllowedRoles.length <= 0)
/*    */       return; 
/* 59 */     ObjectArrayList<String> combinedList = new ObjectArrayList(this.flockAllowedRoles.length);
/* 60 */     Collections.addAll((Collection<? super String>)combinedList, this.flockAllowedRoles);
/* 61 */     for (String blockedRole : blockedRoles) {
/* 62 */       combinedList.remove(blockedRole);
/*    */     }
/* 64 */     this.flockAllowedRoles = (String[])combinedList.toArray(x$0 -> new String[x$0]);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMaxGrowSize() {
/* 71 */     return this.maxGrowSize;
/*    */   }
/*    */   
/*    */   public boolean isFlockAllowedRole(String role) {
/* 75 */     return (Arrays.binarySearch((Object[])this.flockAllowedRoles, role) >= 0);
/*    */   }
/*    */   
/*    */   public void increaseSize() {
/* 79 */     this.size++;
/*    */   }
/*    */   
/*    */   public void decreaseSize() {
/* 83 */     this.size--;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public Component<EntityStore> clone() {
/* 90 */     PersistentFlockData data = new PersistentFlockData();
/* 91 */     data.maxGrowSize = this.maxGrowSize;
/* 92 */     data.flockAllowedRoles = this.flockAllowedRoles;
/* 93 */     data.size = this.size;
/* 94 */     return data;
/*    */   }
/*    */   
/*    */   private PersistentFlockData() {}
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\flock\PersistentFlockData.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */