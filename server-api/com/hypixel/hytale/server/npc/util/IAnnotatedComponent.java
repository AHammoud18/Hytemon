/*    */ package com.hypixel.hytale.server.npc.util;
/*    */ 
/*    */ import com.hypixel.hytale.server.npc.role.Role;
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
/*    */ public interface IAnnotatedComponent
/*    */ {
/*    */   void getInfo(Role paramRole, ComponentInfo paramComponentInfo);
/*    */   
/*    */   void setContext(IAnnotatedComponent paramIAnnotatedComponent, int paramInt);
/*    */   
/*    */   @Nullable
/*    */   IAnnotatedComponent getParent();
/*    */   
/*    */   int getIndex();
/*    */   
/*    */   default String getLabel() {
/* 30 */     int index = getIndex();
/* 31 */     return (index >= 0) ? String.format("[%s]%s", new Object[] { Integer.valueOf(index), getClass().getSimpleName() }) : getClass().getSimpleName();
/*    */   }
/*    */   
/*    */   default void getBreadCrumbs(@Nonnull StringBuilder sb) {
/* 35 */     IAnnotatedComponent parent = getParent();
/* 36 */     if (parent != null) parent.getBreadCrumbs(sb); 
/* 37 */     String label = getLabel();
/* 38 */     if (label != null && !label.isEmpty()) {
/* 39 */       if (!sb.isEmpty()) sb.append('|'); 
/* 40 */       sb.append(label);
/*    */     } 
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   default String getBreadCrumbs() {
/* 46 */     StringBuilder sb = new StringBuilder();
/* 47 */     getBreadCrumbs(sb);
/* 48 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\np\\util\IAnnotatedComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */