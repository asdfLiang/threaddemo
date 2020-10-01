package com.example.queue;

import java.util.LinkedList;

/**
 * @author liangzj9624@163.com
 * @date 2020/8/23 上午8:53
 */
public class EventQueue {

    private final int MAX;

    private static final int DEFAULT_MAX_EVENT = 10;

    static class Event {
    }

    private final LinkedList<Event> eventQueue = new LinkedList<>();

    public EventQueue() {
        this(DEFAULT_MAX_EVENT);
    }

    public EventQueue(int max) {
        MAX = max;
    }

    public void offer(Event event) {
        synchronized (eventQueue) {
            if (eventQueue.size() >= MAX) {
                try {
                    console("the queue is full");
                    eventQueue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            console(" the new event is submitted");
            eventQueue.addLast(event);
            eventQueue.notify();
        }
    }

    public Event take() {
        synchronized (eventQueue) {
            if (eventQueue.isEmpty()) {
                try {
                    console(" the queue is empty.");
                    eventQueue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            Event event = eventQueue.removeFirst();
            eventQueue.notify();
            console(" the event " + event + " is handled.");
            return event;
        }
    }

    private void console(String message) {
        System.out.println(Thread.currentThread().getName() + ":" + message);
    }
}
