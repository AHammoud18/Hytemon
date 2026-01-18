/*    */ package com.hypixel.hytale.server.core.universe.datastore;
/*    */ 
/*    */ import com.hypixel.hytale.codec.Codec;
/*    */ import com.hypixel.hytale.codec.KeyedCodec;
/*    */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*    */ import com.hypixel.hytale.codec.validation.Validators;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nonnull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DiskDataStoreProvider
/*    */   implements DataStoreProvider
/*    */ {
/*    */   public static final String ID = "Disk";
/*    */   public static final BuilderCodec<DiskDataStoreProvider> CODEC;
/*    */   private String path;
/*    */   
/*    */   static {
/* 21 */     CODEC = ((BuilderCodec.Builder)BuilderCodec.builder(DiskDataStoreProvider.class, DiskDataStoreProvider::new).append(new KeyedCodec("Path", (Codec)Codec.STRING), (diskDataStoreProvider, s) -> diskDataStoreProvider.path = s, diskDataStoreProvider -> diskDataStoreProvider.path).addValidator(Validators.nonNull()).add()).build();
/*    */   }
/*    */ 
/*    */   
/*    */   public DiskDataStoreProvider(String path) {
/* 26 */     this.path = path;
/*    */   }
/*    */ 
/*    */   
/*    */   protected DiskDataStoreProvider() {}
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public <T> DataStore<T> create(BuilderCodec<T> builderCodec) {
/* 35 */     return new DiskDataStore<>(this.path, builderCodec);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public String toString() {
/* 41 */     return "DiskDataStoreProvider{path='" + this.path + "'}";
/*    */   }
/*    */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\datastore\DiskDataStoreProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */