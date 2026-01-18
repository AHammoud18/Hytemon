/*    */ package io.sentry.util;
/*    */ 
/*    */ import io.sentry.FilterString;
/*    */ import io.sentry.SentryEvent;
/*    */ import io.sentry.protocol.Message;
/*    */ import java.util.HashSet;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class ErrorUtils
/*    */ {
/*    */   @Internal
/*    */   public static boolean isIgnored(@Nullable List<FilterString> ignoredErrors, @NotNull SentryEvent event) {
/* 19 */     if (event == null || ignoredErrors == null || ignoredErrors.isEmpty()) {
/* 20 */       return false;
/*    */     }
/*    */     
/* 23 */     Set<String> possibleMessages = new HashSet<>();
/*    */     
/* 25 */     Message eventMessage = event.getMessage();
/* 26 */     if (eventMessage != null) {
/* 27 */       String stringMessage = eventMessage.getMessage();
/* 28 */       if (stringMessage != null) {
/* 29 */         possibleMessages.add(stringMessage);
/*    */       }
/* 31 */       String formattedMessage = eventMessage.getFormatted();
/* 32 */       if (formattedMessage != null) {
/* 33 */         possibleMessages.add(formattedMessage);
/*    */       }
/*    */     } 
/* 36 */     Throwable throwable = event.getThrowable();
/* 37 */     if (throwable != null) {
/* 38 */       possibleMessages.add(throwable.toString());
/*    */     }
/*    */     
/* 41 */     for (FilterString filter : ignoredErrors) {
/* 42 */       if (possibleMessages.contains(filter.getFilterString())) {
/* 43 */         return true;
/*    */       }
/*    */     } 
/*    */     
/* 47 */     for (FilterString filter : ignoredErrors) {
/* 48 */       for (String message : possibleMessages) {
/* 49 */         if (filter.matches(message)) {
/* 50 */           return true;
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/* 55 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentr\\util\ErrorUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */