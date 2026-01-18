/*    */ package com.hypixel.hytale.builtin.adventure.worldlocationcondition;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.server.core.plugin.JavaPlugin;
/*    */ import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
/*    */ import com.hypixel.hytale.server.core.universe.world.worldlocationcondition.WorldLocationCondition;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldLocationConditionPlugin
/*    */   extends JavaPlugin
/*    */ {
/*    */   public WorldLocationConditionPlugin(@Nonnull JavaPluginInit init) {
/* 15 */     super(init);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void setup() {
/* 20 */     WorldLocationCondition.CODEC.register("NeighbourBlockTags", NeighbourBlockTagsLocationCondition.class, (Codec)NeighbourBlockTagsLocationCondition.CODEC);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\adventure\worldlocationcondition\WorldLocationConditionPlugin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */