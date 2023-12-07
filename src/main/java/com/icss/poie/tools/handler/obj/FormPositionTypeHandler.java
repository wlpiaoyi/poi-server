package com.icss.poie.tools.handler.obj;

import com.icss.poie.framework.common.tools.gson.GsonBuilder;
import com.icss.poie.biz.form.domain.model.FormPosition;
import lombok.SneakyThrows;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/10/11 15:13
 * {@code @version:}:       1.0
 */
public class FormPositionTypeHandler implements TypeHandler<FormPosition> {
    @Override
    public void setParameter(PreparedStatement ps, int i, FormPosition parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, GsonBuilder.gsonDefault().toJson(parameter));
    }

    @SneakyThrows
    private FormPosition parseObj(InputStream is){
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[4];
        while ((nRead = is.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        final String json = new String(buffer.toByteArray(), "utf-8");
        return GsonBuilder.gsonDefault().fromJson(json, FormPosition.class);
    }

    @Override
    public FormPosition getResult(ResultSet rs, String columnName) throws SQLException {
        InputStream is = rs.getBinaryStream(columnName);
        return parseObj(is);
    }

    @Override
    public FormPosition getResult(ResultSet rs, int columnIndex) throws SQLException {
        InputStream is = rs.getBinaryStream(columnIndex);
        return parseObj(is);
    }

    @Override
    public FormPosition getResult(CallableStatement cs, int columnIndex) throws SQLException {
        InputStream is = cs.getClob(columnIndex).getAsciiStream();
        return parseObj(is);
    }
}
