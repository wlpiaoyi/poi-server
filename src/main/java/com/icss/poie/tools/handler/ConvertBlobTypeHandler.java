package com.icss.poie.tools.handler;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/9/22 16:25
 * {@code @version:}:       1.0
 */
public class ConvertBlobTypeHandler extends JacksonTypeHandler {

    private static final String DEFAULT_CHARSET = "utf-8";

    public ConvertBlobTypeHandler(Class<?> type) {
        super(type);
    }

    @Override
    protected Object parse(String json) {
        return super.parse(json);
    }
    public Object getNullableResult(ResultSet rs, String columnName) throws SQLException {
      try{
          InputStream is = rs.getBinaryStream(columnName);
          if(columnName.equals("rc_value")){
              System.out.println();
          }
          ByteArrayOutputStream buffer = new ByteArrayOutputStream();
          int nRead;
          byte[] data = new byte[4];
          while ((nRead = is.read(data, 0, data.length)) != -1) {
              buffer.write(data, 0, nRead);
          }
          final String json = new String(buffer.toByteArray(), "utf-8");
          return StringUtils.isBlank(json) ? null : parse(json);
      }catch (Exception e){

      }
      return "";
    }
}
