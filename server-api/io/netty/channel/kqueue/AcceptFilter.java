/*    */ package io.netty.channel.kqueue;
/*    */ 
/*    */ import io.netty.util.internal.ObjectUtil;
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
/*    */ public final class AcceptFilter
/*    */ {
/* 23 */   static final AcceptFilter PLATFORM_UNSUPPORTED = new AcceptFilter("", "");
/*    */   private final String filterName;
/*    */   private final String filterArgs;
/*    */   
/*    */   public AcceptFilter(String filterName, String filterArgs) {
/* 28 */     this.filterName = (String)ObjectUtil.checkNotNull(filterName, "filterName");
/* 29 */     this.filterArgs = (String)ObjectUtil.checkNotNull(filterArgs, "filterArgs");
/*    */   }
/*    */   
/*    */   public String filterName() {
/* 33 */     return this.filterName;
/*    */   }
/*    */   
/*    */   public String filterArgs() {
/* 37 */     return this.filterArgs;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 42 */     if (o == this) {
/* 43 */       return true;
/*    */     }
/* 45 */     if (!(o instanceof AcceptFilter)) {
/* 46 */       return false;
/*    */     }
/* 48 */     AcceptFilter rhs = (AcceptFilter)o;
/* 49 */     return (this.filterName.equals(rhs.filterName) && this.filterArgs.equals(rhs.filterArgs));
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 54 */     return 31 * (31 + this.filterName.hashCode()) + this.filterArgs.hashCode();
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 59 */     return this.filterName + ", " + this.filterArgs;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\netty\channel\kqueue\AcceptFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */