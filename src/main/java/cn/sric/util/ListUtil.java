package cn.sric.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sric
 * @version V1.0
 * @date 2021/1/20
 * @package cn.sric.util
 * @description List的一些帮助类
 **/
public class ListUtil {

    /**
     * 封装一下List集合的分页
     *
     * @param list    本来的数据
     * @param current 当前页
     * @param size    页大小
     * @param <T>     元素类型
     * @return 分页后的数据
     */
    public static <T> List<T> pageList(List<T> list, int current, int size) {
        if (current < 1) {
            current = 1;
        }
        if (size < 1) {
            size = 10;
        }

        //从第几条开始查询
        int skip = (current - 1) * size;
        //从哪个索引开始查询
        int fromIndex = skip > list.size() ? list.size() / size * size : skip;

        //结束的索引位置      判断剩余的数量是否大于页大小，如果不大于就查询到最后一条数据
        int toIndex = (list.size() - skip) > size ? skip + size : list.size();
        list = list.subList(fromIndex, toIndex);
        return list;
    }

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 18; i++) {
            list.add(i);
        }
        pageList(list, 2, 9).forEach(System.out::println);
    }
}
