package com.hypixel.hytale.server.core.meta;

@FunctionalInterface
public interface MetaEntryConsumer {
  <T> void accept(MetaKey<T> paramMetaKey, T paramT);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\hypixel\hytale\server\core\meta\IMetaRegistry$MetaEntryConsumer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */