/*    */ package com.hypixel.hytale.server.core.prefab.selection;
/*    */ 
/*    */ import java.util.concurrent.atomic.AtomicReference;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public final class SelectionManager {
/*  7 */   private static final AtomicReference<SelectionProvider> SELECTION_PROVIDER = new AtomicReference<>();
/*    */ 
/*    */ 
/*    */   
/*    */   public static void setSelectionProvider(SelectionProvider provider) {
/* 12 */     SELECTION_PROVIDER.set(provider);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public static SelectionProvider getSelectionProvider() {
/* 17 */     return SELECTION_PROVIDER.get();
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\prefab\selection\SelectionManager.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */