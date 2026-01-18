/*     */ package com.google.common.flogger;
/*     */ 
/*     */ import com.google.common.flogger.util.Checks;
/*     */ import com.google.errorprone.annotations.CheckReturnValue;
/*     */ import org.checkerframework.checker.nullness.compatqual.NullableDecl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @CheckReturnValue
/*     */ public abstract class LogSite
/*     */   implements LogSiteKey
/*     */ {
/*     */   public static final int UNKNOWN_LINE = 0;
/*     */   
/*  57 */   public static final LogSite INVALID = new LogSite()
/*     */     {
/*     */       public String getClassName()
/*     */       {
/*  61 */         return "<unknown class>";
/*     */       }
/*     */ 
/*     */       
/*     */       public String getMethodName() {
/*  66 */         return "<unknown method>";
/*     */       }
/*     */ 
/*     */       
/*     */       public int getLineNumber() {
/*  71 */         return 0;
/*     */       }
/*     */ 
/*     */       
/*     */       public String getFileName() {
/*  76 */         return null;
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract String getClassName();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract String getMethodName();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract int getLineNumber();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract String getFileName();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String toString() {
/* 116 */     StringBuilder out = (new StringBuilder()).append("LogSite{ class=").append(getClassName()).append(", method=").append(getMethodName()).append(", line=").append(getLineNumber());
/* 117 */     if (getFileName() != null) {
/* 118 */       out.append(", file=")
/* 119 */         .append(getFileName());
/*     */     }
/* 121 */     return out.append(" }").toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static LogSite injectedLogSite(String internalClassName, String methodName, int encodedLineNumber, @NullableDecl String sourceFileName) {
/* 148 */     return new InjectedLogSite(internalClassName, methodName, encodedLineNumber, sourceFileName);
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class InjectedLogSite
/*     */     extends LogSite
/*     */   {
/*     */     private final String internalClassName;
/*     */     
/*     */     private final String methodName;
/*     */     private final int encodedLineNumber;
/*     */     private final String sourceFileName;
/* 160 */     private int hashcode = 0;
/*     */ 
/*     */     
/*     */     private InjectedLogSite(String internalClassName, String methodName, int encodedLineNumber, String sourceFileName) {
/* 164 */       this.internalClassName = (String)Checks.checkNotNull(internalClassName, "class name");
/* 165 */       this.methodName = (String)Checks.checkNotNull(methodName, "method name");
/* 166 */       this.encodedLineNumber = encodedLineNumber;
/* 167 */       this.sourceFileName = sourceFileName;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getClassName() {
/* 176 */       return this.internalClassName.replace('/', '.');
/*     */     }
/*     */ 
/*     */     
/*     */     public String getMethodName() {
/* 181 */       return this.methodName;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int getLineNumber() {
/* 187 */       return this.encodedLineNumber & 0xFFFF;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getFileName() {
/* 192 */       return this.sourceFileName;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object obj) {
/* 197 */       if (obj instanceof InjectedLogSite) {
/* 198 */         InjectedLogSite other = (InjectedLogSite)obj;
/*     */         
/* 200 */         return (this.internalClassName.equals(other.internalClassName) && this.methodName
/* 201 */           .equals(other.methodName) && this.encodedLineNumber == other.encodedLineNumber);
/*     */       } 
/*     */       
/* 204 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 209 */       if (this.hashcode == 0) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 214 */         int temp = 157;
/* 215 */         temp = 31 * temp + this.internalClassName.hashCode();
/* 216 */         temp = 31 * temp + this.methodName.hashCode();
/* 217 */         temp = 31 * temp + this.encodedLineNumber;
/* 218 */         this.hashcode = temp;
/*     */       } 
/* 220 */       return this.hashcode;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\common\flogger\LogSite.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */