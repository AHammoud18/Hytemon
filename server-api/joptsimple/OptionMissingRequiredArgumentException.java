/*    */ package joptsimple;
/*    */ 
/*    */ import java.util.Arrays;
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
/*    */ class OptionMissingRequiredArgumentException
/*    */   extends OptionException
/*    */ {
/*    */   private static final long serialVersionUID = -1L;
/*    */   
/*    */   OptionMissingRequiredArgumentException(OptionSpec<?> option) {
/* 39 */     super(Arrays.asList((OptionSpec<?>[])new OptionSpec[] { option }));
/*    */   }
/*    */ 
/*    */   
/*    */   Object[] messageArguments() {
/* 44 */     return new Object[] { singleOptionString() };
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\joptsimple\OptionMissingRequiredArgumentException.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */