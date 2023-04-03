package com.example.progresstest.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class ReadWriterProblem {
    public static final Lock rwLock = new ReentrantLock();
    public static final Condition readerCondition = rwLock.newCondition();
    public static final Condition writerCondition = rwLock.newCondition();
    public static int readerCount = 0;
    public static int writerCount = 0;

    public String simulateReaderWriterProblem() {
        // 启动读者线程
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                try {
                    startReading();
                    Thread.sleep(1000);
                    endReading();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "Reader " + i).start();
        }

        // 启动写者线程
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                try {
                    startWriting();
                    Thread.sleep(1000);
                    endWriting();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "Writer " + i).start();
        }

        return "读者-写者问题模拟已开始";
    }

    public void startReading() throws InterruptedException {
        rwLock.lock();
        try {
            while (writerCount > 0) {
                System.out.println(Thread.currentThread().getName() + " 等待写者完成");
                readerCondition.await();
            }
            readerCount++;
            System.out.println("当前阅读者数量: " + readerCount);
        } finally {
            rwLock.unlock();
        }
        System.out.println(Thread.currentThread().getName() + " 开始阅读");
    }

    public void endReading() {
        rwLock.lock();
        try {
            readerCount--;
            System.out.println("当前阅读者数量: " + readerCount);
            if (readerCount == 0) {
                writerCondition.signal();
            }
        } finally {
            rwLock.unlock();
        }
        System.out.println(Thread.currentThread().getName() + " 结束阅读");
    }


    public void startWriting() throws InterruptedException {
        rwLock.lock();
        try {
            while (readerCount > 0 || writerCount > 0) {
                System.out.println(Thread.currentThread().getName() + " 等待读者和其他写者完成");
                writerCondition.await();
            }
            writerCount++;
            System.out.println("当前写者数量: " + writerCount);
        } finally {
            rwLock.unlock();
        }
        System.out.println(Thread.currentThread().getName() + " 开始写作");
    }

    public void endWriting() {
        rwLock.lock();
        try {
            writerCount--;
            System.out.println("当前写者数量: " + writerCount);
            writerCondition.signal();
            readerCondition.signalAll();
        } finally {
            rwLock.unlock();
        }
        System.out.println(Thread.currentThread().getName() + " 结束写作");
    }
}
