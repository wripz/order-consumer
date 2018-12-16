package cn.vpclub.order.consumer.utils;

import cn.vpclub.moses.utils.common.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class AverageAssignUtil {

    /**
     * 将一个list均分成n个list,主要通过偏移量来实现的
     *
     * @param source
     * @return
     */
    public static  List<List> averageAssign(List source) {

        //Excel的插入数据库的长度
         Integer EXCEL_LONG = 2000;

        int n = source.size();
        int a;
        if (n % EXCEL_LONG == 0) {
            a = n / EXCEL_LONG;
        } else {
            a = n / EXCEL_LONG + 1;
        }
        List<List> result = new ArrayList<>();
        for (int i = 1; i <= a; i++) {
            List list;
            if (i == a) {
                list = new ArrayList<>(source.subList((a - 1) * EXCEL_LONG, source.size()));
            } else {
                list = new ArrayList<>(source.subList((i - 1) * EXCEL_LONG, i * EXCEL_LONG));
            }

            if (!CollectionUtils.isEmpty(list)) {
                log.info("长度：" + list.size() + "第"+ i +"个：" + JsonUtil.objectToJson((list.get(0))));
            }
            result.add(list);
        }
        return result;
    }

    /**
     * 将一个list均分成n个list,主要通过偏移量来实现的
     *
     * @param source
     * @return
     */
    public static  List<List> averageAssign60000(List source) {

        //Excel的插入数据库的长度
        Integer EXCEL_LONG = 60000;

        int n = source.size();
        int a;
        if (n % EXCEL_LONG == 0) {
            a = n / EXCEL_LONG;
        } else {
            a = n / EXCEL_LONG + 1;
        }
        List<List> result = new ArrayList<>();
        for (int i = 1; i <= a; i++) {
            List list;
            if (i == a) {
                list = new ArrayList<>(source.subList((a - 1) * EXCEL_LONG, source.size()));
            } else {
                list = new ArrayList<>(source.subList((i - 1) * EXCEL_LONG, i * EXCEL_LONG));
            }

            if (!CollectionUtils.isEmpty(list)) {
                log.info("长度：" + list.size() + "第"+ i +"个：" + JsonUtil.objectToJson((list.get(0))));
            }
            result.add(list);
        }
        return result;
    }

}
