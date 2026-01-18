/*    */ package com.hypixel.hytale.server.npc.asset.builder.validators;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class IntValidator
/*    */   extends Validator
/*    */ {
/*    */   public abstract boolean test(int paramInt);
/*    */   
/*    */   public static boolean compare(int value, @Nonnull RelationalOperator op, int c) {
/* 13 */     switch (op) { default: throw new MatchException(null, null);case NotEqual: return 
/* 14 */           (value != c);
/* 15 */       case Less: return (value < c);
/* 16 */       case LessEqual: return (value <= c);
/* 17 */       case Greater: return (value > c);
/* 18 */       case GreaterEqual: return (value >= c);
/* 19 */       case Equal: break; }  return (value == c);
/*    */   }
/*    */   
/*    */   public abstract String errorMessage(int paramInt);
/*    */   
/*    */   public abstract String errorMessage(int paramInt, String paramString);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\validators\IntValidator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */