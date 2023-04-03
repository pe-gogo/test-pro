package com.example.progresstest.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

@Service
public class PhilosopherProblem {
    // 哲学家就餐问题相关
    public static final int PHILOSOPHERS_COUNT = 5;
    public static final Semaphore[] philosopherSemaphores = new Semaphore[PHILOSOPHERS_COUNT];

    public ArrayList list = new ArrayList();

    public ArrayList simulatePhilosopherProblem() {
        this.list = new ArrayList();
        for (int i = 0; i < PHILOSOPHERS_COUNT; i++) {
            philosopherSemaphores[i] = new Semaphore(1);
        }

        // 启动哲学家线程
        for (int i = 0; i < PHILOSOPHERS_COUNT; i++) {
            final int philosopherId = i;
            new Thread(() -> {
                try {
                    think(philosopherId);
                    takeForks(philosopherId);
                    eat(philosopherId);
                    putForks(philosopherId);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "Philosopher " + i).start();
        }

        return list;
    }

    public void think(int philosopherId) throws InterruptedException {
        list.add("哲学家 " + philosopherId + " 正在思考");
        System.out.println("哲学家 " + philosopherId + " 正在思考");
        Thread.sleep(1000);
    }

    public void takeForks(int philosopherId) throws InterruptedException {
        philosopherSemaphores[philosopherId].acquire();
        list.add("哲学家 " + philosopherId + " 拿起了左手的筷子");
        System.out.println("哲学家 " + philosopherId + " 拿起了左手的筷子");
        philosopherSemaphores[(philosopherId + 1) % PHILOSOPHERS_COUNT].acquire();
        list.add("哲学家 " + philosopherId + " 拿起了右手的筷子");
        System.out.println("哲学家 " + philosopherId + " 拿起了右手的筷子");
    }

    public void eat(int philosopherId) throws InterruptedException {
        list.add("哲学家 " + philosopherId + "正在吃饭");
        System.out.println("哲学家 " + philosopherId + "正在吃饭");
    }

    public void putForks(int philosopherId) {
        philosopherSemaphores[philosopherId].release();
        list.add("哲学家 " + philosopherId + " 放下了左手的筷子");
        System.out.println("哲学家 " + philosopherId + " 放下了左手的筷子");
        philosopherSemaphores[(philosopherId + 1) % PHILOSOPHERS_COUNT].release();
        list.add("哲学家 " + philosopherId + " 放下了右手的筷子");
        System.out.println("哲学家 " + philosopherId + " 放下了右手的筷子");
    }
}
