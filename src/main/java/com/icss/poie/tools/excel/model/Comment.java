package com.icss.poie.tools.excel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.springframework.data.annotation.Transient;

@Data
public class Comment {

    private int dx1;
    private int dy1;
    private int dx2;
    private int dy2;
    private int col1;
    private int row1;
    private int col2;
    private int row2;

    private Point point;

    private String text;
    private String author;

    public Comment(){}

    public Comment(ClientAnchor clientAnchor, Point point, String text, String author){
        this.dx1 = clientAnchor.getDx1();
        this.dy1 = clientAnchor.getDy1();
        this.dx2 = clientAnchor.getDx2();
        this.dy2 = clientAnchor.getDy2();
        this.col1 = clientAnchor.getCol1();
        this.row1 = clientAnchor.getRow1();
        this.col2 = clientAnchor.getCol2();
        this.row2 = clientAnchor.getRow2();
        this.point = point;
        this.text = text;
        this.author = author;
    }

}
