/*    */ package com.hypixel.hytale.server.core.modules.i18n.event;
/*    */ 
/*    */ import com.hypixel.hytale.event.IEvent;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ public class MessagesUpdated
/*    */   implements IEvent<Void> {
/*    */   private final Map<String, Map<String, String>> changedMessages;
/*    */   private final Map<String, Map<String, String>> removedMessages;
/*    */   
/*    */   public MessagesUpdated(Map<String, Map<String, String>> changedMessages, Map<String, Map<String, String>> removedMessages) {
/* 13 */     this.changedMessages = changedMessages;
/* 14 */     this.removedMessages = removedMessages;
/*    */   }
/*    */   
/*    */   public Map<String, Map<String, String>> getChangedMessages() {
/* 18 */     return this.changedMessages;
/*    */   }
/*    */   
/*    */   public Map<String, Map<String, String>> getRemovedMessages() {
/* 22 */     return this.removedMessages;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 28 */     return "MessagesUpdated{changedMessages=" + String.valueOf(this.changedMessages) + ", removedMessages=" + String.valueOf(this.removedMessages) + "}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\modules\i18n\event\MessagesUpdated.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */