package com.filling.module.poi.tools.utils.excel;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/9/21 14:38
 * {@code @version:}:       1.0
 */
public interface ICellValue {

    /**
     * 是否为空
     * @return
     */
    boolean isNotEmpty();

    int getType();

    void setType(int type);

    String getV();
    void setV(String v);

    String getM();
    void setM(String m);

    String getF();

    void setF(String f);
    String getBg();

    void setBg(String bg);

    String getFf();

    void setFf(String ff);

    String getFc();

    void setFc(String fc);

    int getIt();

    void setIt(int it);

    int getBl();

    void setBl(int bl);

    short getFs();

    void setFs(short fs);
    byte getUn();

    void setUn(byte un);

    int getVt();

    void setVt(int vt);

    int getHt();

    void setHt(int ht);
}
