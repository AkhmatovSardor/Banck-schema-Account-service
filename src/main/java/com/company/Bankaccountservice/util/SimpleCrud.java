package com.company.Bankaccountservice.util;

import com.company.Bankaccountservice.dto.ResponseDto;

public interface SimpleCrud<K,V> {

    ResponseDto<V> create(V dto);
    ResponseDto<V> get(K id);
    ResponseDto<V> update(K id,V dto);
    ResponseDto<V> delete(K id);

}
