/*    */ package com.hypixel.hytale.server.core.util.message;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.Message;
/*    */ import java.awt.Color;
/*    */ import java.util.Collection;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class MessageFormat
/*    */ {
/* 15 */   private static final Message ENABLED = Message.translation("server.general.enabled").color(Color.GREEN);
/* 16 */   private static final Message DISABLED = Message.translation("server.general.disabled").color(Color.RED);
/*    */ 
/*    */ 
/*    */   
/*    */   private static final int LIST_MAX_INLINE_VALUES = 4;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public static Message enabled(boolean b) {
/* 27 */     return b ? ENABLED : DISABLED;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public static Message list(@Nullable Message header, @Nonnull Collection<Message> values) {
/* 46 */     Message msg = Message.empty();
/* 47 */     if (header != null) {
/* 48 */       msg.insert(Message.translation("server.formatting.list.header")
/* 49 */           .param("header", header)
/* 50 */           .param("count", values.size()));
/*    */       
/* 52 */       if (values.size() <= 4) {
/* 53 */         msg.insert(Message.translation("server.formatting.list.inlineHeaderSuffix"));
/*    */       }
/*    */     } 
/*    */     
/* 57 */     if (values.isEmpty()) {
/* 58 */       msg.insert(Message.translation("server.formatting.list.empty"));
/* 59 */       return msg;
/*    */     } 
/*    */     
/* 62 */     if (values.size() <= 4) {
/* 63 */       Message separator = Message.translation("server.formatting.list.itemSeparator");
/* 64 */       Message[] array = values.<Message>toArray(x$0 -> new Message[x$0]);
/* 65 */       for (int i = 0; i < array.length; i++) {
/* 66 */         msg.insert(array[i]);
/* 67 */         if (i < array.length - 1) msg.insert(separator); 
/*    */       } 
/*    */     } else {
/* 70 */       Message delim = Message.raw("\n");
/* 71 */       for (Message value : values) {
/* 72 */         msg.insert(delim);
/* 73 */         msg.insert(Message.translation("server.formatting.list.row").param("value", value));
/*    */       } 
/*    */     } 
/*    */     
/* 77 */     return msg;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\util\message\MessageFormat.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */