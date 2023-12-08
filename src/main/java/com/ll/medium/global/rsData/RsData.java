package com.ll.medium.global.rsData;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class RsData<T> {

    private final String ResultCode;
    private final String msg;
    private T data;

}
