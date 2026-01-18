/*    */ package io.sentry.clientreport;
/*    */ 
/*    */ import io.sentry.util.Objects;
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ @Internal
/*    */ final class ClientReportKey {
/*    */   @NotNull
/*    */   private final String reason;
/*    */   
/*    */   ClientReportKey(@NotNull String reason, @NotNull String category) {
/* 13 */     this.reason = reason;
/* 14 */     this.category = category;
/*    */   } @NotNull
/*    */   private final String category; @NotNull
/*    */   public String getReason() {
/* 18 */     return this.reason;
/*    */   }
/*    */   @NotNull
/*    */   public String getCategory() {
/* 22 */     return this.category;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 27 */     if (this == o) return true; 
/* 28 */     if (!(o instanceof ClientReportKey)) return false; 
/* 29 */     ClientReportKey that = (ClientReportKey)o;
/* 30 */     return (Objects.equals(getReason(), that.getReason()) && 
/* 31 */       Objects.equals(getCategory(), that.getCategory()));
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 36 */     return Objects.hash(new Object[] { getReason(), getCategory() });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\clientreport\ClientReportKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */