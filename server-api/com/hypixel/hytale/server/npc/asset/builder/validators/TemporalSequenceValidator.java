/*    */ package com.hypixel.hytale.server.npc.asset.builder.validators;
/*    */ 
/*    */ import com.hypixel.hytale.server.core.modules.time.WorldTimeResource;
/*    */ import java.time.LocalDateTime;
/*    */ import java.time.temporal.TemporalAmount;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TemporalSequenceValidator
/*    */   extends TemporalArrayValidator
/*    */ {
/*    */   private final RelationalOperator relationLower;
/*    */   private final TemporalAmount lower;
/*    */   private final RelationalOperator relationUpper;
/*    */   private final TemporalAmount upper;
/*    */   private final RelationalOperator relationSequence;
/*    */   
/*    */   private TemporalSequenceValidator(RelationalOperator relationLower, TemporalAmount lower, RelationalOperator relationUpper, TemporalAmount upper, RelationalOperator relationSequence) {
/* 23 */     this.lower = lower;
/* 24 */     this.upper = upper;
/* 25 */     this.relationLower = relationLower;
/* 26 */     this.relationUpper = relationUpper;
/* 27 */     this.relationSequence = relationSequence;
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static TemporalSequenceValidator betweenMonotonic(TemporalAmount lower, TemporalAmount upper) {
/* 32 */     return new TemporalSequenceValidator(RelationalOperator.GreaterEqual, lower, RelationalOperator.LessEqual, upper, RelationalOperator.Less);
/*    */   }
/*    */   
/*    */   @Nonnull
/*    */   public static TemporalSequenceValidator betweenWeaklyMonotonic(TemporalAmount lower, TemporalAmount upper) {
/* 37 */     return new TemporalSequenceValidator(RelationalOperator.GreaterEqual, lower, RelationalOperator.LessEqual, upper, RelationalOperator.LessEqual);
/*    */   }
/*    */   
/*    */   public static boolean compare(@Nonnull LocalDateTime value, @Nonnull RelationalOperator op, LocalDateTime c) {
/* 41 */     switch (op) { default: throw new MatchException(null, null);case NotEqual: return 
/* 42 */           !value.equals(c);
/*    */       case Less: 
/* 44 */       case LessEqual: return !value.isAfter(c);
/*    */       case Greater: 
/* 46 */       case GreaterEqual: return !value.isBefore(c);
/* 47 */       case Equal: break; }  return value.equals(c);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean test(@Nonnull TemporalAmount[] values) {
/* 54 */     LocalDateTime zeroDate = LocalDateTime.ofInstant(WorldTimeResource.ZERO_YEAR, WorldTimeResource.ZONE_OFFSET);
/* 55 */     LocalDateTime min = zeroDate.plus(this.lower);
/* 56 */     LocalDateTime max = zeroDate.plus(this.upper);
/*    */     
/* 58 */     boolean expectPeriod = values[0] instanceof java.time.Period;
/* 59 */     for (int i = 0; i < values.length; i++) {
/* 60 */       TemporalAmount value = values[i];
/*    */       
/* 62 */       if (value instanceof java.time.Period && !expectPeriod) return false; 
/* 63 */       if (value instanceof java.time.Duration && expectPeriod) return false;
/*    */       
/* 65 */       LocalDateTime dateValue = zeroDate.plus(values[i]);
/* 66 */       if (!compare(dateValue, this.relationLower, min) && compare(dateValue, this.relationUpper, max)) return false;
/*    */       
/* 68 */       if (i > 0 && this.relationSequence != null) {
/* 69 */         LocalDateTime previousValue = zeroDate.plus(values[i - 1]);
/* 70 */         if (!compare(previousValue, this.relationSequence, dateValue)) return false; 
/*    */       } 
/*    */     } 
/* 73 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String errorMessage(String name, TemporalAmount[] value) {
/* 79 */     return name + name + (
/* 80 */       (this.relationLower == null) ? "" : (" values should be " + this.relationLower.asText() + " " + String.valueOf(this.lower) + " and")) + (
/* 81 */       (this.relationUpper == null) ? "" : (" values should be " + this.relationUpper.asText() + " " + String.valueOf(this.upper) + " and")) + " values must all either be periods or durations but is " + (
/* 82 */       (this.relationSequence == null) ? "" : (" succeeding values should be " + this.relationSequence.asText() + " preceding values and"));
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\npc\asset\builder\validators\TemporalSequenceValidator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */