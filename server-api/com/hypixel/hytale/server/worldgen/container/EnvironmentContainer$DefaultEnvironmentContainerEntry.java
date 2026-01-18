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
/*    */ public class DefaultEnvironmentContainerEntry
/*    */   extends EnvironmentContainer.EnvironmentContainerEntry
/*    */ {
/*    */   public DefaultEnvironmentContainerEntry(IWeightedMap<Integer> environmentMapping, NoiseProperty valueNoise) {
/* 44 */     super(environmentMapping, valueNoise, (ICoordinateCondition)DefaultCoordinateCondition.DEFAULT_TRUE);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 50 */     return "DefaultEnvironmentContainerEntry{environmentMapping=" + String.valueOf(this.environmentMapping) + ", valueNoise=" + String.valueOf(this.valueNoise) + ", mapCondition=" + String.valueOf(this.mapCondition) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\worldgen\container\EnvironmentContainer$DefaultEnvironmentContainerEntry.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */