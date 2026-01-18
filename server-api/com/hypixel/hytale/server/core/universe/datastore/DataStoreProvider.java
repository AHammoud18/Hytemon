/*   */ package com.hypixel.hytale.server.core.universe.datastore;
/*   */ 
/*   */ import com.hypixel.hytale.codec.builder.BuilderCodec;
/*   */ import com.hypixel.hytale.codec.lookup.BuilderCodecMapCodec;
/*   */ 
/*   */ public interface DataStoreProvider {
/* 7 */   public static final BuilderCodecMapCodec<DataStoreProvider> CODEC = new BuilderCodecMapCodec("Type");
/*   */   
/*   */   <T> DataStore<T> create(BuilderCodec<T> paramBuilderCodec);
/*   */ }


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\cor\\universe\datastore\DataStoreProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */