/*    */ package io.sentry.util;
/*    */ 
/*    */ import io.sentry.HubScopesWrapper;
/*    */ import io.sentry.IScopes;
/*    */ import io.sentry.Scopes;
/*    */ import io.sentry.Sentry;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class ScopesUtil
/*    */ {
/*    */   public static void printScopesChain(@Nullable IScopes scopes) {
/* 19 */     System.out.println("==========================================");
/* 20 */     System.out.println("=============== v Scopes v ===============");
/* 21 */     System.out.println("==========================================");
/*    */     
/* 23 */     printScopesChainInternal(scopes);
/*    */     
/* 25 */     System.out.println("==========================================");
/* 26 */     System.out.println("=============== ^ Scopes ^ ===============");
/* 27 */     System.out.println("==========================================");
/*    */   }
/*    */ 
/*    */   
/*    */   private static void printScopesChainInternal(@Nullable IScopes someScopes) {
/* 32 */     if (someScopes != null) {
/* 33 */       if (someScopes instanceof Scopes) {
/* 34 */         Scopes scopes = (Scopes)someScopes;
/*    */         
/* 36 */         String info = String.format("%-25s {g=%-25s, i=%-25s, c=%-25s} [%s]", new Object[] {
/*    */ 
/*    */               
/* 39 */               scopes, scopes.getGlobalScope(), scopes
/* 40 */               .getIsolationScope(), scopes
/* 41 */               .getScope(), scopes
/* 42 */               .getCreator() });
/* 43 */         System.out.println(info);
/* 44 */         printScopesChainInternal(someScopes.getParentScopes());
/* 45 */       } else if (someScopes instanceof io.sentry.ScopesAdapter || someScopes instanceof io.sentry.HubAdapter) {
/*    */         
/* 47 */         printScopesChainInternal(Sentry.getCurrentScopes());
/* 48 */       } else if (someScopes instanceof HubScopesWrapper) {
/* 49 */         HubScopesWrapper wrapper = (HubScopesWrapper)someScopes;
/* 50 */         printScopesChainInternal(wrapper.getScopes());
/*    */       } else {
/* 52 */         System.out.println("Hit unhandled Scopes class" + someScopes.getClass());
/*    */       } 
/*    */     } else {
/* 55 */       System.out.println("-");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentr\\util\ScopesUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */