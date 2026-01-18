/*    */ package com.hypixel.hytale.server.worldgen.container;
/*    */ 
/*    */ import com.hypixel.hytale.common.map.IWeightedMap;
/*    */ import com.hypixel.hytale.procedurallib.condition.DefaultCoordinateCondition;
/*    */ import com.hypixel.hytale.procedurallib.condition.ICoordinateCondition;
/*    */ import com.hypixel.hytale.procedurallib.property.NoiseProperty;
/*    */ import javax.annotation.Nonnull;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DefaultTintContainerEntry
/*    */   extends TintContainer.TintContainerEntry
/*    */ {
/*    */   public DefaultTintContainerEntry(IWeightedMap<Integer> colorMapping, NoiseProperty valueNoise) {
/* 46 */     super(colorMapping, valueNoise, (ICoordinateCondition)DefaultCoordinateCondition.DEFAULT_TRUE);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 52 */     return "DefaultTintContainerEntry{}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\container\TintContainer$DefaultTintContainerEntry.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */