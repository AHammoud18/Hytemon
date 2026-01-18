/*    */ package com.hypixel.hytale.server.npc.asset.builder;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
/*    */ import it.unimi.dsi.fastutil.objects.Object2ObjectMaps;
/*    */ 
/*    */ 
/*    */ public class EmptyBuilderModifier
/*    */   extends BuilderModifier
/*    */ {
/* 10 */   public static final EmptyBuilderModifier INSTANCE = new EmptyBuilderModifier();
/*    */ 
/*    */   
/*    */   private EmptyBuilderModifier() {
/* 14 */     super((Object2ObjectMap<String, BuilderModifier.ExpressionHolder>)Object2ObjectMaps.EMPTY_MAP, null, null, null, null);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isEmpty() {
/* 19 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public int exportedStateCount() {
/* 24 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void applyComponentStateMap(BuilderSupport support) {
/* 29 */     throw new UnsupportedOperationException("applyComponentStateMap is not valid for EmptyBuilderModifier");
/*    */   }
/*    */ 
/*    */   
/*    */   public void popComponentStateMap(BuilderSupport support) {
/* 34 */     throw new UnsupportedOperationException("popComponentStateMap is not valid for EmptyBuilderModifier");
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\EmptyBuilderModifier.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */