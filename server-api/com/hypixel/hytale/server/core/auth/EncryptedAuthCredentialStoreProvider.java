/*    */ package com.hypixel.hytale.server.core.auth;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import java.nio.file.Path;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
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
/*    */ public class EncryptedAuthCredentialStoreProvider
/*    */   implements AuthCredentialStoreProvider
/*    */ {
/*    */   public static final String ID = "Encrypted";
/*    */   public static final String DEFAULT_PATH = "auth.enc";
/*    */   public static final BuilderCodec<EncryptedAuthCredentialStoreProvider> CODEC;
/*    */   
/*    */   static {
/* 27 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(EncryptedAuthCredentialStoreProvider.class, EncryptedAuthCredentialStoreProvider::new).append(new KeyedCodec("Path", (Codec)Codec.STRING), (o, p) -> o.path = p, o -> o.path).add()).build();
/*    */   }
/* 29 */   private String path = "auth.enc";
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public IAuthCredentialStore createStore() {
/* 34 */     return new EncryptedAuthCredentialStore(Path.of(this.path, new String[0]));
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 40 */     return "EncryptedAuthCredentialStoreProvider{path='" + this.path + "'}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\auth\EncryptedAuthCredentialStoreProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */