/*    */ package com.google.common.flogger.parameter;
/*    */ 
/*    */ import com.google.common.flogger.backend.FormatChar;
/*    */ import com.google.common.flogger.backend.FormatOptions;
/*    */ import com.google.common.flogger.backend.FormatType;
/*    */ import java.text.FieldPosition;
/*    */ import java.text.MessageFormat;
/*    */ import java.util.Locale;
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
/*    */ public class BraceStyleParameter
/*    */   extends Parameter
/*    */ {
/* 34 */   private static final FormatOptions WITH_GROUPING = FormatOptions.of(16, -1, -1);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 39 */   private static final MessageFormat prototypeMessageFormatter = new MessageFormat("{0}", Locale.ROOT);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static final int MAX_CACHED_PARAMETERS = 10;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 49 */   private static final BraceStyleParameter[] DEFAULT_PARAMETERS = new BraceStyleParameter[10]; static {
/* 50 */     for (int index = 0; index < 10; index++) {
/* 51 */       DEFAULT_PARAMETERS[index] = new BraceStyleParameter(index);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static BraceStyleParameter of(int index) {
/* 63 */     return (index < 10) ? 
/* 64 */       DEFAULT_PARAMETERS[index] : 
/* 65 */       new BraceStyleParameter(index);
/*    */   }
/*    */   
/*    */   private BraceStyleParameter(int index) {
/* 69 */     super(FormatOptions.getDefault(), index);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void accept(ParameterVisitor visitor, Object value) {
/* 75 */     if (FormatType.INTEGRAL.canFormat(value)) {
/* 76 */       visitor.visit(value, FormatChar.DECIMAL, WITH_GROUPING);
/* 77 */     } else if (FormatType.FLOAT.canFormat(value)) {
/*    */ 
/*    */ 
/*    */       
/* 81 */       visitor.visit(value, FormatChar.FLOAT, WITH_GROUPING);
/* 82 */     } else if (value instanceof java.util.Date) {
/*    */ 
/*    */ 
/*    */       
/* 86 */       String formatted = ((MessageFormat)prototypeMessageFormatter.clone()).format(new Object[] { value }, new StringBuffer(), (FieldPosition)null).toString();
/* 87 */       visitor.visitPreformatted(value, formatted);
/* 88 */     } else if (value instanceof java.util.Calendar) {
/* 89 */       visitor.visitDateTime(value, DateTimeFormat.DATETIME_FULL, getFormatOptions());
/*    */     } else {
/* 91 */       visitor.visit(value, FormatChar.STRING, getFormatOptions());
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public String getFormat() {
/* 97 */     return "%s";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\common\flogger\parameter\BraceStyleParameter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */