/*    */ package com.hypixel.hytale.server.npc.asset.builder;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface BuilderContext
/*    */ {
/*    */   BuilderContext getOwner();
/*    */   
/*    */   String getLabel();
/*    */   
/*    */   default void setCurrentStateName(String name) {}
/*    */   
/*    */   @Nullable
/*    */   default Builder<?> getParent() {
/* 20 */     BuilderContext owner = getOwner();
/* 21 */     return (owner instanceof Builder) ? (Builder)owner : ((owner != null) ? owner.getParent() : null);
/*    */   }
/*    */   
/*    */   default void getBreadCrumbs(@Nonnull StringBuilder stringBuilder) {
/* 25 */     BuilderContext owner = getOwner();
/* 26 */     if (owner != null) owner.getBreadCrumbs(stringBuilder); 
/* 27 */     String label = getLabel();
/* 28 */     if (label != null && !label.isEmpty()) {
/* 29 */       if (!stringBuilder.isEmpty()) stringBuilder.append('|'); 
/* 30 */       stringBuilder.append(label);
/*    */     } 
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   default String getBreadCrumbs() {
/* 36 */     StringBuilder stringBuilder = new StringBuilder(80);
/* 37 */     getBreadCrumbs(stringBuilder);
/* 38 */     return stringBuilder.toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\BuilderContext.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */