package com.icss.poie.biz.form.domain.vo;

import com.icss.poie.biz.form.domain.entity.Form;
import com.icss.poie.biz.form.domain.entity.FormHead;
import com.icss.poie.biz.form.domain.entity.FormHeadHr;
import com.icss.poie.biz.form.domain.entity.FormHeadRela;
import com.icss.poie.biz.form.domain.model.FormPosition;
import com.icss.poie.framework.common.exception.BusinessException;
import com.icss.poie.framework.common.tools.ValueUtils;
import com.icss.poie.domain.entity.BaseEntity;
import com.icss.poie.biz.excel.domain.entity.CellData;
import com.icss.poie.tools.excel.Scope;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.lang.ref.WeakReference;
import java.util.*;

/**
 * {@code @author:}         wlpiaoyi
 * {@code @description:}    TODO
 * {@code @date:}           2023/11/23 15:31
 * {@code @version:}:       1.0
 */
@Data
public class FormVo extends Form {

    private List<FormHeadContext> formHeadContexts;
    private List<FormHeadVo> formHeads;
    private List<FormHeadRela> formHeadRelas;
    private List<FormHeadHr> formHeadHrs;


    public void onlyMainDataForLocation(){
        if(ValueUtils.isNotBlank(this.getFormHeads())){
            for (FormHeadVo formHeadVo : this.getFormHeads()){
                formHeadVo.onlyMainDataForLocation();
            }
        }
        if(ValueUtils.isNotBlank(this.getFormHeadRelas())){
            for (FormHeadRela formHeadRela : this.getFormHeadRelas()){
                formHeadRela.onlyMainDataForLocation();
            }
        }
    }

    public static void checkData(Form entity){
        if(ValueUtils.isBlank(entity.getName())){
            throw new BusinessException("表单名称不能为空");
        }
        if(entity.getType() == 1){
            if(ValueUtils.isBlank(entity.getExcelId())){
                throw new BusinessException("ExcelId不能为空");
            }
            if(ValueUtils.isBlank(entity.getSheetId())){
                throw new BusinessException("SheetId不能为空");
            }
            if(ValueUtils.isBlank(entity.getTab())){
                throw new BusinessException("标签不能为空");
            }
        }
        Set<String> tableCodeSet = new HashSet<>();
        if(ValueUtils.isBlank(entity.getTableName())){
            String tableCode = BaseEntity.createPropertyCode(entity.getName());
            int i = 10;
            while (i-- > 0 && tableCodeSet.contains(tableCode)){
                tableCode = BaseEntity.createPropertyCode(entity.getName());
            }
            if(tableCodeSet.contains(tableCode)){
                throw new BusinessException("动态生成字段名称超时");
            }
            entity.setTableName(tableCode);
            tableCodeSet.add(tableCode);
        }
    }

    public void clearDb(){
        super.clearDb();
        this.setSheetId(null);
        this.setExcelId(null);
        if(ValueUtils.isNotBlank(this.formHeads)){
            for (FormHead formHead : this.formHeads){
                formHead.clearDb();
            }
        }
        if(ValueUtils.isNotBlank(this.formHeadRelas)){
            for (FormHeadRela formHead : this.formHeadRelas){
                formHead.clearDb();
            }
        }
        if(ValueUtils.isNotBlank(this.formHeadHrs)){
            for (FormHeadHr formHeadHr : this.formHeadHrs){
                formHeadHr.clearDb();
            }
        }
    }

    public boolean checkFormPosition(){
        if(this.getFormPosition() == null){
            return false;
        }
        if(this.getFormPosition().getC() < 0){
            return false;
        }
        if(this.getFormPosition().getR() < 0){
            return false;
        }
        return true;
    }

    @Override
    public void synProperties() {
        super.synProperties();
        int hd = 0;
        int vd = 0;
        int ohd = 0;
        int ovd = 0;
        if(ValueUtils.isNotBlank(this.getFormHeadContexts())){
            FormPosition formPosition = new FormPosition();
            formPosition.setC(-1);
            formPosition.setR(-1);
            formPosition.setCs(-1);
            formPosition.setRs(-1);
            this.setFormPosition(formPosition);
            List<FormHeadVo> temps = new ArrayList<>();
            for (FormHeadContext formHeadContext : this.getFormHeadContexts()){
                if(formHeadContext.getFormPosition().getC() < 0){
                    continue;
                }
                if(formHeadContext.getFormPosition().getR() < 0){
                    continue;
                }
                formPosition.checkRc(formHeadContext);
                if(ValueUtils.isBlank(formHeadContext.getFormHeads())){
                    continue;
                }
                for(FormHeadVo formHead : formHeadContext.getFormHeads()){
                    formHead.setDirection(formHeadContext.getDirection());
                }
                switch (formHeadContext.getDirection()){
                    case 1:{
                        List<FormHeadVo> hFormHeads = hFormHeads(formHeadContext.getFormHeads());
                        temps.addAll(hFormHeads);
                        hd ++;
                    }
                    break;
                    case 2:{
                        List<FormHeadVo> vFormHeads = vFormHeads(formHeadContext.getFormHeads());
                        for (FormHeadVo formHead : vFormHeads){
                            formHead.setDataType(null);
                        }
                        temps.addAll(vFormHeads);
                        vd ++;
                    }
                    break;
                    case 3:{

                    }
                    break;
                    case 4:{
                        List<FormHeadVo> hFormHeads = hFormHeads(formHeadContext.getFormHeads());
                        for (FormHeadVo formHead : hFormHeads){
                            formHead.setDirection((byte)4);
                        }
                        temps.addAll(hFormHeads);
                        ohd ++;
                    }
                    break;
                    case 5:{
                        List<FormHeadVo> vFormHeads = vFormHeads(formHeadContext.getFormHeads());
                        for (FormHeadVo formHead : vFormHeads){
                            formHead.setDirection((byte)5);
                        }
                        temps.addAll(vFormHeads);
                        ovd ++;
                    }
                    break;
                    default:
                        break;
                }
            }
            this.setFormHeads(temps);
        }
        else if(ValueUtils.isNotBlank(this.formHeads)){
            for (FormHead formHead : this.formHeads){
                switch (formHead.getDirection()){
                    case 1:{
                        hd ++;
                    }
                    break;
                    case 2:{
                        vd ++;
                    }
                    break;
                    case 3:{

                    }
                    break;
                    case 4:{
                        ohd ++;
                    }
                    break;
                    case 5:{
                        ovd ++;
                    }
                    break;
                    default:
                        break;
                }
            }
        }
        if(this.getDirection() == null || this.getDirection() <= 0){
            if(hd > 0 && vd == 0){
                this.setDirection((byte) 1);
            }else if(hd == 0 && vd > 0){
                this.setDirection((byte) 2);
            }else if(hd > 0 && vd > 0){
                this.setDirection((byte) 3);
            }else if(ohd > 0 || ovd > 0){
                this.setDirection((byte) 4);
            }
        }
    }


    public static <T extends FormHead> List<T> pushHFormHeads(List<T> formHeads){
        List<T> temps = new ArrayList<>();
        for (T formHead : formHeads){
            if(formHead.getIsLeaf().byteValue() != 1){
                continue;
            }
            if(formHead.getDirection().byteValue() == 1){
                temps.add(formHead);
            }
        }
        return temps;
    }
    public static <T extends FormHead> List<T> pushVFormHeads(List<T> formHeads){
        List<T> temps = new ArrayList<>();
        for (T formHead : formHeads){
            if(formHead.getIsLeaf().byteValue() != 1){
                continue;
            }
            if(formHead.getDirection().byteValue() == 2){
                temps.add(formHead);
            }
        }
        return temps;
    }


    /**
     * 获取二维表头
     * @param entityList
     * @return
     */
    private static Map<String, List<FormHeadVo>> W2DFormHeads(List<FormHeadVo> entityList){

        List<FormHeadVo> hFormHeads = hFormHeads(entityList);
        for (FormHeadVo formHead : hFormHeads){
            formHead.setDirection((byte) 1);
        }
        List<FormHeadVo> vFormHeads = vFormHeads(entityList);
        for (FormHead formHead : vFormHeads){
            formHead.setDirection((byte) 2);
        }
        List<FormHead> jFormHeads = new ArrayList<>();
        for (FormHead vFormHead : vFormHeads){
            for (FormHead hFormHead : hFormHeads){
                if(vFormHead == hFormHead){
                    FormHead jFormHead = hFormHead;
                    jFormHeads.add(jFormHead);
                    jFormHead.setDirection((byte)3);
                }
            }
        }
        vFormHeads.removeAll(jFormHeads);
        hFormHeads.removeAll(jFormHeads);

        return new HashMap(){{
            put("hFormHeads", hFormHeads);
            put("vFormHeads", vFormHeads);
            put("jFormHeads", jFormHeads);
        }};
    }

    /**
     * 获取纵向表头
     * @param entityList
     * @return
     */
    private static List<FormHeadVo> vFormHeads(List<FormHeadVo> entityList){
        entityList.sort((o1, o2) -> {
            if(o1.getLocation().getC() < o2.getLocation().getC()){
                return -1;
            }
            if(o1.getLocation().getC() > o2.getLocation().getC()){
                return 1;
            }
            if(o1.getLocation().getR() < o2.getLocation().getR()){
                return -1;
            }
            if(o1.getLocation().getR() > o2.getLocation().getR()){
                return 1;
            }
            return 0;
        });
        List<List<FormHeadVo>> fhs2 = new ArrayList<>();
        List<FormHeadVo> res = new ArrayList<>();
        int sort = 1;
        int c_index = -1;
        List<FormHeadVo> fhs1 = null;
        Map<String, Integer> fhMcMap = new HashMap<>();
        for (FormHeadVo formHead : entityList){
            if(formHead.getLocation() != null && formHead.getLocation().getV() != null && formHead.getLocation().getV().getMc() != null){
                /** 起始列 **/
                int c = formHead.getLocation().getV().getMc().getC();
                /** 合并列数量 **/
                int cs = formHead.getLocation().getV().getMc().getCs();
                /** 起始行 **/
                int r = formHead.getLocation().getV().getMc().getR();
                /** 合并行数量 **/
                int rs = formHead.getLocation().getV().getMc().getRs();
                for (int i = c; i < c + cs; i ++){
                    for (int j = r; j < r + rs; j ++){
                        Integer cl = fhMcMap.get("c_" + i);
                        if(cl == null){
                            cl = 0;
                        }
                        cl ++;
                        Integer rl = fhMcMap.get("r_" + j);
                        if(rl == null){
                            rl = 0;
                        }
                        rl ++;

                        if(i == c  && j == r){
                        }else{
                            fhMcMap.put("c_" + i, cl);
                            fhMcMap.put("r_" + j, rl);
                        }
                    }
                }
            }
            if(c_index  != formHead.getLocation().getC()){
                c_index = formHead.getLocation().getC();
                if(fhs1 != null){
                    Integer ml = fhMcMap.get("c_" + c_index);
                    if(ml == null){
                        ml = 0;
                    }
                    if(!fhs2.isEmpty() && fhs2.get(fhs2.size() - 1).size() > fhs1.size() + ml){
                        fhs1.clear();
                        break;
                    }
                    fhs2.add(fhs1);
                    res.addAll(fhs1);
                }
                fhs1 = new ArrayList<>();
            }
            fhs1.add(formHead);
        }
        if(ValueUtils.isNotBlank(fhs1)){
            fhs2.add(fhs1);
            res.addAll(fhs1);

        }
        vFormHeadTree(res);
        for (FormHead formHead : res){
            formHead.setDirection((byte) 2);
        }
        return res;
    }
    private static List<FormHeadVo> vFormHeadTree(Collection<FormHeadVo> formHeads){
        List<FormHeadVo> trees = new ArrayList<>();
        for (FormHeadVo formHead : formHeads){
            formHead.setIsLeaf((byte) 1);
            FormHeadVo pFh = vFormHeadParent(trees, formHead);
            if(pFh == null){
                trees.add(formHead);
            }else{
                pFh.setIsLeaf((byte) 0);
                if(pFh.getChildrenHeads() == null){
                    pFh.setChildrenHeads(new ArrayList<>());
                }
                if(!pFh.getChildrenHeads().contains(formHead)){
                    pFh.getChildrenHeads().add(formHead);
                }
                formHead.setParentHead(new WeakReference(pFh));
            }
            formHead.synDeep();
        }
        return trees;
    }

    /**
     * 获取横向表头
     * @param entityList
     * @return
     */
    private static List<FormHeadVo> hFormHeads(List<FormHeadVo> entityList){
        entityList.sort((o1, o2) -> {
            if(o1.getLocation().getR() < o2.getLocation().getR()){
                return -1;
            }
            if(o1.getLocation().getR() > o2.getLocation().getR()){
                return 1;
            }
            if(o1.getLocation().getC() < o2.getLocation().getC()){
                return -1;
            }
            if(o1.getLocation().getC() > o2.getLocation().getC()){
                return 1;
            }
            return 0;
        });
        List<List<FormHeadVo>> fhs2 = new ArrayList<>();
        List<FormHeadVo> res = new ArrayList<>();
        int sort = 1;
        int r_index = -1;
        List<FormHeadVo> fhs1 = null;
        Map<String, Integer> fhMcMap = new HashMap<>();
        for (FormHeadVo formHead : entityList){
            if(formHead.getLocation() != null && formHead.getLocation().getV() != null && formHead.getLocation().getV().getMc() != null){
                /** 起始列 **/
                int c = formHead.getLocation().getV().getMc().getC();
                /** 合并列数量 **/
                int cs = formHead.getLocation().getV().getMc().getCs();
                /** 起始行 **/
                int r = formHead.getLocation().getV().getMc().getR();
                /** 合并行数量 **/
                int rs = formHead.getLocation().getV().getMc().getRs();
                for (int i = c; i < c + cs; i ++){
                    for (int j = r; j < r + rs; j ++){
                        Integer cl = fhMcMap.get("c_" + i);
                        if(cl == null){
                            cl = 0;
                        }
                        cl ++;
                        Integer rl = fhMcMap.get("r_" + j);
                        if(rl == null){
                            rl = 0;
                        }
                        rl ++;

                        if(i == c  && j == r){
                        }else{
                            fhMcMap.put("c_" + i, cl);
                            fhMcMap.put("r_" + j, rl);
                        }
                    }
                }
            }
            if(r_index != formHead.getLocation().getR()){
                r_index = formHead.getLocation().getR();
                if(fhs1 != null){
                    Integer ml = fhMcMap.get("r_" + r_index);
                    if(ml == null){
                        ml = 0;
                    }
                    if(!fhs2.isEmpty() && fhs2.get(fhs2.size() - 1).size() > fhs1.size() + ml){
                        fhs1.clear();
                        break;
                    }
                    fhs2.add(fhs1);
                    res.addAll(fhs1);
                }
                fhs1 = new ArrayList<>();
            }
            fhs1.add(formHead);
        }
        if(ValueUtils.isNotBlank(fhs1)){
            fhs2.add(fhs1);
            res.addAll(fhs1);

        }
        for (FormHead formHead : res){
            formHead.setDirection((byte) 1);
        }
        hFormHeadTree(res);
        return res;
    }
    private static List<FormHeadVo> hFormHeadTree(Collection<FormHeadVo> formHeads){
        List<FormHeadVo> trees = new ArrayList<>();
        for (FormHeadVo formHead : formHeads){
            formHead.setIsLeaf((byte) 1);
            FormHeadVo pFh = hFormHeadParent(trees, formHead);
            if(pFh == null){
                trees.add(formHead);
                hFormHeadParent(trees, formHead);
            }else{
                pFh.setIsLeaf((byte) 0);
                if(pFh.getChildrenHeads() == null){
                    pFh.setChildrenHeads(new ArrayList<>());
                }
                if(!pFh.getChildrenHeads().contains(formHead)){
                    pFh.getChildrenHeads().add(formHead);
                }
                formHead.setParentHead(new WeakReference(pFh));
            }
            formHead.synDeep();
        }
        return trees;
    }

    private static FormHeadVo hFormHeadParent(Collection<FormHeadVo> trees, FormHeadVo formHead){
        if(ValueUtils.isBlank(trees)){
            return null;
        }
        for (FormHeadVo fh : trees){
            if(fh.getLocation().getV().getMc() == null){
                if(fh.getLocation().getR() + 1 == formHead.getLocation().getR()
                        && fh.getLocation().getC() == formHead.getLocation().getC()){
                    return fh;
                }
            }else{
                if(fh.getLocation().getV().getMc().getR() + 1 ==  formHead.getLocation().getR()
                        && fh.getLocation().getV().getMc().getC() <= formHead.getLocation().getC()
                        && fh.getLocation().getV().getMc().getC() + fh.getLocation().getV().getMc().getCs() > formHead.getLocation().getC()){
                    return fh;
                }
            }
            if(ValueUtils.isNotBlank(fh.getChildrenHeads())){
                FormHeadVo res = hFormHeadParent(fh.getChildrenHeads(), formHead);
                if(res != null){
                    return res;
                }
            }
        }
        return null;
    }
    private static FormHeadVo vFormHeadParent(Collection<FormHeadVo> trees, FormHeadVo formHead){
        if(ValueUtils.isBlank(trees)){
            return null;
        }
        FormHeadVo tfh = null;
        for (FormHeadVo fh : trees){
            if(fh.getLocation().getV().getMc() == null){
                if(fh.getLocation().getC() + 1 == formHead.getLocation().getC()
                        && fh.getLocation().getR() == formHead.getLocation().getR()){
                    return fh;
                }
            }else{
                Scope mc = fh.getLocation().getV().getMc();
                if(mc.getC() >=  formHead.getLocation().getC()){
                    continue;
                }
                if(mc.getR() <= formHead.getLocation().getR()
                        && mc.getR() + mc.getRs() > formHead.getLocation().getR()){
                    tfh = fh;
                    continue;
                }
            }
            if(ValueUtils.isNotBlank(fh.getChildrenHeads())){
                FormHeadVo res = vFormHeadParent(fh.getChildrenHeads(), formHead);
                if(res != null){
                    return res;
                }
            }
        }
        return tfh;
    }

    private Byte headDirectionForDataType(){
        if(ValueUtils.isBlank(this.getFormHeads())){
            return null;
        }
        byte direction = 0;
        for (FormHead formHead : this.getFormHeads()){
            if(formHead.getDataType() == null){
                continue;
            }
            if(direction == 0){
                direction = formHead.getDirection();
            }
            if(formHead.getDirection().byteValue() != direction){
                throw new BusinessException("表单头数据类型不能同时存在两个方向");
            }
        }
        if(direction == 0){
            throw new BusinessException("没有找到表单头方向");
        }
        return direction;
    }
    public List<FormHead> formHeadsForDataType(){
        List<FormHead> formHeads = new ArrayList<>();
        if(this.getDirection().byteValue() == 4){
            for (FormHead fh : this.getFormHeads()){
                if(fh.getDataType() == null){
                    continue;
                }
                if(fh.getIsLeaf() == null){
                    continue;
                }
                if(fh.getIsLeaf().byteValue() != 1){
                    continue;
                }
                formHeads.add(fh);
            }
        }else{
            Byte direction = this.headDirectionForDataType();
            if(direction == null){
                return null;
            }
            for (FormHead fh : this.getFormHeads()){
                if(fh.getDirection().byteValue() != direction.byteValue()){
                    continue;
                }
                if(fh.getIsLeaf().byteValue() != 1){
                    continue;
                }
                formHeads.add(fh);
            }
        }
        return formHeads;
    }
    public static FormHead formHeadByCellData(List<FormHead> formHeads, CellData cellData){
        for (FormHead formHead : formHeads){
            if(formHead.getDirection() == null){
                continue;
            }
            if(formHead.getDirection().byteValue() == 1){
                if(formHead.getLocation().getC() == cellData.getC()){
                    if(cellData.getR() <= formHead.getLocation().getR()){
                        return null;
                    }
                    return formHead;
                }
            }
            if(formHead.getDirection().byteValue() == 2){
                if(formHead.getLocation().getR() == cellData.getR()){
                    if(cellData.getC() <= formHead.getLocation().getC()){
                        return null;
                    }
                    return formHead;
                }
            }
        }
        return null;
    }

    @Data
    public static class FormHeadContext {
        @Schema(description = "方向 1:横向 2:纵向")
        @NotNull(message = "方向不能为空")
        private Byte direction;
        @Schema(description = "表头位置")
        @NotNull(message = "表头位置不能为空")
        private FormPosition formPosition;
        @Schema(description = "表头数据项")
        @NotNull(message = "表头数据项不能为空")
        private List<FormHeadVo> formHeads;

    }

}
