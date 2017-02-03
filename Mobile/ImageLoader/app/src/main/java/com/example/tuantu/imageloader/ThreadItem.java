package com.example.tuantu.imageloader;

/**
 * Created by TUAN TU on 8/10/2016.
 */
public class ThreadItem {
    public WorkerThread thread;
    public int id;
    public ThreadItem(WorkerThread thread, int id)
    {
        this.thread = thread;
        this.id = id;
    }
}
