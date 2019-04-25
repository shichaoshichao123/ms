package com.sc.ms.juc;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author: shichao
 * @date: 2019/4/24
 * @description: 读写锁demo
 */
public class ReadWriteLockDemo {


    public static void main(String[] args) {

        NoteBooks noteBooks = new NoteBooks();
        for (int i = 0; i < 6; i++) {
            final int tempInt = i;
            new Thread(() -> {
                noteBooks.read(tempInt + "");
            }, "R:" + String.valueOf(i)).start();
        }
        for (int i = 0; i < 6; i++) {
            final int tempInt = i;
            new Thread(() -> {
                noteBooks.write(tempInt + "", tempInt);
            }, "W:" + String.valueOf(i)).start();
        }
        for (int i = 0; i < 6; i++) {
            final int tempInt = i;
            new Thread(() -> {
                noteBooks.read(tempInt + "");
            }, "R:" + String.valueOf(i)).start();
        }
    }
}

class NoteBooks {
    private volatile Map<String, Object> map = new HashMap<>(16);
    private ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
    private ReentrantReadWriteLock.ReadLock readLock = reentrantReadWriteLock.readLock();
    private ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();

    public Object read(String key) {
        readLock.lock();
        try {
            System.out.println("线程 " + Thread.currentThread().getName() + "开始读取...");
            Object result = map.get(key);
            System.out.println("线程 " + Thread.currentThread().getName() + "读取完成...结果:" + result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            readLock.unlock();
        }
    }

    public void write(String key, Object value) {
        writeLock.lock();
        try {
            System.out.println("线程 " + Thread.currentThread().getName() + "开始写入...");
            map.put(key, value);
            TimeUnit.MILLISECONDS.sleep(300);
            System.out.println("线程 " + Thread.currentThread().getName() + "写入完成...");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            writeLock.unlock();
        }
    }

    public void clear() {
        writeLock.lock();
        try {
            System.out.println("线程 " + Thread.currentThread().getName() + "清空内容...");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            writeLock.unlock();
        }
    }
}
