/*    */ package com.hypixel.hytale.server.core.receiver;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.Message;
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
/*    */ public interface IEventTitleReceiver
/*    */ {
/*    */   public static final float DEFAULT_DURATION = 4.0F;
/*    */   public static final float DEFAULT_FADE_DURATION = 1.5F;
/*    */   
/*    */   default void showEventTitle(Message primaryTitle, Message secondaryTitle, boolean isMajor, String icon) {
/* 23 */     showEventTitle(primaryTitle, secondaryTitle, isMajor, icon, 4.0F);
/*    */   }
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
/*    */   default void showEventTitle(Message primaryTitle, Message secondaryTitle, boolean isMajor, String icon, float duration) {
/* 36 */     showEventTitle(primaryTitle, secondaryTitle, isMajor, icon, duration, 1.5F, 1.5F);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   void showEventTitle(Message paramMessage1, Message paramMessage2, boolean paramBoolean, String paramString, float paramFloat1, float paramFloat2, float paramFloat3);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   default void hideEventTitle() {
/* 53 */     hideEventTitle(1.5F);
/*    */   }
/*    */   
/*    */   void hideEventTitle(float paramFloat);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\receiver\IEventTitleReceiver.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */