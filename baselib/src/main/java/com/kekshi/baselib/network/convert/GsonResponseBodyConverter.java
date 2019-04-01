package com.kekshi.baselib.network.convert;

import androidx.annotation.NonNull;
import com.google.gson.TypeAdapter;
import com.kekshi.baselib.data.BaseResponse;
import okhttp3.ResponseBody;
import retrofit2.Converter;

import java.io.IOException;


final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, Object> {

    private final TypeAdapter<T> adapter;

    GsonResponseBodyConverter(TypeAdapter<T> adapter) {
        this.adapter = adapter;
    }

    @Override
    public Object convert(@NonNull ResponseBody value) throws IOException {
        try {
            BaseResponse response = (BaseResponse) adapter.fromJson(value.charStream());
            int code = response.getCode();
            //TODO 根据实际业务进行判断，这里返回1成功，0失败，其他异常
            if (code == 1) {
                return response;
            } else if (code == 0) {
                throw new RuntimeException(response.getMessage());
            } else {
                throw new RuntimeException();
            }
        } finally {
            value.close();
        }
    }
}
