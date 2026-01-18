package com.google.protobuf;

@CheckReturnValue
interface SchemaFactory {
  <T> Schema<T> createSchema(Class<T> paramClass);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\SchemaFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */