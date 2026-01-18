/*    */ package joptsimple.util;
/*    */ 
/*    */ import java.nio.file.Path;
/*    */ import java.nio.file.Paths;
/*    */ import java.text.MessageFormat;
/*    */ import java.util.ResourceBundle;
/*    */ import joptsimple.ValueConversionException;
/*    */ import joptsimple.ValueConverter;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PathConverter
/*    */   implements ValueConverter<Path>
/*    */ {
/*    */   private final PathProperties[] pathProperties;
/*    */   
/*    */   public PathConverter(PathProperties... pathProperties) {
/* 18 */     this.pathProperties = pathProperties;
/*    */   }
/*    */ 
/*    */   
/*    */   public Path convert(String value) {
/* 23 */     Path path = Paths.get(value, new String[0]);
/*    */     
/* 25 */     if (this.pathProperties != null) {
/* 26 */       for (PathProperties each : this.pathProperties) {
/* 27 */         if (!each.accept(path)) {
/* 28 */           throw new ValueConversionException(message(each.getMessageKey(), path.toString()));
/*    */         }
/*    */       } 
/*    */     }
/* 32 */     return path;
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<Path> valueType() {
/* 37 */     return Path.class;
/*    */   }
/*    */ 
/*    */   
/*    */   public String valuePattern() {
/* 42 */     return null;
/*    */   }
/*    */   
/*    */   private String message(String errorKey, String value) {
/* 46 */     ResourceBundle bundle = ResourceBundle.getBundle("joptsimple.ExceptionMessages");
/* 47 */     Object[] arguments = { value, valuePattern() };
/* 48 */     String template = bundle.getString(PathConverter.class.getName() + "." + errorKey + ".message");
/* 49 */     return (new MessageFormat(template)).format(arguments);
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\joptsimpl\\util\PathConverter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */