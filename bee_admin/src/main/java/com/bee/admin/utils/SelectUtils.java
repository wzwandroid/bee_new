package com.bee.admin.utils;


import com.bee.admin.vo.SelectVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mark
 * @date 2015-06-09
 */
public class SelectUtils {

    /**
     * @funtion 数组转换为下拉列表
     * @author wzw
     */
    public static List<SelectVo> convertArrayToList(String valArray[][]){
        List<SelectVo> list = new ArrayList<SelectVo>();

        // 如果传入参数包含值
        if (valArray != null) {
            for (int i = 0; i < valArray.length; i++) {
                SelectVo vo = new SelectVo();
                vo.setValue(valArray[i][0]);
                vo.setText(valArray[i][1]);
                list.add(vo);
            }
        }
        return list;
    }
}
