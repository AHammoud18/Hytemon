/*    */ package io.sentry;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.io.Reader;
/*    */ import java.io.Writer;
/*    */ import java.util.Map;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ final class NoOpSerializer
/*    */   implements ISerializer
/*    */ {
/* 15 */   private static final NoOpSerializer instance = new NoOpSerializer();
/*    */   
/*    */   public static NoOpSerializer getInstance() {
/* 18 */     return instance;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public <T, R> T deserializeCollection(@NotNull Reader reader, @NotNull Class<T> clazz, @Nullable JsonDeserializer<R> elementDeserializer) {
/* 28 */     return null;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public <T> T deserialize(@NotNull Reader reader, @NotNull Class<T> clazz) {
/* 33 */     return null;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public SentryEnvelope deserializeEnvelope(@NotNull InputStream inputStream) {
/* 38 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public <T> void serialize(@NotNull T entity, @NotNull Writer writer) throws IOException {}
/*    */ 
/*    */   
/*    */   public void serialize(@NotNull SentryEnvelope envelope, @NotNull OutputStream outputStream) throws Exception {}
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public String serialize(@NotNull Map<String, Object> data) throws Exception {
/* 50 */     return "";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\io\sentry\NoOpSerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */