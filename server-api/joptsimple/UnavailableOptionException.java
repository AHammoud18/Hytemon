/*    */ package joptsimple;
/*    */ 
/*    */ import java.util.List;
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
/*    */ class UnavailableOptionException
/*    */   extends OptionException
/*    */ {
/*    */   private static final long serialVersionUID = -1L;
/*    */   
/*    */   UnavailableOptionException(List<? extends OptionSpec<?>> forbiddenOptions) {
/* 38 */     super(forbiddenOptions);
/*    */   }
/*    */ 
/*    */   
/*    */   Object[] messageArguments() {
/* 43 */     return new Object[] { multipleOptionString() };
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\joptsimple\UnavailableOptionException.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */