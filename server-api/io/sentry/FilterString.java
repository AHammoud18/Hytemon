/*    */ package io.sentry;
/*    */ 
/*    */ import java.util.Objects;
/*    */ import java.util.regex.Pattern;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ public final class FilterString {
/*    */   @NotNull
/*    */   private final String filterString;
/*    */   
/*    */   public FilterString(@NotNull String filterString) {
/* 13 */     this.filterString = filterString;
/* 14 */     Pattern pattern = null;
/*    */     try {
/* 16 */       pattern = Pattern.compile(filterString);
/* 17 */     } catch (Throwable t) {
/* 18 */       Sentry.getCurrentScopes()
/* 19 */         .getOptions()
/* 20 */         .getLogger()
/* 21 */         .log(SentryLevel.DEBUG, "Only using filter string for String comparison as it could not be parsed as regex: %s", new Object[] { filterString });
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 26 */     this.pattern = pattern;
/*    */   } @Nullable
/*    */   private final Pattern pattern; @NotNull
/*    */   public String getFilterString() {
/* 30 */     return this.filterString;
/*    */   }
/*    */   
/*    */   public boolean matches(String input) {
/* 34 */     if (this.pattern == null) {
/* 35 */       return false;
/*    */     }
/* 37 */     return this.pattern.matcher(input).matches();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 42 */     if (o == null || getClass() != o.getClass()) return false; 
/* 43 */     FilterString that = (FilterString)o;
/* 44 */     return Objects.equals(this.filterString, that.filterString);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 49 */     return Objects.hash(new Object[] { this.filterString });
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\FilterString.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */