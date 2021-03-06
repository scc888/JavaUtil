package cn.sric.util;


import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 创建线程池的帮助类
 * 可以使线程池状态为暂停，继续运行
 *
 * @author sunchuanchuan
 * @version V1.0
 * @date 2020/12/22
 * @package cn.sric.config
 * @description
 **/
public class ThreadPoolExecutorUtil extends ThreadPoolExecutor {


    /**
     * @param corePoolSize    核心线程池数量  不会被回收
     * @param maximumPoolSize 最大线程数量  当核心线程到达最大值，并且队列中也到最大值，就会创建新的线程
     * @param keepAliveTime   设置线程最多保持多长时间默认 0  超过这个时间会回收不是核心的线程   也就是  maximumPoolSize - corePoolSize
     * @param unit            keepAliveTime 时间类型
     * @param workQueue       创建队列的方式 （当新的任务来临后并且核心线程满了之后会创建队列来进行排队等待
     * @param handler         拒绝策略  当队列达到最大值并且   最大线程池（ maximumPoolSize）也是最大值，这时新来的任务没有地方储存，则会使用拒绝策略
     */
    public ThreadPoolExecutorUtil(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }


    /**
     * @param threadFactory 创建线程的方式
     *                      这个构造方法对比上面的多了一个 threadFactory 参数
     *                      表示线程的创建工厂，此参数一般用的比较少，我们通常在创建线程池时不指定此参数，它会使用默认的线程创建工厂的方法来创建线程，
     *                      <p>
     *                      其余参数看上面的构造方法注释
     */

    public ThreadPoolExecutorUtil(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue,
                                  ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }


    boolean isPause = false;
    ReentrantLock lock = new ReentrantLock();
    Condition condition = lock.newCondition();


    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        lock.lock();
        try {
            while (isPause) {
                long ms = 10L;
                System.out.printf("pausing, %s\n", t.getName());
                Thread.sleep(ms);
                condition.await();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
    }

    /**
     * 暂停
     */
    public void pause() {
        lock.lock();
        try {
            System.out.println("exe pause");
            isPause = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 继续执行
     */
    public void resume() {
        lock.lock();
        try {
            System.out.println("un pause");
            isPause = false;
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        /**
         * new LinkedBlockingQueue<>(100) 队列 括号填写参数为队列的最大值，不填写参数为无界队列
         *  new ThreadPoolExecutor.CallerRunsPolicy() 拒绝策略，如果添加到线程池失败，那么主线程会自己去执行该任务，不会等待线程池中的线程去执行。
         */
        ThreadPoolExecutorUtil threadPoolExecutorUtil = new ThreadPoolExecutorUtil(5, 10,
                0, TimeUnit.SECONDS, new LinkedBlockingQueue<>(100), new ThreadPoolExecutor.CallerRunsPolicy());

        for (int i = 1; i <= 150; i++) {

            System.out.println("i:" + i + "---->>>当前活动的线程数量:" + threadPoolExecutorUtil.getActiveCount() + "队列中的数量为:" + threadPoolExecutorUtil.getQueue().size() +
                    "-----》》》》最大线程数" + threadPoolExecutorUtil.getPoolSize());

            int finalI = i;
            threadPoolExecutorUtil.execute(() -> {
                try {
                    System.err.println(Thread.currentThread().getName() + "---->>>i:" + finalI);
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });


            if (i == 100) {
                System.out.println("100当前活动的线程数量:" + threadPoolExecutorUtil.getActiveCount() + "队列中的数量为:" + threadPoolExecutorUtil.getQueue().size() +
                        "最大线程数" + threadPoolExecutorUtil.getPoolSize());
            } else if (i == 110) {
                System.out.println("110当前活动的线程数量:" + threadPoolExecutorUtil.getActiveCount() + "队列中的数量为:" + threadPoolExecutorUtil.getQueue().size() +
                        "最大线程数" + threadPoolExecutorUtil.getPoolSize());
            } else if (i == 120) {
                System.out.println("120当前活动的线程数量:" + threadPoolExecutorUtil.getActiveCount() + "队列中的数量为:" + threadPoolExecutorUtil.getQueue().size() +
                        "最大线程数" + threadPoolExecutorUtil.getPoolSize());
            } else if (i == 130) {
                System.out.println("120当前活动的线程数量:" + threadPoolExecutorUtil.getActiveCount() + "队列中的数量为:" + threadPoolExecutorUtil.getQueue().size() +
                        "最大线程数" + threadPoolExecutorUtil.getPoolSize());
            } else if (i == 150) {
                System.out.println("150当前活动的线程数量:" + threadPoolExecutorUtil.getActiveCount() + "队列中的数量为:" + threadPoolExecutorUtil.getQueue().size() +
                        "最大线程数" + threadPoolExecutorUtil.getPoolSize());
            }
        }
        TimeUnit.SECONDS.sleep(5);
        System.out.println("当前活动的线程数量:" + threadPoolExecutorUtil.getActiveCount() + "队列中的数量为:" + threadPoolExecutorUtil.getQueue().size() +
                "最大线程数" + threadPoolExecutorUtil.getPoolSize());
    }
}
