package com.icss.poie.framework.common.tools.data;

import java.nio.charset.Charset;

public interface WriterInterface {

    int getSize();

    boolean hasNext(int index);

    interface WriterBytesInterface extends WriterInterface{

        byte[] writeBytes(int index);

    }

    interface WriterStringInterface extends WriterInterface{

        String writeString(int index, Charset charset);

    }

}
