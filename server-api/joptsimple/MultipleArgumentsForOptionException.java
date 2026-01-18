/*    */ package joptsimple;
/*    */ 
/*    */ import java.util.Collections;
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
/*    */ class MultipleArgumentsForOptionException
/*    */   extends OptionException
/*    */ {
/*    */   private static final long serialVersionUID = -1L;
/*    */   
/*    */   MultipleArgumentsForOptionException(OptionSpec<?> options) {
/* 39 */     super(Collections.singleton(options));
/*    */   }
/*    */ 
/*    */   
/*    */   Object[] messageArguments() {
/* 44 */     return new Object[] { singleOptionString() };
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\joptsimple\MultipleArgumentsForOptionException.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */