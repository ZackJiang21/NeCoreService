package org.iiai.ne.util;

import java.util.List;

public class BatchOperUtil {
    public interface Operator<T> {
        void execute(List<T> data);
    }

    public interface DataFetcher<T> {
        List<T> fetch(int start, int limit);
    }

    public static <T> void executeWithFetcher(int batchSize, DataFetcher dataFetcher, Operator operator) {
        for (int start = 0; ; start += batchSize) {
            List<T> data = dataFetcher.fetch(start, batchSize);
            operator.execute(data);
            if (data.size() < batchSize) {
                break;
            }
        }
    }

}
