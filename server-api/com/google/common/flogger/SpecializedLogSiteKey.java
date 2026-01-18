/*    */ package com.google.common.flogger;
/*    */ 
/*    */ import com.google.common.flogger.util.Checks;
/*    */ import org.checkerframework.checker.nullness.compatqual.NullableDecl;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ final class SpecializedLogSiteKey
/*    */   implements LogSiteKey
/*    */ {
/*    */   private final LogSiteKey delegate;
/*    */   private final Object qualifier;
/*    */   
/*    */   static LogSiteKey of(LogSiteKey key, Object qualifier) {
/* 30 */     return new SpecializedLogSiteKey(key, qualifier);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private SpecializedLogSiteKey(LogSiteKey key, Object qualifier) {
/* 37 */     this.delegate = (LogSiteKey)Checks.checkNotNull(key, "log site key");
/* 38 */     this.qualifier = Checks.checkNotNull(qualifier, "log site qualifier");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(@NullableDecl Object obj) {
/* 45 */     if (!(obj instanceof SpecializedLogSiteKey)) {
/* 46 */       return false;
/*    */     }
/* 48 */     SpecializedLogSiteKey other = (SpecializedLogSiteKey)obj;
/* 49 */     return (this.delegate.equals(other.delegate) && this.qualifier.equals(other.qualifier));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 55 */     return this.delegate.hashCode() ^ this.qualifier.hashCode();
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 60 */     return "SpecializedLogSiteKey{ delegate='" + this.delegate + "', qualifier='" + this.qualifier + "' }";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\common\flogger\SpecializedLogSiteKey.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */