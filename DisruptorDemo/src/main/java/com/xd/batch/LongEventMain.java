package com.xd.batch;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;

import java.nio.ByteBuffer;

public class LongEventMain {
    public static void main(String[] args) throws InterruptedException {
        int bufferSize = 1024;
        LongEventFactory factory = new LongEventFactory();
        Disruptor<LongEvent> disruptor = new Disruptor<>(factory,bufferSize, DaemonThreadFactory.INSTANCE);

        disruptor.handleEventsWith(new LongEventHandler());

        disruptor.start();

        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

        LongEventProducer longEventProducer = new LongEventProducer(ringBuffer);
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);

        for (long i = 0; true; i++) {
            byteBuffer.putLong(0,i);

            longEventProducer.onData(byteBuffer);

            Thread.sleep(1000);

        }
    }
}
