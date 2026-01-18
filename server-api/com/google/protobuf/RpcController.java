package com.google.protobuf;

public interface RpcController {
  void reset();
  
  boolean failed();
  
  String errorText();
  
  void startCancel();
  
  void setFailed(String paramString);
  
  boolean isCanceled();
  
  void notifyOnCancel(RpcCallback<Object> paramRpcCallback);
}


/* Location:              D:\Workspace\Hytale\Modding\TestMod\app\libs\HytaleServer.jar!\com\google\protobuf\RpcController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */