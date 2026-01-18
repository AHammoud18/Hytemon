/*    */ package com.hypixel.hytale.builtin.hytalegenerator.referencebundle;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ReferenceBundle
/*    */ {
/*    */   @Nonnull
/* 15 */   private final Map<String, Reference> dataLayerMap = new HashMap<>(); @Nonnull
/* 16 */   private final Map<String, String> layerTypeMap = new HashMap<>();
/*    */ 
/*    */   
/*    */   public <T extends Reference> void put(@Nonnull String name, @Nonnull Reference reference, @Nonnull Class<T> type) {
/* 20 */     this.dataLayerMap.put(name, reference);
/* 21 */     this.layerTypeMap.put(name, type.getName());
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public Reference getLayerWithName(@Nonnull String name) {
/* 26 */     return this.dataLayerMap.get(name);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public <T extends Reference> T getLayerWithName(@Nonnull String name, @Nonnull Class<T> type) {
/* 31 */     String storedType = this.layerTypeMap.get(name);
/* 32 */     if (storedType == null)
/* 33 */       return null; 
/* 34 */     if (!storedType.equals(type.getName()))
/* 35 */       return null; 
/* 36 */     return (T)this.dataLayerMap.get(name);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\builtin\hytalegenerator\referencebundle\ReferenceBundle.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */