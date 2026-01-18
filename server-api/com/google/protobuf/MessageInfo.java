package com.google.protobuf;

@CheckReturnValue
interface MessageInfo {
  ProtoSyntax getSyntax();
  
  boolean isMessageSetWireFormat();
  
  MessageLite getDefaultInstance();
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\MessageInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */