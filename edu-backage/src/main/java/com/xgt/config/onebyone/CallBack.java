package com.xgt.config.onebyone;

public interface CallBack<T> {
  
    /**  
     * 调用  
     * @return 结果  
     */  
    T invoke();  
      
}  