/*    */ package com.hypixel.hytale.server.npc.util.expression;
/*    */ 
/*    */ import java.util.concurrent.ThreadLocalRandom;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StdLib
/*    */   extends StdScope
/*    */ {
/* 14 */   private static final StdLib instance = new StdLib();
/*    */   
/*    */   private StdLib() {
/* 17 */     super(null);
/* 18 */     addConst("true", true);
/* 19 */     addConst("false", false);
/* 20 */     addConst("PI", 3.1415927410125732D);
/* 21 */     addInvariant("max", (context, numArgs) -> context.popPush(Math.max(context.getNumber(0), context.getNumber(1)), 2), ValueType.NUMBER, new ValueType[] { ValueType.NUMBER, ValueType.NUMBER });
/*    */     
/* 23 */     addInvariant("min", (context, numArgs) -> context.popPush(Math.min(context.getNumber(0), context.getNumber(1)), 2), ValueType.NUMBER, new ValueType[] { ValueType.NUMBER, ValueType.NUMBER });
/*    */     
/* 25 */     addInvariant("isEmpty", (context, numArgs) -> {
/*    */           String string = context.getString(0);
/*    */           
/* 28 */           context.popPush((string == null || string.isEmpty()), 1);
/*    */         }ValueType.BOOLEAN, new ValueType[] { ValueType.STRING });
/* 30 */     addInvariant("isEmptyStringArray", (context, numArgs) -> context.popPush(((context.getStringArray(0)).length == 0), 1), ValueType.BOOLEAN, new ValueType[] { ValueType.STRING_ARRAY });
/*    */     
/* 32 */     addInvariant("isEmptyNumberArray", (context, numArgs) -> context.popPush(((context.getNumberArray(0)).length == 0), 1), ValueType.BOOLEAN, new ValueType[] { ValueType.NUMBER_ARRAY });
/*    */     
/* 34 */     addVariant("random", (context, numArgs) -> context.push(ThreadLocalRandom.current().nextDouble()), ValueType.NUMBER, new ValueType[0]);
/* 35 */     addVariant("randomInRange", (context, numArgs) -> context.popPush(ThreadLocalRandom.current().nextDouble(context.getNumber(1), context.getNumber(0)), 2), ValueType.NUMBER, new ValueType[] { ValueType.NUMBER, ValueType.NUMBER });
/*    */     
/* 37 */     addInvariant("makeRange", (context, numArgs) -> { double value = context.getNumber(0); context.popPush(new double[] { value, value }, 1); }ValueType.NUMBER_ARRAY, new ValueType[] { ValueType.NUMBER });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static StdScope getInstance() {
/* 44 */     return instance;
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\np\\util\expression\StdLib.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */