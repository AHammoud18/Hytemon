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
/*    */ class IllegalOptionSpecificationException
/*    */   extends OptionException
/*    */ {
/*    */   private static final long serialVersionUID = -1L;
/*    */   
/*    */   IllegalOptionSpecificationException(String option) {
/* 39 */     super(Collections.singletonList(option));
/*    */   }
/*    */ 
/*    */   
/*    */   Object[] messageArguments() {
/* 44 */     return new Object[] { singleOptionString() };
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\joptsimple\IllegalOptionSpecificationException.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */