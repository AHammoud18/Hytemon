/*    */ package com.hypixel.hytale.server.npc.asset.builder.validators;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class DoubleValidator
/*    */   extends Validator
/*    */ {
/*    */   public abstract boolean test(double paramDouble);
/*    */   
/*    */   public static boolean compare(double value, @Nonnull RelationalOperator predicate, double c) {
/* 13 */     switch (predicate) { default: throw new MatchException(null, null);case NotEqual: return 
/* 14 */           (value != c);
/* 15 */       case Less: return (value < c);
/* 16 */       case LessEqual: return (value <= c);
/* 17 */       case Greater: return (value > c);
/* 18 */       case GreaterEqual: return (value >= c);
/* 19 */       case Equal: break; }  return (value == c);
/*    */   }
/*    */   
/*    */   public abstract String errorMessage(double paramDouble);
/*    */   
/*    */   public abstract String errorMessage(double paramDouble, String paramString);
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\validators\DoubleValidator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */