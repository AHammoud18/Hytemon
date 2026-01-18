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
/*    */ 
/*    */ class MissingRequiredOptionsException
/*    */   extends OptionException
/*    */ {
/*    */   private static final long serialVersionUID = -1L;
/*    */   
/*    */   protected MissingRequiredOptionsException(List<? extends OptionSpec<?>> missingRequiredOptions) {
/* 39 */     super(missingRequiredOptions);
/*    */   }
/*    */ 
/*    */   
/*    */   Object[] messageArguments() {
/* 44 */     return new Object[] { multipleOptionString() };
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\joptsimple\MissingRequiredOptionsException.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */