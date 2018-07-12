package com.xg.feign;

import feign.FeignException;
import feign.Response;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
import org.springframework.util.StreamUtils;

/**
 * ceshi
 *
 * @author guzhen
 * @date 2018/7/12
 */
public class ByteArrayOutputStreamDecoder implements Decoder {

  @Override
  public Object decode(Response response, Type type)
      throws IOException, DecodeException, FeignException {
    ByteArrayOutputStream outputStream= new ByteArrayOutputStream();
    StreamUtils.copy(response.body().asInputStream(),outputStream);
    return outputStream;
  }
}
