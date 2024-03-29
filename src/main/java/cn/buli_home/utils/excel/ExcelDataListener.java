package cn.buli_home.utils.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.util.ListUtils;

import java.util.List;
import java.util.Objects;

public class ExcelDataListener<T> extends AnalysisEventListener<T> {

    // 有个很重要的点 DemoDataListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
    /**
     * 每隔 3000 条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 3000;
    /**
     * 缓存的数据
     */
    private List<T> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    private ExcelDataCallback<T> callback;


    public ExcelDataListener() {
    }

    public ExcelDataListener(ExcelDataCallback<T> callback) {
        this.callback = callback;
    }

    /**
     * 这个每一条数据解析都会来调用
     *
     * @param data    one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param context
     */
    @Override
    public void invoke(T data, AnalysisContext context) {
        cachedDataList.add(data);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (cachedDataList.size() >= BATCH_COUNT) {
            // 存储完成清理 list
            if (Objects.nonNull(callback)) {
                callback.invoke(cachedDataList);
            }

            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        if (Objects.nonNull(callback)) {
            callback.invoke(cachedDataList);
            callback.finish();
        }

        cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
    }

}


