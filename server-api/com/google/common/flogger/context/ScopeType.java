/*    */ package com.google.common.flogger.context;
/*    */ 
/*    */ import com.google.common.flogger.LoggingScope;
/*    */ import com.google.common.flogger.LoggingScopeProvider;
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
/*    */ public final class ScopeType
/*    */   implements LoggingScopeProvider
/*    */ {
/* 57 */   public static final ScopeType REQUEST = create("request");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private final String name;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ScopeType create(String name) {
/* 68 */     return new ScopeType(name);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private ScopeType(String name) {
/* 74 */     this.name = (String)Checks.checkNotNull(name, "name");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   LoggingScope newScope() {
/* 80 */     return LoggingScope.create(this.name);
/*    */   }
/*    */ 
/*    */   
/*    */   @NullableDecl
/*    */   public LoggingScope getCurrentScope() {
/* 86 */     return ContextDataProvider.getInstance().getScope(this);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\common\flogger\context\ScopeType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */