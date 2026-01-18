/*    */ package io.sentry;
/*    */ 
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SpanOptions
/*    */ {
/*    */   @Nullable
/* 13 */   private SentryDate startTimestamp = null;
/*    */   @NotNull
/* 15 */   private ScopeBindingMode scopeBindingMode = ScopeBindingMode.AUTO;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public SentryDate getStartTimestamp() {
/* 23 */     return this.startTimestamp;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setStartTimestamp(@Nullable SentryDate startTimestamp) {
/* 32 */     this.startTimestamp = startTimestamp;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private boolean trimStart = false;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private boolean trimEnd = false;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private boolean isIdle = false;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/* 56 */   protected String origin = "manual";
/*    */   
/*    */   public boolean isTrimStart() {
/* 59 */     return this.trimStart;
/*    */   }
/*    */   
/*    */   public boolean isTrimEnd() {
/* 63 */     return this.trimEnd;
/*    */   }
/*    */   
/*    */   public boolean isIdle() {
/* 67 */     return this.isIdle;
/*    */   }
/*    */   
/*    */   public void setTrimStart(boolean trimStart) {
/* 71 */     this.trimStart = trimStart;
/*    */   }
/*    */   
/*    */   public void setTrimEnd(boolean trimEnd) {
/* 75 */     this.trimEnd = trimEnd;
/*    */   }
/*    */   
/*    */   public void setIdle(boolean idle) {
/* 79 */     this.isIdle = idle;
/*    */   }
/*    */   @Nullable
/*    */   public String getOrigin() {
/* 83 */     return this.origin;
/*    */   }
/*    */   
/*    */   public void setOrigin(@Nullable String origin) {
/* 87 */     this.origin = origin;
/*    */   }
/*    */   @NotNull
/*    */   public ScopeBindingMode getScopeBindingMode() {
/* 91 */     return this.scopeBindingMode;
/*    */   }
/*    */   
/*    */   public void setScopeBindingMode(@NotNull ScopeBindingMode scopeBindingMode) {
/* 95 */     this.scopeBindingMode = scopeBindingMode;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\SpanOptions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */